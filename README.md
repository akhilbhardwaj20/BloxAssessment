# BloxAssessment
****   This repository contains the solutions to the problem statements 1,3 and 4  *******

Explanation to Problem 1 :

1. Mandatory: Elaborate what your internship or last company projects were?

a. What did the system do?

The projects I worked on at my last company involved building and maintaining a distributed microservices architecture designed for enhanced scalability and modularity. Key responsibilities included decomposing a monolithic application into smaller, independent services such as authentication, transaction processing, payments, and email notifications. I also designed and developed over five API endpoints that facilitated data presentation and workflow management for mortgage request functionalities for our client, We-Unit Bank. The system incorporated Redis for caching and session management, which significantly improved performance and reduced strain on primary data sources.

****************************************************************************************************************

b. What other systems have you seen in the wild like that?

Similar architectures are common in:

1) Banking Systems: Modern banking platforms use microservices to handle distinct tasks like customer onboarding and transactions.
2) E-commerce Platforms: Services like Amazon leverage microservices for managing authentication, payments, and order management.
3) Streaming Services: Platforms such as Netflix use microservices for video streaming, user preferences, and content recommendations.

**********************************************************************************************************
c. How do you approach the development problem?

1. Requirements Analysis: Understanding the project goals and constraints.
2. Design Phase: Creating architecture plans that follow REST principles for efficient service communication.
3. Tool Selection: Choosing technologies like Redis for enhanced performance.
4. Development: Writing modular code for microservices and APIs.
5. Testing and Debugging: Implementing unit and integration tests.
6. Deployment: Using CI/CD pipelines for deployment and monitoring.
7. Documentation: Keeping detailed documentation for future reference.

***********************************************************************************************************
d. What were interesting aspects where you copied code from Stack Overflow?
   
   I used code snippets from Stack Overflow for:

1. Redis Integration: Effective patterns for session management.
2. Exception Handling: Customizing HTTP responses with @ControllerAdvice.
3. Data Transfer: Serializing/deserializing JSON payloads.

***********************************************************************************************************

e. What did you learn from some very specific copy-paste?

1. Redis Connection: Avoiding connection leaks by managing Redis connections properly.
2. Error Handling: Implementing robust @ControllerAdvice for consistent API error feedback.
3. Pagination: Efficient pagination techniques for handling large data sets in APIs.
   These learnings refined my understanding of best practices and the importance of adapting solutions thoughtfully.

************************************************************************************************************

Explanation to Problem 3 :

Problem statement - Write a function to parse any valid json string into a corresponding Object, List, or Map object. You can use C,C++, Java, Scala, Kotlin, Python, Node. Note that the integer and floating point should be arbitrary precision.

Explanation:
1.ObjectMapper: We use Jackson's ObjectMapper to parse JSON strings into JsonNode objects.

2.JsonNode Traversal:

a) Objects (Map): If the node is an object, we traverse each field, recursively parsing its value.
b) Arrays (List): If the node is an array, we parse each element and add it to a list.
c) Numbers: For integers and floating-point numbers, we use BigDecimal to ensure arbitrary precision.
d) Other Types: The function handles booleans, strings, and returns null for unknown types.

3. Main Method: Demonstrates parsing a sample JSON string and prints the resulting structure.

   This approach guarantees that large integers and precise floating-point values are accurately represented.

   Input : {"key1": 12345678901234567890, "key2": [1, 2.5, "string"], "key3": true}

   >>>>>>>>>>> Expected Output >>>>>>>>>>>
   The main method will parse the JSON string and print the result in a structure that represents Java's Map and List 
   types. 
   The output will look like:

   {key1=12345678901234567890, key2=[1, 2.5, string], key3=true}

    Breakdown:
    key1 is a large integer and will be printed as BigDecimal with full precision.
    key2 is an array containing an integer, a floating-point number, and a string.
    key3 is a boolean value.


   *****************************************************************************************************************

   Explanation to Problem 4 :

   Problem statement :	There is an API that one must call to get data. The trouble is it will not let you cross the limit 
   of call - say 15 calls per minute. If you cross the limit, the system penalizes you by one additional minute of penalty 
   where you can not make any call. Here is how the API looks like: function string call_me(string input).

Propose a solution by which:
1.	You would be able to use the API within the safe limit.
2.	What happens if you are supposed to call the API 20 times per minute? Is there any way to accomplish this?
3.	If you were the API designer, what would you do to implement this behavior?


>>>>>>>>>>>>>>>  Solution >>>>>>>>>>>>>>>

1. Rate Limiting Logic:

a) AtomicInteger tracks the number of API calls.
b) A ScheduledExecutorService resets callCount to 0 every minute, ensuring the count refreshes.

2. Handling Penalty:

a) If the call limit (15 per minute) is exceeded, the safeCall method pauses execution for one minute before resetting the 
   count and allowing more calls.
  
3. Concurrent Calls:

a)  The ExecutorService simulates multiple threads making API calls.
b)   This demonstrates handling concurrent requests and maintaining the call limit within the minute.

>>>>>>>>>>>>  Behavior Analysis  >>>>>>>>>>>>>>>>

1. Within Safe Limit: The system tracks the number of calls and ensures no more than 15 calls are made per minute.
2. Exceeding 15 Calls: If 20 calls are attempted within one minute, the system will apply a one-minute penalty after 
   reaching the 15-call threshold, causing the remaining calls to wait.

>>>>>>>>>>>>>>>>> API Design Improvements >>>>>>>>>>>>>>>>>>
1. Use a token bucket algorithm for smoother call distribution.
2. Return HTTP headers that inform users about the remaining allowed calls and the time until reset.
3. Provide detailed error responses when the limit is exceeded, with instructions on when calls can be resumed.

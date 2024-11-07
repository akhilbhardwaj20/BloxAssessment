package org.example;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ApiRateLimiter {
    private static final int MAX_CALLS_PER_MINUTE = 15;
    private static final int PENALTY_DURATION_MS = 60_000; // 1 minute in milliseconds

    private final AtomicInteger callCount = new AtomicInteger(0);
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public ApiRateLimiter() {
        // Resets the call count every minute
        scheduler.scheduleAtFixedRate(() -> callCount.set(0), 0, 1, TimeUnit.MINUTES);
    }

    public synchronized String safeCall(String input) throws InterruptedException {
        if (callCount.get() >= MAX_CALLS_PER_MINUTE) {
            System.out.println("Call limit exceeded. Applying penalty.");
            Thread.sleep(PENALTY_DURATION_MS);
            callCount.set(0); // Reset after penalty
        }

        callCount.incrementAndGet();
        return callMe(input);
    }

    public String callMe(String input) {
        // Mock API implementation
        return "Response for input: " + input;
    }

    public static void main(String[] args) {
        ApiRateLimiter rateLimiter = new ApiRateLimiter();
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 20; i++) {
            int finalI = i;
            executor.submit(() -> {
                try {
                    String response = rateLimiter.safeCall("Request " + finalI);
                    System.out.println(response);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
    }
}

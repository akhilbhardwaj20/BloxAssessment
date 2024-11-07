package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Object parseJson(String jsonString) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(jsonString);
        return parseJsonNode(rootNode);
    }

    private static Object parseJsonNode(JsonNode node) {
        if (node.isObject()) {
            Map<String, Object> map = new LinkedHashMap<>();
            node.fieldNames().forEachRemaining(fieldName -> {
                map.put(fieldName, parseJsonNode(node.get(fieldName)));
            });
            return map;
        } else if (node.isArray()) {
            List<Object> list = new ArrayList<>();
            node.forEach(element -> list.add(parseJsonNode(element)));
            return list;
        } else if (node.isBigInteger()) {
            return node.bigIntegerValue();
        } else if (node.isBigDecimal()) {
            return node.decimalValue();
        } else if (node.isDouble() || node.isFloat()) {
            return BigDecimal.valueOf(node.doubleValue());
        } else if (node.isLong()) {
            return BigDecimal.valueOf(node.longValue());
        } else if (node.isInt()) {
            return BigDecimal.valueOf(node.intValue());
        } else if (node.isBoolean()) {
            return node.booleanValue();
        } else if (node.isTextual()) {
            return node.textValue();
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        String jsonString = "{\"key1\": 12345678901234567890, \"key2\": [1, 2.5, \"string\"], \"key3\": true}";
        try {
            Object parsedJson = parseJson(jsonString);
            System.out.println(parsedJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

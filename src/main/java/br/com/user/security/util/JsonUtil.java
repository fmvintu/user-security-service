package br.com.user.security.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {
    private static final ObjectMapper OBJECT_MAPPER = new CustomObjectMapper();

    private JsonUtil() {}

    public static <T> T convertValue(Object value, Class<T> response) {
        return OBJECT_MAPPER.convertValue(value, response);
    }

    public static String writeValueAsString(Object value) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(value);
    }

    public static <T> T readValue(String content, TypeReference<T> valueTypeRef)
            throws IOException {
        return OBJECT_MAPPER.readValue(content, valueTypeRef);
    }

    public static String writerWithDefaultPrettyPrinter(Object value) throws IOException {
        return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(value);
    }
}

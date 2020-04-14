package br.com.user.security.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class CustomObjectMapper extends ObjectMapper {

    private static final long serialVersionUID = -5829814108020959847L;

    public CustomObjectMapper() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        registerModule(javaTimeModule);

        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        enable(SerializationFeature.INDENT_OUTPUT);
        enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}
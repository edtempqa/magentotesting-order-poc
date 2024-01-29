package org.example.util;

import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.DeserializationFeature;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;

import java.util.List;

public class JsonParser {
    public <T> T jsonToObject(String jsonString, Class<T> objectClass) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
        return mapper.readValue(jsonString, mapper.getTypeFactory().constructType(objectClass));
    }

    public <T> List<T> jsonToObjects(@NonNull String jsonString, Class<T> objectClass) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
        return mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, objectClass));
    }
}
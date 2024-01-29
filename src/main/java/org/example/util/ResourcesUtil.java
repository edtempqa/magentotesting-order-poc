package org.example.util;

import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

public class ResourcesUtil {

    public String getResourceContent(@NonNull String resourceFile) throws URISyntaxException, IOException {
        final var resource = getClass().getClassLoader().getResource(resourceFile);
        final var uri = Objects.requireNonNull(resource).toURI();
        return Files.readString(Paths.get(uri), StandardCharsets.UTF_8);
    }

    public <T> T getResourceAsJson(@NonNull String resourceFile, Class<T> objectClass) throws URISyntaxException, IOException {
        final JsonParser jsonParser = new JsonParser();
        return jsonParser.jsonToObject(getResourceContent(resourceFile), objectClass);
    }

    public Map<String, Map<String, String>> getCssProperties(@NonNull String resourceFile) throws URISyntaxException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(getResourceContent(resourceFile), Map.class);
    }
}
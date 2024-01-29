package org.example.domain;

import lombok.NonNull;

import java.util.Arrays;

public enum CssProperties {
    CSS_COLOR("color"),
    CSS_FONT_FAMILY("font-family"),
    CSS_WIDTH("width"),
    CSS_HEIGHT("height");

    private final String cssProperty;

    CssProperties(final String cssProperty) {
        this.cssProperty = cssProperty;
    }

    @Override
    public String toString() {
        return this.cssProperty;
    }

    public static CssProperties getCssProperty(@NonNull String cssPropertyName) {
        return Arrays.stream(values())
                .filter(p -> p.cssProperty.equalsIgnoreCase(cssPropertyName))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid property name: " + cssPropertyName));
    }
}

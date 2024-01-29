package org.example.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.example.domain.CssProperties;

@UtilityClass
public class CssUtils {
    private final String GET_CSS_ACTION = "element => getComputedStyle(element)['%s']";

    public String getCssPropertyAction(@NonNull String cssPropertyName) {
        return String.format(GET_CSS_ACTION, CssProperties.getCssProperty(cssPropertyName));
    }
}

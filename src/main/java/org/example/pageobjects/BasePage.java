package org.example.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.util.CssUtils;

@Slf4j
public abstract class BasePage {
    protected Page page;

    protected BasePage(Page page) {
        this.page = page;
    }

    public String getCssProperty(@NonNull Locator locator, @NonNull String cssPropertyName) {
        final String action = CssUtils.getCssPropertyAction(cssPropertyName);
        return (String) locator.evaluate(action);
    }

    protected String getCookie(@NonNull String cookieName) {
        final var cookies = page.context().cookies();
        return cookies.stream().filter(c -> c.name.equalsIgnoreCase(cookieName))
                .map(c -> c.value)
                .findFirst().orElse(null);
    }
}

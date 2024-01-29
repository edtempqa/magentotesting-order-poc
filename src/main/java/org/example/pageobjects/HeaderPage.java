package org.example.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.trim;

public class HeaderPage extends BasePage {

    public HeaderPage(Page page) {
        super(page);
    }

    private Locator getSearchBoxLocator() {
        return page.locator("#search");
    }

    private Locator getCartQuantityLocator() {
        return page.locator("span.counter-number");
    }

    private Locator getCartWrapperLocator() {
        return page.locator("div.minicart-wrapper");
    }

    public void searchBy(String keywords) {
        getSearchBoxLocator().fill(keywords);
        getSearchBoxLocator().press("Enter");
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    public String getCartQuantity() {
        return trim(Objects.requireNonNull(getCartQuantityLocator()).textContent());
    }

    public void clickMiniCart() {
        Objects.requireNonNull(getCartWrapperLocator()).click();
    }
}

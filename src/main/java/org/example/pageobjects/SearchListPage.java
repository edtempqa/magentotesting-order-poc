package org.example.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.NonNull;
import org.example.domain.Product;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.trim;

public class SearchListPage extends BasePage {
    public SearchListPage(Page page) {
        super(page);
    }

    private List<Locator> getProductsLocators() {
        return page.locator("li.product-item").all();
    }

    private Locator getSearchResultsLocator() {
        return page.locator(".search.results");
    }

    public void navigateToProduct(@NonNull Product product) {
        getProductsLocators().stream()
                .filter(p -> product.getName().equalsIgnoreCase(trim(p.locator("a.product-item-link").textContent())))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("There i no product with name " + product.getName()))
                .click();
    }
}

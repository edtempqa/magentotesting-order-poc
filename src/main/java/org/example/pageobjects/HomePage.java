package org.example.pageobjects;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

public class HomePage extends BasePage {

    private final HeaderPage headerPage = new HeaderPage(page);

    public HomePage(Page page) {
        super(page);
    }

    public void open(String url) {
        page.navigate(url);
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    public void searchBy(String keywords) {
        headerPage.searchBy(keywords);
    }
}

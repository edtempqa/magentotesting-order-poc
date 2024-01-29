package org.example.steps;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public abstract class BaseSteps {

    protected final Playwright playwright = Playwright.create();
    protected final Page page = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)
                    .setArgs(List.of("--start-maximized")))
            .newContext(new Browser.NewContextOptions().setViewportSize(null)).newPage();

    protected BaseSteps() {
        page.setDefaultTimeout(10000);
    }
}

package org.example.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import lombok.NonNull;
import org.example.domain.CartItem;
import org.example.domain.Product;
import org.example.domain.ProductAttribute;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.trim;
import static org.example.configuration.ValidationMessages.*;

public class PdpPage extends BasePage {

    private final HeaderPage headerPage = new HeaderPage(page);

    public PdpPage(Page page) {
        super(page);
    }

    private Locator getProductNameLocator() {
        return page.locator("h1.page-title > span");
    }

    private List<Locator> getSizeLocators() {
        return page.locator("div.size div.swatch-option").all();
    }

    private List<Locator> getColorOptionsLocators() {
        return page.locator("div.swatch-option.color").all();
    }

    private Locator getQuantityLocator() {
        return page.locator("input.qty");
    }

    private Locator getAddToCartButtonLocator() {
        return page.locator("button.tocart");
    }

    private Locator getSizeErrorMessageLocator() {
        return page.locator("div.size div.mage-error");
    }

    private Locator getColorErrorMessageLocator() {
        return page.locator("div.color div.mage-error");
    }

    private Locator getQuantityErrorMessageLocator() {
        return page.locator("div.qty div.mage-error");
    }

    private Locator getActionMessageLocator() {
        return page.locator("div.messages div.messages > div > div");
    }

    public void isDisplayed(@NonNull Product product) {
        final SoftAssert assertions = new SoftAssert();
        assertions.assertEquals(getProductNameLocator().textContent(), product.getName());
        // TODO add other validations like descriptions, etc.
        assertions.assertAll();
    }

    public String selectRandomSize() {
        final var randomSizeLocator = Objects.requireNonNull(getSizeLocators()).stream()
                .skip((int) (getSizeLocators().size() * Math.random()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("There is no size for this product!"));
        randomSizeLocator.click();
        return randomSizeLocator.textContent();
    }

    public String selectRandomColor() {
        final var randomColorLocator = Objects.requireNonNull(getColorOptionsLocators()).stream()
                .skip((int) (getColorOptionsLocators().size() * Math.random()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("There is no Color option for this product!"));
        randomColorLocator.click();
        return randomColorLocator.getAttribute("option-label");
    }

    public void setQuantity(String quantityValue) {
        Objects.requireNonNull(getQuantityLocator()).fill(quantityValue);
    }

    public void clickAddToCartButton() {
        getAddToCartButtonLocator().click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    public void validateProductAttributes(@NonNull List<ProductAttribute> attributes, @NonNull String expectedErrorMessage) {
        final SoftAssert assertions = new SoftAssert();
        attributes.forEach(a -> assertions.assertEquals(getAttributeErrorMessage(a), expectedErrorMessage));
        assertions.assertAll();
    }

    public String getAttributeErrorMessage(ProductAttribute attribute) {
        return switch (attribute) {
            case SIZE -> trim(getSizeErrorMessageLocator().textContent());
            case COLOR -> trim(getColorErrorMessageLocator().textContent());
            case QUANTITY -> trim(getQuantityErrorMessageLocator().textContent());
        };
    }

    public void validateProductQuantity(@NonNull CartItem cartItem) {
        try {
            final var quantity = Integer.valueOf(cartItem.getQuantity());
            final String expectedErrorMessage = quantity <= 0 ? GREATER_THAN_ZERO_MESSAGE : VALID_NUMBER_MESSAGE;
            Assert.assertEquals(trim(getQuantityErrorMessageLocator().textContent()), expectedErrorMessage);
        } catch (NumberFormatException e) {
            Assert.assertEquals(trim(getQuantityErrorMessageLocator().textContent()), VALID_NUMBER_MESSAGE);
        }
    }

    public void validateMessageDisplayedForProduct(@NonNull Product product) {
        final String displayedMessage = Objects.requireNonNull(getActionMessageLocator()).textContent();
        final String expectedMessagePart = String.format(PRODUCT_SUCCESSFULLY_ADDED_MESSAGE, product.getName());
        Assert.assertTrue(trim(displayedMessage).startsWith(expectedMessagePart),
                String.format("The displayed message %s does not start with expected one %s",
                        displayedMessage, expectedMessagePart));
    }

    public void validateProductQuantityAddedToCart(CartItem cartItem) {
        final String displayedCartQuantity = headerPage.getCartQuantity();
        Assert.assertEquals(displayedCartQuantity, cartItem.getQuantity());
    }

    public void clickOnCart() {
        headerPage.clickMiniCart();
    }
}

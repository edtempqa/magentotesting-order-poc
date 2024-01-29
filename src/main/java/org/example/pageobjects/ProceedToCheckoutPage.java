package org.example.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.example.domain.CartItem;
import org.example.domain.Product;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProceedToCheckoutPage extends BasePage {
    public ProceedToCheckoutPage(Page page) {
        super(page);
    }

    private Locator getProceedToCheckOutButtonLocator() {
        return page.locator("#top-cart-btn-checkout");
    }

    private List<Locator> getProductsInMiniCartLocators() {
        return page.locator("ol#mini-cart>li.product.product-item").all();
    }

    public void productsDisplayed(List<CartItem> cartItems) {
        final var miniCartItems = getMiniCartItems();
        Assert.assertTrue(Objects.requireNonNull(miniCartItems).size() > 0,
                "There are no products in mini cart!");
        final SoftAssert assertions = new SoftAssert();
        assertions.assertEqualsNoOrder(cartItems.toArray(), miniCartItems.toArray());
        assertions.assertAll();
    }

    public List<CartItem> getMiniCartItems() {
        return Objects.requireNonNull(getProductsInMiniCartLocators()).stream()
                .map(p -> {
                    final var productOptions = p.locator("dl.product.options.list dd.values > span").all();
                    return CartItem.builder()
                            .product(Product.builder().name(p.locator(".product-item-name > a").textContent()).build())
                            .size(productOptions.get(0).textContent())
                            .color(productOptions.get(1).textContent())
                            .quantity(p.locator("input.item-qty.cart-item-qty").getAttribute("data-item-qty"))
                            .build();
                }).toList();
    }
}

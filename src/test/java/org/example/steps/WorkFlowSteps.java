package org.example.steps;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.CartItem;
import org.example.domain.Product;
import org.example.pageobjects.HomePage;
import org.example.pageobjects.PdpPage;
import org.example.pageobjects.ProceedToCheckoutPage;
import org.example.pageobjects.SearchListPage;

import java.util.List;

import static org.example.configuration.ValidationMessages.*;
import static org.example.domain.ProductAttribute.COLOR;
import static org.example.domain.ProductAttribute.SIZE;

@Slf4j
public class WorkFlowSteps extends BaseSteps {

    private final HomePage homePage = new HomePage(page);
    private final SearchListPage searchListPage = new SearchListPage(page);
    private final PdpPage pdpPage = new PdpPage(page);

    private final ProceedToCheckoutPage proceedToCheckoutPage = new ProceedToCheckoutPage(page);

    private final Product product = Product.builder().build();
    private final CartItem cartItem = CartItem.builder().product(product).build();

    @After
    public void afterAll() {
        page.close();
    }

    @Given("I am on the Magento website {string}")
    public void iAmOnMagentoWebsite(String url) {
        log.info("I navigate to the Magento website {}", url);
        homePage.open(url);
        log.info("I am on the Magento website {}", url);
    }

    @When("I search for {string}")
    public void iSearchFor(String keywords) {
        log.info("I search for {}", keywords);
        homePage.searchBy(keywords);
    }

    @When("I open the product page for {string}")
    public void iOpenProductPage(String productName) {
        log.info("I open the product page for {}", productName);
        product.setName(productName);
        searchListPage.navigateToProduct(product);
        pdpPage.isDisplayed(product);
    }

    @When("I click the Add to cart button")
    public void iClickAddToCartButton() {
        log.info("I click the Add to cart button");
        pdpPage.clickAddToCartButton();
    }

    @Then("The Size and Color required field error messages are displayed")
    public void sizeAndColorRequiredFieldErrorMessagesDisplayed() {
        log.info("I check if Size and Color required field error messages are displayed");
        pdpPage.validateProductAttributes(List.of(SIZE, COLOR), REQUIRED_FIELD_MESSAGE);
    }

    @When("I enter valid {string} value in the quantity field")
    public void iEnterValidQuantity(String quantityValue) {
        log.info("I enter valid {} value in the quantity field", quantityValue);
        pdpPage.setQuantity(quantityValue);
        cartItem.setQuantity(quantityValue);
    }

    @When("I enter invalid {string} value in the quantity field")
    public void iEnterInvalidQuantity(String quantityValue) {
        log.info("I enter invalid {} value in the quantity field", quantityValue);
        pdpPage.setQuantity(quantityValue);
        cartItem.setQuantity(quantityValue);
    }

    @Then("Validation error message for Quantity field is displayed")
    public void quantityValidationErrorMessageDisplayed() {
        log.info("I check if error message for Quantity field is displayed");
        pdpPage.validateProductQuantity(cartItem);
    }

    @When("I select valid size")
    public void iSelectValidSize() {
        log.info("I select valid size");
        cartItem.setSize(pdpPage.selectRandomSize());
    }

    @When("I select valid color")
    public void iSelectValidColor() {
        log.info("I select valid color");
        cartItem.setColor(pdpPage.selectRandomColor());
    }

    @Then("The success message is displayed and product is added to the cart")
    public void successMessageDisplayedProductAddedToCart() {
        log.info("I check if success message is displayed and product is added to the cart");
        pdpPage.validateMessageDisplayedForProduct(product);
        pdpPage.validateProductQuantityAddedToCart(cartItem);
    }

    @When("I open the mini cart")
    public void iOpenMiniCart() {
        log.info("I open the mini cart");
        pdpPage.clickOnCart();
    }

    @Then("The products are displayed in the Proceed to Checkout overlay")
    public void productsAreDisplayedInProceedToCheckoutOverlay() {
        proceedToCheckoutPage.productsDisplayed(List.of(cartItem));
    }
}

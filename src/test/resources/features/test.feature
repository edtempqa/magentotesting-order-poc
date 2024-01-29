Feature: Order Placement on Magento Website

  Scenario Outline: Placing an order with selected product properties
    Given I am on the Magento website "<url>"
    When I search for "<product_name>"
    And I open the product page for "<product_name>"
    And I click the Add to cart button
    Then The Size and Color required field error messages are displayed
    When I enter invalid "<invalid_quantity>" value in the quantity field
    And I click the Add to cart button
    Then Validation error message for Quantity field is displayed
    When I enter valid "<valid_quantity>" value in the quantity field
    And I select valid size
    And I select valid color
    And I click the Add to cart button
    Then The success message is displayed and product is added to the cart
    When I open the mini cart
    Then The products are displayed in the Proceed to Checkout overlay
    Examples:
      | url                                      | product_name     | invalid_quantity | valid_quantity |
      | https://magento.softwaretestingboard.com | Lando Gym Jacket | -8               | 3              |

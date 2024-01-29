package org.example.configuration;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationMessages {
    public final String REQUIRED_FIELD_MESSAGE = "This is a required field.";
    public final String VALID_NUMBER_MESSAGE = "Please enter a valid number in this field.";
    public final String GREATER_THAN_ZERO_MESSAGE = "Please enter a quantity greater than 0.";
    public final String PRODUCT_SUCCESSFULLY_ADDED_MESSAGE = "You added %s to your";
}

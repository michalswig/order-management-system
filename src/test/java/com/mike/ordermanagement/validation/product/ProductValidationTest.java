package com.mike.ordermanagement.validation.product;

import com.mike.ordermanagement.entity.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductValidationTest {

    @Test
    void whenPassingValidProduct_thenItIsValid() {
        //when
        Product product = new Product();
        product.setName("Valid Name");
        product.setDescription("Valid Description");
        product.setPrice(BigDecimal.valueOf(10.0));
        //then
        assertDoesNotThrow(() -> ProductValidation.validate(product));
    }

    @Test
    void whenPassingInvalidProduct_thenItIsInvalid() {
        //when
        Product product = new Product();
        product.setName(null);
        product.setDescription("Valid Description");
        product.setPrice(BigDecimal.valueOf(10.0));
        //then
        assertThrows(IllegalArgumentException.class, () -> ProductValidation.validate(product));
    }

    @Test
    void whenNameIsTooShort_thenThrowsException() {
        //when
        Product product = new Product();
        product.setName("ab");
        product.setDescription("Valid description");
        product.setPrice(BigDecimal.valueOf(10.0));
        //then
        assertThrows(IllegalArgumentException.class, () -> ProductValidation.validate(product), "Product name length must be between 3 and 50");
    }

    @Test
    void whenNameContainsInvalidCharacters_thenThrowsException() {
        //when
        Product product = new Product();
        product.setName("Invalid@Name!");
        product.setDescription("Valid description");
        product.setPrice(BigDecimal.valueOf(10.0));
        //then
        assertThrows(IllegalArgumentException.class, () -> ProductValidation.validate(product), "Product name can only contain letters, numbers, and spaces");
    }

}
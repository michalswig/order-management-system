package com.mike.ordermanagement.validation.product;

import com.mike.ordermanagement.entity.Product;
import com.mike.ordermanagement.exceptions.ProductValidationException;
import com.mike.ordermanagement.util.MessageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductValidationTest {

    private MessageUtil messageUtil;
    private NameValidator nameValidator;
    private DescriptionValidator descriptionValidator;
    private PriceValidator priceValidator;
    private CompositeValidator compositeValidator;

    @BeforeEach
    void setUp() {
        messageUtil = mock(MessageUtil.class);
        nameValidator = new NameValidator(messageUtil);
        descriptionValidator = new DescriptionValidator(messageUtil);
        priceValidator = new PriceValidator(messageUtil);

        compositeValidator = new CompositeValidator(
                List.of(nameValidator, descriptionValidator, priceValidator),
                messageUtil
        );
    }


    @Test
    void whenPassingValidProduct_thenShouldBeValid() {
        // Given
        Product product = new Product();
        product.setName("Valid Name");
        product.setDescription("Valid Description");
        product.setPrice(BigDecimal.valueOf(10.0));
        //Then
        assertDoesNotThrow(() -> compositeValidator.validate(product));
    }

    @Test
    void whenPassingProductNull_thenThrowsException() {
        // Given
        Product product = null;
        when(messageUtil.getMessage("product.null")).thenReturn("Product cannot be null");
        //When
        ProductValidationException ex = assertThrows(ProductValidationException.class, () -> compositeValidator.validate(product));
        //Then
        assertEquals("Product cannot be null", ex.getMessage());
    }

    @Test
    void whenPassingInvalidProductName_thenThrowsException() {
        // Given
        Product product = new Product();
        product.setName("@@@");
        product.setDescription("Valid Description");
        product.setPrice(BigDecimal.valueOf(10.0));
        //When
        when(messageUtil.getMessage("product.name.invalid")).thenReturn("Invalid Product Name");
        ProductValidationException ex = assertThrows(ProductValidationException.class, () -> compositeValidator.validate(product));
        assertEquals("Invalid Product Name", ex.getMessage());
    }

}
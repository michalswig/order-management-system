package com.mike.ordermanagement.validation.product;

import com.mike.ordermanagement.entity.Product;
import com.mike.ordermanagement.exceptions.ProductValidationException;
import com.mike.ordermanagement.util.MessageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductValidationTest {

    @Mock
    private MessageUtil messageUtil;

    @InjectMocks
    private ProductValidation productValidation;

    @Test
    void whenPassingValidProduct_thenShouldBeValid() {
        // Given
        Product product = new Product();
        product.setName("Valid Name");
        product.setDescription("Valid Description");
        product.setPrice(BigDecimal.valueOf(10.0));
        //Then
        assertDoesNotThrow(() -> productValidation.validate(product));
    }

    @Test
    void whenPassingProductNull_thenThrowsException() {
        // Given
        Product product = null;
        when(messageUtil.getMessage("product.null")).thenReturn("Product price cannot be null");
        //When
        ProductValidationException ex = assertThrows(ProductValidationException.class, () -> productValidation.validate(product));
        //Then
        assertEquals("Product price cannot be null", ex.getMessage());
    }


}
package com.mike.ordermanagement.validation.product;

import com.mike.ordermanagement.entity.Product;
import com.mike.ordermanagement.util.MessageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class DescriptionValidatorTest {

    @Mock
    MessageUtil messageUtil;
    @InjectMocks
    DescriptionValidator validator;

    Product product;

    @BeforeEach
    void setUp() {
        Clock fixedClock = Clock.fixed(
                Instant.parse("2024-03-16T10:00:00Z"),
                ZoneId.systemDefault()
        );
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(BigDecimal.valueOf(100));
        product.setCreatedAt(LocalDateTime.now(fixedClock));

        validator = new DescriptionValidator(messageUtil);

    }

    @Test
    void validateProductDescriptionIfLengthValidAndNotEmpty() {
        assertDoesNotThrow(() -> validator.validate(product));
    }


}
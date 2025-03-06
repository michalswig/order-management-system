package com.mike.ordermanagement.util;

import com.mike.ordermanagement.entity.Customer;
import com.mike.ordermanagement.entity.Product;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Random;

public class CustomerDataGenerator {

    static Random random = new Random();
    private static final Clock clock;

    static {
        clock = Clock.systemUTC();
    }

    public static Customer generatorCustomer() {
        int randomNumber = random.nextInt(100);
        Customer customer = new Customer();
        customer.setName("Test Name " + randomNumber);
        customer.setEmail("test@email.com " + randomNumber);
        customer.setPhone("TestNumber " + randomNumber);
        customer.setAddress("TestAddress " + randomNumber);
        customer.setCity("Test city " + randomNumber);
        customer.setState("Test state " + randomNumber);
        customer.setCountry("test country " + randomNumber);
        customer.setZipCode("Test zip code " + randomNumber);
        customer.setCreatedAt(LocalDateTime.now(clock));
        customer.setUpdatedAt(null);
        return customer;
    }

    public static Product generatorProduct() {
        int randomNumber = random.nextInt(100);
        Product product = new Product();
        product.setName("Test Product Name " + randomNumber);
        product.setDescription("Test Product Description " + randomNumber);
        product.setPrice(BigDecimal.valueOf(randomNumber));
        product.setCreatedAt(LocalDateTime.now(clock));
        product.setUpdatedAt(LocalDateTime.now(clock));
        return product;
    }

    public static Customer generateCustomerPassingCustomerId(Long id) {
        int randomNumber = random.nextInt(100);
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName("Test Name " + randomNumber);
        customer.setEmail("test@email.com " + randomNumber);
        customer.setPhone("TestNumber " + randomNumber);
        customer.setAddress("TestAddress " + randomNumber);
        customer.setCity("Test city " + randomNumber);
        customer.setState("Test state " + randomNumber);
        customer.setCountry("test country " + randomNumber);
        customer.setZipCode("Test zip code " + randomNumber);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(null);
        return customer;

    }

    public static Product generateProductPassingProductId(Long id) {
        int randomNumber = random.nextInt(100);
        Product product = new Product();
        product.setId(id);
        product.setName("Test Product Name " + randomNumber);
        product.setDescription("Test Product Description " + randomNumber);
        product.setPrice(BigDecimal.valueOf(randomNumber));
        product.setCreatedAt(LocalDateTime.now(clock));
        product.setUpdatedAt(LocalDateTime.now(clock));
        return product;
    }

}

package com.mike.ordermanagement.util;

import com.mike.ordermanagement.entity.Customer;

import java.time.LocalDateTime;
import java.util.Random;

public class CustomerDataGenerator {

    static Random random = new Random();

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
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(null);
        return customer;
    }


}

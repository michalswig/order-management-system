package com.mike.ordermanagement.service;

import com.mike.ordermanagement.entity.Customer;
import com.mike.ordermanagement.exceptions.CustomerNotFoundException;
import com.mike.ordermanagement.repository.CustomerRepository;
import org.springframework.stereotype.Component;

@Component
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + customerId + " not found."));
    }

}

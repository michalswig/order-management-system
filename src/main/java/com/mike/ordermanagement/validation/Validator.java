package com.mike.ordermanagement.validation;

import com.mike.ordermanagement.exceptions.ProductValidationException;

public interface Validator<T> {
    void validate(T product) throws ProductValidationException;
}

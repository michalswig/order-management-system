package com.mike.ordermanagement.constants;

public enum ErrorMessage {
    VALIDATION_ERROR("Validation Error"),
    NOT_FOUND("Not Found"),
    BAD_REQUEST("Bad Request"),
    INTERNAL_SERVER_ERROR("Internal Server Error"),
    METHOD_NOT_ALLOWED("Method Not Allowed"),
    UNEXPECTED_ERROR("An unexpected error occurred"),
    INVALID_PAYLOAD("Invalid request payload");

    public final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

}
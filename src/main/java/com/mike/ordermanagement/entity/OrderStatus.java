package com.mike.ordermanagement.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderStatus {
    PENDING, APPROVED, REJECTED;

    @JsonCreator
    public static OrderStatus fromString(String value) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid OrderStatus: " + value);
    }

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }

}

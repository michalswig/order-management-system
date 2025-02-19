package com.mike.ordermanagement.config;

import com.mike.ordermanagement.entity.OrderStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class OrderStatusConverter implements Converter<String, OrderStatus> {

    @Override
    public OrderStatus convert(String source) {
        return Arrays.stream(OrderStatus.values())
                .filter(status -> status.name().equalsIgnoreCase(source))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid value: '" + source + "' for field: 'status'. " +
                                "Allowed values: " + Arrays.toString(OrderStatus.values())
                ));
    }
}

package com.mike.ordermanagement.util;

import com.mike.ordermanagement.entity.Order;
import com.mike.ordermanagement.entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.Random;

public class OrderDataGenerator {

    private final static OrderStatus[] ORDER_STATUSES = OrderStatus.values();
    private final static Random RANDOM = new Random();

    public static Order orderGenerator(){
        Order order = new Order();
        order.setCustomer(CustomerDataGenerator.generatorCustomer());
        order.setStatus(ORDER_STATUSES[RANDOM.nextInt(ORDER_STATUSES.length)]);
        order.setOrderDate(LocalDateTime.now());
        order.setCanceledDate(null);
        order.setDeliveryDate(null);
        return order;
    }

    public static Order orderGeneratorSetOrderDate(LocalDateTime orderDate){
        Order order = new Order();
        order.setCustomer(CustomerDataGenerator.generatorCustomer());
        order.setStatus(ORDER_STATUSES[RANDOM.nextInt(ORDER_STATUSES.length)]);
        order.setOrderDate(orderDate);
        order.setCanceledDate(null);
        order.setDeliveryDate(null);
        return order;
    }

    public static Order orderGeneratorSetOrderStatus(OrderStatus status){
        Order order = new Order();
        order.setCustomer(CustomerDataGenerator.generatorCustomer());
        order.setStatus(status);
        order.setOrderDate(LocalDateTime.now());
        order.setCanceledDate(null);
        order.setDeliveryDate(null);
        return order;
    }


}
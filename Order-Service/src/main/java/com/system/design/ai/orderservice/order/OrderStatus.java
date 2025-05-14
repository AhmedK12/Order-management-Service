package com.system.design.ai.orderservice.order;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OrderStatus {
    PENDING,
    SHIPPED,
    DELIVERED,
    COMPLETED;
    @JsonCreator
    public static OrderStatus from(String value) {
        return OrderStatus.valueOf(value.toUpperCase());
    }
}

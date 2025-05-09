package com.system.design.ai.orderservice.order;

import java.math.BigDecimal;

public interface OrderService {
    Order createOrder(Order order);

    Order updateOrderStatus(Long id, OrderStatus status);

    BigDecimal calculateTotalOrderValue(Long customerId);
}

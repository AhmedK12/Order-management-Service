package com.system.design.ai.orderservice.customer;

import org.springframework.stereotype.Component;
import com.system.design.ai.orderservice.order.Order;

import java.util.List;

@Component
public interface CustomerService {
    Customer createCustomer(Customer customer);

    Customer getCustomer(Long id);

    List<Order> getOrdersByCustomerId(Long id);
}

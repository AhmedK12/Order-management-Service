package com.system.design.ai.orderservice.customer;

import org.springframework.stereotype.Component;
import com.system.design.ai.orderservice.order.Order;

import java.util.List;


public interface CustomerService {
    CustomerResponseDTO createCustomer(CustomerRequestDTO customer);

    CustomerResponseDTO getCustomer(Long id);

    List<Order> getOrdersByCustomerId(Long id);

    void deleteCustomer(Long id);
}

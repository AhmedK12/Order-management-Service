package com.system.design.ai.orderservice.order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);

    OrderResponseDTO updateOrderStatus(ChangeStatusRequestDTO changeStatusRequestDTO);

    BigDecimal calculateTotalOrderValue(Long customerId);

    List<OrderResponseDTO> getAllOrders(Long customerId);
}

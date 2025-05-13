package com.system.design.ai.orderservice.order;

import com.system.design.ai.orderservice.orderitem.OrderItemResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        Long id,
        Long customerId,
        LocalDateTime orderDate,
        OrderStatus status,
        Integer version,
        Long paymentId,
        List<OrderItemResponseDTO> items
) {}

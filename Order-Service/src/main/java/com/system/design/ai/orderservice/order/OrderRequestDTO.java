package com.system.design.ai.orderservice.order;


import com.system.design.ai.orderservice.orderitem.OrderItemRequestDTO;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequestDTO(
        @NotNull(message = "Customer ID is required") Long customerId,
        @NotNull(message = "Status is required") OrderStatus status,
        @NotNull(message = "Version should be there") Integer version,
        Long paymentId,
        List<OrderItemRequestDTO> items
) {}

package com.system.design.ai.orderservice.orderitem;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemRequestDTO {
    private Long productId;
    private Integer quantity;
    private Long orderId;
    private BigDecimal price;
    // Add any other necessary fields
}
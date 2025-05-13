package com.system.design.ai.orderservice.orderitem;

import lombok.Data;

@Data
public class OrderItemResponseDTO {
    private Long id;
    private Long productId;
    private Integer quantity;
    private Long orderId;
    private Double price;
    // Add any other necessary fields
}

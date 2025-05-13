package com.system.design.ai.orderservice.order;

import com.system.design.ai.orderservice.Auditable;
import com.system.design.ai.orderservice.orderitem.OrderItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Customer ID is required")
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @NotNull(message = "Order date is required")
    private LocalDateTime orderDate = LocalDateTime.now();

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @Version
    private Integer version;

    private Long paymentId;

    // Define One-to-Many relationship with CascadeType.ALL to persist items and orphanRemoval for clean up
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    // Getters and setters
}

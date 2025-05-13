package com.system.design.ai.orderservice.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public record OrderDTO(Long orderId, Long paymentId, OrderStatus status, LocalDateTime orderDate, Integer version) {
}

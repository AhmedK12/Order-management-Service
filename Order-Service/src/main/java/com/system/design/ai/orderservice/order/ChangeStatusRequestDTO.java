package com.system.design.ai.orderservice.order;

import jakarta.validation.constraints.NotNull;

public record ChangeStatusRequestDTO(
        @NotNull Long orderId,
        @NotNull OrderStatus orderStatus,
        @NotNull Integer version)
{ }

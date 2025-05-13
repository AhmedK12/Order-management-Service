package com.system.design.ai.orderservice.address;

import jakarta.validation.constraints.NotBlank;

public record AddressRequestDTO(
        @NotBlank String street,
        @NotBlank String city,
        @NotBlank String state,
        @NotBlank String zip,
        @NotBlank String country,
        String landmark
) {}

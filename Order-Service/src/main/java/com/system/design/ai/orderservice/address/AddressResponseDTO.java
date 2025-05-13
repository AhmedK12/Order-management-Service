package com.system.design.ai.orderservice.address;

public record AddressResponseDTO(
        Long id,
        String street,
        String city,
        String state,
        String zip,
        String country,
        String landmark
) {}

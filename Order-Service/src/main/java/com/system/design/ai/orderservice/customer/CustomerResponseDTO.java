package com.system.design.ai.orderservice.customer;

import com.system.design.ai.orderservice.address.AddressDTO;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public record CustomerResponseDTO(
        Long id,
        String firstname,
        String lastname,
        String email,
        String mobile,
        List<AddressDTO> address,
        LocalDateTime createdAt
) {}

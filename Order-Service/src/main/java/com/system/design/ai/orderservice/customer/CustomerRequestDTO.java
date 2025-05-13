package com.system.design.ai.orderservice.customer;

import com.system.design.ai.orderservice.address.AddressDTO;
import com.system.design.ai.orderservice.address.AddressRequestDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record CustomerRequestDTO(
        @NotBlank String firstname,
        @NotBlank String lastname,
        @Email @NotBlank String email,
        @NotBlank String mobile,
        List<AddressRequestDTO> address
) {}

package com.system.design.ai.orderservice.address;

import java.util.List;


public interface AddressService {
    AddressResponseDTO create(AddressRequestDTO dto);
    List<AddressResponseDTO> findAll();
}

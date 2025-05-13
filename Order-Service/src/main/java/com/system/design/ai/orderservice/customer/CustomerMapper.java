package com.system.design.ai.orderservice.customer;

import com.system.design.ai.orderservice.address.AddressMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface CustomerMapper {

    Customer toEntity(CustomerRequestDTO dto);

    @Mapping(target = "createdAt", source = "createdAt")
    CustomerResponseDTO toResponseDTO(Customer customer);
}

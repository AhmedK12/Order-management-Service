package com.system.design.ai.orderservice.address;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AddressMapper {
    Address toEntity(AddressRequestDTO dto);
    AddressResponseDTO toResponseDTO(Address entity);
}


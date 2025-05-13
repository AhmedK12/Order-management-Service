package com.system.design.ai.orderservice.order;

import com.system.design.ai.orderservice.orderitem.OrderItemMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "version", ignore = true)
    Order toEntity(OrderRequestDTO dto);

    OrderResponseDTO toResponseDTO(Order order);
}

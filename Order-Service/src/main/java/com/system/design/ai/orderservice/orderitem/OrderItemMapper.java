package com.system.design.ai.orderservice.orderitem;

import com.system.design.ai.orderservice.orderitem.OrderItem;
import com.system.design.ai.orderservice.orderitem.OrderItemRequestDTO;
import com.system.design.ai.orderservice.orderitem.OrderItemResponseDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "price", target = "price")
    OrderItem toEntity(OrderItemRequestDTO request);

    @Mapping(source = "order.id", target = "orderId") // ✅ ADD THIS LINE
    OrderItemResponseDTO toResponse(OrderItem orderItem);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(OrderItemRequestDTO request, @MappingTarget OrderItem orderItem);

    default OrderItem updateEntity(OrderItem entity, OrderItemRequestDTO request) {
        updateEntityFromRequest(request, entity);
        return entity;
    }
}

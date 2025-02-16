package com.mike.ordermanagement.mapper;

import com.mike.ordermanagement.dto.OrderCreateRequest;
import com.mike.ordermanagement.dto.OrderGetResponse;
import com.mike.ordermanagement.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    // Map DTO -> Entity
    @Mapping(target = "id", ignore = true)  // ID is auto-generated
    @Mapping(target = "deletedAt", ignore = true)
    // Let `deletedAt` be null initially
    Order toEntity(OrderCreateRequest orderCreateRequest);

    // Map Entity -> DTO
    OrderGetResponse toDto(Order order);

}

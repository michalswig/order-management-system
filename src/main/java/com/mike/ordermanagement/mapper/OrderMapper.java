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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    Order toEntity(OrderCreateRequest orderCreateRequest);

    OrderGetResponse toDto(Order order);

}

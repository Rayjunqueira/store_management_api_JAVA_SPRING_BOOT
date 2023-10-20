package com.example.softwaresystemapi.mappers;

import com.example.softwaresystemapi.dtos.OrderDto;
import com.example.softwaresystemapi.models.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "order_id", ignore = true)
    OrderModel toOrderModel (OrderDto orderDto);
}

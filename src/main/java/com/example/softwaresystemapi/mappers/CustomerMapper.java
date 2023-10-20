package com.example.softwaresystemapi.mappers;

import com.example.softwaresystemapi.dtos.CustomerDto;
import com.example.softwaresystemapi.models.CustomerModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(target = "customer_id", ignore = true)
    CustomerModel toCustomerModel (CustomerDto customerDto);
}

package com.example.softwaresystemapi.mappers;

import com.example.softwaresystemapi.dtos.CustomerCategoryDto;
import com.example.softwaresystemapi.models.CustomerCategoryModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CustomerCategoryMapper {
    @Mapping(target = "customer_category_id", ignore = true)
    CustomerCategoryModel toCustomerCategoryModel (CustomerCategoryDto customerCategoryDto);
}

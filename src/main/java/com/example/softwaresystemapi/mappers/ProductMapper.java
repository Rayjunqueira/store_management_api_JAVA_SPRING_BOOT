package com.example.softwaresystemapi.mappers;

import com.example.softwaresystemapi.dtos.ProductDto;
import com.example.softwaresystemapi.models.ProductModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "product_id", ignore = true)
    ProductModel toProductModel (ProductDto productDto);

}

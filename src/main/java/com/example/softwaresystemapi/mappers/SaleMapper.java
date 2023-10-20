package com.example.softwaresystemapi.mappers;

import com.example.softwaresystemapi.dtos.SaleDto;
import com.example.softwaresystemapi.models.SaleModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface SaleMapper {
    @Mapping(target = "sale_id", ignore = true)
    SaleModel toSaleModel (SaleDto saleDto);

}

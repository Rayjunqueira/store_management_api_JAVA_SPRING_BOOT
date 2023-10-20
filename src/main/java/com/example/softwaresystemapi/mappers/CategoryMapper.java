package com.example.softwaresystemapi.mappers;

import com.example.softwaresystemapi.dtos.CategoryDto;
import com.example.softwaresystemapi.models.CategoryModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "category_id", ignore = true)
    CategoryModel toCategoryModel (CategoryDto categoryDto);
}
package com.example.softwaresystemapi.dtos;

import com.example.softwaresystemapi.models.ProductModel;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class CategoryDto {
    @NotBlank
    private String name;

    private String description;

    private List<ProductModel> products;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }
}
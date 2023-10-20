package com.example.softwaresystemapi.dtos;

import com.example.softwaresystemapi.models.CategoryModel;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductDto {
    @NotBlank
    private String name;
    private String description;
    private String img;
    private String brand;
    @Positive(message = "Stock must be greater than zero")
    private Integer stock;
    @NotNull
    private CategoryModel category;
    @DecimalMin(value = "0.01", message = "Price must be greater than zero!")
    private Float price;
    @DecimalMin(value = "0.01", message = "Price must be greater than zero!")
    private Float cost;

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }
}
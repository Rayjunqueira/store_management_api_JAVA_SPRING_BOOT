package com.example.softwaresystemapi.dtos;

import com.example.softwaresystemapi.models.CategoryModel;
import com.example.softwaresystemapi.models.CustomerModel;
import com.example.softwaresystemapi.models.ProductModel;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SaleDto {
    @NotNull
    private CustomerModel customer;
    @NotNull
    private ProductModel product;
    @Positive(message = "Stock must be greater than zero")
    private Integer stock;
    @DecimalMin(value = "0.01", message = "Price must be greater than zero!")
    private Float price;

    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}

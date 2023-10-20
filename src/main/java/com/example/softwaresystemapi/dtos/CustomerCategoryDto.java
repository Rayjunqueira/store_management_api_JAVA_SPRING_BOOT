package com.example.softwaresystemapi.dtos;

import com.example.softwaresystemapi.models.CustomerModel;
import com.example.softwaresystemapi.models.ProductModel;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class CustomerCategoryDto {
    @NotBlank
    private String name;

    private String description;

    private List<CustomerModel> customers;

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

    public List<CustomerModel> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerModel> customers) {
        this.customers = customers;
    }
}

package com.example.softwaresystemapi.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

public class TransactionDto {
    @NotBlank
    private String name;
    private Boolean status;
    @DecimalMin(value = "0.01", message = "Price must be greater than zero!")
    private Float totalValue;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Float totalValue) {
        this.totalValue = totalValue;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}

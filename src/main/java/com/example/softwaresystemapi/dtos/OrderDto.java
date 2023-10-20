package com.example.softwaresystemapi.dtos;

import com.example.softwaresystemapi.models.CustomerModel;
import com.example.softwaresystemapi.models.SaleModel;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class OrderDto {
    @NotNull
    private CustomerModel customer;
    private List<SaleModel> sales;
    private LocalDate orderDate;
    @DecimalMin(value = "0.01", message = "Price must be greater than zero!")
    private Float totalAmount;
    @DecimalMin(value = "0.01", message = "Price must be greater than zero!")
    private Float totalCost;
    @DecimalMin(value = "0.00", message = "Discount must be greater than or equal to zero!")
    private Float discount;
    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public List<SaleModel> getSales() {
        return sales;
    }

    public void setSales(List<SaleModel> sales) {
        this.sales = sales;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Float getTotalCost() {
        return totalCost;
    }
    public void setTotalCost(Float totalCost) {
        this.totalCost = totalCost;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }
}

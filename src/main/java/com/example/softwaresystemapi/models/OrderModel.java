package com.example.softwaresystemapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderModel {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID order_id;
    @Column(nullable = false, length = 80)
    private Date createdAt;
    @Column(nullable = false, length = 80)
    private Time createdHour;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerModel customer;
    @JsonManagedReference
    @OneToMany(mappedBy = "order")
    private List<SaleModel> sales;
    @Column(nullable = false, length = 40)
    private Float totalAmount;
    @Column(nullable = false, length = 40)
    private Float totalCost;
    @Column(nullable = false, length = 40)
    private Float profit;
    @Column(name = "orderDate")
    private LocalDate orderDate;
    @Column(nullable = false, length = 80)
    private String customerName;
    @Column(nullable = false, length = 80)
    private String orderNumber;
    @Column(nullable = false, length = 80)
    private String customerPhone;
    @Column(nullable = false, length = 80)
    private String categoryName;
    @Column(nullable = true, length = 40)
    private Float discount;
    @Column(nullable = true, length = 180)
    private String customer_identification;
    public UUID getOrder_id() {
        return order_id;
    }

    public void setOrder_id(UUID order_id) {
        this.order_id = order_id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Time getCreatedHour() {
        return createdHour;
    }

    public void setCreatedHour(Time createdHour) {
        this.createdHour = createdHour;
    }

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

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Float getProfit() {
        return profit;
    }

    public void setProfit(Float profit) {
        this.profit = profit;
    }

    public Float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Float totalCost) {
        this.totalCost = totalCost;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public String getCustomer_identification() {
        return customer_identification;
    }

    public void setCustomer_identification(String customer_identification) {
        this.customer_identification = customer_identification;
    }
}

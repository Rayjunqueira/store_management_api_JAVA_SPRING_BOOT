package com.example.softwaresystemapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "customers")
public class CustomerModel {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID customer_id;
    @Column(nullable = false, length = 80)
    private String name;
    @Column(nullable = false, length = 80)
    private Time createdHour;
    @Column(nullable = false, length = 80)
    private Date createdAt;
    @Column(nullable = true, length = 380)
    private String note;
    @Column(nullable = false, length = 80)
    private String category;
    @Column(nullable = true, length = 80)
    private String cellphone1;
    @Column(nullable = true, length = 80)
    private String cellphone2;
    @Column(nullable = true, length = 80)
    private String email;
    @Column(nullable = true, length = 190)
    private Float totalSpent;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_category_id")
    private CustomerCategoryModel customercategory;
    @Column(nullable = false, length = 80)
    private Date last_order_date;
    @Column(nullable = false, length = 80)
    private Time last_order_hour;
    public UUID getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(UUID customer_id) {
        this.customer_id = customer_id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCellphone1() {
        return cellphone1;
    }

    public void setCellphone1(String cellphone1) {
        this.cellphone1 = cellphone1;
    }

    public String getCellphone2() {
        return cellphone2;
    }

    public void setCellphone2(String cellphone2) {
        this.cellphone2 = cellphone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CustomerCategoryModel getCustomercategory() {
        return customercategory;
    }

    public void setCustomercategory(CustomerCategoryModel customercategory) {
        this.customercategory = customercategory;
    }

    public Float getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(Float totalSpent) {
        this.totalSpent = totalSpent;
    }

    public Date getLast_order_date() {
        return last_order_date;
    }

    public void setLast_order_date(Date last_order_date) {
        this.last_order_date = last_order_date;
    }

    public Time getLast_order_hour() {
        return last_order_hour;
    }

    public void setLast_order_hour(Time last_order_hour) {
        this.last_order_hour = last_order_hour;
    }
}

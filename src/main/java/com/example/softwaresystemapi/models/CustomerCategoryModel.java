package com.example.softwaresystemapi.models;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "customercategories")
public class CustomerCategoryModel {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID customer_category_id;
    @Column(nullable = false, length = 80)
    private Time createdHour;
    @Column(nullable = false, length = 80)
    private Date createdAt;
    @Column(nullable = false, length = 80)
    private String name;
    @Column(nullable = true, length = 80)
    private String description;
    @OneToMany(mappedBy = "customercategory")
    private List<CustomerModel> customers;

    public UUID getCustomer_category_id() {
        return customer_category_id;
    }

    public void setCustomer_category_id(UUID customer_category_id) {
        this.customer_category_id = customer_category_id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Time getCreatedHour() {
        return createdHour;
    }

    public void setCreatedHour(Time createdHour) {
        this.createdHour = createdHour;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

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

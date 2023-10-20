package com.example.softwaresystemapi.models;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Entity
@Table(name = "categories")
public class CategoryModel {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID category_id;
    @Column(nullable = false, length = 80)
    private String name;
    @Column(nullable = false, length = 80)
    private Time createdHour;
    @Column(nullable = false, length = 80)
    private Date createdAt;
    @Column(nullable = true, length = 80)
    private String description;

    @OneToMany(mappedBy = "category")
    private List<ProductModel> products;

    public UUID getCategory_id() {
        return category_id;
    }

    public void setCategory_id(UUID category_id) {
        this.category_id = category_id;
    }

    public Time getCreatedHour() {
        return createdHour;
    }

    public void setCreatedHour(Time createdHour) {
        this.createdHour = createdHour;
    }

    public Date getCreatedAt() {
        return createdAt;
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

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }
}
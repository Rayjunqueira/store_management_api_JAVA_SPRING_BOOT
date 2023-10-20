package com.example.softwaresystemapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="products")
public class ProductModel {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID product_id;
    @Column(nullable = false, length = 80)
    private Date createdAt;
    @Column(nullable = false, length = 110)
    private String name;
    @Column(nullable = true, length = 110)
    private String description;
    @Column(nullable = true, length = 190)
    private String img;
    @Column(nullable = true, length = 190)
    private String categoryIdentification;
    @Column(nullable = true, length = 110)
    private String brand;
    @Column(nullable = false, length = 110)
    private Integer stock;
    @Column(nullable = false, length = 110)
    private String categoryName;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryModel category;
    @Column(nullable = false, length = 40)
    private Float price;
    @Column(nullable = true, length = 40)
    private Float cost;

    public UUID getProduct_id() {
        return product_id;
    }

    public void setProduct_id(UUID product_id) {
        this.product_id = product_id;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryIdentification() {
        return categoryIdentification;
    }

    public void setCategoryIdentification(String categoryIdentification) {
        this.categoryIdentification = categoryIdentification;
    }
}
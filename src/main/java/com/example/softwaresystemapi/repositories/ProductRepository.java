package com.example.softwaresystemapi.repositories;

import com.example.softwaresystemapi.models.CategoryModel;
import com.example.softwaresystemapi.models.OrderModel;
import com.example.softwaresystemapi.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {
    boolean existsByName (String name);
    List<ProductModel> findProductsByCategory(CategoryModel category);

}
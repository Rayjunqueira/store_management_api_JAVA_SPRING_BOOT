package com.example.softwaresystemapi.services;

import com.example.softwaresystemapi.models.CategoryModel;
import com.example.softwaresystemapi.models.OrderModel;
import com.example.softwaresystemapi.models.ProductModel;
import com.example.softwaresystemapi.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ProductService {
    final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductModel save(ProductModel productModel) {
        return productRepository.save(productModel);
    }

    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }
    public List<ProductModel> findAll() {
        return productRepository.findAll();
    }

    public Optional<ProductModel> findById(UUID id) {
        return productRepository.findById(id);
    }
    public List<ProductModel> findProductsByCategory(CategoryModel category) {
        return productRepository.findProductsByCategory(category);
    }

    public void delete(ProductModel productModel) {
        productRepository.delete(productModel);
    }
}
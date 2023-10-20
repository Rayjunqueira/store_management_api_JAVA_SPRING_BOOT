package com.example.softwaresystemapi.controllers;

import com.example.softwaresystemapi.dtos.CustomerDto;
import com.example.softwaresystemapi.dtos.ProductDto;
import com.example.softwaresystemapi.mappers.ProductMapper;
import com.example.softwaresystemapi.models.*;
import com.example.softwaresystemapi.services.CategoryService;
import com.example.softwaresystemapi.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/product")
public class ProductController {
    final ProductService productService;
    final CategoryService categoryService;
    final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper, CategoryService categoryService) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Object> saveProduct(@RequestBody @Valid ProductDto productDto) {
        try {
            ProductModel productModel = productMapper.toProductModel(productDto);
             CategoryModel categoryModel = categoryService.findById(productDto.getCategory().getCategory_id())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            productModel.setCategory(categoryModel);

            productModel.setCategoryName(categoryModel.getName());
            productModel.setCategoryIdentification(categoryModel.getCategory_id().toString());

            LocalDate localDate = LocalDate.now();
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            productModel.setCreatedAt(date);

            return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
        }
    }

    @Cacheable(value = "products")
    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAll());
    }

    @Cacheable(value = "products", key = "#id")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> productModelOptional = productService.findById(id);
        if (!productModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(productModelOptional.get());
    }

    @GetMapping("/findproductbycategory/{id}")
    public ResponseEntity<Object> getProductsByCategory(@PathVariable(value = "id") UUID id) {
        CategoryModel categoryModel = categoryService.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        List<ProductModel> productModelList = productService.findProductsByCategory(categoryModel);

        return ResponseEntity.status(HttpStatus.OK).body(productModelList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProductDto productDto) {
        try {
            Optional<ProductModel> productModelOptional = productService.findById(id);
            if (!productModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
            }
            ProductModel productModel = productModelOptional.get();
            productModel.setName(productDto.getName());
            productModel.setDescription(productDto.getDescription());
            productModel.setCategory(productDto.getCategory());
            productModel.setBrand(productDto.getBrand());
            productModel.setPrice(productDto.getPrice());
            productModel.setStock(productDto.getStock());

            CategoryModel categoryModel = categoryService.findById(productDto.getCategory().getCategory_id())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            productModel.setCategory(categoryModel);
            productModel.setCategoryName(categoryModel.getName());

            return ResponseEntity.status(HttpStatus.OK).body(productService.save(productModel));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The product could not be found. Please check if the sent ID is correct.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> productModelOptional = productService.findById(id);
        if (!productModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
        }
        ProductModel productModel = productModelOptional.get();

        productService.delete(productModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted succesfully!");
    }
}

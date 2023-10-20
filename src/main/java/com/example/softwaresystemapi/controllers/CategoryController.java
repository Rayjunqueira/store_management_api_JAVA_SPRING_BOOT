package com.example.softwaresystemapi.controllers;

import com.example.softwaresystemapi.dtos.CategoryDto;
import com.example.softwaresystemapi.mappers.CategoryMapper;
import com.example.softwaresystemapi.models.CategoryModel;
import com.example.softwaresystemapi.models.OrderModel;
import com.example.softwaresystemapi.models.ProductModel;
import com.example.softwaresystemapi.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/category")
public class CategoryController {

    final CategoryService categoryService;
    final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @PostMapping
    public ResponseEntity<Object> saveCategory(@RequestBody @Valid CategoryDto categoryDto) {
        try {
            if (categoryService.existsByName(categoryDto.getName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Category already exists!");
            }
            CategoryModel categoryModel = categoryMapper.toCategoryModel(categoryDto);

            LocalDate localDate = LocalDate.now();
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            categoryModel.setCreatedAt(date);

            LocalDateTime currentlyHour = LocalDateTime.now();
            Time sqlTime = java.sql.Time.valueOf(currentlyHour.toLocalTime());
            categoryModel.setCreatedHour(sqlTime);

            return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(categoryModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot create category. Check if the fields sent in your request are correct.");
        }
    }

    @GetMapping
    public ResponseEntity<List<CategoryModel>> getAllCategories() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneCategory(@PathVariable(value = "id") UUID id) {
        try {
            Optional<CategoryModel> categoryModelOptional = categoryService.findById(id);
            if (!categoryModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
            }
            return ResponseEntity.status(HttpStatus.OK).body(categoryModelOptional.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The category could not be found. Please check if the sent ID is correct.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable(value = "id") UUID id, @RequestBody @Valid CategoryDto categoryDto) {
        try {
            Optional<CategoryModel> categoryModelOptional = categoryService.findById(id);
            if (!categoryModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found!");
            }
            CategoryModel categoryModel = categoryModelOptional.get();
            categoryModel.setName(categoryDto.getName());
            categoryModel.setDescription(categoryDto.getDescription());
            categoryModel.setProducts(categoryDto.getProducts());

            return ResponseEntity.status(HttpStatus.OK).body(categoryService.save(categoryModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The category could not be found. Please check if the sent ID is correct.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable(value = "id") UUID id) {
        try {
            Optional<CategoryModel> categoryModelOptional = categoryService.findById(id);
            if (!categoryModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found!");
            }
            categoryService.delete(categoryModelOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("Category deleted succesfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The category could not be found. Please check if the sent ID is correct.");
        }
    }
}
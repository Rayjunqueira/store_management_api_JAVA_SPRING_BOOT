package com.example.softwaresystemapi.services;

import com.example.softwaresystemapi.models.CategoryModel;
import com.example.softwaresystemapi.models.CustomerCategoryModel;
import com.example.softwaresystemapi.repositories.CategoryRepository;
import com.example.softwaresystemapi.repositories.CustomerCategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerCategoryService {
    final CustomerCategoryRepository customerCategoryRepository;

    public CustomerCategoryService (CustomerCategoryRepository customerCategoryRepository) {
        this.customerCategoryRepository = customerCategoryRepository;
    }

    public boolean existsByName(String name) {
        return customerCategoryRepository.existsByName(name);
    }

    public List<CustomerCategoryModel> findAll() {
        return customerCategoryRepository.findAll();
    }

    public Optional<CustomerCategoryModel> findById(UUID id) {
        return customerCategoryRepository.findById(id);
    }
    @Transactional
    public CustomerCategoryModel save(CustomerCategoryModel customerCategoryModel) {
        return customerCategoryRepository.save(customerCategoryModel);
    }


    public void delete(CustomerCategoryModel customerCategoryModel) {
        customerCategoryRepository.delete(customerCategoryModel);
    }

}

package com.example.softwaresystemapi.repositories;

import com.example.softwaresystemapi.models.CustomerCategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerCategoryRepository extends JpaRepository<CustomerCategoryModel, UUID> {
    boolean existsByName(String name);

}

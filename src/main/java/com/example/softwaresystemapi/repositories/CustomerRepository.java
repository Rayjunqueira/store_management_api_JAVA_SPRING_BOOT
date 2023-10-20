package com.example.softwaresystemapi.repositories;

import com.example.softwaresystemapi.models.*;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerModel, UUID> {
    List<CustomerModel> findCustomersByCustomercategory(CustomerCategoryModel customerCategory);
}

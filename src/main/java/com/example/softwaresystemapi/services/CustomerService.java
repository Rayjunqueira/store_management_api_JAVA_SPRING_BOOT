package com.example.softwaresystemapi.services;

import com.example.softwaresystemapi.models.*;
import com.example.softwaresystemapi.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerModel save(CustomerModel customerModel) {
        return customerRepository.save(customerModel);
    }

    public List<CustomerModel> findAll() {
        return customerRepository.findAll();
    }

    public Optional<CustomerModel> findById(UUID id) {
        return customerRepository.findById(id);
    }

    public List<CustomerModel> findCustomersByCategory(CustomerCategoryModel customerCategory) {
        return customerRepository.findCustomersByCustomercategory(customerCategory);
    }
    public void delete(CustomerModel customerModel) {
        customerRepository.delete(customerModel);
    }
}

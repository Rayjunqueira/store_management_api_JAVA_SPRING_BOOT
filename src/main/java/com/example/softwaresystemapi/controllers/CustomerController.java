package com.example.softwaresystemapi.controllers;

import com.example.softwaresystemapi.dtos.CustomerDto;
import com.example.softwaresystemapi.mappers.CustomerMapper;
import com.example.softwaresystemapi.models.*;
import com.example.softwaresystemapi.services.CustomerCategoryService;
import com.example.softwaresystemapi.services.CustomerService;
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
@CrossOrigin(origins="*")
@RequestMapping("/customer")
public class CustomerController {
    final CustomerService customerService;
    final CustomerMapper customerMapper;
    final CustomerCategoryService customerCategoryService;
    public CustomerController(CustomerService customerService, CustomerMapper customerMapper, CustomerCategoryService customerCategoryService) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
        this.customerCategoryService = customerCategoryService;
    }

    @PostMapping
    public ResponseEntity<Object> saveCustomer(@RequestBody @Valid CustomerDto customerDto) {
        try {
            CustomerModel customerModel = customerMapper.toCustomerModel(customerDto);
            CustomerCategoryModel customerCategoryModel = customerCategoryService.findById(customerDto.getCustomercategory().getCustomer_category_id())
                    .orElseThrow(() -> new RuntimeException("Customer category not found"));
            customerModel.setCustomercategory(customerCategoryModel);

            customerModel.setCategory(customerCategoryModel.getName());
            customerModel.setTotalSpent(0.0f);

            LocalDate localDate = LocalDate.now();
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            customerModel.setCreatedAt(date);

            LocalDateTime currentlyHour = LocalDateTime.now();
            Time sqlTime = java.sql.Time.valueOf(currentlyHour.toLocalTime());
            customerModel.setCreatedHour(sqlTime);

            return ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(customerModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
        }
    }


    @Cacheable(value = "customer")
    @GetMapping
    public ResponseEntity<List<CustomerModel>> getAllCustomers() {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findAll());
    }

    @Cacheable(value = "customer", key = "#id")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneCustomer(@PathVariable(value = "id") UUID id) {
        Optional<CustomerModel> customerModelOptional = customerService.findById(id);
        if (!customerModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(customerModelOptional.get());
    }
    @GetMapping("/findcustomerbycategory/{categoryId}")
    public ResponseEntity<Object> getCustomersByCategory(@PathVariable("categoryId") UUID categoryId) {
        CustomerCategoryModel customerCategoryModel = customerCategoryService.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Customer category not found"));

        List<CustomerModel> customerModelList = customerService.findCustomersByCategory(customerCategoryModel);

        return ResponseEntity.status(HttpStatus.OK).body(customerModelList);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable(value = "id") UUID id, @RequestBody @Valid CustomerDto customerDto) {
        try {
            Optional<CustomerModel> customerModelOptional = customerService.findById(id);
            if (!customerModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found!");
            }
            CustomerModel customerModel = customerModelOptional.get();
            customerModel.setName(customerDto.getName());
            customerModel.setNote(customerDto.getNote());
            customerModel.setCellphone1(customerDto.getCellphone1());
            customerModel.setCellphone2(customerDto.getCellphone2());
            customerModel.setEmail(customerDto.getEmail());
            customerModel.setCustomercategory(customerDto.getCustomercategory());

            return ResponseEntity.status(HttpStatus.OK).body(customerService.save(customerModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The customer could not be found. Please check if the sent ID is correct.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable(value = "id") UUID id) {
        try {
            Optional<CustomerModel> customerModelOptional = customerService.findById(id);
            if (!customerModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer category not found!");
            }
            customerService.delete(customerModelOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("Customer deleted succesfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The customer could not be found. Please check if the sent ID is correct.");
        }
    }
}

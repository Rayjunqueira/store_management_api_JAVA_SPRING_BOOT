package com.example.softwaresystemapi.repositories;

import com.example.softwaresystemapi.models.CustomerModel;
import com.example.softwaresystemapi.models.OrderModel;
import com.example.softwaresystemapi.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderModel, UUID> {
    Optional<OrderModel> findByOrderNumber(String orderNumber);
    List<OrderModel> findOrdersByCustomer(CustomerModel customer);
    List<OrderModel> findOrdersBySalesProduct(ProductModel product);
    List<OrderModel> findOrdersByOrderDateBetween(LocalDate startDate, LocalDate endDate);
}

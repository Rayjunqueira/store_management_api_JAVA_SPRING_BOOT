package com.example.softwaresystemapi.services;

import com.example.softwaresystemapi.models.CustomerModel;
import com.example.softwaresystemapi.models.OrderModel;
import com.example.softwaresystemapi.models.ProductModel;
import com.example.softwaresystemapi.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderModel save(OrderModel orderModel) {
        return orderRepository.save(orderModel);
    }

    public List<OrderModel> findAll() {
        return orderRepository.findAll();
    }

    public Optional<OrderModel> findById(UUID id) {
        return orderRepository.findById(id);
    }

    public List<OrderModel> findOrdersByCustomer(CustomerModel customer) {
        return orderRepository.findOrdersByCustomer(customer);
    }
    public List<OrderModel> findOrdersByProduct(ProductModel product) {
        return orderRepository.findOrdersBySalesProduct(product);
    }
    public Optional<OrderModel> findByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }
    public void delete(OrderModel orderModel) {
        orderRepository.delete(orderModel);
    }
}

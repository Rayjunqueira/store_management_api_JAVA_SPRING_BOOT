package com.example.softwaresystemapi.controllers;

import com.example.softwaresystemapi.dtos.OrderDto;
import com.example.softwaresystemapi.mappers.OrderMapper;
import com.example.softwaresystemapi.models.*;
import com.example.softwaresystemapi.services.*;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/order")
public class OrderController {
    final CustomerService customerService;
    final SaleService saleService;
    final OrderMapper orderMapper;
    final OrderService orderService;
    final ProductService productService;
    final GenerateOrderNumberService generateOrderNumberService;

    public OrderController(CustomerService customerService, SaleService saleService, OrderMapper orderMapper, OrderService orderService, GenerateOrderNumberService generateOrderNumberService, ProductService productService) {
        this.customerService = customerService;
        this.saleService = saleService;
        this.orderMapper = orderMapper;
        this.orderService = orderService;
        this.generateOrderNumberService = generateOrderNumberService;
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Object> saveOrder(@RequestBody @Valid OrderDto orderDto) {
        try {
            OrderModel orderModel = orderMapper.toOrderModel(orderDto);
            CustomerModel customerModel = customerService.findById(orderDto.getCustomer().getCustomer_id())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));

            if (customerModel != null) {
                LocalDate localDateCustomer = LocalDate.now();
                Date dateOrderCustomer = Date.from(localDateCustomer.atStartOfDay(ZoneId.systemDefault()).toInstant());

                LocalDateTime currentlyHourCustomer = LocalDateTime.now();
                Time sqlTimeCustomerOrder = java.sql.Time.valueOf(currentlyHourCustomer.toLocalTime());

                customerModel.setLast_order_date(dateOrderCustomer);
                customerModel.setLast_order_hour(sqlTimeCustomerOrder);

                customerService.save(customerModel);
            }

            orderModel.setCustomer(customerModel);
            orderModel.setCustomerName(customerModel.getName());
            orderModel.setCustomer_identification(customerModel.getCustomer_id().toString());

            orderModel.setCustomerPhone(customerModel.getCellphone1());
            orderModel.setCategoryName(customerModel.getCategory());

            List<UUID> orderItemIds = orderDto.getSales().stream()
                    .map(SaleModel::getSale_id)
                    .collect(Collectors.toList());

            float totalPrice = 0.0f;
            float totalCost = 0.0f;
            for (UUID orderItemId: orderItemIds) {
                SaleModel saleModel = saleService.findById(orderItemId)
                        .orElseThrow(() -> new RuntimeException("Order Item not found"));
                saleModel.setOrder(orderModel);
                orderModel.getSales().add(saleModel);

                if (saleModel.getPrice() != null) {
                    totalPrice += saleModel.getPrice();
                    totalCost += saleModel.getCost();
                } else {
                    throw new RuntimeException("Invalid Order Item: Quantity and/or Price not provided.");
                }
            }

            if (totalPrice > 0.0f && totalCost > 0.0f) {
                Float discount = orderDto.getDiscount();
                if (discount != null) {
                    float totalAmount = totalPrice - discount;
                    orderModel.setTotalAmount(totalAmount);
                    orderModel.setDiscount(discount);
                    orderModel.setProfit(totalAmount - totalCost);
                    customerModel.setTotalSpent(customerModel.getTotalSpent() + totalAmount);
                } else {
                    orderModel.setProfit(totalPrice - totalCost);
                    orderModel.setTotalAmount(totalPrice);
                    customerModel.setTotalSpent(customerModel.getTotalSpent() + totalPrice);
                }
                orderModel.setTotalCost(totalCost);
            } else {
                throw new RuntimeException("Invalid Order: Total price cannot be zero or negative.");
            }

            LocalDate localDate = LocalDate.now();
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            orderModel.setCreatedAt(date);

            LocalDateTime currentlyHour = LocalDateTime.now();
            Time sqlTime = java.sql.Time.valueOf(currentlyHour.toLocalTime());
            orderModel.setCreatedHour(sqlTime);


            String generatedOrderNumber = generateOrderNumberService.generateRandomOrderNumber();

            Optional<OrderModel> listOrdersByNumber = orderService.findByOrderNumber(generatedOrderNumber);

            if (!generatedOrderNumber.isEmpty() && !listOrdersByNumber.isPresent()) {
                orderModel.setOrderNumber(generatedOrderNumber);
            } else {
                String newGeneratedOrderNumber = generateOrderNumberService.generateRandomOrderNumber();
                orderModel.setOrderNumber(newGeneratedOrderNumber);
            }

            orderModel.setOrderNumber(generateOrderNumberService.generateRandomOrderNumber());

            return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(orderModel));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot create order. Check if the fields sent in your request are correct.");
        }
    }
    @Cacheable(value = "order")
    @GetMapping
    public ResponseEntity<List<OrderModel>> getAllOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findAll());
    }

    @Cacheable(value = "order", key = "#id")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneOrder(@PathVariable(value = "id") UUID id) {
        Optional<OrderModel> orderModelOptional = orderService.findById(id);
        if (!orderModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderModelOptional.get());
    }

    @GetMapping("/findbycustomer/{id}")
    public ResponseEntity<Object> getOrdersByCustomer(@PathVariable(value = "id") UUID id) {
        CustomerModel customerModel = customerService.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<OrderModel> orderModelList = orderService.findOrdersByCustomer(customerModel);

        if (orderModelList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Orders not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(orderModelList);
    }
    @GetMapping("/findbyorder/{id}")
    public ResponseEntity<Object> getOrdersByProduct(@PathVariable(value = "id") UUID id) {
        ProductModel productModel = productService.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        List<OrderModel> orderModelListByProduct = orderService.findOrdersByProduct(productModel);

        return ResponseEntity.status(HttpStatus.OK).body(orderModelListByProduct);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable(value = "id") UUID id) {
        try {
            Optional<OrderModel> orderModelOptional = orderService.findById(id);
            if (!orderModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found!");
            }
            OrderModel orderModel = orderModelOptional.get();
            orderService.delete(orderModelOptional.get());

            Float totalAmount = orderModel.getTotalAmount();

            UUID customerId = UUID.fromString(orderModel.getCustomer_identification());
            Optional<CustomerModel> customerModelOptional = customerService.findById(customerId);

            if (!customerModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found!");
            }

            CustomerModel customerModel = customerModelOptional.get();
            customerModel.setTotalSpent(customerModel.getTotalSpent() - totalAmount);

            List<OrderModel> orderModelList = orderService.findOrdersByCustomer(customerModel);

            if (!orderModelList.isEmpty()) {
                OrderModel lastOrder = orderModelList.get(orderModelList.size() - 1);
                customerModel.setLast_order_date(lastOrder.getCreatedAt());
                customerModel.setLast_order_hour(lastOrder.getCreatedHour());
            }

            customerService.save(customerModel);

            return ResponseEntity.status(HttpStatus.OK).body("Order deleted succesfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The order could not be found. Please check if the sent ID is correct.");
        }
    }
}

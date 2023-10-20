package com.example.softwaresystemapi.controllers;

import com.example.softwaresystemapi.dtos.CustomerDto;
import com.example.softwaresystemapi.dtos.SaleDto;
import com.example.softwaresystemapi.mappers.CustomerMapper;
import com.example.softwaresystemapi.mappers.ProductMapper;
import com.example.softwaresystemapi.mappers.SaleMapper;
import com.example.softwaresystemapi.models.*;
import com.example.softwaresystemapi.services.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/sale")
public class SaleController {
    final SaleService saleService;
    final SaleMapper saleMapper;
    final OrderService orderService;
    final ProductService productService;
    final CustomerService customerService;

    public SaleController(SaleService saleService, SaleMapper saleMapper,CustomerService customerService, ProductService productService, OrderService orderService) {
        this.saleService = saleService;
        this.saleMapper = saleMapper;
        this.productService = productService;
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Object> saveSale(@RequestBody @Valid SaleDto saleDto) {
        try {
            SaleModel saleModel = saleMapper.toSaleModel(saleDto);
            CustomerModel customerModel = customerService.findById(saleDto.getCustomer().getCustomer_id())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));

            ProductModel productModel = productService.findById(saleDto.getProduct().getProduct_id())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (productModel.getCost() != null) {
                Float totalCost = productModel.getCost() * saleDto.getStock();
                saleModel.setCost(totalCost);
            }

            if (productModel.getPrice() != null && productModel.getStock() > 0) {
                Float totalPrice = productModel.getPrice() * saleDto.getStock();
                productModel.setStock(productModel.getStock() - saleDto.getStock());
                productService.save(productModel);
                saleModel.setPrice(totalPrice);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(saleService.save(saleModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
        }
    }
    @GetMapping
    public ResponseEntity<List<SaleModel>> getAllSales() {
        return ResponseEntity.status(HttpStatus.OK).body(saleService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneSale(@PathVariable(value = "id") UUID id) {
        Optional<SaleModel> saleModelOptional = saleService.findById(id);
        if (!saleModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sale not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(saleModelOptional.get());
    }

    @GetMapping("/findsalesbyorder/{id}")
    public ResponseEntity<Object> getSalesByOrder(@PathVariable(value = "id") UUID id) {
        OrderModel orderModel = orderService.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        List<SaleModel> saleModelList = saleService.findSalesByOrder(orderModel);

        return ResponseEntity.status(HttpStatus.OK).body(saleModelList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSale(@PathVariable(value = "id") UUID id, @RequestBody @Valid SaleDto saleDto) {
        try {
            Optional<SaleModel> saleModelOptional = saleService.findById(id);
            if (!saleModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sale not found!");
            }
            SaleModel saleModel = saleModelOptional.get();
            saleModel.setCustomer(saleDto.getCustomer());
            saleModel.setProduct(saleDto.getProduct());
            saleModel.setStock(saleDto.getStock());

            ProductModel productModel = productService.findById(saleDto.getProduct().getProduct_id())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (productModel.getPrice() != null) {
                Float totalPrice = productModel.getPrice() * saleDto.getStock();
                saleModel.setPrice(totalPrice);
            }

            return ResponseEntity.status(HttpStatus.OK).body(saleService.save(saleModel));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The sale could not be found. Please check if the sent ID is correct.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSale(@PathVariable(value = "id") UUID id) {
        try {
            Optional<SaleModel> saleModelOptional = saleService.findById(id);
            if (!saleModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sale  not found!");
            }
            SaleModel saleModel = saleModelOptional.get();

            ProductModel productModel = productService.findById(saleModel.getProduct().getProduct_id())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            productModel.setStock(productModel.getStock() + saleModel.getStock());

            saleService.delete(saleModelOptional.get());

            return ResponseEntity.status(HttpStatus.OK).body("Sale deleted succesfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The sale could not be found. Please check if the sent ID is correct.");
        }
    }

}

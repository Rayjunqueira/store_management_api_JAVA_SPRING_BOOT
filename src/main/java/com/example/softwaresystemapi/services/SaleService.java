package com.example.softwaresystemapi.services;

import com.example.softwaresystemapi.models.CategoryModel;
import com.example.softwaresystemapi.models.OrderModel;
import com.example.softwaresystemapi.models.ProductModel;
import com.example.softwaresystemapi.models.SaleModel;
import com.example.softwaresystemapi.repositories.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SaleService {

    final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public SaleModel save(SaleModel saleModel) {
        return saleRepository.save(saleModel);
    }

    public List<SaleModel> findAll() {
        return saleRepository.findAll();
    }

    public Optional<SaleModel> findById(UUID id) {
        return saleRepository.findById(id);
    }

    public List<SaleModel> findSalesByOrder(OrderModel order) {
        return saleRepository.findSalesByOrder(order);
    }
    public void delete(SaleModel saleModel) {
        saleRepository.delete(saleModel);
    }
}

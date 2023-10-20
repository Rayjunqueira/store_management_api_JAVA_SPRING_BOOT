package com.example.softwaresystemapi.repositories;

import com.example.softwaresystemapi.models.CategoryModel;
import com.example.softwaresystemapi.models.OrderModel;
import com.example.softwaresystemapi.models.ProductModel;
import com.example.softwaresystemapi.models.SaleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SaleRepository extends JpaRepository<SaleModel, UUID> {
    List<SaleModel> findSalesByOrder(OrderModel order);
}

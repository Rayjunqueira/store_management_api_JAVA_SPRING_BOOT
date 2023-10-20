package com.example.softwaresystemapi.repositories;

import com.example.softwaresystemapi.models.OrderModel;
import com.example.softwaresystemapi.models.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, UUID> {
    Optional<TransactionModel> findByTransactionNumber(String transactionNumber);

}

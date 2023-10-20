package com.example.softwaresystemapi.services;

import com.example.softwaresystemapi.models.OrderModel;
import com.example.softwaresystemapi.models.ProductModel;
import com.example.softwaresystemapi.models.SaleModel;
import com.example.softwaresystemapi.models.TransactionModel;
import com.example.softwaresystemapi.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {
    final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    public TransactionModel save(TransactionModel transactionModel) {
        return transactionRepository.save(transactionModel);
    }
    public Optional<TransactionModel> findById(UUID id) {
        return transactionRepository.findById(id);
    }
    public List<TransactionModel> findAll() {
        return transactionRepository.findAll();
    }
    public Optional<TransactionModel> findByTransactionNumber(String transactionNumber) {
        return transactionRepository.findByTransactionNumber(transactionNumber);
    }
    public void delete(TransactionModel transactionModel) {
        transactionRepository.delete(transactionModel);
    }

}

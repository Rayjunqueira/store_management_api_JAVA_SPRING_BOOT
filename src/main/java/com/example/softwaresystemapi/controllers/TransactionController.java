package com.example.softwaresystemapi.controllers;

import com.example.softwaresystemapi.dtos.TransactionDto;
import com.example.softwaresystemapi.mappers.TransactionMapper;
import com.example.softwaresystemapi.models.*;
import com.example.softwaresystemapi.services.GenerateOrderNumberService;
import com.example.softwaresystemapi.services.TransactionService;
import jakarta.validation.Valid;
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
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/transaction")
public class TransactionController {
    final TransactionService transactionService;
    final TransactionMapper transactionMapper;
    final GenerateOrderNumberService generateOrderNumberService;

    public TransactionController(TransactionService transactionService, TransactionMapper transactionMapper, GenerateOrderNumberService generateOrderNumberService) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
        this.generateOrderNumberService = generateOrderNumberService;
    }

    @PostMapping
    public ResponseEntity<Object> saveTransaction(@RequestBody @Valid TransactionDto transactionDto) {
        try {
            TransactionModel transactionModel = transactionMapper.toTransactionModel(transactionDto);

            LocalDate localDate = LocalDate.now();
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            transactionModel.setCreatedAt(date);

            LocalDateTime currentlyHour = LocalDateTime.now();
            Time sqlTime = java.sql.Time.valueOf(currentlyHour.toLocalTime());
            transactionModel.setCreatedHour(sqlTime);

            String generatedOrderNumber = generateOrderNumberService.generateRandomOrderNumber();

            Optional<TransactionModel> listOrdersByNumber = transactionService.findByTransactionNumber(generatedOrderNumber);

            if (!generatedOrderNumber.isEmpty() && !listOrdersByNumber.isPresent()) {
                transactionModel.setTransactionNumber(generatedOrderNumber);
            } else {
                String newGeneratedOrderNumber = generateOrderNumberService.generateRandomOrderNumber();
                transactionModel.setTransactionNumber(newGeneratedOrderNumber);
            }

            transactionModel.setTransactionNumber(generateOrderNumberService.generateRandomOrderNumber());

            return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.save(transactionModel));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
        }
    }
    @GetMapping
    public ResponseEntity<List<TransactionModel>> getAllTransactions() {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneTransaction(@PathVariable(value = "id") UUID id) {
        Optional<TransactionModel> transactionModelOptional = transactionService.findById(id);
        if (!transactionModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(transactionModelOptional.get());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTransaction(@PathVariable(value = "id") UUID id, @RequestBody @Valid TransactionDto transactionDto) {
        try {
            Optional<TransactionModel> transactionModelOptional = transactionService.findById(id);
            if (!transactionModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction not found!");
            }
            TransactionModel transactionModel = transactionModelOptional.get();
            transactionModel.setName(transactionDto.getName());
            transactionModel.setStatus(transactionDto.getStatus());
            transactionModel.setTotalValue(transactionDto.getTotalValue());

            return ResponseEntity.status(HttpStatus.OK).body(transactionService.save(transactionModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The transaction could not be found. Please check if the sent ID is correct.");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTransaction(@PathVariable(value = "id") UUID id) {
        Optional<TransactionModel> transactionModelOptional = transactionService.findById(id);
        if (!transactionModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction not found!");
        }
        TransactionModel transactionModel = transactionModelOptional.get();

        transactionService.delete(transactionModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Transaction deleted succesfully!");
    }
}

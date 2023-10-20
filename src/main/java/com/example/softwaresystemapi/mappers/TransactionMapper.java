package com.example.softwaresystemapi.mappers;

import com.example.softwaresystemapi.dtos.TransactionDto;
import com.example.softwaresystemapi.models.TransactionModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(target = "transaction_id", ignore = true)
    @Mapping(source = "name", target = "name")
    TransactionModel toTransactionModel (TransactionDto transactionDto);
}

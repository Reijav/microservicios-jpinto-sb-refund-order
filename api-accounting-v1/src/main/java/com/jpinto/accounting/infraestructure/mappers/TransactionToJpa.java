package com.jpinto.accounting.infraestructure.mappers;

import com.jpinto.accounting.domain.model.Transaction;

import com.jpinto.accounting.infraestructure.out.entity.TransactionEntity;

public class TransactionToJpa {
    public static TransactionEntity transactionToEntity(Transaction transaction){
        var transactionEntity= TransactionEntity.builder()
                .createdBy(transaction.getCreatedBy())
                .transactionDate(transaction.getTransactionDate())
                .description(transaction.getDescription())
                .id(transaction.getId())
                .build();
        return transactionEntity;
    }
}

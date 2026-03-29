package com.jpinto.accounting.infraestructure.mappers;

import com.jpinto.accounting.domain.model.Transaction;
import com.jpinto.accounting.infraestructure.out.entity.TransactionEntity;

public class TransactionJpaToDomain {
    public static Transaction toDomain(TransactionEntity transactionEntity){
        return Transaction.builder()
                .transactionLineList(transactionEntity.getTransactionLines().stream().map(TransactionLineJpaToDomain::toDomain).toList())
                .id(transactionEntity.getId())
                .transactionDate(transactionEntity.getTransactionDate())
                .description(transactionEntity.getDescription())
                .createdBy(transactionEntity.getCreatedBy())
                .build();
    }
}

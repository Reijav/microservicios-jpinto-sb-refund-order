package com.jpinto.accounting.infraestructure.mappers;

import com.jpinto.accounting.domain.model.TransactionLine;
import com.jpinto.accounting.infraestructure.out.entity.TransactionLineEntity;

public class TransactionLineJpaToDomain {
    public static TransactionLine toDomain(TransactionLineEntity transactionLineEntity){
        return TransactionLine.builder()
                .id(transactionLineEntity.getId())
                .accountId(transactionLineEntity.getAccount().getId())
                .credit(transactionLineEntity.getCredit())
                .debit(transactionLineEntity.getDebit())
                .transactionId(transactionLineEntity.getTransaction().getId())
                .build();
    }
}

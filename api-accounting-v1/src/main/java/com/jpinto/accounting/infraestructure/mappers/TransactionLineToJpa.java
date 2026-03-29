package com.jpinto.accounting.infraestructure.mappers;

import com.jpinto.accounting.domain.model.TransactionLine;
import com.jpinto.accounting.infraestructure.out.entity.AccountEntity;
import com.jpinto.accounting.infraestructure.out.entity.TransactionLineEntity;

public class TransactionLineToJpa {
    public static TransactionLineEntity toLineTransactionJpa(TransactionLine transactionLine){
        return TransactionLineEntity.builder()
                .id(transactionLine.getId())
                .account(new AccountEntity(transactionLine.getAccountId(), null, null, null))
                .credit(transactionLine.getCredit())
                .debit(transactionLine.getDebit())
                .build();
    }
}

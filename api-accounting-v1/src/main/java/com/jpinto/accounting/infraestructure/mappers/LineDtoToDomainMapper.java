package com.jpinto.accounting.infraestructure.mappers;

import com.jpinto.accounting.domain.model.TransactionLine;
import com.jpinto.accounting.infraestructure.in.dto.TransactionLineDto;

public class LineDtoToDomainMapper {

    public static TransactionLine toDomain(TransactionLineDto lineDto){
        return TransactionLine.builder()
                .transactionId(lineDto.getTransactionId())
                .accountId(lineDto.getAccountId())
                .id(lineDto.getId())
                .debit(lineDto.getDebit())
                .credit(lineDto.getCredit())
                .build();
    }
}

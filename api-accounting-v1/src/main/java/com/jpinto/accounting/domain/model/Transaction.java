package com.jpinto.accounting.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    private Long id;

    private String description;

    private LocalDate transactionDate;

    private List<TransactionLine> transactionLineList;

    private Long createdBy;
}

package com.jpinto.accounting.infraestructure.in.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionLineDto {
    private Long id;

    private Long transactionId;

    private Long accountId;

    private BigDecimal debit;

    private BigDecimal credit;
}

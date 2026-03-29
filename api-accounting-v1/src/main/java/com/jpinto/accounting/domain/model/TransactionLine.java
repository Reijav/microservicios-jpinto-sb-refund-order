package com.jpinto.accounting.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
public class TransactionLine {
    private Long id;

    private Long transactionId;

    private Long accountId;

    private BigDecimal debit;

    private BigDecimal credit;

}

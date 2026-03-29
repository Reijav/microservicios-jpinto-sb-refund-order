package com.jpinto.orchestator.client.accounting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionLineDto {
    private Long id;

    private Long transactionId;

    private Long accountId;

    private BigDecimal debit;

    private BigDecimal credit;
}

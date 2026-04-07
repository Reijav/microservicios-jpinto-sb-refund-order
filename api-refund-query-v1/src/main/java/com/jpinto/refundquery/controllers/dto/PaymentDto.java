package com.jpinto.refundquery.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class PaymentDto {
        private String id;
        private String payeeType;
        private BigDecimal amount;
        private String paymentMethod;
        private LocalDateTime paymentDate;
        private Long transactionId;
        private String state;
        private String bank;
        private String savingAccount;
}
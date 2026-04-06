package com.jpinto.refundquery.infraestructure.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class PaymentDocument{
        private String id;
        private String payeeType;
        private BigDecimal amount;
        private String paymentMethod;
        private LocalDate paymentDate;
        private Long transactionId;
        private String state;
}
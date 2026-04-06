package com.jpinto.orchestator.client.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Payment(
        String id,
        String payeeType,
        BigDecimal amount,
        String paymentMethod,
        LocalDate paymentDate,
        String transactionId,
        String state
) {}
package com.jpinto.orchestator.client.payment.dto;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PaymentResponse(
        UUID id,
        PayeeType payeeType,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        LocalDate paymentDate,
        String transactionId,
        PaymentState state
) {}

package com.jpinto.orchestator.client.payment.dto;



import java.math.BigDecimal;
import java.time.LocalDate;

public record CreatePaymentRequest(
        PayeeType payeeType,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        LocalDate paymentDate
) {}

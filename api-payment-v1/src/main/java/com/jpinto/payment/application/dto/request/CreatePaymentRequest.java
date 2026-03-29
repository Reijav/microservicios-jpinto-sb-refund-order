package com.jpinto.payment.application.dto.request;

import com.jpinto.payment.domain.model.PayeeType;
import com.jpinto.payment.domain.model.PaymentMethod;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreatePaymentRequest(

        @NotNull(message = "payeeType is required")
        PayeeType payeeType,

        @NotNull(message = "amount is required")
        @DecimalMin(value = "0.01", message = "amount must be greater than zero")
        BigDecimal amount,

        @NotNull(message = "paymentMethod is required")
        PaymentMethod paymentMethod,

        @NotNull(message = "paymentDate is required")
        LocalDate paymentDate
) {}

package com.jpinto.payment.application.dto.response;

import com.jpinto.payment.domain.model.PayeeType;
import com.jpinto.payment.domain.model.PaymentMethod;
import com.jpinto.payment.domain.model.PaymentState;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentResponse(
        UUID id,
        PayeeType payeeType,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        LocalDateTime paymentDate,
        String transactionId,
        PaymentState state
) {}

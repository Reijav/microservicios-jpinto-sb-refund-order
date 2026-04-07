package com.jpinto.refundquery.listener.order.approved.event;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record Payment(
        String id,
        String payeeType,
        BigDecimal amount,
        String paymentMethod,
        LocalDateTime paymentDate,
        Long transactionId,
        String state,
        String bank,
        String savingAccount
) {}
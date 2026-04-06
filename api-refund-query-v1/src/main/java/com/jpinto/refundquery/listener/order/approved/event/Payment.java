package com.jpinto.refundquery.listener.order.approved.event;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Payment(
        String id,
        String payeeType,
        BigDecimal amount,
        String paymentMethod,
        LocalDate paymentDate,
        Long transactionId,
        String state
) {}
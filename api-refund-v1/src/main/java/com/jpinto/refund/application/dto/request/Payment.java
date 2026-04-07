package com.jpinto.refund.application.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Payment(String id,
                      String payeeType,
                      BigDecimal amount,
                      String paymentMethod,
                      LocalDate paymentDate,
                      String transactionId,
                      String state,
                      String bank,
                      String savingAccount) {
}

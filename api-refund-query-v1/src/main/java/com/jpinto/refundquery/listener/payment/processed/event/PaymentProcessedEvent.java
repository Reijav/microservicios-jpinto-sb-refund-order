package com.jpinto.refundquery.listener.payment.processed.event;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PaymentProcessedEvent {
    private String paymentId;
    private LocalDateTime paymentDate;
}

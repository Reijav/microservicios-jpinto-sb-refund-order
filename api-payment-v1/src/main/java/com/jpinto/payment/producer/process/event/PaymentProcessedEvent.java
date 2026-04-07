package com.jpinto.payment.producer.process.event;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PaymentProcessedEvent {
    private String paymentId;
    private LocalDateTime paymentDate;
}

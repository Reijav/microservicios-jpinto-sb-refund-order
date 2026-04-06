package com.jpinto.accounting.producer.create.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTransactionEvent {
    private String orderRefundId;
    private Long transactionId;
}

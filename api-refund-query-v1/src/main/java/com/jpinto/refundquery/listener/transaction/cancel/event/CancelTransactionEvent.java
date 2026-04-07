package com.jpinto.refundquery.listener.transaction.cancel.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CancelTransactionEvent {
    private String orderRefundId;
    private Long transactionId;
}

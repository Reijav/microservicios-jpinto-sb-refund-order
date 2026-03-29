package com.jpinto.orchestator.client.payment.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProcessPaymentDto {
    private long transactionId;
}

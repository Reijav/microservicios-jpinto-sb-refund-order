package com.jpinto.orchestator.client.accounting.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestCancelTransactionDto {
    private String idOrderRefund;
    private Long idTransaction;
}

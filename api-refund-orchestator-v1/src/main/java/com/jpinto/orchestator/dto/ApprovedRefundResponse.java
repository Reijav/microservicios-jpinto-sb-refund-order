package com.jpinto.orchestator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class ApprovedRefundResponse {
    private UUID idOrderRefund;
    private UUID idPayment;
    private Long idTransactionAccount;
}

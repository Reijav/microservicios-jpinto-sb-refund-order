package com.jpinto.orchestator.client.refund.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RollbackStateRequest {
    private RefundState rollbackRefundState;
}

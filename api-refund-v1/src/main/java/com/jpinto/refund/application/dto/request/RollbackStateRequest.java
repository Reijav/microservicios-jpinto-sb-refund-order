package com.jpinto.refund.application.dto.request;

import com.jpinto.refund.domain.model.RefundState;
import lombok.Data;

@Data
public class RollbackStateRequest {
    private RefundState rollbackRefundState;
}

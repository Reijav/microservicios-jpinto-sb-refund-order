package com.jpinto.orchestator.client.refund.dto;

import java.util.UUID;

public record RejectRefundRequest(
        Long approverId,
        UUID orderRefundId,
        String observation
) {}

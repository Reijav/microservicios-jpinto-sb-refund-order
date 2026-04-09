package com.jpinto.orchestator.dto;

import java.util.UUID;

public record RejectRefundOrchestatorRequest(
        UUID orderRefundId,
        String observation
) {}

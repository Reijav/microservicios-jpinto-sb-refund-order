package com.jpinto.refund.application.dto.request;

import java.util.UUID;

public record RejectRefundRequest(
        UUID orderRefundId,
        String observation
) {}

package com.jpinto.orchestator.client.refund.dto;

import java.util.UUID;

public record ApproveRefundRequest(
        Long approverId,
        UUID orderRefundId
) {}

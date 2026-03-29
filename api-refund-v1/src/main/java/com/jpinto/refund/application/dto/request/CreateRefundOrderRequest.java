package com.jpinto.refund.application.dto.request;

import java.util.List;

public record CreateRefundOrderRequest(
        Long employeeId,
        Long supervisorId,
        Long motiveId,
        List<CreateRefundBillRequest> bills
) {}

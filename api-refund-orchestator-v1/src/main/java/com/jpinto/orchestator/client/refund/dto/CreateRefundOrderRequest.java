package com.jpinto.orchestator.client.refund.dto;


import java.util.List;

public record CreateRefundOrderRequest(
        Long employeeId,
        Long supervisorId,
        Long motiveId,
        List<CreateRefundBillRequest> bills
) {}

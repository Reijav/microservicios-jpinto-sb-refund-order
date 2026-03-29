package com.jpinto.orchestator.dto;

import com.jpinto.orchestator.client.refund.dto.CreateRefundBillRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderRefundRequest {
    private Long employeeId;
    private Long motiveId;
    private List<CreateRefundBillRequest> bills;
}

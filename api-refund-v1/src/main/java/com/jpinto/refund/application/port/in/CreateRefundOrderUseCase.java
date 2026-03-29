package com.jpinto.refund.application.port.in;

import com.jpinto.refund.application.dto.request.CreateRefundOrderRequest;
import com.jpinto.refund.application.dto.response.RefundOrderResponse;

public interface CreateRefundOrderUseCase {

    RefundOrderResponse createRefundOrder(CreateRefundOrderRequest request);
}

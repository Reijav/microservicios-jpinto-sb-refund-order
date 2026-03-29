package com.jpinto.orchestator.services.sagaapprove;

import com.jpinto.orchestator.client.accounting.dto.ResponseCreateTransactionDto;
import com.jpinto.orchestator.client.payment.dto.PaymentResponse;
import com.jpinto.orchestator.client.refund.dto.ApproveRefundRequest;
import com.jpinto.orchestator.client.refund.dto.RefundOrderResponse;
import com.jpinto.orchestator.client.talenthuman.dto.ResponseEmployee;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApproveOrderRefundSagaContext {
    private ApproveRefundRequest approveRefundRequest;
    private RefundOrderResponse refundOrderResponse;
    private ResponseEmployee empleado;
    private ResponseEmployee supervisor;
    private PaymentResponse paymentResponse;
    private ResponseCreateTransactionDto createTransactionDto;

    public ApproveOrderRefundSagaContext(ApproveRefundRequest approveRefundRequest){
        this.approveRefundRequest=approveRefundRequest;
    }
}

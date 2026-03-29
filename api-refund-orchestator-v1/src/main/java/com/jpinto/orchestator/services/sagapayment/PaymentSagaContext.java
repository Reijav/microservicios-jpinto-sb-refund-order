package com.jpinto.orchestator.services.sagapayment;

import com.jpinto.orchestator.client.accounting.dto.ResponseCreateTransactionDto;
import com.jpinto.orchestator.client.payment.dto.PaymentResponse;
import com.jpinto.orchestator.client.refund.dto.RefundOrderResponse;
import com.jpinto.orchestator.client.talenthuman.dto.ResponseEmployee;
import com.jpinto.orchestator.dto.PayPaymentRequest;
import lombok.Data;

import java.util.UUID;

@Data
public class PaymentSagaContext {
  //  private UUID paymentId;
    private PayPaymentRequest payPaymentRequest;
    private PaymentResponse paymentResponse;
    private RefundOrderResponse refundOrderResponse;
    private ResponseEmployee empleado;
    private ResponseEmployee supervisor;
    private ResponseCreateTransactionDto transactionDto;

    public PaymentSagaContext(PayPaymentRequest payPaymentRequest){
        this.payPaymentRequest=payPaymentRequest;
    }

}

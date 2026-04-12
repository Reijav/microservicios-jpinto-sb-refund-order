package com.jpinto.refund.adapter.rest;

import com.jpinto.refund.application.dto.request.*;
import com.jpinto.refund.application.dto.response.RefundOrderResponse;
import com.jpinto.refund.application.port.in.ChangeRefundStateUseCase;
import com.jpinto.refund.application.port.in.CreateRefundOrderUseCase;
import com.jpinto.refund.application.port.in.GetRefundOrderUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/refund-orders")
public class RefundOrderController {

    private final CreateRefundOrderUseCase createRefundOrderUseCase;
    private final ChangeRefundStateUseCase changeRefundStateUseCase;
    private final GetRefundOrderUseCase getRefundOrderUseCase;

    public RefundOrderController(CreateRefundOrderUseCase createRefundOrderUseCase,
                                 ChangeRefundStateUseCase changeRefundStateUseCase,
                                 GetRefundOrderUseCase getRefundOrderUseCase) {
        this.createRefundOrderUseCase = createRefundOrderUseCase;
        this.changeRefundStateUseCase = changeRefundStateUseCase;
        this.getRefundOrderUseCase = getRefundOrderUseCase;
    }

    @PostMapping
    public ResponseEntity<RefundOrderResponse> create(@RequestBody CreateRefundOrderRequest request,@RequestHeader Map<String, String> headers) {
        headers.forEach((key, value) -> {
            log.info(String.format("Header '%s' = %s", key, value));
        });

        RefundOrderResponse response = createRefundOrderUseCase.createRefundOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefundOrderResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(getRefundOrderUseCase.getRefundOrderById(id));
    }

    @GetMapping
    public ResponseEntity<List<RefundOrderResponse>> getAll() {
        return ResponseEntity.ok(getRefundOrderUseCase.getAllRefundOrders());
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<RefundOrderResponse> approve(@PathVariable UUID id) {
        return ResponseEntity.ok(changeRefundStateUseCase.approveRefund(id));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<RefundOrderResponse> reject(@PathVariable UUID id,
                                                      @RequestBody RejectRefundRequest request) {
        return ResponseEntity.ok(changeRefundStateUseCase.rejectRefund(id, request));
    }

    @PutMapping("/{id}/generate-payment-order-compensation")
    public ResponseEntity<RefundOrderResponse> registerPaymentOrderByCompensation(@PathVariable UUID id, @RequestBody MarkPayRequest request) {
        return ResponseEntity.ok(changeRefundStateUseCase.registerPaymentOrderByCompensation(id, request));
    }


    @PutMapping("/{id}/generate-payment-order")
    public ResponseEntity<RefundOrderResponse> registerPaymentOrder(@PathVariable UUID id, @RequestBody MarkPayRequest request) {
        return ResponseEntity.ok(changeRefundStateUseCase.registerPaymentOrder(id, request));
    }



    @PutMapping("/{id}/mark-as-payed")
    public ResponseEntity<RefundOrderResponse> markAsPayed(@PathVariable UUID id) {
        return ResponseEntity.ok(changeRefundStateUseCase.markAsPayed(id));
    }

    @GetMapping("/get-by-paymentid/{paymentId}")
    public ResponseEntity<RefundOrderResponse> getByPaymentId(@PathVariable String paymentId){
        return ResponseEntity.ok(getRefundOrderUseCase.getByPaymentId(paymentId));
    }


    @PutMapping("/{id}/rollback-state")
    public ResponseEntity<RefundOrderResponse> rollbackState(@PathVariable UUID id,
                                                       @RequestBody RollbackStateRequest request) {
        return ResponseEntity.ok(changeRefundStateUseCase.roolbackState(id, request.getRollbackRefundState()));
    }
}

package com.jpinto.refund.adapter.rest;

import com.jpinto.refund.application.dto.request.ApproveRefundRequest;
import com.jpinto.refund.application.dto.request.CreateRefundOrderRequest;
import com.jpinto.refund.application.dto.request.MarkAsPayedRequest;
import com.jpinto.refund.application.dto.request.RollbackStateRequest;
import com.jpinto.refund.application.dto.response.RefundOrderResponse;
import com.jpinto.refund.application.port.in.ChangeRefundStateUseCase;
import com.jpinto.refund.application.port.in.CreateRefundOrderUseCase;
import com.jpinto.refund.application.port.in.GetRefundOrderUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    // ─── CRUD ────────────────────────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<RefundOrderResponse> create(@RequestBody CreateRefundOrderRequest request) {
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

    // ─── STATE TRANSITIONS ───────────────────────────────────────────────────

    @PutMapping("/{id}/approve")
    public ResponseEntity<RefundOrderResponse> approve(@PathVariable UUID id,
                                                       @RequestBody ApproveRefundRequest request) {
        return ResponseEntity.ok(changeRefundStateUseCase.approveRefund(id, request));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<RefundOrderResponse> reject(@PathVariable UUID id,
                                                      @RequestBody ApproveRefundRequest request) {
        return ResponseEntity.ok(changeRefundStateUseCase.rejectRefund(id, request));
    }

    @PutMapping("/{id}/generate-payment-order")
    public ResponseEntity<RefundOrderResponse> generatePaymentOrder(@PathVariable UUID id, @RequestBody   MarkAsPayedRequest request) {
        return ResponseEntity.ok(changeRefundStateUseCase.generatePaymentOrder(id, request));
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

package com.jpinto.payment.adapter.rest;

import com.jpinto.payment.application.dto.request.CreatePaymentRequest;
import com.jpinto.payment.application.dto.request.ProcessPaymentDto;
import com.jpinto.payment.application.dto.response.PaymentResponse;
import com.jpinto.payment.application.port.in.CancelPaymentUseCase;
import com.jpinto.payment.application.port.in.CreatePaymentUseCase;
import com.jpinto.payment.application.port.in.GetPaymentUseCase;
import com.jpinto.payment.application.port.in.ProcessPaymentUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final CreatePaymentUseCase createPaymentUseCase;
    private final CancelPaymentUseCase cancelPaymentUseCase;
    private final GetPaymentUseCase    getPaymentUseCase;
    private final ProcessPaymentUseCase processPaymentUseCase;

    public PaymentController(CreatePaymentUseCase createPaymentUseCase,
                             CancelPaymentUseCase cancelPaymentUseCase,
                             GetPaymentUseCase getPaymentUseCase, ProcessPaymentUseCase processPaymentUseCase) {
        this.createPaymentUseCase = createPaymentUseCase;
        this.cancelPaymentUseCase = cancelPaymentUseCase;
        this.getPaymentUseCase    = getPaymentUseCase;
        this.processPaymentUseCase= processPaymentUseCase;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> create(@Valid @RequestBody CreatePaymentRequest request) {
        PaymentResponse response = createPaymentUseCase.createPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(getPaymentUseCase.getPaymentById(UUID.fromString(id)));
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getAll() {
        return ResponseEntity.ok(getPaymentUseCase.getAllPayments());
    }

    // ─── STATE TRANSITIONS ────────────────────────────────────────────────────

    @PutMapping("/{id}/cancel")
    public ResponseEntity<PaymentResponse> cancel(@PathVariable String id) {
        return ResponseEntity.ok(cancelPaymentUseCase.cancelPayment(UUID.fromString(id)));
    }

    @PutMapping("/{id}/process")
    public ResponseEntity<PaymentResponse> processPayment(@PathVariable String id, @RequestBody ProcessPaymentDto processPaymentDto ) {
        return ResponseEntity.ok(processPaymentUseCase.processPayment(UUID.fromString(id), processPaymentDto.getTransactionId()));
    }
}

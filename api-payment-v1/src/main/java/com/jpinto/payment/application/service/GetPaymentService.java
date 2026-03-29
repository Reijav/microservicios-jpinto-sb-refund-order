package com.jpinto.payment.application.service;

import com.jpinto.payment.application.dto.response.PaymentResponse;
import com.jpinto.payment.application.mapper.PaymentMapper;
import com.jpinto.payment.application.port.in.GetPaymentUseCase;
import com.jpinto.payment.domain.exception.PaymentNotFoundException;
import com.jpinto.payment.domain.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class GetPaymentService implements GetPaymentUseCase {

    private final PaymentRepository paymentRepository;

    public GetPaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentResponse getPaymentById(UUID id) {
        return paymentRepository.findById(id)
                .map(PaymentMapper::toResponse)
                .orElseThrow(() -> new PaymentNotFoundException(id));
    }

    @Override
    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(PaymentMapper::toResponse)
                .toList();
    }
}

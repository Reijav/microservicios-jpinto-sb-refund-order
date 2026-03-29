package com.jpinto.payment.application.service;

import com.jpinto.payment.application.dto.response.PaymentResponse;
import com.jpinto.payment.application.mapper.PaymentMapper;
import com.jpinto.payment.application.port.in.CancelPaymentUseCase;
import com.jpinto.payment.domain.exception.PaymentNotFoundException;
import com.jpinto.payment.domain.model.Payment;
import com.jpinto.payment.domain.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class CancelPaymentService implements CancelPaymentUseCase {

    private final PaymentRepository paymentRepository;

    public CancelPaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentResponse cancelPayment(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
        payment.cancel();
        return PaymentMapper.toResponse(paymentRepository.save(payment));
    }
}

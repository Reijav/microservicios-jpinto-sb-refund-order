package com.jpinto.payment.application.service;

import com.jpinto.payment.application.dto.request.CreatePaymentRequest;
import com.jpinto.payment.application.dto.response.PaymentResponse;
import com.jpinto.payment.application.mapper.PaymentMapper;
import com.jpinto.payment.application.port.in.CreatePaymentUseCase;
import com.jpinto.payment.domain.model.Payment;
import com.jpinto.payment.domain.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreatePaymentService implements CreatePaymentUseCase {

    private final PaymentRepository paymentRepository;

    public CreatePaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentResponse createPayment(CreatePaymentRequest request) {
        Payment payment = Payment.create(
                request.payeeType(),
                request.amount(),
                request.paymentMethod(),
                request.paymentDate()
        );
        return PaymentMapper.toResponse(paymentRepository.save(payment));
    }
}

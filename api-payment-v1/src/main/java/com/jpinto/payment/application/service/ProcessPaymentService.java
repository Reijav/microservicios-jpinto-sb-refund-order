package com.jpinto.payment.application.service;

import com.jpinto.payment.application.dto.response.PaymentResponse;
import com.jpinto.payment.application.mapper.PaymentMapper;
import com.jpinto.payment.application.port.in.ProcessPaymentUseCase;
import com.jpinto.payment.domain.exception.PaymentNotFoundException;
import com.jpinto.payment.domain.model.Payment;
import com.jpinto.payment.domain.repository.PaymentRepository;
import com.jpinto.payment.producer.process.ProcessPaymentProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ProcessPaymentService implements ProcessPaymentUseCase {
    private final PaymentRepository paymentRepository;
    private final ProcessPaymentProducer processPaymentProducer;

    @Override
    public PaymentResponse processPayment(UUID paymentId, Long transactionalId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
         payment.process(transactionalId.toString());
        processPaymentProducer.produce(paymentId.toString(), payment.getPaymentDate());
        return PaymentMapper.toResponse(paymentRepository.save(payment));
    }
}

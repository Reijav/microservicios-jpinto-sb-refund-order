package com.jpinto.payment.domain.repository;

import com.jpinto.payment.domain.model.Payment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {

    Payment save(Payment payment);

    Optional<Payment> findById(UUID id);

    List<Payment> findAll();

    void deleteById(UUID id);
}

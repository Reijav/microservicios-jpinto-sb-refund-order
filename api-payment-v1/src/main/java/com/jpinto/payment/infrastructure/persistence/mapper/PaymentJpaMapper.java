package com.jpinto.payment.infrastructure.persistence.mapper;

import com.jpinto.payment.domain.model.Payment;
import com.jpinto.payment.infrastructure.persistence.entity.PaymentJpaEntity;

public class PaymentJpaMapper {

    private PaymentJpaMapper() {}

    public static PaymentJpaEntity toJpaEntity(Payment domain) {
        return new PaymentJpaEntity(
                domain.getId(),
                domain.getPayeeType(),
                domain.getAmount(),
                domain.getPaymentMethod(),
                domain.getPaymentDate(),
                domain.getTransactionId(),
                domain.getState(),
                domain.getBank(),
                domain.getSavingAccount()
        );
    }

    public static Payment toDomain(PaymentJpaEntity entity) {
        return new Payment(
                entity.getId(),
                entity.getPayeeType(),
                entity.getAmount(),
                entity.getPaymentMethod(),
                entity.getPaymentDate(),
                entity.getTransactionId(),
                entity.getState(),
                entity.getBank(),
                entity.getSavingAccount()
        );
    }
}

package com.jpinto.refund.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "refund_bill")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefundBillJpaEntity {

    @Id
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refund_id", nullable = false)
    private RefundOrderJpaEntity refundOrder;

    @Column(name = "provider_ruc", nullable = false, length = 20)
    private String providerRuc;

    @Column(name = "provider_name", nullable = false)
    private String providerName;

    @Column(name = "bill_number", nullable = false, length = 50)
    private String billNumber;

    @Column(name = "detail", columnDefinition = "TEXT")
    private String detail;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal value;

    @Column(name = "bill_file")
    private String billFile;
}

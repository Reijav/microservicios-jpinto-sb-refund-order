package com.jpinto.refund.infrastructure.persistence.entity;

import com.jpinto.refund.domain.model.RefundState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "refund_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefundOrderJpaEntity {

    @Id
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "date_order", nullable = false)
    private LocalDate dateOrder;

    @Column(name = "motive_id", nullable = false)
    private Long motiveId;

    @Column(name = "total_value", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalValue;

    @Column(name = "approver_id")
    private Long approverId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RefundState state;

    @Column(name = "payment_id")
    private String paymentId;

    @Column(name = "observation")
    private String observation;

    @OneToMany(mappedBy = "refundOrder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RefundBillJpaEntity> bills = new ArrayList<>();
}

package com.jpinto.refund.domain.model;

import com.jpinto.refund.domain.exception.InvalidRefundStateTransitionException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class RefundOrder {

    private final UUID id;
    private final Long employeeId;
    private final String employeeName;
    private final LocalDate dateOrder;
    private final Long motiveId;
    private BigDecimal totalValue;
    private Long approverId;
    private final String approverName;
    private RefundState state;
    private String paymentId;
    private String observation;
    private final List<RefundBill> bills;

    // Factory method for creating new refund orders
    public static RefundOrder create(Long employeeId, String employeeName,  Long supervisorId,String supervisorName, Long motiveId, List<RefundBill> bills) {
        if (employeeId == null) throw new IllegalArgumentException("employeeId must not be null");
        if (supervisorId == null) throw new IllegalArgumentException("supervisorId must not be null");
        if (motiveId == null) throw new IllegalArgumentException("motiveId must not be null");
        if (bills == null || bills.isEmpty()) throw new IllegalArgumentException("bills must not be empty");

        BigDecimal total = bills.stream()
                .map(RefundBill::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new RefundOrder(UUID.randomUUID(), employeeId,employeeName,  LocalDate.now(),
                motiveId, total, supervisorId,supervisorName, RefundState.CREATED, null, null, new ArrayList<>(bills));
    }

    // Full constructor for reconstitution from persistence
    public RefundOrder(UUID id, Long employeeId, String employeeName, LocalDate dateOrder, Long motiveId,
                       BigDecimal totalValue, Long approverId,String approverName, RefundState state,
                       String paymentId, String observation,List<RefundBill> bills) {
        this.id = id;
        this.employeeId = employeeId;
        this.employeeName=employeeName;
        this.dateOrder = dateOrder;
        this.motiveId = motiveId;
        this.totalValue = totalValue;
        this.approverId = approverId;
        this.approverName=approverName;
        this.state = state;
        this.paymentId = paymentId;
        this.observation=observation;
        this.bills = bills != null ? new ArrayList<>(bills) : new ArrayList<>();
    }

    // Domain behavior: approve the refund
    public void approve() {
        if (this.state != RefundState.CREATED) {
            throw new InvalidRefundStateTransitionException(this.state, RefundState.APPROVED);
        }
        this.state = RefundState.APPROVED;
    }



    // Domain behavior: roolback state the refund
    public void roolbackState( RefundState rollbackState) {
        if (this.state == RefundState.CREATED) {
            throw new InvalidRefundStateTransitionException(this.state, rollbackState);
        }

        if(rollbackState==RefundState.CREATED){
            this.paymentId=null;
        }

        this.state = rollbackState;
    }

    // Domain behavior: reject the refund
    public void reject(String observation) {
        if (this.state != RefundState.CREATED) {
            throw new InvalidRefundStateTransitionException(this.state, RefundState.REJECTED);
        }

        this.observation = observation;
        this.state = RefundState.REJECTED;
    }

    // Domain behavior: generate payment order
    public void generatePaymentOrder(String paymentId) {
        if (this.state != RefundState.APPROVED) {
            throw new InvalidRefundStateTransitionException(this.state, RefundState.ORDERPAYGENERATED);
        }
        if (paymentId == null || paymentId.isBlank()) throw new IllegalArgumentException("paymentId must not be blank");
        this.paymentId=paymentId;
        this.state = RefundState.ORDERPAYGENERATED;
    }

    // Domain behavior: mark as payed
    public void markAsPayed() {
        if (this.state != RefundState.ORDERPAYGENERATED) {
            throw new InvalidRefundStateTransitionException(this.state, RefundState.PAYED);
        }

        this.state = RefundState.PAYED;
    }

    public UUID getId() { return id; }
    public Long getEmployeeId() { return employeeId; }
    public LocalDate getDateOrder() { return dateOrder; }
    public Long getMotiveId() { return motiveId; }
    public BigDecimal getTotalValue() { return totalValue; }
    public Long getApproverId() { return approverId; }
    public RefundState getState() { return state; }
    public String getPaymentId() { return paymentId; }
    public String getObservation() { return observation;}

    public String getEmployeeName() {
        return employeeName;
    }

    public String getApproverName() {
        return approverName;
    }

    public List<RefundBill> getBills() { return Collections.unmodifiableList(bills); }
}

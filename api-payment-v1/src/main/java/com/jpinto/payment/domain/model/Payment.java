package com.jpinto.payment.domain.model;

import com.jpinto.payment.domain.exception.InvalidPaymentStateTransitionException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Payment {

    private final UUID id;
    private final PayeeType payeeType;
    private final BigDecimal amount;
    private final PaymentMethod paymentMethod;
    private LocalDateTime paymentDate;
    private String transactionId;
    private PaymentState state;
    private String bank;
    private String savingAccount;

    // Factory method — enforces invariants on creation
    public static Payment create(PayeeType payeeType, BigDecimal amount,
                                 PaymentMethod paymentMethod, LocalDateTime paymentDate, String bank,String savingAccoung) {
        if (payeeType == null)     throw new IllegalArgumentException("payeeType must not be null");
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("amount must be positive");
        if (paymentMethod == null) throw new IllegalArgumentException("paymentMethod must not be null");

        return new Payment(UUID.randomUUID(), payeeType, amount, paymentMethod,
                paymentDate, null, PaymentState.CREATED, bank, savingAccoung );
    }

    // Full constructor — used for reconstitution from persistence
    public Payment(UUID id, PayeeType payeeType, BigDecimal amount, PaymentMethod paymentMethod,
                   LocalDateTime paymentDate, String transactionId, PaymentState state, String bank,String savingAccount) {
        this.id            = id;
        this.payeeType     = payeeType;
        this.amount        = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate   = paymentDate;
        this.transactionId = transactionId;
        this.state         = state;
        this.bank           = bank;
        this.savingAccount  = savingAccount;
    }

    // Domain behaviour: process the payment (assign transaction ID)
    public void process(String transactionId) {
        if (this.state != PaymentState.CREATED) {
            throw new InvalidPaymentStateTransitionException(this.state, PaymentState.PROCESSED);
        }
        if (transactionId == null || transactionId.isBlank())
            throw new IllegalArgumentException("transactionId must not be blank");
        this.transactionId = transactionId;
        this.paymentDate = LocalDateTime.now();
        this.state = PaymentState.PROCESSED;
    }

    // Domain behaviour: cancel the payment
    public void cancel() {
        if (this.state == PaymentState.CANCELLED) {
            throw new InvalidPaymentStateTransitionException(this.state, PaymentState.CANCELLED);
        }
        if (this.state == PaymentState.PROCESSED) {
            throw new InvalidPaymentStateTransitionException(this.state, PaymentState.CANCELLED);
        }
        this.transactionId=null;
        this.state = PaymentState.CANCELLED;
    }

    public UUID getId()                  { return id; }
    public PayeeType getPayeeType()      { return payeeType; }
    public BigDecimal getAmount()        { return amount; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public LocalDateTime getPaymentDate()    { return paymentDate; }
    public String getTransactionId()     { return transactionId; }
    public PaymentState getState()       { return state; }
    public String getBank() {return  bank;}
    public String getSavingAccount() {return savingAccount;}
}

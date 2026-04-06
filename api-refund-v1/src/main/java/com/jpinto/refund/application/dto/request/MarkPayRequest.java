package com.jpinto.refund.application.dto.request;

public record MarkAsPayedRequest(
        PaymentResponse payment
) {}

package com.jpinto.orchestator.client.refund.dto;

import com.jpinto.orchestator.client.payment.dto.Payment;

public record MarkAsPayedRequest(
        Payment payment
) {}

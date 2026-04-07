package com.jpinto.refundquery.listener.payment.processed;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpinto.refundquery.infraestructure.repository.RefundOrderRepository;
import com.jpinto.refundquery.listener.payment.processed.event.PaymentProcessedEvent;
import com.jpinto.refundquery.listener.payment.processed.event.PaymentState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentProcessedListener {
    private final RefundOrderRepository refundOrderRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "refund-order.payment-processed-v1", groupId = "refund-order.payment-processed-v1.group")
    public void handle(String message) {
        try {

            PaymentProcessedEvent event= objectMapper.readValue(message, PaymentProcessedEvent.class);
            var refundOrder=refundOrderRepository.findByPayment_Id(event.getPaymentId());

            if(refundOrder.isEmpty()){
                throw new RuntimeException("Error orden de reembolso no encontrado :  " + event.getPaymentId());
            }

            refundOrder.get().setState("PAYED");
            refundOrder.get().getPayment().setState(PaymentState.PROCESSED.name());
            refundOrder.get().getPayment().setPaymentDate(event.getPaymentDate());
            refundOrderRepository.save(refundOrder.get());

            log.info("Consumo mensaje {}",message);
        } catch (Exception ex) {
            log.error("Error trying to process  Created Transaction Event", ex);
        }
    }
}

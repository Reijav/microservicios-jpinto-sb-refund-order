package com.jpinto.refundquery.listener.transaction.create;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.jpinto.refundquery.infraestructure.repository.RefundOrderRepository;
import com.jpinto.refundquery.listener.order.created.event.RefundOrderCreatedEvent;

import com.jpinto.refundquery.listener.transaction.create.event.CreateTransactionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreatedTransactionListener {

    private final RefundOrderRepository refundOrderRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "refund-order.transaction-created.v1", groupId = "refund-order.transaction-created.v1.group")
    public void handle(String message) {
        try {

            CreateTransactionEvent event= objectMapper.readValue(message, CreateTransactionEvent.class);
            var refundOrder=refundOrderRepository.findById(event.getOrderRefundId());

            if(refundOrder.isEmpty()){
                throw new RuntimeException("Error orden de reembolso no encontrado :  " + event.getTransactionId());
            }

            refundOrder.get().getPayment().setTransactionId(event.getTransactionId());
            refundOrderRepository.save(refundOrder.get());

            log.info("Consumo mensaje {}",message);
        } catch (Exception ex) {
            log.error("Error trying to process  Created Transaction Event", ex);
        }
    }
}

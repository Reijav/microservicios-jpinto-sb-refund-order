package com.jpinto.payment.producer.created;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpinto.payment.producer.created.event.PaymentCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreatedPaymentProducer {
    private final static String TOPIC="refund-order.payment-created-v1";
    private final KafkaTemplate<String,Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void produce(String orderRefundId, String paymentId){
        try{
            PaymentCreatedEvent event = PaymentCreatedEvent.builder()
                    .orderRefundId(orderRefundId)
                    .paymentId(paymentId)
                    .build();

            kafkaTemplate.send(TOPIC, orderRefundId, objectMapper.writeValueAsString(event));
            log.info("Order refund approved Event send");

        }catch (Exception ex){
            log.error("Error producing Create payment event");
        }
    }
}

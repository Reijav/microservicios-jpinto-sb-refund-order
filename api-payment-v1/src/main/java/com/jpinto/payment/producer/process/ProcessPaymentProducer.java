package com.jpinto.payment.producer.process;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpinto.payment.producer.process.event.PaymentProcessedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class ProcessPaymentProducer {

    private final static String TOPIC="refund-order.payment-processed-v1";
    private final KafkaTemplate<String,Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void produce(String paymentId){
        try{
            PaymentProcessedEvent event = PaymentProcessedEvent.builder()
                    .paymentId(paymentId)
                    .build();

            kafkaTemplate.send(TOPIC, paymentId, objectMapper.writeValueAsString(event));
            log.info("Order refund approved Event send");

        }catch (Exception ex){
            log.error("Error producing Create payment event");
        }
    }
}

package com.jpinto.accounting.producer.create;

import com.jpinto.accounting.producer.create.event.CreateTransactionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreatedTransactionProducer {
    private static final String TOPIC = "refund-order.transaction-created.v1";

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper mapper;

    public void produce(String orderRefundId, Long transactionId) {

        try {
            CreateTransactionEvent event = CreateTransactionEvent.builder()
                    .orderRefundId(orderRefundId)
                    .transactionId(transactionId)
                    .build();

            kafkaTemplate.send(TOPIC, orderRefundId, mapper.writeValueAsString(event));
            log.info("Order refund approved Event send");
        } catch (Exception ex) {
            log.error("Error trying to send Refund Order Created Event", ex);
        }

    }
}

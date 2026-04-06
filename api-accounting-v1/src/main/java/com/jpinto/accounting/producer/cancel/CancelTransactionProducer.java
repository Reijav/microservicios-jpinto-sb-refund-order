package com.jpinto.accounting.producer.cancel;

import com.jpinto.accounting.producer.cancel.event.CancelTransactionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@Slf4j
@RequiredArgsConstructor
public class CancelTransactionProducer {
    private static final String TOPIC = "refund-order.transaction-cancel.v1";
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper mapper;

    public void produce(String orderRefundId, Long transactionId) {

        try {
            CancelTransactionEvent event = CancelTransactionEvent.builder()
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

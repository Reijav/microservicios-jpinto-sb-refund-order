package com.jpinto.refund.producer.order.rejected;

import com.jpinto.refund.producer.order.rejected.event.RefundOrderRejectedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;


@Slf4j
@Component
@RequiredArgsConstructor
public class OrderRefundRejectedProducter {

    private static final String TOPIC = "refund-order.order-rejected.v1";

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper mapper;

    public void produce(String orderId,String observation ) {

        try {
            RefundOrderRejectedEvent event = RefundOrderRejectedEvent.builder()
                    .id(orderId)
                    .observation(observation)
                    .build();

            kafkaTemplate.send(TOPIC, orderId, mapper.writeValueAsString(event));
            log.info("Refund Order Rejected Event send");
        } catch (Exception ex) {
            log.error("Error trying to send Refund Order Rejected Event", ex);
        }

    }
}

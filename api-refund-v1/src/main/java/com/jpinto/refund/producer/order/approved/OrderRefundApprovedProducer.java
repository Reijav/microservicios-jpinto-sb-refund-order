package com.jpinto.refund.producer.order.approved;

import com.jpinto.refund.application.dto.request.Payment;
import com.jpinto.refund.domain.model.RefundOrder;
import com.jpinto.refund.producer.order.approved.event.OrderApprovedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderRefundApprovedProducer {

    private static final String TOPIC = "refund-order.order-approved.v1";

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper mapper;

    public void produce(RefundOrder order, Payment payment) {

        try {
            OrderApprovedEvent event = OrderApprovedEvent.builder()
                    .idOrderRefund(order.getId().toString())
                    .payment(payment)
                    .build();

            kafkaTemplate.send(TOPIC, order.getId().toString(), mapper.writeValueAsString(event));
            log.info("Order refund approved Event send");
        } catch (Exception ex) {
            log.error("Error trying to send Refund Order Created Event", ex);
        }

    }
}

package com.jpinto.refund.producer.order.created;

import com.jpinto.refund.domain.model.RefundOrder;
import com.jpinto.refund.producer.order.created.event.Employee;
import com.jpinto.refund.producer.order.created.event.RefundOrderCreatedEvent;
import com.jpinto.refund.producer.order.created.event.Supervisor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderRefundCreatedProducer {
    private static final String TOPIC = "refund-order.order-created.v1";

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper mapper;

    public void produce(RefundOrder order) {

        try {
            RefundOrderCreatedEvent event = RefundOrderCreatedEvent.builder()
                    .dateOrder(order.getDateOrder())
                    .id(order.getId())
                    .motiveId(order.getMotiveId())
                    .totalValue(order.getTotalValue())
                    .employee(Employee.builder().id(order.getEmployeeId()).name(order.getEmployeeName()).build())
                    .supervisor(Supervisor.builder().id(order.getApproverId()).name(order.getApproverName()).build())
                    .build();

            kafkaTemplate.send(TOPIC, order.getId().toString(), mapper.writeValueAsString(event));
            log.info("Refund Order Created Event send");
        } catch (Exception ex) {
            log.error("Error trying to send Refund Order Created Event", ex);
        }

    }
}

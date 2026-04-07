package com.jpinto.refundquery.listener.order.created;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpinto.refundquery.infraestructure.document.EmployeeDocument;
import com.jpinto.refundquery.infraestructure.document.RefundOrderDocument;
import com.jpinto.refundquery.infraestructure.document.SupervisorDocument;
import com.jpinto.refundquery.infraestructure.repository.RefundOrderRepository;
import com.jpinto.refundquery.listener.order.created.event.RefundOrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderRefundCreatedListener {
    private final RefundOrderRepository refundOrderRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "refund-order.order-created.v1", groupId = "refund-order.order-created.v1.group")
    public void handle(String message) {
        try {

            RefundOrderCreatedEvent orderCreateEvent= objectMapper.readValue(message, RefundOrderCreatedEvent.class);


            refundOrderRepository.save(RefundOrderDocument.builder()
                            .id(orderCreateEvent.getId())
                            .dateOrder(orderCreateEvent.getDateOrder())
                            .totalValue(orderCreateEvent.getTotalValue())
                            .motiveId(orderCreateEvent.getMotiveId())
                            .employee(EmployeeDocument.builder()
                                    .id(orderCreateEvent.getEmployee().getId())
                                    .name(orderCreateEvent.getEmployee().getName())
                                    .build())
                            .supervisor(SupervisorDocument.builder()
                                    .id(orderCreateEvent.getSupervisor().getId())
                                    .name(orderCreateEvent.getSupervisor().getName())
                                    .build())
                            .state(orderCreateEvent.getState())
                            .build());
            log.info("Consumo mensaje {}",message);
        } catch (Exception ex) {
            log.error("Error trying to process Order Created Event", ex);
        }
    }
}

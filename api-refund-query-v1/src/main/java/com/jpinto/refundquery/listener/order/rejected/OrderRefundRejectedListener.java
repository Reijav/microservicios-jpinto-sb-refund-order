package com.jpinto.refundquery.listener.order.rejected;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpinto.refundquery.infraestructure.repository.RefundOrderRepository;
import com.jpinto.refundquery.listener.order.rejected.event.RefundOrderRejectedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderRefundRejectedListener {
    private final RefundOrderRepository refundOrderRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "refund-order.order-rejected.v1", groupId = "refund-order.order-rejected.v1.group")
    public void handle(String message) {
        try {

            RefundOrderRejectedEvent orderRejectedEvent= objectMapper.readValue(message, RefundOrderRejectedEvent.class);

            var orderRefund=refundOrderRepository.findById(orderRejectedEvent.getId());
            if(orderRefund.isEmpty()){
                throw new IllegalArgumentException("Orden inexistente " + orderRejectedEvent.getId());
            }
            orderRefund.get().setObservation(orderRejectedEvent.getObservation());
            orderRefund.get().setState("REJECTED");
            refundOrderRepository.save(orderRefund.get());
            log.info("Consumo mensaje {}",message);
        } catch (Exception ex) {
            log.error("Error trying to process Order Created Event", ex);
        }
    }

}

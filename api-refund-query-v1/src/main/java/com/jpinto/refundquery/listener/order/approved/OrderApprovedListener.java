package com.jpinto.refundquery.listener.order.approved;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpinto.refundquery.infraestructure.document.PaymentDocument;
import com.jpinto.refundquery.infraestructure.repository.RefundOrderRepository;
import com.jpinto.refundquery.listener.order.approved.event.OrderApprovedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderApprovedListener {

    private final RefundOrderRepository refundOrderRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "refund-order.order-approved.v1", groupId = "refund-order.order-approved.v1.group")
    public void handle(String message) {
        try {

            OrderApprovedEvent markPayEvent= objectMapper.readValue(message, OrderApprovedEvent.class);

            var refundOrderDocument= refundOrderRepository.findById(markPayEvent.getIdOrderRefund());
            if(refundOrderDocument.isEmpty()){
                throw new RuntimeException("Orden de reembolso no encontrada MarkPayListener: Order Id" + markPayEvent.getIdOrderRefund());
            }

            refundOrderDocument.get().setPayment(new PaymentDocument(markPayEvent.getPayment().id(),
                    markPayEvent.getPayment().payeeType(), markPayEvent.getPayment().amount(), markPayEvent.getPayment().paymentMethod(),
                    markPayEvent.getPayment().paymentDate(), markPayEvent.getPayment().transactionId(), markPayEvent.getPayment().state()));

            refundOrderRepository.save(refundOrderDocument.get());
            log.info("Consumo mensaje {}",message);
        } catch (Exception ex) {
            log.error("Error trying to process Order Created Event", ex);
        }
    }

}

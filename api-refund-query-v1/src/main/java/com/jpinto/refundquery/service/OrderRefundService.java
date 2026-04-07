package com.jpinto.refundquery.service;

import com.jpinto.refundquery.controllers.dto.EmployeeDto;
import com.jpinto.refundquery.controllers.dto.PaymentDto;
import com.jpinto.refundquery.controllers.dto.RefundOrderDto;
import com.jpinto.refundquery.controllers.dto.SupervisorDto;
import com.jpinto.refundquery.infraestructure.repository.RefundOrderRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderRefundService {
    private final RefundOrderRepository refundOrderRepository;

    public RefundOrderDto getById(String id){
            var refundDocumento= refundOrderRepository.findById(id);

            if(refundDocumento.isEmpty()){
                throw new NotFoundException("Error no existe la orden de reembolso indicada: " + id);
            }

            return RefundOrderDto.builder()
                    .id(refundDocumento.get().getId())
                    .state(refundDocumento.get().getState())
                    .dateOrder(refundDocumento.get().getDateOrder())
                    .totalValue(refundDocumento.get().getTotalValue())
                    .motiveId(refundDocumento.get().getMotiveId())
                    .state(refundDocumento.get().getState())
                    .employee(EmployeeDto.builder()
                            .name(refundDocumento.get().getEmployee().getName())
                            .id(refundDocumento.get().getEmployee().getId())
                            .build())
                    .supervisor(SupervisorDto.builder()
                            .name(refundDocumento.get().getSupervisor().getName())
                            .id(refundDocumento.get().getSupervisor().getId())
                            .build())
                    .payment(Objects.nonNull(refundDocumento.get().getPayment()) ?  PaymentDto.builder()
                            .amount(refundDocumento.get().getPayment().getAmount())
                            .paymentDate(refundDocumento.get().getPayment().getPaymentDate())
                            .paymentMethod(refundDocumento.get().getPayment().getPaymentMethod())
                            .payeeType(refundDocumento.get().getPayment().getPayeeType())
                            .transactionId(refundDocumento.get().getPayment().getTransactionId())
                            .id(refundDocumento.get().getPayment().getId())
                            .state(refundDocumento.get().getPayment().getState())
                            .bank(refundDocumento.get().getPayment().getBank())
                            .savingAccount(refundDocumento.get().getPayment().getSavingAccoung())
                            .paymentDate(refundDocumento.get().getPayment().getPaymentDate())
                            .build() : null)
                    .build();
    }
}

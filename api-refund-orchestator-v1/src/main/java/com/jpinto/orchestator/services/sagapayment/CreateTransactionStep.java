package com.jpinto.orchestator.services.sagapayment;


import com.jpinto.orchestator.client.accounting.dto.RequestCancelTransactionDto;
import com.jpinto.orchestator.client.accounting.dto.RequestCreateTransactionDto;
import com.jpinto.orchestator.client.accounting.dto.TransactionLineDto;
import com.jpinto.orchestator.services.AccountingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

@Order(200)
@Slf4j
@Component
@RequiredArgsConstructor
public class CreateTransactionStep implements  SagaPaymentStep{
    private final AccountingService accountingService;
    private static final String CUENTA_GASTOS="GV";
    private static final String CUENTA_BANCOS="BNK";

    @Override
    public void execute(PaymentSagaContext context) {
        log.info("Creating transaction on api accounting.");
        var cuentaBancos=accountingService.getAccountByCode(CUENTA_BANCOS);
        var cuentaGastosViaticos=accountingService.getAccountByCode(CUENTA_GASTOS);
        var requestCreateTransaction= RequestCreateTransactionDto.builder()
                .idOrderRefund(context.getRefundOrderResponse().id().toString())
                .descripcion("Pago reembolso nro" + context.getPaymentResponse().id().toString()  + " para empleado " + context.getEmpleado().getFullName() + "("+ context.getEmpleado().getId() +")")
                .lineDtoList(Arrays.asList(
                                        TransactionLineDto.builder()
                                                .debit(context.getPaymentResponse().amount())
                                                .accountId(cuentaGastosViaticos.getId())
                                                .credit(BigDecimal.ZERO).build(),
                                        TransactionLineDto.builder()
                                                .credit(context.getPaymentResponse().amount())
                                                .accountId(cuentaBancos.getId())
                                                .debit(BigDecimal.ZERO).build()))
                .build();
        context.setTransactionDto( accountingService.createTransaction(requestCreateTransaction));
    }

    @Override
    public void compensate(PaymentSagaContext context) {
        log.info("Canceling (Compensing) transaction on api accounting.");
        if(Objects.nonNull(context.getTransactionDto())){
            var requestCancelTransaction= RequestCancelTransactionDto.builder()
                .idOrderRefund(context.getRefundOrderResponse().id().toString())
                        .idTransaction(context.getTransactionDto().getIdTransaction()).build();

            accountingService.cancelTransaction(requestCancelTransaction);
        }
    }
}

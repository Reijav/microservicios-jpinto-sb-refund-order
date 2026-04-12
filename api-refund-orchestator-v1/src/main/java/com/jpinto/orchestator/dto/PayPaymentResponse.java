package com.jpinto.orchestator.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "PayPaymentResponse",
        description = "Respuesta del procesamiento del pago de una orden de reembolso"
)
public record PayPaymentResponse(

        @Schema(description = "Identificador de la transacción contable generada", example = "2001")
        Long idTransaction,

        @Schema(description = "Identificador del pago procesado", example = "b1e9d4f2-33a1-4c87-8e2b-56f123456def")
        String idPayment,

        @Schema(description = "Identificador de la orden de reembolso asociada", example = "a3f7c2d1-84e2-4b91-9f3a-12d456789abc")
        String idOrderRefund
) {}


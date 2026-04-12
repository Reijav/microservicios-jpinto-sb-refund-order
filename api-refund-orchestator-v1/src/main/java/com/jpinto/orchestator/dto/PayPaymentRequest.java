package com.jpinto.orchestator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "PayPaymentRequest",
        description = "Solicitud para procesar el pago de una orden de reembolso aprobada"
)
public class PayPaymentRequest {

    @Schema(description = "Identificador único del pago a procesar", example = "b1e9d4f2-33a1-4c87-8e2b-56f123456def", requiredMode = Schema.RequiredMode.REQUIRED)
    private String paymentId;
}

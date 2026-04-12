package com.jpinto.orchestator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@Schema(
        name = "ApprovedRefundResponse",
        description = "Respuesta de la operación de aprobación o rechazo de una orden de reembolso"
)
public class ApprovedRefundResponse {

    @Schema(description = "Identificador único de la orden de reembolso procesada", example = "a3f7c2d1-84e2-4b91-9f3a-12d456789abc")
    private UUID idOrderRefund;

    @Schema(description = "Identificador único de la orden de pago generada (null si fue rechazada)", example = "b1e9d4f2-33a1-4c87-8e2b-56f123456def")
    private UUID idPayment;
}

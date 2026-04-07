package com.jpinto.orchestator.controller.document;

import com.jpinto.orchestator.dto.PayPaymentRequest;
import com.jpinto.orchestator.dto.PayPaymentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Interfaz de documentación Swagger/OpenAPI para el controlador de pagos de órdenes de reembolso.
 * Define los contratos de la API REST del orquestador de pagos.
 */
@Tag(name = "Pagos de Reembolso", description = "Operaciones del orquestador para procesar el pago de órdenes de reembolso aprobadas")
@SecurityRequirement(name = "BearerToken")
public interface PagoOrdenReembolsoControllerDoc {

    @Operation(
            summary = "Pagar orden de reembolso",
            description = "Procesa el pago de una orden de reembolso previamente aprobada. " +
                    "Orquesta el proceso de pago incluyendo la actualización del estado en el sistema de reembolsos " +
                    "y el registro de la transacción contable. " +
                    "Implementa compensación automática (SAGA) en caso de fallo. " +
                    "**Rol requerido:** CONTADOR"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pago procesado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PayPaymentResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID de pago inválido o datos de solicitud incorrectos",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autenticado — se requiere token JWT válido",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado — se requiere rol CONTADOR",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error durante la orquestación del pago (con compensación SAGA)",
                    content = @Content(mediaType = "application/json")
            )
    })
    PayPaymentResponse payOrderRefund(@RequestBody PayPaymentRequest request);
}

package com.jpinto.orchestator.controller.document;

import com.jpinto.orchestator.client.refund.dto.ApproveRefundRequest;
import com.jpinto.orchestator.client.refund.dto.RefundOrderResponse;
import com.jpinto.orchestator.dto.ApprovedRefundResponse;
import com.jpinto.orchestator.dto.CreateOrderRefundRequest;
import com.jpinto.orchestator.dto.RejectRefundOrchestatorRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Interfaz de documentación Swagger/OpenAPI para el controlador de órdenes de reembolso.
 * Define los contratos de la API REST del orquestador de reembolsos.
 */
@Tag(name = "Órdenes de Reembolso", description = "Operaciones del orquestador para crear, aprobar y rechazar órdenes de reembolso de empleados")
@SecurityRequirement(name = "BearerToken")
public interface OrdenReembolsoControllerDoc {

    @Operation(
            summary = "Crear orden de reembolso",
            description = "Crea una nueva orden de reembolso para un empleado. " +
                    "Orquesta el proceso de registro de facturas y la creación de la orden en el sistema de reembolsos. " +
                    "**Rol requerido:** EMPLEADO"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Orden de reembolso creada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RefundOrderResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de solicitud inválidos",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autenticado — se requiere token JWT válido",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado — se requiere rol EMPLEADO",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor durante la orquestación",
                    content = @Content(mediaType = "application/json")
            )
    })
    RefundOrderResponse createOrderRefund(@RequestBody CreateOrderRefundRequest request);

    @Operation(
            summary = "Aprobar orden de reembolso",
            description = "Aprueba una orden de reembolso existente. " +
                    "Orquesta el proceso de aprobación incluyendo la creación del pago y el registro contable. " +
                    "Implementa compensación automática (SAGA) en caso de fallo. " +
                    "**Rol requerido:** SUPERVISOR"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Orden de reembolso aprobada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApprovedRefundResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID de orden no válido o datos de solicitud incorrectos",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autenticado — se requiere token JWT válido",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado — se requiere rol SUPERVISOR",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error durante la orquestación de aprobación (con compensación SAGA)",
                    content = @Content(mediaType = "application/json")
            )
    })
    ApprovedRefundResponse approveOrderRefund(@RequestBody ApproveRefundRequest approveRefundRequest);

    @Operation(
            summary = "Rechazar orden de reembolso",
            description = "Rechaza una orden de reembolso existente, revertiendo su estado al estado previo. " +
                    "**Rol requerido:** SUPERVISOR"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Orden de reembolso rechazada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RejectRefundOrchestatorRequest.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID de orden no válido o datos de solicitud incorrectos",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autenticado — se requiere token JWT válido",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado — se requiere rol SUPERVISOR",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error durante la orquestación de rechazo (con compensación SAGA)",
                    content = @Content(mediaType = "application/json")
            )
    })
    RefundOrderResponse rejectOrderRefund(@RequestBody RejectRefundOrchestatorRequest rejectRefundRequest);

}

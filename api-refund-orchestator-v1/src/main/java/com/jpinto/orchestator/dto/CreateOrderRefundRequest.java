package com.jpinto.orchestator.dto;

import com.jpinto.orchestator.client.refund.dto.CreateRefundBillRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(
        name = "CreateOrderRefundRequest",
        description = "Solicitud para crear una nueva orden de reembolso de un empleado"
)
public class CreateOrderRefundRequest {

    @Schema(description = "Identificador del motivo de reembolso registrado en el sistema", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long motiveId;

    @Schema(description = "Lista de facturas asociadas a la orden de reembolso", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<CreateRefundBillRequest> bills;
}

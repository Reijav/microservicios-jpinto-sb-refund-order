package com.jpinto.refundquery.controllers.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupervisorDto {
    private Long id;
    private String name;
}

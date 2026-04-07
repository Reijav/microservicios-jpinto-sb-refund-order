package com.jpinto.refundquery.controllers.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeDto {
    private Long id;
    private String name;
}

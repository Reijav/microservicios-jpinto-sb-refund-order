package com.jpinto.refundquery.infraestructure.document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeDocument {
    private Long id;
    private String name;
}

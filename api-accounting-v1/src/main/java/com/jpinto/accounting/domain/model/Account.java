package com.jpinto.accounting.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Account {
    private Long id;
    private String code;
    private String name;
    private String type;
}

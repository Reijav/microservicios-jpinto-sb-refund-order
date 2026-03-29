package com.jpinto.talenthuman.domain.model;

import lombok.Data;

@Data
public class Employee {
    private Long id;
    private Long userId;
    private String fullName;
    private String email;
    private Long inmediateSupervisorId;
    private String position;
    private String bank;
    private String accountNumber;
}

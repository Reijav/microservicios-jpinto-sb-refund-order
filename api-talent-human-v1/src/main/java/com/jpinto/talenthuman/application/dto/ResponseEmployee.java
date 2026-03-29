package com.jpinto.talenthuman.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseEmployee {
    private Long id;
    private Long userId;
    private String fullName;
    private String email;
    private Long inmediateSupervisorId;
    private String position;
    private String bank;
    private String accountNumber;
}

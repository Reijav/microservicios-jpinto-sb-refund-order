package com.jpinto.orchestator.client.talenthuman.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

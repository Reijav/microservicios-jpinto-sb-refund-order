package com.jpinto.talenthuman.infraestructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employee")
@Getter
@Setter
public class EmployeeJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String fullName;
    private String email;
    private Long immediateSupervisorId;
    private String position;
    private String bank;
    private String accountNumber;

}

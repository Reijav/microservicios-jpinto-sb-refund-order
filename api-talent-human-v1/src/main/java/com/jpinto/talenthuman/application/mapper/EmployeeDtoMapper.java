package com.jpinto.talenthuman.application.mapper;

import com.jpinto.talenthuman.application.dto.ResponseEmployee;
import com.jpinto.talenthuman.domain.model.Employee;

public class EmployeeDtoMapper {

    public static ResponseEmployee domainToDto(Employee employee){
        return ResponseEmployee.builder()
                .id(employee.getId())
                .accountNumber(employee.getAccountNumber())
                .userId(employee.getUserId())
                .bank(employee.getBank())
                .fullName(employee.getFullName())
                .email(employee.getEmail())
                .inmediateSupervisorId(employee.getInmediateSupervisorId())
                .position(employee.getPosition())
                .build();

    }
}

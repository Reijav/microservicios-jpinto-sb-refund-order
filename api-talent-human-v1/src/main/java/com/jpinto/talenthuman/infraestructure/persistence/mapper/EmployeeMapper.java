package com.jpinto.talenthuman.infraestructure.persistence.mapper;

import com.jpinto.talenthuman.domain.model.Employee;
import com.jpinto.talenthuman.infraestructure.persistence.entity.EmployeeJpaEntity;

public class EmployeeMapper {

    public static Employee toDomain(EmployeeJpaEntity employeeJpaEntity){
        Employee employee= new Employee();
        employee.setId(employeeJpaEntity.getId());
        employee.setBank(employeeJpaEntity.getBank());
        employee.setPosition(employeeJpaEntity.getPosition());
        employee.setUserId(employeeJpaEntity.getUserId());
        employee.setFullName(employeeJpaEntity.getFullName());
        employee.setEmail(employeeJpaEntity.getEmail());
        employee.setAccountNumber(employeeJpaEntity.getAccountNumber());
        employee.setInmediateSupervisorId(employeeJpaEntity.getImmediateSupervisorId());
        return employee;
    }

    public static EmployeeJpaEntity toEntity(Employee employee){
        EmployeeJpaEntity employeeEntity= new EmployeeJpaEntity();
        employeeEntity.setId(employee.getId());
        employeeEntity.setBank(employee.getBank());
        employeeEntity.setPosition(employee.getPosition());
        employeeEntity.setUserId(employee.getUserId());
        employeeEntity.setFullName(employee.getFullName());
        employeeEntity.setEmail(employee.getEmail());
        employeeEntity.setAccountNumber(employee.getAccountNumber());
        employeeEntity.setImmediateSupervisorId(employee.getInmediateSupervisorId());
        return employeeEntity;
    }
}

package com.jpinto.talenthuman.domain.repository;


import com.jpinto.talenthuman.domain.model.Employee;

public interface EmployeeRepository {
    Employee getEmployeeById(Long id);
    Employee getEmployeeByUserId(Long idUser);
}

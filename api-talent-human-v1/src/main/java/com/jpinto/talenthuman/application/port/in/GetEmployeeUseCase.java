package com.jpinto.talenthuman.application.port.in;

import com.jpinto.talenthuman.domain.model.Employee;


public interface GetEmployeeUseCase {
    Employee findById(Long id);
    Employee findByUserId(Long userId);
}

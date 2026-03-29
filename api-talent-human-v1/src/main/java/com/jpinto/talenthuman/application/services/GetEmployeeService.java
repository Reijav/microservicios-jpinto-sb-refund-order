package com.jpinto.talenthuman.application.services;

import com.jpinto.talenthuman.application.port.in.GetEmployeeUseCase;
import com.jpinto.talenthuman.domain.model.Employee;
import com.jpinto.talenthuman.domain.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetEmployeeService  implements GetEmployeeUseCase {
    private final EmployeeRepository employeeRepository;

    @Override
    public Employee findById(Long id) {
        return employeeRepository.getEmployeeById(id);
    }

    @Override
    public Employee findByUserId(Long userId) {
        return employeeRepository.getEmployeeByUserId(userId);
    }

}

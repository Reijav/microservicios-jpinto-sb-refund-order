package com.jpinto.talenthuman.infraestructure.persistence.adapter;

import com.jpinto.talenthuman.domain.model.Employee;
import com.jpinto.talenthuman.domain.repository.EmployeeRepository;
import com.jpinto.talenthuman.infraestructure.persistence.jpa.EmployeeJpaRepository;
import com.jpinto.talenthuman.infraestructure.persistence.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeRepositoryAdapter  implements EmployeeRepository {
    private final EmployeeJpaRepository employeeJpaRepository;

    @Override
    public Employee getEmployeeById(Long id) {
        return   employeeJpaRepository.findById(id).map(EmployeeMapper::toDomain).orElseThrow(()->new IllegalArgumentException(("No existe el empleado con id " + id)));
    }

    @Override
    public Employee getEmployeeByUserId(Long idUser) {
        return   employeeJpaRepository.findByUserId(idUser).map(EmployeeMapper::toDomain).orElseThrow(()->new IllegalArgumentException(("No existe el empleado con id " + idUser)));
    }
}

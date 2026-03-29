package com.jpinto.talenthuman.adapter.rest;

import com.jpinto.talenthuman.application.dto.ResponseEmployee;
import com.jpinto.talenthuman.application.mapper.EmployeeDtoMapper;
import com.jpinto.talenthuman.application.port.in.GetEmployeeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final GetEmployeeUseCase getEmployeeUseCase;
    @GetMapping("/by-id/{id}")
    public ResponseEmployee findById(@PathVariable Long id){
        return EmployeeDtoMapper.domainToDto( getEmployeeUseCase.findById(id));
    }

    @GetMapping("/by-user-id/{id}")
    public ResponseEmployee findByUserId(@PathVariable Long id){
        return EmployeeDtoMapper.domainToDto( getEmployeeUseCase.findByUserId(id));
    }

}

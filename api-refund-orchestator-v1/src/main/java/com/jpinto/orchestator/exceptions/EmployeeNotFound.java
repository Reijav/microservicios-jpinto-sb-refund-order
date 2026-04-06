package com.jpinto.orchestator.exceptions;

public class EmployeeNotFound extends  RuntimeException{
    public EmployeeNotFound(String message) {
        super(message);
    }
}

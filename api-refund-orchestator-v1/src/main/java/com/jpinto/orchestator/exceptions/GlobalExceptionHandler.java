package com.jpinto.orchestator.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(PaymentFailedException.class)
//    public ResponseEntity<String> handlePaymentFailedException(PaymentFailedException ex) {
//        log.error(ex.getMessage(), ex);
//        return ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(ex.getMessage());
//    }

    @ExceptionHandler(ResourceAccessException.class)
    public ProblemDetail handleResourceAccessException(ResourceAccessException ex) {
        log.error(ex.getMessage(), ex);
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problem.setTitle("Internal Error");
        problem.setDetail(ex.getMessage());
        return problem ;
    }

//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
//        log.error(ex.getMessage(), ex);
//        return ResponseEntity
//                .status(HttpStatus.FORBIDDEN)
//                .body("No tienes permiso para realizar esta acción.");
//    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(IllegalArgumentException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Bad Request");
        problem.setDetail(ex.getMessage());
        return problem;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage,
                        (existing, replacement) -> existing));
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Validation Failed");
        problem.setDetail("One or more fields are invalid");
        problem.setProperty("errors", errors);
        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Bad Request");
        problem.setDetail(ex.getMessage());
        return problem;
    }


    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleruntimeException(Exception ex) {
        log.error(ex.getMessage(), ex);
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Bad Request");
        problem.setDetail(ex.getMessage());
        return problem;
    }
}

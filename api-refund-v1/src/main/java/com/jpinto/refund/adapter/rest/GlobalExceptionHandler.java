package com.jpinto.refund.adapter.rest;

import com.jpinto.refund.domain.exception.InvalidRefundStateTransitionException;
import com.jpinto.refund.domain.exception.RefundOrderNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RefundOrderNotFoundException.class)
    public ProblemDetail handleNotFound(RefundOrderNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Refund Order Not Found");
        problem.setDetail(ex.getMessage());
        return problem;
    }

    @ExceptionHandler(InvalidRefundStateTransitionException.class)
    public ProblemDetail handleInvalidTransition(InvalidRefundStateTransitionException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problem.setTitle("Invalid State Transition");
        problem.setDetail(ex.getMessage());
        return problem;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(IllegalArgumentException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Bad Request");
        problem.setDetail(ex.getMessage());
        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Bad Request");
        problem.setDetail(ex.getMessage());
        log.error(ex.getMessage());
        return problem;
    }

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleIRuntimeException(RuntimeException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Bad Request");
        problem.setDetail(ex.getMessage());
        log.error(ex.getMessage());
        return problem;
    }
}

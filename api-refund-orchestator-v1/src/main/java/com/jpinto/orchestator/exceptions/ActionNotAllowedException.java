package com.jpinto.orchestator.exceptions;

public class ActionNotAllowedException extends  RuntimeException{
    public ActionNotAllowedException(String message, Exception ex) {
        super(message, ex);
    }
}

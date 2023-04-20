package com.todo.backendrestcrud.exception;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

import java.lang.annotation.Annotation;


// This controller is used to serve the index.html file

@ControllerAdvice
public class GlobalExceptionHandler implements ExceptionHandler {

    // This is the default route for 404 errors
    @ExceptionHandler(value = {ChangeSetPersister.NotFoundException.class})
    public String handleNotFoundException() {
        return ("404");
    }

    // This is the default route for 500 errors
    @ExceptionHandler(value = {HttpServerErrorException.InternalServerError.class})
    public String handleInternalServerError() {
        return ("500");
    }
    // This is the default route for 500 errors
    @ExceptionHandler(value = {Exception.class})
    public String handleException() {
        return ("500");
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public String handleRuntimeException() {
        return ("500");
    }

    @ExceptionHandler(value = {Throwable.class})
    public String handleThrowable() {
        return ("error");
    }

    @Override
    public Class<? extends Throwable>[] value() {
        return new Class[0];
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}

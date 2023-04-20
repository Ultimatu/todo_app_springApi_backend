package com.todo.backendrestcrud.exception;


import org.springframework.web.bind.annotation.ControllerAdvice;
import  java.lang.Long;
public class UserNotFoundException extends RuntimeException{
    //this exception is thrown when a user is not found
    public UserNotFoundException(Long id) {
        super("Could not find user " + id);
    }
}

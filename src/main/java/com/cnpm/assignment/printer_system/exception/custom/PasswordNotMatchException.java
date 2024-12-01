package com.cnpm.assignment.printer_system.exception.custom;

public class PasswordNotMatchException extends RuntimeException {
    final private static String DEFAULT_MESSAGE = "Password Not Match Exception...";
    
    PasswordNotMatchException(){
        super(DEFAULT_MESSAGE);
    }

    public PasswordNotMatchException(String mes){
        super(mes);
    }
}
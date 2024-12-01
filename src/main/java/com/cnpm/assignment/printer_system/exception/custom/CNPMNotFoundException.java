package com.cnpm.assignment.printer_system.exception.custom;

public class CNPMNotFoundException extends RuntimeException {
    final private static String DEFAULT_MESSAGE = "Not Found Exception...";
    
    CNPMNotFoundException(){
        super(DEFAULT_MESSAGE);
    }

    public CNPMNotFoundException(String mes){
        super(mes);
    }
}
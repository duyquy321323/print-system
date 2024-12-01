package com.cnpm.assignment.printer_system.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cnpm.assignment.printer_system.exception.custom.CNPMNotFoundException;
import com.cnpm.assignment.printer_system.exception.custom.PasswordNotMatchException;
import com.cnpm.assignment.printer_system.response.ExceptionResponse;

@ControllerAdvice
public class ExceptionHandleCNPM {

    @ExceptionHandler(CNPMNotFoundException.class)
    public ResponseEntity<?> throwNotFound(CNPMNotFoundException ex) {
        List<String> details = new ArrayList<>();
        details.add("Please Try Again!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionResponse.builder().message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND).details(details));
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<?> throwPassNotMatch(PasswordNotMatchException ex) {
        List<String> details = new ArrayList<>();
        details.add("Please Try Again!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionResponse.builder().message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST).details(details));
    }
}
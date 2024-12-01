package com.cnpm.assignment.printer_system.enumeration;

import lombok.Getter;

@Getter
public enum TypePage {
    NORMAL("Loại Thường"),
    COLOR("Loại Màu");

    private final String value;

    TypePage(String value){
        this.value = value;
    }
}
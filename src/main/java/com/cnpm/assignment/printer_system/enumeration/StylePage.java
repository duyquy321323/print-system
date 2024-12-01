package com.cnpm.assignment.printer_system.enumeration;

import lombok.Getter;

@Getter
public enum StylePage {
    A4("Giấy A4"),
    A3("Giấy A3");

    final private String value;

    StylePage(String value){
        this.value = value;
    }
}
package com.cnpm.assignment.printer_system.enumeration;

import lombok.Getter;

@Getter
public enum PrinterStatusStudent {
    AVAILABILITY("Máy In Khả Dụng"),
    BUSY("Có Tài Liệu Đang Trong Quá Trình In");

    final private String value;

    PrinterStatusStudent(String value){
        this.value = value;
    }
}
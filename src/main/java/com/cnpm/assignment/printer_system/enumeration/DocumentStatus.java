package com.cnpm.assignment.printer_system.enumeration;

import lombok.Getter;

@Getter
public enum DocumentStatus {
    PROCESSING("Đang Xử Lý"),
    PRINT_SUCCESS("In Thành Công"),
    PRINT_FALSE("In Thất Bại");

    final private String value;

    DocumentStatus(String value) {
        this.value = value;
    }
}
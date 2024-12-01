package com.cnpm.assignment.printer_system.enumeration;

import lombok.Getter;

@Getter
public enum BillStatus {
    PROCESSING("Đang Xử Lý"),
    SUCCESS("Thành Công"),
    FAILED("Thất Bại");

    private final String value;

    BillStatus(String value) {
        this.value = value;
    }
}
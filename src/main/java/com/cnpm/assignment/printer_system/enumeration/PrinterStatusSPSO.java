package com.cnpm.assignment.printer_system.enumeration;

import lombok.Getter;

@Getter
public enum PrinterStatusSPSO {
    ACTIVE("Hoạt Động"),
    CONNECT_FALED("Mất Kết Nối"),
    MAINTENANCE("Bảo Trì");

    final private String value;

    PrinterStatusSPSO(String value){
        this.value = value;
    }
}
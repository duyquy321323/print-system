package com.cnpm.assignment.printer_system.enumeration;

import lombok.Getter;

@Getter
public enum ContentStatus {
    HAS_ANSWERED("Đã Trả Lời"),
    NO_ANSWERED("Chưa Trả Lời");

    final private String value;

    ContentStatus(String value){
        this.value = value;
    }
}
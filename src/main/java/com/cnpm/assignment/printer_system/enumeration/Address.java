package com.cnpm.assignment.printer_system.enumeration;

import lombok.Getter;

@Getter
public enum Address {
    CS1("268 Lý Thường Kiệt, Phường 14, Quận 10 , Thành phố Hồ Chí Minh , Việt Nam"),
    CS2("VRJ4+65C, Đông Hòa, Dĩ An, Bình Dương");

    final private String value;

    Address(String value){
        this.value = value;
    }
}
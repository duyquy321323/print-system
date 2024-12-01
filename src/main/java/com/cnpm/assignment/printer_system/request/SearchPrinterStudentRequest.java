package com.cnpm.assignment.printer_system.request;

import com.cnpm.assignment.printer_system.enumeration.Address;
import com.cnpm.assignment.printer_system.enumeration.PrinterStatusStudent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchPrinterStudentRequest {
    private Address address;
    private PrinterStatusStudent status;
    private Long idPrinter;
}
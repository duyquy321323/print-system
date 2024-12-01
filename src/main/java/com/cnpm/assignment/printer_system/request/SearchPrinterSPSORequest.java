package com.cnpm.assignment.printer_system.request;

import com.cnpm.assignment.printer_system.enumeration.Address;
import com.cnpm.assignment.printer_system.enumeration.PrinterStatusSPSO;

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
public class SearchPrinterSPSORequest {
    private Address address;
    private PrinterStatusSPSO statusSPSO;
    private Long idPrinter;
}
package com.cnpm.assignment.printer_system.request;

import java.time.LocalDateTime;

import com.cnpm.assignment.printer_system.enumeration.PrinterStatusSPSO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditPrinterRequest {
    private PrinterStatusSPSO status;
    private Long pageQuantity;
    private LocalDateTime lastMaintenanceDate;
}
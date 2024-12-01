package com.cnpm.assignment.printer_system.response;

import java.time.LocalDateTime;

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
public class PrinterSPSOResponse {
    private Long id;
    private String address;
    private LocalDateTime lastMaintenanceDate;
    private String status;
    private Long pageQuantity;
}
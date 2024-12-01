package com.cnpm.assignment.printer_system.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayPackagePrintRequest {
    private Long idPackagePrint;
    private Long quantityPackagePrint;
}
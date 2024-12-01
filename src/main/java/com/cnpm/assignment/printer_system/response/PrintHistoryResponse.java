package com.cnpm.assignment.printer_system.response;

import java.time.LocalDateTime;
import java.util.List;

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
public class PrintHistoryResponse {
    private Long idPrinter;
    private LocalDateTime datePrint;
    private String address;
    private String nameDocument;
    private String typePage;
    private Long quantityPage;
}
package com.cnpm.assignment.printer_system.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailBillResponse {
    private List<PackagePrintResponse> packagePrints;
    private Long totalPageNumber;
    private Long totalPricePackages;
    private Long billId;
    private LocalDateTime datePayment;
}
package com.cnpm.assignment.printer_system.response;

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
public class BuyPackageResponse {
    private Long idPackage;
    private Long quantityPage;
    private Long pricePackage;
    private String namePackage;
}
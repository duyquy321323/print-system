package com.cnpm.assignment.printer_system.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;

import com.cnpm.assignment.printer_system.enumeration.StylePage;
import com.cnpm.assignment.printer_system.enumeration.TypePage;
import com.cnpm.assignment.printer_system.response.BuyPackageResponse;

public interface PrinterService {
    public void printDocuments(Long idPrinter, List<Long> idDocuments, StylePage stylePage, TypePage typePage) throws IOException ;

    public Page<BuyPackageResponse> getPackagePrint(Integer pageNo, Integer pageSize);
}
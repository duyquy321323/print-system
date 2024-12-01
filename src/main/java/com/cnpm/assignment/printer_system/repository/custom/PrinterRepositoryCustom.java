package com.cnpm.assignment.printer_system.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cnpm.assignment.printer_system.entity.Printer;
import com.cnpm.assignment.printer_system.enumeration.PrinterStatusSPSO;
import com.cnpm.assignment.printer_system.request.SearchPrinterSPSORequest;
import com.cnpm.assignment.printer_system.request.SearchPrinterStudentRequest;

public interface PrinterRepositoryCustom {
    public Page<Printer> findByPrinterStudentRequest(SearchPrinterStudentRequest request, PrinterStatusSPSO statusSPSO, Pageable pageable);

    public Page<Printer> findByPrinterSPSORequest(SearchPrinterSPSORequest request, Pageable pageable);
}
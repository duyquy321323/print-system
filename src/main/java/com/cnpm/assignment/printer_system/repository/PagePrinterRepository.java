package com.cnpm.assignment.printer_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cnpm.assignment.printer_system.entity.PagePrinter;
import com.cnpm.assignment.printer_system.entity.id.PagePrinterId;

public interface PagePrinterRepository extends JpaRepository<PagePrinter, PagePrinterId> {
    
}
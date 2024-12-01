package com.cnpm.assignment.printer_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cnpm.assignment.printer_system.entity.Printer;
import com.cnpm.assignment.printer_system.repository.custom.PrinterRepositoryCustom;

public interface PrinterRepository extends JpaRepository<Printer, Long>, PrinterRepositoryCustom {
    
}
package com.cnpm.assignment.printer_system.repository.custom;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.cnpm.assignment.printer_system.entity.Printer;
import com.cnpm.assignment.printer_system.entity.PrinterDocument;
import com.cnpm.assignment.printer_system.entity.Student;

public interface PrinterDocumentRepositoryCustom {
    public List<PrinterDocument> findById_Document_StudentAndId_Printer(Student student, Printer printer, Pageable pageable);
}
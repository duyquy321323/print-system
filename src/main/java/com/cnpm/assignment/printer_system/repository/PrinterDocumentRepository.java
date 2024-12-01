package com.cnpm.assignment.printer_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cnpm.assignment.printer_system.entity.PrinterDocument;
import com.cnpm.assignment.printer_system.entity.id.PrinterDocumentId;
import com.cnpm.assignment.printer_system.repository.custom.PrinterDocumentRepositoryCustom;

public interface PrinterDocumentRepository extends JpaRepository<PrinterDocument, PrinterDocumentId>, PrinterDocumentRepositoryCustom {
    public Page<PrinterDocument> findById_Document_Student_Id(Long idStudent, Pageable pageable);
}
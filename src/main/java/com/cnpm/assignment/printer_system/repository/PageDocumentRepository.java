package com.cnpm.assignment.printer_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cnpm.assignment.printer_system.entity.PageDocument;
import com.cnpm.assignment.printer_system.entity.id.PageDocumentId;

public interface PageDocumentRepository extends JpaRepository<PageDocument, PageDocumentId> {
    
}
package com.cnpm.assignment.printer_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cnpm.assignment.printer_system.entity.Content;
import com.cnpm.assignment.printer_system.entity.id.ContentId;

public interface ContentRepository extends JpaRepository<Content, ContentId> {
    
}
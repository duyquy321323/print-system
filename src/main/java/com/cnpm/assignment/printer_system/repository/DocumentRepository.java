package com.cnpm.assignment.printer_system.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cnpm.assignment.printer_system.entity.Document;
import com.cnpm.assignment.printer_system.entity.Student;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    public Page<Document> findByStudentAndActive(Student student, Boolean active, Pageable pageable);

    public Optional<Document> findByStudentAndActiveAndId(Student student, Boolean active, Long id);
}
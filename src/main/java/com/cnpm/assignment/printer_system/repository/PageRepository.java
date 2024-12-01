package com.cnpm.assignment.printer_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cnpm.assignment.printer_system.entity.Page;
import com.cnpm.assignment.printer_system.enumeration.TypePage;

public interface PageRepository extends JpaRepository<Page, Long> {
    public Optional<Page> findByType(TypePage typePage);
}
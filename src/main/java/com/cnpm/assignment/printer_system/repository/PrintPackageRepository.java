package com.cnpm.assignment.printer_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cnpm.assignment.printer_system.entity.PrintPackage;

public interface PrintPackageRepository extends JpaRepository<PrintPackage, Long> {
}
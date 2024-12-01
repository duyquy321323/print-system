package com.cnpm.assignment.printer_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cnpm.assignment.printer_system.entity.Bill;
import com.cnpm.assignment.printer_system.entity.BillPrintPackage;
import com.cnpm.assignment.printer_system.entity.PrintPackage;
import com.cnpm.assignment.printer_system.entity.id.BillPrintPackageId;

public interface BillPrintPackageRepository extends JpaRepository<BillPrintPackage, BillPrintPackageId> {
    public Optional<BillPrintPackage> findById_BillAndId_PrintPackage(Bill bill, PrintPackage printPackage);
}
package com.cnpm.assignment.printer_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cnpm.assignment.printer_system.entity.Bill;
import com.cnpm.assignment.printer_system.entity.Student;

public interface BillRepository extends JpaRepository<Bill, Long> {
    public Page<Bill> findByStudent(Student student, Pageable pageable);
}
package com.cnpm.assignment.printer_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cnpm.assignment.printer_system.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    public Page<Student> findByFullNameContaining(String fullName, Pageable pageable);
}
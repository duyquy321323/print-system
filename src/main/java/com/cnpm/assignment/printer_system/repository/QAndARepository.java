package com.cnpm.assignment.printer_system.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cnpm.assignment.printer_system.entity.QAndA;
import com.cnpm.assignment.printer_system.entity.SPSO;
import com.cnpm.assignment.printer_system.entity.Student;

public interface QAndARepository extends JpaRepository<QAndA, Long> {
    public Optional<QAndA> findByIdAndStudent(Long id, Student student);

    public Page<QAndA> findBySpso(SPSO spso, Pageable pageable);
}
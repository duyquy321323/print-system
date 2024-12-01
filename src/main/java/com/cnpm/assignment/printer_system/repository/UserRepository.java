package com.cnpm.assignment.printer_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cnpm.assignment.printer_system.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmailAndActive(String email, Boolean active);
}
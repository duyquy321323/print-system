package com.cnpm.assignment.printer_system.service;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;

import com.cnpm.assignment.printer_system.entity.User;

public interface JwtToken {
    public String generateToken(User user);

    public String extractEmail(String token);

    public boolean validateToken(String token, UserDetails userDetails);

    public Date extractExpirationToken(String token);
}
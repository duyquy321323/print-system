package com.cnpm.assignment.printer_system.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cnpm.assignment.printer_system.entity.User;
import com.cnpm.assignment.printer_system.repository.UserRepository;

public class UserSecurityService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAndActive(email, true)
                .orElseThrow(() -> new UsernameNotFoundException("Tài khoản không tồn tại"));
        return UserSecurity.build(user);
    }

}
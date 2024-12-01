package com.cnpm.assignment.printer_system.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cnpm.assignment.printer_system.entity.SPSO;
import com.cnpm.assignment.printer_system.entity.Student;
import com.cnpm.assignment.printer_system.entity.User;

public class UserSecurity implements UserDetails {
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserSecurity build(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user instanceof SPSO) {
            authorities.add(new SimpleGrantedAuthority("ROLE_SPSO"));
        } else if(user instanceof Student){
            authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        }
        return new UserSecurity(user.getEmail(), user.getPassword(), authorities);
    }

    UserSecurity(String email, String password, List<GrantedAuthority> authorities) {
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
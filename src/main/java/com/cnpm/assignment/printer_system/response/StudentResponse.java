package com.cnpm.assignment.printer_system.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
    private Long id;
    private String fullName;
    private String email;
    private LocalDateTime lastAccessedDate;
    private String mssv;
    private LocalDateTime birthday;
    private String sex;
    private String phoneNumber;
    private Boolean active;
}
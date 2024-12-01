package com.cnpm.assignment.printer_system.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PrinterStudentResponse {
    private Long id;
    private String address;
    private LocalDateTime dateOfUse;
    private Long timeout;
    private List<String> historyUse; // danh sách tên file đã được in bằng máy in này của người dùng hiện tại
    private String status;
}
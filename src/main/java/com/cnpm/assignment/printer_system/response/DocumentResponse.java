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
@AllArgsConstructor
@NoArgsConstructor
public class DocumentResponse {
    private Long id;
    private String filename;
    private Long size;
    private LocalDateTime postDate;
}
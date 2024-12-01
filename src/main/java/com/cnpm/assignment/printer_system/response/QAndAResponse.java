package com.cnpm.assignment.printer_system.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QAndAResponse {
    private Long idQAndA;
    private String nameOfStudent;
    private LocalDateTime dateQuestion;
    private String firstQuestion;
    private String status;
}
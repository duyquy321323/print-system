package com.cnpm.assignment.printer_system.response;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Nội dung câu hỏi + trả lời")
public class ContentResponse {
    private String nameStudent;
    private String nameSPSO;
    private LocalDateTime dateQuestion;
    private LocalDateTime dateAnswer;
    private String question;
    private String answer;
}
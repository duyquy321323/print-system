package com.cnpm.assignment.printer_system.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class PageResponse {
    private Long idPage;
    private String type;
    private Long quantityPage;
}
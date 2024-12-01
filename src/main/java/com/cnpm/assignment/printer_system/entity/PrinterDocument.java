package com.cnpm.assignment.printer_system.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.cnpm.assignment.printer_system.entity.id.PrinterDocumentId;
import com.cnpm.assignment.printer_system.enumeration.DocumentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrinterDocument {
    @EmbeddedId
    private PrinterDocumentId id;

    @Column(name = "print_date", nullable = false)
    private LocalDateTime printDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentStatus status;
}
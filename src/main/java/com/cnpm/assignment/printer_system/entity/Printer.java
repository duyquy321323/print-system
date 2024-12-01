package com.cnpm.assignment.printer_system.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.cnpm.assignment.printer_system.enumeration.Address;
import com.cnpm.assignment.printer_system.enumeration.PrinterStatusSPSO;
import com.cnpm.assignment.printer_system.enumeration.PrinterStatusStudent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Printer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Address address;

    private Long timeout;

    @Column(name = "date_of_use")
    private LocalDateTime dateOfUse;
    @Enumerated(EnumType.STRING)
    @Column(name = "status_student", nullable = false)
    private PrinterStatusStudent printerStatusStudent;

    @Column(name = "last_maintenance_date")
    private LocalDateTime lastMaintenanceDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_spso", nullable = false)
    private PrinterStatusSPSO printerStatusSPSO;

    @OneToMany(mappedBy = "id.printer", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private List<PrinterDocument> printerDocuments = new ArrayList<>();

    @OneToMany(mappedBy = "id.printer", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private List<PagePrinter> pagePrinters = new ArrayList<>();
}
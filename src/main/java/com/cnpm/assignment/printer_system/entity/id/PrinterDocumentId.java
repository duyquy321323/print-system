package com.cnpm.assignment.printer_system.entity.id;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.cnpm.assignment.printer_system.entity.Document;
import com.cnpm.assignment.printer_system.entity.Printer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Embeddable
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PrinterDocumentId implements Serializable {
    @ManyToOne
    @JoinColumn(name="id_printer")
    private Printer printer;

    @ManyToOne
    @JoinColumn(name="id_document")
    private Document document;
}
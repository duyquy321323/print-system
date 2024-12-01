package com.cnpm.assignment.printer_system.entity.id;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.cnpm.assignment.printer_system.entity.Page;
import com.cnpm.assignment.printer_system.entity.Printer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagePrinterId implements Serializable {
    @ManyToOne
    @JoinColumn(name="id_printer")
    private Printer printer;

    @ManyToOne
    @JoinColumn(name="id_page")
    private Page page;
}
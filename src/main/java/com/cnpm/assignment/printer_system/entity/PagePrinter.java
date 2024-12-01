package com.cnpm.assignment.printer_system.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.cnpm.assignment.printer_system.entity.id.PagePrinterId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Builder
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class PagePrinter {
    @EmbeddedId
    private PagePrinterId id;

    @Column(name="page_quantity", nullable = false)
    private Long pageQuantity;
}
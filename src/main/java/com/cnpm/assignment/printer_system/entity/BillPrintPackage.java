package com.cnpm.assignment.printer_system.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.cnpm.assignment.printer_system.entity.id.BillPrintPackageId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BillPrintPackage {
    @EmbeddedId
    private BillPrintPackageId id;

    @Column(name = "package_quantity", nullable = false)
    private Long packageQuantity;
}
package com.cnpm.assignment.printer_system.entity.id;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.cnpm.assignment.printer_system.entity.Bill;
import com.cnpm.assignment.printer_system.entity.PrintPackage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillPrintPackageId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "id_print_package")
    private PrintPackage printPackage;

    @ManyToOne
    @JoinColumn(name = "id_bill")
    private Bill bill;
}
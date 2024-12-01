package com.cnpm.assignment.printer_system.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.cnpm.assignment.printer_system.entity.id.PageStudentId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PageStudent {
    @EmbeddedId
    private PageStudentId id;

    @Column(nullable = false, name = "page_quantity")
    private Long pageQuantity;
}
package com.cnpm.assignment.printer_system.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.cnpm.assignment.printer_system.entity.id.PageDocumentId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageDocument {
    @EmbeddedId
    private PageDocumentId id;

    @Column(nullable = false, name = "page_quantity")
    private Long pageQuantity;
}
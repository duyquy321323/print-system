package com.cnpm.assignment.printer_system.entity.id;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.cnpm.assignment.printer_system.entity.Document;
import com.cnpm.assignment.printer_system.entity.Page;

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
public class PageDocumentId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "id_document")
    private Document document;

    @ManyToOne
    @JoinColumn(name = "id_page")
    private Page page;
}
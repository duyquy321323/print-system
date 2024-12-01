package com.cnpm.assignment.printer_system.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "q_and_a")
public class QAndA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_spso")
    private SPSO spso;

    private String title;

    @ManyToOne
    @JoinColumn(name = "id_student", nullable = false)
    private Student student;

    @OneToMany(mappedBy = "id.qAndA", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, orphanRemoval = true)
    private List<Content> contents = new ArrayList<>();
}
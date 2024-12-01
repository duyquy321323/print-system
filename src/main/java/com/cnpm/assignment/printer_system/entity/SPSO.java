package com.cnpm.assignment.printer_system.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SPSO extends User {

    @OneToMany(mappedBy = "spso", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, orphanRemoval = true)
    private List<QAndA> qAndAs = new ArrayList<>();
}
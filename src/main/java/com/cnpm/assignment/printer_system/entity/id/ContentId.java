package com.cnpm.assignment.printer_system.entity.id;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.cnpm.assignment.printer_system.entity.QAndA;

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
public class ContentId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "id_q_and_a")
    private QAndA qAndA;

    private Long id;
}
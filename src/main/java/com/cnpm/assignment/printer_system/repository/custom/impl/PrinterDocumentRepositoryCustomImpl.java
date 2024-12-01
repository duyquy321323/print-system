package com.cnpm.assignment.printer_system.repository.custom.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Pageable;

import com.cnpm.assignment.printer_system.entity.Printer;
import com.cnpm.assignment.printer_system.entity.PrinterDocument;
import com.cnpm.assignment.printer_system.entity.Student;
import com.cnpm.assignment.printer_system.repository.custom.PrinterDocumentRepositoryCustom;

public class PrinterDocumentRepositoryCustomImpl implements PrinterDocumentRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<PrinterDocument> findById_Document_StudentAndId_Printer(Student student, Printer printer,
            Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(PrinterDocument.class);
        Root<PrinterDocument> root = criteriaQuery.from(PrinterDocument.class);
        List<Predicate> predicates = new ArrayList<>();

        if(student != null){
            predicates.add(criteriaBuilder.equal(root.get("id").get("document").get("student"), student));
        }
        if(printer != null){
            predicates.add(criteriaBuilder.equal(root.get("id").get("printer"), printer));
        }

        if(!predicates.isEmpty()){
            Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            criteriaQuery.where(finalPredicate);
        }

        Order order = criteriaBuilder.desc(root.get("printDate"));
        criteriaQuery.orderBy(order);

        TypedQuery<PrinterDocument> query = entityManager.createQuery(criteriaQuery);
        query.setMaxResults(pageable.getPageSize());
        query.setFirstResult((int) pageable.getOffset());
        return query.getResultList();
    }
    
}
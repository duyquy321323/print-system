package com.cnpm.assignment.printer_system.repository.custom.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.cnpm.assignment.printer_system.entity.Printer;
import com.cnpm.assignment.printer_system.enumeration.PrinterStatusSPSO;
import com.cnpm.assignment.printer_system.repository.custom.PrinterRepositoryCustom;
import com.cnpm.assignment.printer_system.request.SearchPrinterSPSORequest;
import com.cnpm.assignment.printer_system.request.SearchPrinterStudentRequest;

public class PrinterRepositoryCustomImpl implements PrinterRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Printer> findByPrinterStudentRequest(SearchPrinterStudentRequest request, PrinterStatusSPSO statusSPSO, Pageable pageable) {

        // Lấy đối tượng CriteriaBuilder của entityManager
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        // Kiểu dữ liệu để hứng data từ dbs là Printer
        CriteriaQuery<Printer> criteriaQuery = criteriaBuilder.createQuery(Printer.class);
        Root<Printer> rootPrinter = criteriaQuery.from(Printer.class);
        List<Predicate> predicates = new ArrayList<>();

        if(request.getAddress() != null && !request.getAddress().getValue().isEmpty()){
            predicates.add(criteriaBuilder.equal(rootPrinter.get("address"), request.getAddress()));
        }
        if(request.getIdPrinter() != null){
            predicates.add(criteriaBuilder.equal(rootPrinter.get("id"), request.getIdPrinter()));
        }
        if(request.getStatus() != null){
            predicates.add(criteriaBuilder.equal(rootPrinter.get("printerStatusStudent"), request.getStatus()));
        }
        if(statusSPSO != null){
            predicates.add(criteriaBuilder.equal(rootPrinter.get("printerStatusSPSO"), statusSPSO));
        }

        // Tạo câu query cuối cùng
        if(!predicates.isEmpty()){
            Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(Predicate[]::new));
            criteriaQuery.where(finalPredicate);
        }
        
        TypedQuery<Printer> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Printer> printers = query.getResultList();
        return new PageImpl<>(printers , pageable, printers.size());
    }

    @Override
    public Page<Printer> findByPrinterSPSORequest(SearchPrinterSPSORequest request, Pageable pageable) {

        // Lấy đối tượng CriteriaBuilder của entityManager
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        // Kiểu dữ liệu để hứng data từ dbs là Printer
        CriteriaQuery<Printer> criteriaQuery = criteriaBuilder.createQuery(Printer.class);
        Root<Printer> rootPrinter = criteriaQuery.from(Printer.class);
        List<Predicate> predicates = new ArrayList<>();

        if(request.getAddress() != null && !request.getAddress().getValue().isEmpty()){
            predicates.add(criteriaBuilder.equal(rootPrinter.get("address"), request.getAddress()));
        }
        if(request.getIdPrinter() != null){
            predicates.add(criteriaBuilder.equal(rootPrinter.get("id"), request.getIdPrinter()));
        }
        if(request.getStatusSPSO() != null){
            predicates.add(criteriaBuilder.equal(rootPrinter.get("printerStatusSPSO"), request.getStatusSPSO()));
        }

        // Tạo câu query cuối cùng
        if(!predicates.isEmpty()){
            Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(Predicate[]::new));
            criteriaQuery.where(finalPredicate);
        }
        
        TypedQuery<Printer> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Printer> printers = query.getResultList();
        return new PageImpl<>(printers , pageable, printers.size());
    }
    
}
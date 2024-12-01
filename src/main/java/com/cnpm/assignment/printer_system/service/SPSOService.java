package com.cnpm.assignment.printer_system.service;

import org.springframework.data.domain.Page;

import com.cnpm.assignment.printer_system.request.EditPrinterRequest;
import com.cnpm.assignment.printer_system.request.SearchPrinterSPSORequest;
import com.cnpm.assignment.printer_system.response.PrintHistoryResponse;
import com.cnpm.assignment.printer_system.response.PrinterSPSOResponse;
import com.cnpm.assignment.printer_system.response.QAndAResponse;
import com.cnpm.assignment.printer_system.response.StudentResponse;

public interface SPSOService {
    public Page<PrinterSPSOResponse> getPrinter(SearchPrinterSPSORequest request, Integer pageNo, Integer pageSize);

    public Page<StudentResponse> searchStudent(String fullName, Integer pageNo, Integer pageSize);

    public Page<PrintHistoryResponse> getHistoryPrint(Long studentId, Integer pageNo, Integer pageSize);

    public void changeActive(Long studentId);

    public Page<QAndAResponse> getHistoryQAndA(Integer pageNo, Integer pageSize);

    public void sendAnswer(Long idQAndA, String message);

    public void editPrinter(EditPrinterRequest request, Long idPrinter);
}
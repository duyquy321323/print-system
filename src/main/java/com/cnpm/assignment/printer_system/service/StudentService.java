package com.cnpm.assignment.printer_system.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.cnpm.assignment.printer_system.request.PayPackagePrintRequest;
import com.cnpm.assignment.printer_system.request.SearchPrinterStudentRequest;
import com.cnpm.assignment.printer_system.response.DetailBillResponse;
import com.cnpm.assignment.printer_system.response.DocumentResponse;
import com.cnpm.assignment.printer_system.response.HistoryPaymentResponse;
import com.cnpm.assignment.printer_system.response.PageResponse;
import com.cnpm.assignment.printer_system.response.PrinterStudentResponse;

public interface StudentService {
    public Page<DocumentResponse> getUploadedDocument(Integer pageNo, Integer pageSize);

    public void uploadDocument(List<MultipartFile> documents) throws IOException ;

    public Page<PrinterStudentResponse> getPrinterActive(SearchPrinterStudentRequest request, Integer pageNo,
            Integer pageSize);

    public List<PageResponse> getPageNow();

    public Page<HistoryPaymentResponse> getHistoryPayments(Integer pageNo, Integer pageSize);

    public void payPackagePrints(List<PayPackagePrintRequest> packages);

    public Page<DetailBillResponse> getDetailBills(Integer pageNo, Integer pageSize);

    public void createQuestion(Long idQAndA, String title, String message);

    public void deleteUploadedDocument(Long idDocument);
}
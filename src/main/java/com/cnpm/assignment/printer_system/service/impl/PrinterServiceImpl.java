package com.cnpm.assignment.printer_system.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.cnpm.assignment.printer_system.entity.Document;
import com.cnpm.assignment.printer_system.entity.PageDocument;
import com.cnpm.assignment.printer_system.entity.PageStudent;
import com.cnpm.assignment.printer_system.entity.PrintPackage;
import com.cnpm.assignment.printer_system.entity.Printer;
import com.cnpm.assignment.printer_system.entity.PrinterDocument;
import com.cnpm.assignment.printer_system.entity.Student;
import com.cnpm.assignment.printer_system.entity.User;
import com.cnpm.assignment.printer_system.entity.id.PageDocumentId;
import com.cnpm.assignment.printer_system.entity.id.PrinterDocumentId;
import com.cnpm.assignment.printer_system.enumeration.DocumentStatus;
import com.cnpm.assignment.printer_system.enumeration.PrinterStatusStudent;
import com.cnpm.assignment.printer_system.enumeration.StylePage;
import com.cnpm.assignment.printer_system.enumeration.TypePage;
import com.cnpm.assignment.printer_system.exception.custom.CNPMNotFoundException;
import com.cnpm.assignment.printer_system.repository.DocumentRepository;
import com.cnpm.assignment.printer_system.repository.PageDocumentRepository;
import com.cnpm.assignment.printer_system.repository.PageRepository;
import com.cnpm.assignment.printer_system.repository.PageStudentRepository;
import com.cnpm.assignment.printer_system.repository.PrintPackageRepository;
import com.cnpm.assignment.printer_system.repository.PrinterDocumentRepository;
import com.cnpm.assignment.printer_system.repository.PrinterRepository;
import com.cnpm.assignment.printer_system.repository.UserRepository;
import com.cnpm.assignment.printer_system.response.BuyPackageResponse;
import com.cnpm.assignment.printer_system.service.PrinterService;
import com.cnpm.assignment.printer_system.util.PageCalculator;

@Service
@Transactional
public class PrinterServiceImpl implements PrinterService {

    @Autowired
    private PrinterDocumentRepository printerDocumentRepository;

    @Autowired
    private PrinterRepository printerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private PrintPackageRepository printPackageRepository;

    @Autowired
    private PageCalculator pageCalculator;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private PageDocumentRepository pageDocumentRepository;

    @Autowired
    private PageStudentRepository pageStudentRepository;

    /**
     * Hàm để thêm các tài liệu yêu cầu in vào database (PrinterDocument)
     * B1: Lấy Printer từ database bằng idPrinter
     * B2: Lấy các Document từ database bằng danh sách idDocuments
     * B3: Lưu vào bảng PrinterDocument
     */
    @Override
    public void printDocuments(Long idPrinter, List<Long> idDocuments, StylePage stylePage, TypePage typePage)
            throws IOException {
        // TODO
        // Kiểm tra người dùng hiện tại
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails;
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            userDetails = (UserDetails) authentication.getPrincipal();
            if (userDetails != null) {
                User user = userRepository.findByEmailAndActive(userDetails.getUsername(), true)
                        .orElseThrow(() -> new CNPMNotFoundException("Tài khoản không tồn tại"));
                Student student = (Student) user;
                Printer printer = printerRepository.findById(idPrinter)
                        .orElseThrow(() -> new CNPMNotFoundException("Máy in không tồn tại...!"));
                printer.setPrinterStatusStudent(PrinterStatusStudent.BUSY);
                printerRepository.save(printer);
                List<Document> documents = documentRepository.findAllById(idDocuments);
                List<PrinterDocument> printerDocuments = new ArrayList<>();
                com.cnpm.assignment.printer_system.entity.Page page = pageRepository.findByType(typePage)
                        .orElseThrow(() -> new CNPMNotFoundException("Loại trang không tồn tại...!"));
                Long numberPage = student.getPageStudents().stream().filter(item -> Objects.equals(item.getId().getPage().getId(), page.getId())).collect(Collectors.toList()).getFirst().getPageQuantity();
                for (Document document : documents) {
                    Long quantityPage = Long
                            .valueOf(String.valueOf(pageCalculator.getQuantityPage(stylePage, document.getUrlFile())));
                    pageDocumentRepository.save(
                            PageDocument.builder().id(PageDocumentId.builder().document(document).page(page).build())
                                    .pageQuantity(quantityPage).build());
                    printerDocuments.add(PrinterDocument.builder()
                            .id(PrinterDocumentId.builder().document(document).printer(printer).build())
                            .printDate(LocalDateTime.now()).status(DocumentStatus.PROCESSING).build());
                    if(numberPage - quantityPage < 0) throw new CNPMNotFoundException("Không đủ trang, vui lòng mua thêm!");
                    else numberPage -= quantityPage;
                }
                PageStudent pageStudent = student.getPageStudents().stream().filter(item -> Objects.equals(item.getId().getPage().getId(), page.getId())).collect(Collectors.toList()).getFirst();
                pageStudent.setPageQuantity(numberPage);
                pageStudentRepository.save(pageStudent);
                printerDocumentRepository.saveAll(printerDocuments);
            }
        }
    }

    /**
     * Hàm để lấy hết tất cả các gói in ra để hiện lên màn hình cho học sinh chọn
     * mua
     * 
     * Lấy các đối tượng PrintPackage từ database và chuyển thành danh sách
     * BuyPackageResponse -> trả về
     */
    @Override
    public Page<BuyPackageResponse> getPackagePrint(Integer pageNo, Integer pageSize) {
        // TODO
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<PrintPackage> printPackages = printPackageRepository.findAll(pageable);
        List<BuyPackageResponse> responses = printPackages.stream()
                .map((item) -> BuyPackageResponse.builder().idPackage(item.getId()).namePackage(item.getName())
                        .quantityPage(item.getPageQuantity())
                        .pricePackage(item.getPage().getPrice() * item.getPageQuantity()).build())
                .collect(Collectors.toList());
        return new PageImpl<>(responses, pageable, responses.size());
    }

}
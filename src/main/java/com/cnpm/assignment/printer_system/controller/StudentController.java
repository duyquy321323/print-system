package com.cnpm.assignment.printer_system.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cnpm.assignment.printer_system.request.DocumentsRequest;
import com.cnpm.assignment.printer_system.request.PayPackagePrintRequest;
import com.cnpm.assignment.printer_system.request.SearchPrinterStudentRequest;
import com.cnpm.assignment.printer_system.service.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/student")
@Tag(name = "Student Controller", description = "Controller này dùng riêng cho sinh viên thao tác")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/document")
    @Operation(summary = "Lấy các tài liệu đã tải lên", description = "Lấy các tài liệu chưa xóa mà sinh viên đã từng tải lên hệ thống")
    public ResponseEntity<?> getUploadedDocument(@RequestParam(defaultValue = "0", required = false) Integer pageNo,
            @RequestParam(defaultValue = "6", required = false) Integer pageSize) {
        return ResponseEntity.ok().body(studentService.getUploadedDocument(pageNo, pageSize));
    }

    @DeleteMapping("/document")
    @Operation(summary = "Xóa các tài liệu đã tải lên", description = "Xóa tài liệu mà sinh viên đã tải lên bằng id document")
    public ResponseEntity<?> deleteUploadedDocument(@RequestParam Long idDocument) {
        studentService.deleteUploadedDocument(idDocument);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/document")
    @Operation(summary = "Tải tài liệu mới", description = "Tải tài liệu mới lên hệ thống", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "multipart/form-data", schema = @Schema(implementation = DocumentsRequest.class))))
    public ResponseEntity<?> uploadDocument(DocumentsRequest documents) {
        try {
            studentService.uploadDocument(documents.getDocuments());
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/printer")
    @Operation(summary = "Tìm kiếm máy in", description = "Tìm kiếm các máy in đang hoạt động theo địa chỉ, trạng thái, id")
    public ResponseEntity<?> searchPrinterActive(@RequestBody SearchPrinterStudentRequest request,
            @RequestParam(defaultValue = "0", required = false) Integer pageNo,
            @RequestParam(defaultValue = "10", required = false) Integer pageSize) {
        return ResponseEntity.ok().body(studentService.getPrinterActive(request, pageNo, pageSize));
    }

    @GetMapping("/page")
    @Operation(summary = "Lấy trang còn lại", description = """
            l\u1ea5y c\u00e1c trang c\u00f2n l\u1ea1i c\u1ee7a h\u1ecdc vi\u1ec7n hi\u1ec7n t\u1ea1i bao g\u1ed3m c\u00e1c lo\u1ea1i trang nh\u01b0
                 * (Trang m\u00e0u, trang th\u01b0\u1eddng, ...th\u00eam n\u1ebfu c\u00f3 lo\u1ea1i trang m\u1edbi)""" //
    )
    @ApiResponse(responseCode = "200", description = "Chi tiết lấy các loại trang + số lượng hiện tại của user", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
    public ResponseEntity<?> getPageNow() {
        return ResponseEntity.ok().body(studentService.getPageNow());
    }

    @GetMapping("/history-payment")
    @Operation(summary = "Lấy lịch sử thanh toán", description = "Lấy lịch sử thanh toán không chi tiết")
    public ResponseEntity<?> getHistoryPayments(@RequestParam(defaultValue = "0", required = false) Integer pageNo,
            @RequestParam(defaultValue = "6", required = false) Integer pageSize) {
        return ResponseEntity.ok().body(studentService.getHistoryPayments(pageNo, pageSize));
    }

    @PostMapping("/pay")
    @Operation(summary = "Thanh toán", description = "Thanh toán các gói in đã chọn")
    public ResponseEntity<?> payPackagePrints(@RequestBody List<PayPackagePrintRequest> request) {
        studentService.payPackagePrints(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/bills")
    @Operation(summary = "Lấy lịch sử thanh toán", description = "Lấy lịch sử thanh toán chi tiết")
    public ResponseEntity<?> getDetailBills(@RequestParam(defaultValue = "0", required = false) Integer pageNo,
            @RequestParam(defaultValue = "3", required = false) Integer pageSize) {
        return ResponseEntity.ok().body(studentService.getDetailBills(pageNo, pageSize));
    }

    @PostMapping("/question")
    @Operation(summary = "Tạo câu hỏi", description = "Tạo câu hỏi trong chủ đề có sẵn hoặc chủ để mới")
    public ResponseEntity<?> questionQAndA(@RequestParam(required = false) Long idQAndA, @RequestParam(required=false) String title, @RequestParam String message) {
        studentService.createQuestion(idQAndA, title, message);
        return ResponseEntity.ok().build();
    }
}
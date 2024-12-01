package com.cnpm.assignment.printer_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cnpm.assignment.printer_system.request.EditPrinterRequest;
import com.cnpm.assignment.printer_system.request.SearchPrinterSPSORequest;
import com.cnpm.assignment.printer_system.service.SPSOService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/spso")
@Tag(name = "SPSO Controller", description = "Controller này để dùng riếng cho SPSO")
public class SPSOController {

    @Autowired
    private SPSOService spsoService;

    @PostMapping("/printer")
    @Operation(summary = "Tìm kiếm máy in", description = "Tìm kiếm máy in bằng địa chỉ, trạng thái, id")
    public ResponseEntity<?> searchPrinter(@RequestBody SearchPrinterSPSORequest request,
            @RequestParam(defaultValue = "0", required = false) Integer pageNo, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok().body(spsoService.getPrinter(request, pageNo, pageSize));
    }

    @GetMapping("/students")
    @Operation(summary = "Tìm kiếm học sinh", description = "Tìm kiếm học sinh bằng tên đầy đủ")
    public ResponseEntity<?> searchStudent(@RequestParam(required=false) String fullName,
            @RequestParam(defaultValue = "0", required = false) Integer pageNo, @RequestParam(defaultValue = "10", required = false) Integer pageSize) {
        return ResponseEntity.ok().body(spsoService.searchStudent(fullName, pageNo, pageSize));
    }

    @GetMapping("/history-print{studentId}")
    @Operation(summary = "Lấy lịch sử in", description = "Xem lịch sử in ấn của 1 sinh viên bằng cách truyền id của sinh viên đó vào")
    public ResponseEntity<?> getHistoryPrint(@RequestParam("studentId") Long studentId,
            @RequestParam(defaultValue = "0", required = false) Integer pageNo, @RequestParam(defaultValue = "11", required = false) Integer pageSize) {
        return ResponseEntity.ok().body(spsoService.getHistoryPrint(studentId, pageNo, pageSize));
    }

    @PutMapping("/change-active{studentId}")
    @Operation(summary = "Thay đổi trạng thái", description = "Khóa hoặc mở tài khoản cho 1 sinh viên")
    public ResponseEntity<?> changeActive(@RequestParam(name = "studentId") Long studentId) {
        spsoService.changeActive(studentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/history-q-and-a")
    @Operation(summary = "Lấy các chủ đề hỏi", description = "Lấy ra các chủ để hỏi của tất cả các sinh viên, đã trả lời hoặc chưa trả lời")
    public ResponseEntity<?> getHistoryQAndA(@RequestParam(defaultValue = "0", required = false) Integer pageNo,
            @RequestParam(defaultValue = "10", required = false) Integer pageSize) {
        return ResponseEntity.ok().body(spsoService.getHistoryQAndA(pageNo, pageSize));
    }

    @PostMapping("/answer")
    @Operation(summary = "Trả lời câu hỏi", description = "Trả lời câu hỏi mới nhất trong 1 chủ đề")
    public ResponseEntity<?> answerQAndA(@RequestParam Long idQAndA, @RequestParam String message) {
        spsoService.sendAnswer(idQAndA, message);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/printer")
    @Operation(summary = "Chỉnh sửa máy in", description = "Chỉnh sửa máy in bằng id")
    public ResponseEntity<?> editPrinter(@RequestParam Long idPrinter, @RequestBody EditPrinterRequest request) {
        spsoService.editPrinter(request, idPrinter);
        return ResponseEntity.ok().build();
    }
}
package com.cnpm.assignment.printer_system.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cnpm.assignment.printer_system.enumeration.StylePage;
import com.cnpm.assignment.printer_system.enumeration.TypePage;
import com.cnpm.assignment.printer_system.service.PrinterService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/printer")
@Tag(name = "Printer Controller", description = "Controller này dùng để thao tác với các Printer mà không liên quan nhiều đến user")
public class PrinterController {

    @Autowired
    public PrinterService printerService;

    @Operation(summary = "In tài liệu", description = "Truyền vào máy in và tài liệu cần in")
    @PostMapping("/print{idPrinter}{idDocuments}")
    public ResponseEntity<?> printDocument(@RequestParam(name = "idPrinter") Long idPrinter,
            @RequestParam(name = "idDocuments") List<Long> idDocuments,
            @RequestParam(name = "stylePage") StylePage stylePage, @RequestParam(name = "typePage") TypePage typePage) {
        try {
            printerService.printDocuments(idPrinter, idDocuments, stylePage, typePage);
            return ResponseEntity.ok().build();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/package-print")
    @ApiResponse(responseCode = "200", description = "Chi tiết lấy các cặp câu hỏi và trả lời trong 1 chủ đề", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    @Operation(summary = "Lấy ra các gói in", description = "Các gói in có thể mua trong trang mua gói in của sinh viên, ....")
    public ResponseEntity<?> getPackagePrints(@RequestParam(defaultValue = "0", required = false) Integer pageNo,
            @RequestParam(defaultValue = "6", required = false) Integer pageSize) {
        return ResponseEntity.ok(printerService.getPackagePrint(pageNo, pageSize));
    }
}
package com.cnpm.assignment.printer_system.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cnpm.assignment.printer_system.request.AvatarRequest;
import com.cnpm.assignment.printer_system.response.ContentResponse;
import com.cnpm.assignment.printer_system.response.InformationResponse;
import com.cnpm.assignment.printer_system.response.LoginResponse;
import com.cnpm.assignment.printer_system.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/account")
@Tag(name = "Account Controller", description = "Controller này dùng để thao tác với Account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @ApiResponse(responseCode = "200", description = "Chi tiết tài khoản đăng nhập thành công", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class)))
    @Operation(summary = "Đăng Nhập", description = "Đăng nhập vào hệ thống bằng email và password")
    @PostMapping("/login{email}{password}")
    public ResponseEntity<?> login(@RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password) {
        return ResponseEntity.ok().body(accountService.login(email, password));
    }

    @Operation(summary = "Cập nhật avatar", description = "Dùng để cập nhật avatar trong trang thông tin cá nhân", requestBody = @RequestBody(content = @Content(mediaType = "multipart/form-data", schema = @Schema(implementation = AvatarRequest.class))))
    @PutMapping("/information")
    public ResponseEntity<?> updateAvatar(AvatarRequest avatar){
        try{
        accountService.updateAvatar(avatar.getAvatar());
        }catch(IOException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Thông tin cá nhân", description = "Lấy thông tin cá nhân về và gắn ở trang thông tin cá nhân")
    @ApiResponse(responseCode = "200", description = "Chi tiết lấy thông tin cá nhân thành công", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InformationResponse.class)))
    @GetMapping("/information")
    public ResponseEntity<?> getInformation() {
        return ResponseEntity.ok().body(accountService.getInformation());
    }

    @ApiResponse(responseCode = "200", description = "Chi tiết lấy các cặp câu hỏi và trả lời trong 1 chủ đề", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ContentResponse.class)))
    @Operation(summary = "Lấy nội dung", description = "Lấy nội dung các câu hỏi và câu trả lời trong 1 chủ để hỏi đáp của học sinh và spso")
    @GetMapping("/detail-q-and-a{idQAndA}")
    public ResponseEntity<?> getDetailQAndA(HttpServletResponse response, @RequestParam(name = "idQAndA") Long idQAndA) {
        try{
            return ResponseEntity.ok().body(accountService.getDetailQAndA(response, idQAndA));
        }catch(IOException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
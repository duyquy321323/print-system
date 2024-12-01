package com.cnpm.assignment.printer_system.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.cnpm.assignment.printer_system.response.ContentResponse;
import com.cnpm.assignment.printer_system.response.InformationResponse;
import com.cnpm.assignment.printer_system.response.LoginResponse;

public interface AccountService {
    public LoginResponse login(String email, String password);

    public void updateAvatar(MultipartFile avatar) throws IOException ;

    public InformationResponse getInformation();

    public List<ContentResponse> getDetailQAndA(HttpServletResponse response, Long idQAndA) throws IOException ;
}
package com.cnpm.assignment.printer_system.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cnpm.assignment.printer_system.enumeration.StylePage;

@Component
public class PageCalculator {

           // Kích thước giấy (mm)
           double a4Height = 297;
           double a4Width = 210;
           double a3Height = 420;
           double a3Width = 297;
   
           // Lề giấy (mm)
           double marginTop = 10;
           double marginBottom = 10;
           double marginLeft = 10;
           double marginRight = 10;
   
           // Kích thước font (mm)
           double fontHeight = 5; // Chiều cao một dòng chữ (bao gồm khoảng cách dòng)
           double fontWidth = 2;  // Chiều rộng trung bình của ký tự
    @SuppressWarnings("deprecation")
    public int getQuantityPage(StylePage style, String fileUrl) throws IOException {

        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        MultipartFile file;
        // Lấy InputStream từ URL
        try (InputStream inputStream = connection.getInputStream()) {
            // Chuyển InputStream thành MultipartFile
             file = new MockMultipartFile(
                "file",                               // Tên file
                "downloaded-file",                    // Tên gốc của file
                connection.getContentType(),          // Loại MIME
                inputStream                           // Nội dung file
            );
        }

        int totalCharacters = countCharactersInMultipartFile(file);

        // Tính số trang cho A4

        if(style == StylePage.A4){
            return calculatePages(totalCharacters, a4Height, a4Width, marginTop, marginBottom, marginLeft, marginRight, fontHeight, fontWidth);
        }
        // Tính số trang cho A3
        else if(style == StylePage.A3){
            return calculatePages(totalCharacters, a3Height, a3Width, marginTop, marginBottom, marginLeft, marginRight, fontHeight, fontWidth);
        }
        return -1;
    }

    public static int countCharactersInMultipartFile(MultipartFile file) throws IOException {
        int totalCharacters = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            int currentChar;
            while ((currentChar = reader.read()) != -1) {
                totalCharacters++;
            }
        }

        return totalCharacters;
    }

    public static int calculatePages(int totalCharacters, double paperHeight, double paperWidth,
                                     double marginTop, double marginBottom, double marginLeft, double marginRight,
                                     double fontHeight, double fontWidth) {
        // Kích thước in được
        double usableHeight = paperHeight - (marginTop + marginBottom);
        double usableWidth = paperWidth - (marginLeft + marginRight);

        // Số dòng trên mỗi trang
        int linesPerPage = (int) (usableHeight / fontHeight);

        // Số ký tự trên mỗi dòng
        int charactersPerLine = (int) (usableWidth / fontWidth);

        // Số ký tự trên mỗi trang
        int charactersPerPage = linesPerPage * charactersPerLine;

        // Tính số trang
        int totalPages = totalCharacters / charactersPerPage;
        if (totalCharacters % charactersPerPage != 0) {
            totalPages += 1; // Nếu còn dư ký tự, thêm 1 trang
        }

        return totalPages;
    }
}
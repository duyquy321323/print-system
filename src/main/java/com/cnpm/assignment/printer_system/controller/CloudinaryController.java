package com.cnpm.assignment.printer_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cnpm.assignment.printer_system.service.CloudinaryService;

@RestController
@RequestMapping("/cloudinary")
public class CloudinaryController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("/all-file")
    public ResponseEntity<?> getAllFileCloudinary(@RequestParam String type) throws Exception {
        return ResponseEntity.ok().body(cloudinaryService.getAllFileCloud(type));
    }
}
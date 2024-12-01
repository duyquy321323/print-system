package com.cnpm.assignment.printer_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dau0dy9eu",
                "api_key", "979983237128725",
                "api_secret", "wprSDBGed33Dk9Dt6kyzeIQ_sEA"));
    }
}

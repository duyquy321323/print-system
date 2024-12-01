package com.cnpm.assignment.printer_system.service.impl;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cnpm.assignment.printer_system.service.CloudinaryService;

@Service
@Transactional
public class CloudinaryServiceImpl implements CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    @SuppressWarnings({"rawtypes", "CallToPrintStackTrace"})
    @Override
    public Map getAllFileCloud(String type) {
        try {
            System.out.println(type);
            // Lấy danh sách file từ Cloudinary
            return cloudinary.api().resources(ObjectUtils.asMap(
                    "resource_type", type));
        } catch (Exception e) {
            // Xử lý ngoại lệ, in log hoặc trả về giá trị phù hợp
            e.printStackTrace();
            return null; // Trả về null trong trường hợp có lỗi
        }
    }

}
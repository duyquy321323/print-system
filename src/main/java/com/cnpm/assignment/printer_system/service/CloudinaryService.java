package com.cnpm.assignment.printer_system.service;

import java.util.Map;

public interface CloudinaryService {
    @SuppressWarnings("rawtypes")
    public Map getAllFileCloud(String type) throws Exception;
}
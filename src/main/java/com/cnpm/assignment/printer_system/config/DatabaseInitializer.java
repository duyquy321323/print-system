package com.cnpm.assignment.printer_system.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        // Tạo Trigger khi ứng dụng khởi động
        String sql1 = "CREATE TRIGGER IF NOT EXISTS before_insert_content " +
                "BEFORE INSERT ON content " +
                "FOR EACH ROW BEGIN " +
                "DECLARE max_id BIGINT; " +
                "SELECT COALESCE(MAX(id), 0) + 1 INTO max_id " +
                "FROM content " +
                "WHERE id_q_and_a = NEW.id_q_and_a; " +
                "SET NEW.id = max_id; " +
                "END;";
        jdbcTemplate.execute(sql1);
    }
}

package com.example.scheduler.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JDBCTemplateSchedulerRepository {

    private final JdbcTemplate jdbcTemplate;

    public JDBCTemplateSchedulerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


}

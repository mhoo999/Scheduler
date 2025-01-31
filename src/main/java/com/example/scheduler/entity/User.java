package com.example.scheduler.entity;

import com.example.scheduler.dto.SchedulerRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class User {

    private Long userId;
    private String email;
    private String password;
    private String writer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(SchedulerRequestDto dto) {
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.writer = dto.getWriter();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

}

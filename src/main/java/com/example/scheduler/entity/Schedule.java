package com.example.scheduler.entity;

import com.example.scheduler.dto.SchedulerRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long scheduleId;
    private String contents;
    private String writer;
    private String password;
    private LocalDateTime updateTime;

    public Schedule(SchedulerRequestDto dto) {
        this.contents = dto.getContents();
        this.writer = dto.getWriter();
        this.password = dto.getPassword();
        this.updateTime = LocalDateTime.now();
    }
}

package com.example.scheduler.entity;

import com.example.scheduler.dto.CreateScheduleRequestDto;
import com.example.scheduler.dto.SchedulerRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long scheduleId;
    private String contents;
    private LocalDateTime updatedAt;
    private Long userId;
    private User user;

    public Schedule(CreateScheduleRequestDto dto, Long userId, User user) {

        this.contents = dto.getContents();
        this.updatedAt = LocalDateTime.now();
        this.userId = userId;
        this.user = user;

    }

    public Schedule(Long id, String contents, LocalDateTime updatedAt, Long userId) {
        this.scheduleId = id;
        this.contents = contents;
        this.updatedAt = updatedAt;
        this.userId = userId;
        this.user = null;
    }
}

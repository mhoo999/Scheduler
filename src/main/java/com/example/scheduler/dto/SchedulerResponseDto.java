package com.example.scheduler.dto;

import com.example.scheduler.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SchedulerResponseDto {

    private Long scheduleId;
    private String contents;
    private String writer;
    private LocalDateTime updateTime;

    public SchedulerResponseDto(Schedule schedule) {
        scheduleId = schedule.getScheduleId();
        contents = schedule.getContents();
        writer = schedule.getUser().getWriter();
        updateTime = schedule.getUpdatedAt();
    }
}

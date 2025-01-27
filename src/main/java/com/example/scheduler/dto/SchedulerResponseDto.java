package com.example.scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class SchedulerResponseDto {

    private String userId;

    private Long scheduleId;
    private String contents;
    private LocalDate update;

}

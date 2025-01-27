package com.example.scheduler.dto;

import lombok.Getter;

@Getter
public class SchedulerRequestDto {

    private String userId;
    private String password;

    private String contents;
}

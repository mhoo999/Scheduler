package com.example.scheduler.dto;

import lombok.Getter;

@Getter
public class SchedulerRequestDto {

    private String email;
    private String password;
    private String writer;
    private String contents;

    private String updateDate;

}

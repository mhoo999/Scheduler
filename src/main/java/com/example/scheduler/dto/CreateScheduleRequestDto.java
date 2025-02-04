package com.example.scheduler.dto;

import lombok.Getter;

@Getter
public class CreateScheduleRequestDto {

    private String email;
    private String password;
    private String writer;
    private String contents;

}

package com.example.scheduler.service;

import com.example.scheduler.dto.CreateScheduleRequestDto;
import com.example.scheduler.dto.CreateScheduleResponseDto;
import com.example.scheduler.dto.SchedulerRequestDto;
import com.example.scheduler.dto.SchedulerResponseDto;

import java.util.List;

public interface SchedulerService {
    CreateScheduleResponseDto saveSchedule(CreateScheduleRequestDto dto);
    List<SchedulerResponseDto> findScheduleByFilter(SchedulerRequestDto dto);
    SchedulerResponseDto findScheduleById(Long id);
    SchedulerResponseDto updateSchedule(Long id, String contents, String writer, String password);
    void deleteSchedule(Long id, String password);
}

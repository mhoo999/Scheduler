package com.example.scheduler.service;

import com.example.scheduler.dto.*;

import java.util.List;

public interface SchedulerService {
    CreateScheduleResponseDto saveSchedule(CreateScheduleRequestDto dto);
    List<SchedulerResponseDto> findScheduleByFilter(SchedulerRequestDto dto);
    SchedulerResponseDto findScheduleById(Long id);
    SchedulerResponseDto updateSchedule(Long id, String contents, String writer, String password);
    void deleteSchedule(Long id, String password);

    List<SchedulerResponseDto> findPage(PaginationDto dto);
}

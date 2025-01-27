package com.example.scheduler.repository;

import com.example.scheduler.dto.SchedulerRequestDto;
import com.example.scheduler.dto.SchedulerResponseDto;
import com.example.scheduler.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface SchedulerRepository {
    SchedulerResponseDto saveSchedule(Schedule schedule);
    List<SchedulerResponseDto>findScheduleByFilter(SchedulerRequestDto dto);
    Optional<Schedule> findScheduleById(Long id);
    Boolean checkPassword(Long id, String password);
    int updateSchedule(Long id, String contents, String writer);
}

package com.example.scheduler.repository;

import com.example.scheduler.dto.CreateScheduleResponseDto;
import com.example.scheduler.dto.SchedulerRequestDto;
import com.example.scheduler.dto.SchedulerResponseDto;
import com.example.scheduler.entity.Schedule;
import com.example.scheduler.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SchedulerRepository {
    Long findOrSaveUser(User user);
    CreateScheduleResponseDto saveSchedule(Schedule schedule);
    List<SchedulerResponseDto>findScheduleByFilter(SchedulerRequestDto dto);
    Optional<Schedule> findScheduleById(Long id);
    int updateSchedule(Long id, String contents, String writer);
    int deleteSchedule(Long id);
    Boolean checkPassword(Long scheduleId, String password);
}

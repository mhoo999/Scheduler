package com.example.scheduler.service;

import com.example.scheduler.dto.SchedulerRequestDto;
import com.example.scheduler.dto.SchedulerResponseDto;
import com.example.scheduler.entity.Schedule;
import com.example.scheduler.repository.SchedulerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    private final SchedulerRepository schedulerRepository;

    public SchedulerServiceImpl(SchedulerRepository schedulerRepository) {
        this.schedulerRepository = schedulerRepository;
    }

    @Override
    public SchedulerResponseDto saveSchedule(SchedulerRequestDto dto) {

        LocalDateTime currentTime = LocalDateTime.now();
        Schedule schedule = new Schedule(dto);

        return schedulerRepository.saveSchedule(schedule);
    }

    @Override
    public List<SchedulerResponseDto> findScheduleByFilter(SchedulerRequestDto dto) {
        return schedulerRepository.findScheduleByFilter(dto);
    }

    @Override
    public SchedulerResponseDto findScheduleById(Long id) {
        Optional<Schedule>optionalSchedule = schedulerRepository.findScheduleById(id);

        if (optionalSchedule.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dose not exited id =" + id);
        }

        return new SchedulerResponseDto(optionalSchedule.get());
    }
}

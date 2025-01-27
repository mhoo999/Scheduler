package com.example.scheduler.controller;

import com.example.scheduler.dto.SchedulerRequestDto;
import com.example.scheduler.dto.SchedulerResponseDto;
import com.example.scheduler.service.SchedulerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class SchedulerController {

    private final SchedulerService schedulerService;

    public SchedulerController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @PostMapping
    public ResponseEntity<SchedulerResponseDto> createSchedule(@RequestBody SchedulerRequestDto dto) {
        return new ResponseEntity<>(schedulerService.saveSchedule(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<SchedulerResponseDto> findScheduleByFilter(@RequestBody SchedulerRequestDto dto) {
        return schedulerService.findScheduleByFilter(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchedulerResponseDto> findScheduleById(@PathVariable Long id) {
        return new ResponseEntity<>(schedulerService.findScheduleById(id), HttpStatus.OK);
    }

    /*@PatchMapping("/{id}")
    public ResponseEntity<SchedulerResponseDto> updateSchedule(@PathVariable Long id)*/
}

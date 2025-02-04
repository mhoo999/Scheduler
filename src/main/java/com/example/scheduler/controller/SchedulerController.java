package com.example.scheduler.controller;

import com.example.scheduler.dto.*;
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

    // 스케줄 생성
    @PostMapping
    public ResponseEntity<CreateScheduleResponseDto> createSchedule(@RequestBody CreateScheduleRequestDto dto) {
        return new ResponseEntity<>(schedulerService.saveSchedule(dto), HttpStatus.CREATED);
    }

    // 전체 조회(by writer or updateDate)
    @GetMapping
    public List<SchedulerResponseDto> findScheduleByFilter(@RequestBody SchedulerRequestDto dto) {
        return schedulerService.findScheduleByFilter(dto);
    }

    // 단건 조회(by id)
    @GetMapping("/{id}")
    public ResponseEntity<SchedulerResponseDto> findScheduleById(@PathVariable Long id) {
        return new ResponseEntity<>(schedulerService.findScheduleById(id), HttpStatus.OK);
    }

    // 스케줄 수정
    @PatchMapping("/{id}")
    public ResponseEntity<SchedulerResponseDto> updateSchedule(@PathVariable Long id, @RequestBody UpdateScheduleRequestDto dto) {
        return new ResponseEntity<>(schedulerService.updateSchedule(id, dto.getContents(), dto.getWriter(), dto.getPassword()), HttpStatus.OK);
    }

    // 스케줄 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, @RequestBody DeleteScheduleRequestDto dto) {
        schedulerService.deleteSchedule(id, dto.getPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 페이지네이션
    @GetMapping("/page")
    public List<SchedulerResponseDto> findPage(@RequestBody PaginationDto dto) {
        return schedulerService.findPage(dto);
    }
}

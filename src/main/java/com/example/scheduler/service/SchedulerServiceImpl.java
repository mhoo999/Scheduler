package com.example.scheduler.service;

import com.example.scheduler.dto.*;
import com.example.scheduler.entity.Schedule;
import com.example.scheduler.entity.User;
import com.example.scheduler.repository.SchedulerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    private final SchedulerRepository schedulerRepository;

    public SchedulerServiceImpl(SchedulerRepository schedulerRepository) {
        this.schedulerRepository = schedulerRepository;
    }

    @Override
    public CreateScheduleResponseDto saveSchedule(CreateScheduleRequestDto dto) {

        User user = new User(dto);
        // findOrSaveUser = DB에 user를 조회하고 없다면 생성, 있다면 패스워드 확인하는 메소드
        Long userId = schedulerRepository.findOrSaveUser(user);

        Schedule schedule = new Schedule(dto, userId, user);

        return schedulerRepository.saveSchedule(schedule);
    }

    @Override
    public List<SchedulerResponseDto> findScheduleByFilter(SchedulerRequestDto dto) {
        return schedulerRepository.findScheduleByFilter(dto);
    }

    @Override
    public SchedulerResponseDto findScheduleById(Long id) {
        Optional<Schedule>optionalSchedule = schedulerRepository.findScheduleById(id);

        // 없을 경우
        if (optionalSchedule.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dose not exited id =" + id);
        }

        return new SchedulerResponseDto(optionalSchedule.get());
    }

    @Override
    public SchedulerResponseDto updateSchedule(Long id, String contents, String writer, String password) {
        // 입력 내용 확인
        if (contents ==null || writer == null || password == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid parameters");
        }

        // 비밀번호 확인
        if (!schedulerRepository.checkPassword(id, password)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "It's either an ID that doesn't exist or an incorrect password");
        }

        // 업데이트 실행
        int updateRow = schedulerRepository.updateSchedule(id, contents, writer);

        // 업데이트 내역 없을 경우, Exception 처리
        if (updateRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dose not exited id =" + id);
        }

        Optional<Schedule>optionalSchedule = schedulerRepository.findScheduleById(id);

        return new SchedulerResponseDto(optionalSchedule.get());
    }

    @Override
    public void deleteSchedule(Long id, String password) {
        // 비밀번호 확인
        if (!schedulerRepository.checkPassword(id, password)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "It's either an ID that doesn't exist or an incorrect password");
        }

        int deleteRow = schedulerRepository.deleteSchedule(id);

        if (deleteRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dose not exited id =" + id);
        }
    }

    @Override
    public List<SchedulerResponseDto> findPage(PaginationDto dto) {
        return schedulerRepository.findPage(dto);
    }
}

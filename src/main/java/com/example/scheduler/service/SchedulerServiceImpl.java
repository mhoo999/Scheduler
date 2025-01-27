package com.example.scheduler.service;

import com.example.scheduler.repository.SchedulerRepository;
import org.springframework.stereotype.Service;

@Service
public class SchedulerServiceImpl {

    private final SchedulerRepository schedulerRepository;

    public SchedulerServiceImpl(SchedulerRepository schedulerRepository) {
        this.schedulerRepository = schedulerRepository;
    }


}

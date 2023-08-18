package com.fontana.backend.schedules.repository;

import com.fontana.backend.schedules.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    

}

package com.fontana.backend.schedule.repository;

import com.fontana.backend.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

}

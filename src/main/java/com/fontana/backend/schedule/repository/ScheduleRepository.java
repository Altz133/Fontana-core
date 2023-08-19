package com.fontana.backend.schedule.repository;

import com.fontana.backend.schedule.entity.Schedule;
import com.fontana.backend.schedule.utils.ScheduleCycle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    List<Schedule> findAllByCycle(List<ScheduleCycle> scheduleCycle);

}

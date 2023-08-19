package com.fontana.backend.schedule.service;

import com.fontana.backend.schedule.entity.Schedule;
import com.fontana.backend.schedule.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public void removeById(Integer id) {
        scheduleRepository.deleteById(id);
    }

    public List<Schedule> findByMonth(Timestamp date){
        return scheduleRepository.findByMonth(date);
    }

    public List<Schedule> findByDate(Timestamp date){
        return scheduleRepository.findByDate(date);
    }

}

package com.fontana.backend.schedule.service;

import com.fontana.backend.schedule.entity.Schedule;
import com.fontana.backend.schedule.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    void addSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    void deleteSchedule(Schedule schedule) {
        scheduleRepository.delete(schedule);
    }

    void deleteSchedule(Integer id) {
        scheduleRepository.deleteById(id);
    }

    void updateSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    Schedule getSchedule(Integer id) {
        return scheduleRepository.getReferenceById(id);
    }

    public boolean isCycle(Schedule schedule){
        return !schedule.getCycleDays().isEmpty();
    }
}

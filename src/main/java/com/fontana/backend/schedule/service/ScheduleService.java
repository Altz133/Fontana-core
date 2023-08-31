package com.fontana.backend.schedule.service;

import com.fontana.backend.exception.customExceptions.ScheduleException;
import com.fontana.backend.schedule.entity.Schedule;
import com.fontana.backend.schedule.repository.ScheduleRepository;
import com.fontana.backend.schedule.service.player.SchedulePlayerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public void addSchedule(Schedule schedule) throws ScheduleException {
        if (schedule.getStartTimestamp().before(Timestamp.valueOf(LocalDateTime.now()))){
            throw new ScheduleException("start time is in past");
        }

        if (schedule.getEndTimestamp() != null) {
            if (Timestamp.valueOf(schedule.getEndTimestamp().toLocalDateTime().plusDays(1).toLocalDate().atStartOfDay()).before(schedule.getStartTimestamp())){
                throw new ScheduleException("end date is before start date");
            }
        }



        scheduleRepository.save(schedule);
    }

    public void updateSchedule(Schedule schedule) throws ScheduleException{
        if (schedule.getStartTimestamp().before(Timestamp.valueOf(LocalDateTime.now()))){
            throw new ScheduleException("start time is in past");
        }

        if (schedule.getEndTimestamp() != null) {
            if (Timestamp.valueOf(schedule.getEndTimestamp().toLocalDateTime().plusDays(1).toLocalDate().atStartOfDay()).before(schedule.getStartTimestamp())){
                throw new ScheduleException("end date is before start date");
            }
        }

        scheduleRepository.save(schedule);
    }

    public void deleteSchedule(Schedule schedule) {
        scheduleRepository.delete(schedule);
    }

    public void deleteSchedule(Integer id) {
        scheduleRepository.deleteById(id);
    }

    public Schedule getSchedule(Integer id) {
        return scheduleRepository.getReferenceById(id);
    }

    public boolean isCycle(Schedule schedule) {
        return !schedule.getCycleDays().isEmpty();
    }
}
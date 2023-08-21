package com.fontana.backend.schedule.mapper;

import com.fontana.backend.schedule.dto.ScheduleCardDTO;
import com.fontana.backend.schedule.dto.ScheduleFormDTO;
import com.fontana.backend.schedule.entity.Schedule;
import org.springframework.stereotype.Service;

@Service
public class ScheduleMapper {
    public ScheduleCardDTO scheduleToScheduleCardDTO(Schedule schedule) {
        return new ScheduleCardDTO();
    }

    public Schedule scheduleFormDTOToSchedule(ScheduleFormDTO scheduleFormDTO) {
        return new Schedule();
    }
}

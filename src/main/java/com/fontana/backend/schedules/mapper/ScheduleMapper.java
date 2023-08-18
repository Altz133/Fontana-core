package com.fontana.backend.schedules.mapper;

import com.fontana.backend.schedules.dto.ScheduleCardDTO;
import com.fontana.backend.schedules.dto.ScheduleFormDTO;
import com.fontana.backend.schedules.entity.Schedule;
import org.springframework.stereotype.Service;

@Service
public class ScheduleMapper {
    //name
    //start z timestampu start date biore sama godzine
    //end do godziny startowej dodaje z template duration * repetitions z schedule
    //duration end-start
    //username z schedule
    public ScheduleCardDTO scheduleToScheduleCardDTO(Schedule schedule) {
        return ScheduleCardDTO.builder()
                .name(schedule.getName())
                .start(schedule.getStartDate())
                .end(schedule.getEndDate())
                .duration(schedule.getEndDate())
                .username(schedule.getUser().getUsername()).build();

    }

    public Schedule scheduleFormDTOToSchedule(ScheduleFormDTO scheduleFormDTO) {
        return new Schedule();
    }
}

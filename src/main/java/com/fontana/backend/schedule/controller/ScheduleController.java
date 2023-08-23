package com.fontana.backend.schedule.controller;

import com.fontana.backend.schedule.dto.ScheduleCardDto;
import com.fontana.backend.schedule.dto.ScheduleFormDto;
import com.fontana.backend.schedule.mapper.ScheduleMapper;
import com.fontana.backend.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.fontana.backend.config.RestEndpoints.*;

@RestController
@RequestMapping(SCHEDULE)
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;

    @PostMapping(value = SCHEDULE_ADD)
    public void addSchedule(@RequestBody ScheduleFormDto scheduleFormDto) {
        scheduleService.addSchedule(scheduleMapper.SchduleFormDtoToSchedule(scheduleFormDto));
    }

    @DeleteMapping(value = SCHEDULE_DELETE)
    public void deleteSchedule(@PathVariable Integer id) {
        scheduleService.deleteSchedule(id);
    }

    @PutMapping(value = SCHEDULE_UPDATE)
    public void updateSchedule(@RequestBody ScheduleFormDto scheduleFormDto) {
        scheduleService.updateSchedule(scheduleMapper.SchduleFormDtoToSchedule(scheduleFormDto));
    }

    @GetMapping(value = SCHEDULE_GET_MONTH)
    public Set<Integer> getDaysHavingSchedulesInMonth(Integer year, Integer month, Integer timeZoneDiffMin) {
        return scheduleService.getDaysHavingSchedulesInMonth(year, month, timeZoneDiffMin);
    }

    @GetMapping(value = SCHEDULE_GET_DAY)
    public List<ScheduleCardDto> getSchedulesInDay(Integer year, Integer month, Integer day, Integer timeZoneDiffMin) {
        return scheduleService.getSchedulesInDay(year, month, day, timeZoneDiffMin);
    }

}

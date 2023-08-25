package com.fontana.backend.schedule.controller;

import com.fontana.backend.schedule.dto.ScheduleCardDto;
import com.fontana.backend.schedule.dto.ScheduleFormDto;
import com.fontana.backend.schedule.mapper.ScheduleMapper;
import com.fontana.backend.schedule.service.ScheduleDateService;
import com.fontana.backend.schedule.service.ScheduleService;
import com.fontana.backend.schedule.service.player.SchedulePlayerService;
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
    private final ScheduleDateService scheduleDateService;
    private final SchedulePlayerService schedulePlayerService;

    @PostMapping(value = SCHEDULE_ADD)
    public void addSchedule(@RequestBody ScheduleFormDto scheduleFormDto) {
        scheduleService.addSchedule(scheduleMapper.ScheduleFormDtoToSchedule(scheduleFormDto));
    }

    @DeleteMapping(value = SCHEDULE_DELETE)
    public void deleteSchedule(@PathVariable Integer id) {
        scheduleService.deleteSchedule(id);
    }

    @PutMapping(value = SCHEDULE_UPDATE)
    public void updateSchedule(@RequestBody ScheduleFormDto scheduleFormDto) {
        scheduleService.updateSchedule(scheduleMapper.ScheduleFormDtoToSchedule(scheduleFormDto));
    }

    @GetMapping(value = SCHEDULE_GET_MONTH)
    public Set<Integer> getDaysHavingSchedulesInMonth(@RequestParam Integer year, @RequestParam Integer month) {
        return scheduleDateService.getDaysHavingSchedulesInMonth(year, month);
    }

    @GetMapping(value = SCHEDULE_GET_DAY)
    public List<ScheduleCardDto> getSchedulesInDay(@RequestParam Integer year, @RequestParam Integer month, @RequestParam Integer day) {
        return scheduleDateService.getSchedulesInDay(year, month, day);
    }

    @PutMapping(value = SCHEDULE_STOP)
    public void stopSchedule() {
        schedulePlayerService.stop();
    }

    @GetMapping(value = SCHEDULE_GET_IS_PLAYING)
    public boolean getIsSchedulePlaying() {
        return SchedulePlayerService.isPlaying();
    }

}

package com.fontana.backend.schedule.controller;

import com.fontana.backend.schedule.dto.ScheduleFormDTO;
import com.fontana.backend.schedule.service.ScheduleService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

import static com.fontana.backend.config.RestEndpoints.*;

@Controller
@RequestMapping(SCHEDULE)
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    //TODO implementacja
    //dodawanie z formu
    @PostMapping(value = SCHEDULE_ADD)
    public ResponseEntity<Object> addSchedule(@RequestBody ScheduleFormDTO scheduleFormDTO) {
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = SCHEDULE_DELETE)
    public ResponseEntity<Object> deleteSchedule(@PathVariable @NotNull Integer id) {
        scheduleService.removeById(id);
        return ResponseEntity.ok().build();
    }

    //TODO implementacja
    @GetMapping(value = SCHEDULE_FIND_BY_DATE)
    public ResponseEntity<Object> findSchedulesByDate(@PathVariable Timestamp date){
        return ResponseEntity.ok(scheduleService.findByDate(date));
    }
    @GetMapping(value = SCHEDULE_FIND_BY_MONTH)
    public ResponseEntity<Object> findSchedulesByMonth(@PathVariable Timestamp date){
        return ResponseEntity.ok(scheduleService.findByMonth(date));
    }
}

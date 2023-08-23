package com.fontana.backend.schedule.controller;

import com.fontana.backend.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.fontana.backend.config.RestEndpoints.*;

@Controller
@RequestMapping(SCHEDULE)
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping(value = SCHEDULE_ADD)
    public ResponseEntity<Object> addSchedule() {
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = SCHEDULE_DELETE)
    public ResponseEntity<Object> deleteSchedule() {
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = SCHEDULE_UPDATE)
    public ResponseEntity<Object> updateSchedule() {
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = SCHEDULE_GET)
    public ResponseEntity<Object> getSchedule() {
        return ResponseEntity.ok().build();
    }

}

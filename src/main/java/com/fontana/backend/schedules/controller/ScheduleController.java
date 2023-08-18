package com.fontana.backend.schedules.controller;

import com.fontana.backend.schedules.dto.ScheduleCardDTO;
import com.fontana.backend.schedules.dto.ScheduleFormDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;

import static com.fontana.backend.config.RestEndpoints.*;

@Controller
@RequestMapping(SCHEDULE)
@RequiredArgsConstructor
public class ScheduleController {

    //TODO implementacja
    //dodawanie z formu
    @PostMapping(value = SCHEDULE_ADD)
    public ResponseEntity<Object> addSchedule(@RequestBody ScheduleFormDTO scheduleFormDTO) {
        return ResponseEntity.ok().build();
    }

    //TODO implementacja
    //usuwanie po kardzie
    @PostMapping(value = SCHEDULE_DELETE)
    public ResponseEntity<Object> deleteSchedule(@RequestBody ScheduleCardDTO scheduleCardDTO) {
        return ResponseEntity.ok().build();
    }

    //TODO implementacja
    @GetMapping(value = SCHEDULE_FIND_BY_DATE_AND_CYCLE)
    public ResponseEntity<Object> findScheduleByDate(@RequestBody Timestamp date){
        return ResponseEntity.ok().build();
    }
}

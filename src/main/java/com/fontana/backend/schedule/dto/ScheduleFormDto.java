package com.fontana.backend.schedule.dto;

import com.fontana.backend.schedule.entity.ScheduleCycleDays;

import java.sql.Timestamp;
import java.util.List;

public class ScheduleFormDto {
    private Integer id;

    private String name;

    private String username;

    private Timestamp startTime;

    private Timestamp endTime;

    private List<ScheduleCycleDays> cycleDays;

    private Integer repeat;

    private boolean enabled;

    private List<Integer> templates;
}

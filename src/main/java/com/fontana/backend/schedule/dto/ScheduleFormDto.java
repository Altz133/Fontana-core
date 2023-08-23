package com.fontana.backend.schedule.dto;

import com.fontana.backend.schedule.entity.ScheduleCycleDays;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleFormDto {
    private Integer id;

    private String name;

    private String username;

    private Timestamp startTime;

    private Timestamp endTime;

    private List<ScheduleCycleDays> cycleDays;

    private Integer repeat;

    private List<Integer> templates;

    private boolean isEnabled;
}

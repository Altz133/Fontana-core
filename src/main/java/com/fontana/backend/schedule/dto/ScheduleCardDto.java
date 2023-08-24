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
public class ScheduleCardDto {
    private Integer id;

    private String name;

    private String username;

    private Timestamp startTime;

    private Timestamp endTime;

    private Integer length;

    private boolean isPlaying;

    private boolean isCycle;

    private List<ScheduleCycleDays> cycleDays;

    private boolean isEnabled;
}

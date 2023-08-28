package com.fontana.backend.schedule.dto;

import com.fontana.backend.schedule.entity.ScheduleCycleDays;
import com.fontana.backend.template.dto.TemplateCardDto;
import jakarta.persistence.criteria.CriteriaBuilder;
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

    private Integer repetitions;

    private List<Integer> templateIds;

    private List<String> templateNames;
}

package com.fontana.backend.schedule.dto;

import com.fontana.backend.schedule.entity.ScheduleCycleDays;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @Min(value = 1)
    @Max(value = Integer.MAX_VALUE)
    private Integer id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    private Timestamp startTime;

    private Timestamp endTime;

    @NotNull
    private List<ScheduleCycleDays> cycleDays;

    @Min(value = 0)
    @Max(value = Integer.MAX_VALUE)
    @NotNull
    private Integer repeat;

    @NotNull
    private List<Integer> templates;

    @NotNull
    private boolean isEnabled;
}

package com.fontana.backend.schedule.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.sql.Timestamp;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@Validated
@Builder
public class ScheduleCardDTO {
    @NotNull
    private Integer id;
    @NotEmpty
    private String name;
    @NotEmpty
    private Timestamp start;
    @NotEmpty
    private Timestamp end;
    @NotEmpty
    private Timestamp duration;
    @NotEmpty
    private String username;

}

package com.fontana.backend.schedules.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
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
public class ScheduleFormDTO {

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

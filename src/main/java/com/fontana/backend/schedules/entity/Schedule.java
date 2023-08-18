package com.fontana.backend.schedules.entity;

import com.fontana.backend.template.entity.Template;
import com.fontana.backend.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Schedule {

    @Id
    private Integer id;
    @NotEmpty
    private String name;
    @NotEmpty
    private Timestamp startDate;
    @NotEmpty
    private Timestamp endDate;
    @Min(value = Byte.MIN_VALUE)
    @Max(value = Byte.MAX_VALUE)
    //zmiana byte na set
    // enumow
    //PN  WT SR CZ PT SB ND  EN(ON/OFF)
    //128 64 32 16 8   4  2  1
    private Byte cycle;
    @Min(value = 0)
    private Integer repetitions;
    @ManyToOne
    @JoinColumn(name="username")
    private User user;
    @ManyToMany
    @JoinTable(
            name="templates_schedules",
            joinColumns=@JoinColumn(name="scheduleId", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="templateId", referencedColumnName="id")
    )
    @OrderColumn(name="templateIndex")
    private List<Template> templates;
}

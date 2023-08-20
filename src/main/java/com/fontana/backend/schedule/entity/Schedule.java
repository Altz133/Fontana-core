package com.fontana.backend.schedule.entity;

import com.fontana.backend.schedule.utils.ScheduleDate;
import com.fontana.backend.template.entity.Template;
import com.fontana.backend.user.entity.User;
import jakarta.persistence.*;
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
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduleDate> dates;
    private Timestamp duration;
    private Boolean enabled;
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

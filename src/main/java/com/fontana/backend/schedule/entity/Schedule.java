package com.fontana.backend.schedule.entity;

import com.fontana.backend.template.entity.Template;
import com.fontana.backend.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Set<ScheduleCycleDays> cycleDays;

    private Timestamp startTimestamp;

    private Timestamp endTimestamp;
    
    private int repeat;

    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "templates_schedules",
            joinColumns = @JoinColumn(name = "scheduleId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "templateId", referencedColumnName = "id")
    )
    @OrderColumn(name = "templateIndex")
    private List<Template> templates;
}

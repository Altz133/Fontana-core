package com.fontana.backend.schedule.entity;

import com.fontana.backend.template.entity.Template;
import com.fontana.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;


    @Column
    private LocalDateTime startTime;

    @ManyToMany
    @JoinTable(
            name = "schedules_templates",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "template_id")
    )
    private List<Template> templates;
}
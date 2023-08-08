package com.fontana.backend.template.entity;

import com.fontana.backend.schedule.entity.Schedule;
import com.fontana.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @Column
    private String name;

    @Column
    private LocalDateTime timestamp;

    @Column
    private int duration;


    @ManyToMany(mappedBy = "templates")
    private List<Schedule> schedules;
}
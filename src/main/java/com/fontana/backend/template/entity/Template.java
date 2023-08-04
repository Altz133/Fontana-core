package com.fontana.backend.template.entity;

import com.fontana.backend.schedule.entity.Schedule;
import com.fontana.backend.snapshot.entity.Snapshot;
import com.fontana.backend.user.entity.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name="templates")
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_name")
    private Users user;

    @Column
    private String name;

    @Column
    private LocalDateTime timestamp;

    @Column
    private int duration;

    @OneToMany(mappedBy = "template")
    private List<Snapshot> snapshot;

    @ManyToMany(mappedBy = "templates")
    private List<Schedule> schedules;
}
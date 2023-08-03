package com.fontana.backend.templates.entity;

import com.fontana.backend.schedules.entity.Schedules;
import com.fontana.backend.snapshots.entity.Snapshots;
import com.fontana.backend.user.entity.Users;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Templates {
    @Id
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
    private List<Snapshots> snapshot;

    @ManyToMany(mappedBy = "templates")
    private List<Schedules> schedules;
}

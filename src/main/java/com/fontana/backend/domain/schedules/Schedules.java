package com.fontana.backend.domain.schedules;

import com.fontana.backend.domain.templates.Templates;
import com.fontana.backend.domain.user.entity.Users;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Schedules {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_name")
    private Users user;


    @Column
    private LocalDateTime startTime;

    @ManyToMany
    @JoinTable(
            name = "schedules_templates",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "template_id")
    )
    private List<Templates> templates;

}

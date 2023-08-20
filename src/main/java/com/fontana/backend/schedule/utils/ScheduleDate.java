package com.fontana.backend.schedule.utils;

import com.fontana.backend.schedule.entity.Schedule;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class ScheduleDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Schedule schedule;
    @Column(nullable = false)
    private Timestamp date;
}

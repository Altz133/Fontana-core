package com.fontana.backend.snapshot.entity;

import com.fontana.backend.template.entity.Template;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Snapshot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;

    private byte[] snapshot;
}
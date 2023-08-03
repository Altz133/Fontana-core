package com.fontana.backend.snapshots.entity;

import com.fontana.backend.templates.entity.Templates;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Snapshots {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private Templates template;

    @ElementCollection
    private List<Integer> snapshot;
}

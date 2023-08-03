package com.fontana.backend.domain.templates.snapshots;

import com.fontana.backend.domain.templates.Templates;
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

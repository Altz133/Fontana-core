package com.fontana.backend.template;

import com.fontana.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "template")
public class TemplateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_name")
    private User user;

    private Timestamp created_at;

    private Timestamp updates_at;

    private int[] snapshots_sequence;

    @Enumerated
    private TemplateStatus status;

    @ManyToMany
    @JoinTable(name = "favourite_templates",
            joinColumns = @JoinColumn(name = "template_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_name", referencedColumnName = "username")
    )
    private List<User> users = new ArrayList<>();
}

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
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    private Timestamp createdAt;

    private Timestamp updatesAt;

    private int[] snapshotsSequence;

    @Enumerated
    private TemplateStatus status;

    @ManyToMany
    @JoinTable(name = "favourite_templates",
            joinColumns = @JoinColumn(name = "templateId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "username", referencedColumnName = "username")
    )
    private List<User> users = new ArrayList<>();
}

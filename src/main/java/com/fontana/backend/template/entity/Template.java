package com.fontana.backend.template.entity;

import com.fontana.backend.snapshot.entity.Snapshot;
import com.fontana.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

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

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @Enumerated
    private TemplateStatus status;

    @ManyToMany
    @JoinTable(name = "favourite_templates",
            joinColumns = @JoinColumn(name = "templateId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "username", referencedColumnName = "username")
    )
    private Set<User> usersFavourited;

    @ManyToMany
    @JoinTable(name = "snapshots_templates",
            joinColumns = @JoinColumn(name = "templateId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "snapshotId", referencedColumnName = "id")
    )
    @OrderColumn(name = "snapshotIndex")
    private List<Snapshot> snapshotsSequence;

}

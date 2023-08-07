package com.fontana.backend.role.entity;

import com.fontana.backend.user.entity.Users;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated
    private RoleType roleType;

    @OneToMany(mappedBy = "role")
    private List<Users> users = new ArrayList<>();

}

package com.fontana.backend.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fontana.backend.log.entity.Log;
import com.fontana.backend.role.entity.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Do not change @Table name to `user`. It won't work properly with postgreSQL.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    private String username;
    private String firstName;
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "roleId")
    @JsonIgnore
    private Role role;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Log> logs = new ArrayList<>();
}

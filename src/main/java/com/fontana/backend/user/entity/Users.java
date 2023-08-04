package com.fontana.backend.user.entity;

import com.fontana.backend.role.entity.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class Users {

    @Id
    private String username;
    private String firstName;
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;


}

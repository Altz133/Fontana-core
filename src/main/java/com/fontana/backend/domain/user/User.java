package com.fontana.backend.domain.user;

import com.fontana.backend.domain.Roles.Roles;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class User {

    @Id
    private String name;
    //private String password;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles role;

    public User() {
    }

}

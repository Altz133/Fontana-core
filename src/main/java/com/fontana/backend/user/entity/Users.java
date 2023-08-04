package com.fontana.backend.user.entity;

import com.fontana.backend.roles.entity.Roles;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

public class Users {

    @Id
    private String name;
    //private String password;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles role;
    public Users() {
    }

}

package com.fontana.backend.domain.user;

import com.fontana.backend.domain.Roles.Roles;
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

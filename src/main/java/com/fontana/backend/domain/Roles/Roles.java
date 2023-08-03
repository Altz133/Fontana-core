package com.fontana.backend.domain.Roles;

import com.fontana.backend.domain.user.entity.Users;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Roles {

    @Id
    private Integer id;
    @OneToMany(mappedBy = "role")
    private List<Users> listOfUsers = new ArrayList<>();

    private String name;


    public Roles() {
    }

    public Roles(Integer id, List<Users> listOfUsers, String name) {
        this.id = id;
        this.listOfUsers = listOfUsers;
        this.name = name;
    }
}

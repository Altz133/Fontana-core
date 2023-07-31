package com.fontana.backend.domain.Roles;

import com.fontana.backend.domain.user.User;
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
    private List<User> listofUser = new ArrayList<>();
    private String name;


    public Roles() {
    }
}

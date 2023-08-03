package com.fontana.backend.role.entity;

import com.fontana.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {

    @Id
    private Integer id;

    @OneToMany(mappedBy = "role")
    private List<User> users = new ArrayList<>();

    private String name;
}

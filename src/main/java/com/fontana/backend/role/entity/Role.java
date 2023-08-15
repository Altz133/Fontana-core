package com.fontana.backend.role.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fontana.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany
    @JsonIgnore
    private List<User> users = new ArrayList<>();

}

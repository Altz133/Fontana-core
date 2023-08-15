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
//tabela MUSI nazywac sie users zamiast user, ponieważ według moich (Adama) testów postgres nie lubi nazwy user, ponieważ gdzieś tam w swoim systemie się ta nazwa gryzie
@Table(name = "users")
public class User {

    @Id
    private String username;
    private String firstName;
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;
}

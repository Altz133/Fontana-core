package com.fontana.backend.user.dtos;

import com.fontana.backend.role.entity.Role;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    @Id
    private String username;
    private String firstName;
    private String lastName;
    private Role role;
}

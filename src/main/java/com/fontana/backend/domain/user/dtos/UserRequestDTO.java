package com.fontana.backend.domain.user.dtos;

import lombok.Data;

public @Data class UserRequestDTO {
    private final String login;
    private final String password;

}

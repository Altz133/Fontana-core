package com.fontana.backend.user.dto;

import lombok.Data;

public @Data class UserRequestDTO {
    private final String login;
    private final String password;

}

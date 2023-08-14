package com.fontana.backend.security.blacklist;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BlacklistTokenRequest {

    @NotBlank(message = "Token cannot be blank.")
    private String token;

}
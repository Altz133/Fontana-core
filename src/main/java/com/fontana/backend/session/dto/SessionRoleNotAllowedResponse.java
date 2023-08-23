package com.fontana.backend.session.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionRoleNotAllowedResponse {

    private String message;
    private LocalDateTime intercepted;
}

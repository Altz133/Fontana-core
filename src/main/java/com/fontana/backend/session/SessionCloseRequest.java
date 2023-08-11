package com.fontana.backend.session;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionCloseRequest {

    @NotNull
    @PastOrPresent
    private LocalDateTime closedTime;
}

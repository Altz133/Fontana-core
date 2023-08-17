package com.fontana.backend.security.blacklist;

import com.fontana.backend.security.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlacklistTokenRequest {
    private String token;
    private TokenType tokenType;
}

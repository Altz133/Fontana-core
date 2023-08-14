package com.fontana.backend.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Slf4j
public class AppUtils {

    public Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication;
        }
        return null;
    }

    public String extractAuthenticatedAuthority() {
        Collection<? extends GrantedAuthority> authorities = getAuthentication().getAuthorities();

        GrantedAuthority authority = null;
        if (!authorities.isEmpty()) {
            authority = authorities.iterator().next();
        }
        return authority.toString();
    }
}

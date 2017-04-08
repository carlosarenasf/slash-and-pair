package com.slashandpair.mobile.service.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Created by guillermoblascojimenez on 08/04/17.
 */
@Service
@Slf4j
public class SecurityService {

    public void authenticate(String userId) {

        SecurityContext context = SecurityContextHolder.getContext();

        Authentication authentication = context.getAuthentication();
        if (authentication == null || "anonymousUser".equals(authentication.getName())) {
            log.info("New user authenticated with id {}", userId);
            authentication = new UsernamePasswordAuthenticationToken(userId, null, null);
            context.setAuthentication(authentication);
        }
    }

    public Authentication getAuthentication() {

        SecurityContext context = SecurityContextHolder.getContext();

        Authentication authentication = context.getAuthentication();
        return authentication;

    }


}

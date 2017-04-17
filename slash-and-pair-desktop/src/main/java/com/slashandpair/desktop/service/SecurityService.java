package com.slashandpair.desktop.service;

import com.slashandpair.exchange.PairingToken;
import com.slashandpair.exchange.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by guillermoblascojimenez on 08/04/17.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityService {

    private final TokenService tokenService;
    
    public String generateNewUserId() {
        return RandomStringUtils.randomAlphanumeric(32);
    }

    public Authentication getAuthenticationOrCreateNewOne() {
    	
        SecurityContext context = SecurityContextHolder.getContext();

        Authentication authentication = context.getAuthentication();
        if (authentication == null || "anonymousUser".equals(authentication.getName())) {
            String newUserName = generateNewUserId();
            log.info("New user authenticated with id {}", newUserName);
            log.info("Doing getAuthenticationOrCreateNewOne newusername <<<<<<<<<<<<<<<<<< {}", newUserName);
            authentication = new UsernamePasswordAuthenticationToken(newUserName, null, null);
            context.setAuthentication(authentication);
        }
        return authentication;

    }

    public PairingToken generateToken() {

        int min = 1000;
        int max = 9999;
        int generatedInt = new Random().nextInt(max + 1 - min)  + min;
        Authentication authentication = getAuthenticationOrCreateNewOne();


        String userId = authentication.getName();
        PairingToken pairingToken = PairingToken.of(userId, String.valueOf(generatedInt));
        tokenService.storePairingToken(pairingToken);
        return pairingToken;
    }

}

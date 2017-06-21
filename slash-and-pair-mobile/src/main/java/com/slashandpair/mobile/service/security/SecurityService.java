package com.slashandpair.mobile.service.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Authentication of users is controlled in this class.
 * 
 * @author Carlos
 * @author Victor
 * @author Guillermo
 */
@Service
@Slf4j
public class SecurityService {

	/**
	 * Check if given a userId the user is logged on, otherwise creates a
	 * session for the user
	 * 
	 * @param userId
	 * @return Authentication
	 */
	public Authentication authenticate(String userId) {

		SecurityContext context = SecurityContextHolder.getContext();

		Authentication authentication = context.getAuthentication();
		if (authentication == null || "anonymousUser".equals(authentication.getName())) {
			log.info("New user authenticated with id {}", userId);
			authentication = new UsernamePasswordAuthenticationToken(userId, null, null);
			context.setAuthentication(authentication);
		}
		return authentication;
	}

	/**
	 * Getter of the user session in context
	 * 
	 * @return Authentication
	 */
	public Authentication getAuthentication() {

		SecurityContext context = SecurityContextHolder.getContext();

		Authentication authentication = context.getAuthentication();
		return authentication;

	}

}

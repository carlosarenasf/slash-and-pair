package com.slashandpair.desktop.service;

import java.util.Optional;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.slashandpair.exchange.PairingToken;
import com.slashandpair.exchange.TokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Class that defines security utilities, it goes from generating pairing codes
 * until store it in Redis
 * 
 * @author Victor
 * @author Carlos
 * @author Guillermo
 * 
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityService {

	private final TokenService tokenService;

	/**
	 * generateNewUserId that returns random chars string identifier
	 * 
	 * @return
	 */
	public String generateNewUserId() {
		return RandomStringUtils.randomAlphanumeric(32);
	}

	/**
	 * getAuthenticationOrCreateNewOne this method finds/creates authentication
	 * for a new user request
	 * 
	 * @return authentication
	 * 
	 */
	public Authentication getAuthenticationOrCreateNewOne() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		if (authentication == null || "anonymousUser".equals(authentication.getName())) {
			String newUserName = generateNewUserId();
			log.info("New user authenticated with id {}", newUserName);
			authentication = new UsernamePasswordAuthenticationToken(newUserName, null, null);
			context.setAuthentication(authentication);
		}
		return authentication;
	}

	/**
	 * generateToken Method that creates token when users requests it
	 */
	public PairingToken generateToken() {
		Authentication authentication = getAuthenticationOrCreateNewOne();
		String userId = authentication.getName();
		log.info("user id in generateToken {}", userId);
		String newuseridgenerated = generateNewUserId();
		log.info("newnew user id generated {}", newuseridgenerated);
		PairingToken pairingToken = PairingToken.of(userId, newuseridgenerated);
		tokenService.storePairingToken(pairingToken);
		log.info("newtokengeneratedseemore {}", pairingToken.getToken());
		return pairingToken;
	}

	/**
	 * generateToken4Digits Method that generate 4 digits codes when is
	 * requested
	 * 
	 * @return pairingToken String of 4 int used for authenticate
	 */
	public PairingToken generateToken4Digits(String userId) {
		int min = 1000;
		int max = 9999;
		int generatedInt = new Random().nextInt(max + 1 - min) + min;
		Optional<PairingToken> token1 = tokenService.findPairingTokenByUserId(userId);
		PairingToken token2 = token1.get();
		String token = token2.getToken();
		String userIdToken = token2.getUserId();
		log.debug(" token generated, userId{}, tokenId{} ", userIdToken, token);

		PairingToken pairingToken = PairingToken.of(userId, String.valueOf(generatedInt));
		tokenService.storePairingToken(pairingToken);
		return pairingToken;
	}

}

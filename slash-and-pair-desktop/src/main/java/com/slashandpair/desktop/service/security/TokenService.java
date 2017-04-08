package com.slashandpair.desktop.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * Created by guillermoblascojimenez on 08/04/17.
 */
@Service
@RequiredArgsConstructor
public class TokenService {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String TOKEN_NAMESPACE = "token:";
    private static final String USER_ID_NAMESPACE = "userId:";


    public Optional<PairingToken> findPairingTokenByToken(String token) {
        Map<Object, Object> map = redisTemplate.opsForHash().entries(TOKEN_NAMESPACE + token);
        if (map != null) {
            return Optional.of(PairingToken.fromMap(map));
        } else {
            return Optional.empty();
        }
    }
    public Optional<PairingToken> findPairingTokenByUserId(String userId) {
        Map<Object, Object> map = redisTemplate.opsForHash().entries(USER_ID_NAMESPACE + userId);
        if (map != null) {
            return Optional.of(PairingToken.fromMap(map));
        } else {
            return Optional.empty();
        }
    }

    public void storePairingToken(PairingToken pairingToken) {
        storePairingTokenByTokenKey(pairingToken);
        storePairingTokenByUserIdKey(pairingToken);
    }

    private void storePairingTokenByTokenKey(PairingToken pairingToken) {
        redisTemplate.opsForHash().putAll(TOKEN_NAMESPACE + pairingToken.getToken(), pairingToken.toMap());
    }
    private void storePairingTokenByUserIdKey(PairingToken pairingToken) {
        redisTemplate.opsForHash().putAll(USER_ID_NAMESPACE + pairingToken.getUserId(), pairingToken.toMap());
    }
}

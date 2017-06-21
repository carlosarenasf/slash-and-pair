package com.slashandpair.exchange;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * Class that is in charge of the management of the token using the communication with Redis
 * @author Carlos
 * @author Victor
 * @author Guillermo
 */
@Service
@RequiredArgsConstructor
public class TokenService {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String TOKEN_NAMESPACE = "token:";
    private static final String USER_ID_NAMESPACE = "userId:";


    /**
     * Find stored pairing tokens by tokenId
     * @param token
     * @return
     */
    public Optional<PairingToken> findPairingTokenByToken(String token) {
        Map<Object, Object> map = redisTemplate.opsForHash().entries(TOKEN_NAMESPACE + token);
        if (map != null && !map.isEmpty()) {
            return Optional.of(PairingToken.fromMap(map));
        } else {
            return Optional.empty();
        }
    }

    /**
     *  Find stored pairing tokens by userId
     * @param userId
     * @return
     */
    public Optional<PairingToken> findPairingTokenByUserId(String userId) {
        Map<Object, Object> map = redisTemplate.opsForHash().entries(USER_ID_NAMESPACE + userId);
        if (map != null) {
            return Optional.of(PairingToken.fromMap(map));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Stores token in Redis
     * @param pairingToken
     */
    public void storePairingToken(PairingToken pairingToken) {
        storePairingTokenByTokenKey(pairingToken);
        storePairingTokenByUserIdKey(pairingToken);
    }

    /**
     * Stores token by it's token key
     * @param pairingToken
     */
    private void storePairingTokenByTokenKey(PairingToken pairingToken) {
        redisTemplate.opsForHash().putAll(TOKEN_NAMESPACE + pairingToken.getToken(), pairingToken.toMap());
    }

    /**
     * Stores token by it's userId key
     * @param pairingToken
     */
    private void storePairingTokenByUserIdKey(PairingToken pairingToken) {
        redisTemplate.opsForHash().putAll(USER_ID_NAMESPACE + pairingToken.getUserId(), pairingToken.toMap());
    }
}

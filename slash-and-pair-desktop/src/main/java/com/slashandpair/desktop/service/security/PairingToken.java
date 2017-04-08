package com.slashandpair.desktop.service.security;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guillermoblascojimenez on 08/04/17.
 */
@Data(staticConstructor = "of")
public class PairingToken {

    private final String userId;
    private final String token;


    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("userId", userId);
        map.put("token", token);
        return map;
    }

    public static PairingToken fromMap(Map<Object, Object> map) {
        return PairingToken.of(map.get("userId").toString(),  map.get("token").toString());
    }
}

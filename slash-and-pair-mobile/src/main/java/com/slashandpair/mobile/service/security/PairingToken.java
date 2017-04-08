package com.slashandpair.mobile.service.security;

import lombok.Data;

/**
 * Created by guillermoblascojimenez on 08/04/17.
 */
@Data
public class PairingToken {

    private final String userId;
    private final String token;

}

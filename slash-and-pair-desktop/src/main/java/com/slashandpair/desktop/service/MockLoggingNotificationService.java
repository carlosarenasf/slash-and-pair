package com.slashandpair.desktop.service;

import com.slashandpair.exchange.StringContentExchange;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by guillermoblascojimenez on 08/04/17.
 */
@Slf4j
public class MockLoggingNotificationService implements NotificationService {

    public void notifyNewData(@NonNull String user, @NonNull StringContentExchange data) {
        log.info("Sending to {} data {}", user, data);
    }

    public void notifyMobileConnected(String user) {
        log.info("Connected! {}", user);

    }

}

package com.slashandpair.desktop.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by guillermoblascojimenez on 08/04/17.
 */
@Slf4j
public class MockLoggingNotificationService<E> implements NotificationService<E> {


    @Override
    public void notifyNewData(@NonNull String user, @NonNull E data) {
        log.info("Sending to {} data {}", user, data);
    }

}

package com.slashandpair.desktop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * Created by guillermoblascojimenez on 08/04/17.
 */
@RequiredArgsConstructor
public class WebSocketNotificationService<E> implements NotificationService<E> {

    private final SimpMessagingTemplate messagingTemplate;
    private static final String WEB_SOCKET_SEND_DATA_DESTINATION = "/desktop/receiveMobileData";
    private static final String WEB_SOCKET_CONNECTION_SUCCESS_DESTINATION = "/desktop/mobileConnectionSuccess";

    public void notifyNewData(String user, E data) {

        messagingTemplate.convertAndSendToUser(user, WEB_SOCKET_SEND_DATA_DESTINATION, data);
    }

    public void notifyMobileConnected(String user) {
        messagingTemplate.convertAndSendToUser(user, WEB_SOCKET_CONNECTION_SUCCESS_DESTINATION, new Object());
    }


}

package com.slashandpair.mobile.service;

import com.slashandpair.exchange.StringContentExchange;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by guillermoblascojimenez on 08/04/17.
 */
@Service
@RequiredArgsConstructor
public class WebSocketNotificationService implements NotificationService {

    private final SimpMessagingTemplate messagingTemplate;
    private static final String WEB_SOCKET_SEND_DATA_DESTINATION = "/desktop/sendMobileData";

    public void notifyNewData(String user, StringContentExchange data) {

        messagingTemplate.convertAndSendToUser(user, WEB_SOCKET_SEND_DATA_DESTINATION, data);
    }



}

package com.slashandpair.desktop.service;

import com.slashandpair.exchange.StringContentExchange;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by guillermoblascojimenez on 08/04/17.
 */
@Service
@RequiredArgsConstructor
public class WebSocketNotificationService implements NotificationService {

    private final SimpMessagingTemplate messagingTemplate;
    private static final String WEB_SOCKET_SEND_DATA_DESTINATION = "/desktop/receiveMobileData";
    private static final String WEB_SOCKET_CONNECTION_SUCCESS_DESTINATION = "/desktop/mobileConnectionSuccess";

    public void notifyNewData(String user, StringContentExchange data) {
        messagingTemplate.convertAndSendToUser(user, WEB_SOCKET_SEND_DATA_DESTINATION, data);
    }

    @RabbitListener(
        bindings = @QueueBinding(
            value = @Queue(value = "pairing-queue", durable = "true"),
            exchange = @Exchange(value = "slash-and-pair", ignoreDeclarationExceptions = "true"),
            key = "pairing"
        )
    )
    public void notifyMobileConnected(String user) {
        messagingTemplate.convertAndSendToUser(user, WEB_SOCKET_CONNECTION_SUCCESS_DESTINATION, "connected");
    }


}

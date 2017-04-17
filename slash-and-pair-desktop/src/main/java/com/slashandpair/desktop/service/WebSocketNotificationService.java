package com.slashandpair.desktop.service;

import com.slashandpair.datastructures.GyroscopeData;
import com.slashandpair.exchange.StringContentExchange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONObject;
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
@Slf4j
@RequiredArgsConstructor
public class WebSocketNotificationService implements NotificationService {

    private final SimpMessagingTemplate messagingTemplate;
    private static final String WEB_SOCKET_SEND_DATA_DESTINATION = "/desktop/receiveMobileData";
    private static final String WEB_SOCKET_CONNECTION_SUCCESS_DESTINATION = "/desktop/mobileConnectionSuccess";

    @RabbitListener(
        bindings = @QueueBinding(
            value = @Queue(value = "pairing-queue", durable = "true"),
            exchange = @Exchange(value = "slash-and-pair-pairing", ignoreDeclarationExceptions = "true"),
            key = "pairing"
        )
    )
    public void notifyMobileConnected(String user) {
    	log.info("NotifyPAIRING 111 notifyMobileConnected user <<<<<<<<<<<<<<<<<< {}", user);
        messagingTemplate.convertAndSendToUser(user, WEB_SOCKET_CONNECTION_SUCCESS_DESTINATION, "connected");
    }

    @RabbitListener(
            bindings = @QueueBinding(
                value = @Queue(value = "data-queue", durable = "true"),
                exchange = @Exchange(value = "slash-and-pair-data", ignoreDeclarationExceptions = "true"),
                key = "data"
            )
        )
    
    public void notifyNewData(String data) {
    	JSONObject jsonObj = new JSONObject(data);
    	String user = (String) jsonObj.get("userId");
    	log.info("NotifyNewData 222 WebSocketNotificationService data <<<<<<<<<<<<<<<<<< {}", data);
    	log.info("NotifyNewData 222 WebSocketNotificationService user principal <<<<<<<<<<<<<<<<<< {}", user);
        messagingTemplate.convertAndSendToUser(user, WEB_SOCKET_CONNECTION_SUCCESS_DESTINATION, data);
        messagingTemplate.convertAndSend( WEB_SOCKET_SEND_DATA_DESTINATION, data);
    }
}

package com.slashandpair.desktop.service;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.slashandpair.datastructures.ObjectData;
import com.slashandpair.desktop.web.MainApp;
import com.slashandpair.exchange.DataConvert;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Class that implements listeners that act when callback does by Rabbit
 * @author Victor 
 * @author Carlos
 * @author Guillermo
 * 
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class WebSocketNotificationService implements NotificationService {

    private final SimpMessagingTemplate messagingTemplate;
    @Autowired 
    private final MainApp mainapp;
    private static final String WEB_SOCKET_SEND_DATA_DESTINATION = "/receiveMobileData";
    private static final String WEB_SOCKET_CONNECTION_SUCCESS_DESTINATION = "/mobileConnectionSuccess";

    @RabbitListener(
        bindings = @QueueBinding(
            value = @Queue(value = "pairing-queue", durable = "true"),
            exchange = @Exchange(value = "slash-and-pair-pairing", ignoreDeclarationExceptions = "true"),
            key = "pairing"
        )
    )
    /**
     * notifyMobileConnected Method that notify some new user connection from Mobile
     * @param user Petition origins user 
     */
    public void notifyMobileConnected(String user) {
    	//log.info("NotifyPAIRING 111 notifyMobileConnected user <<<<<<<<<<<<<<<<<< {}", user);
    	mainapp.getApplicationPage();
        messagingTemplate.convertAndSendToUser(user, WEB_SOCKET_CONNECTION_SUCCESS_DESTINATION, "connected");
    }

    @RabbitListener(
        bindings = @QueueBinding(
            value = @Queue(value = "data-queue", durable = "true"),
            exchange = @Exchange(value = "slash-and-pair-data", ignoreDeclarationExceptions = "true"),
            key = "newData"
        )
    )
    /**
     * notifyNewData Method that notify some new data received by Desktop
     * @param data some information sended by RabbitMQ
     */
    public void notifyNewData(String data) {
    	ObjectData dataMap = (ObjectData) DataConvert.mappingFromJson(data);
    	log.info("notifyNewData ---- WebSocketNotificationService {}", dataMap.getJson());
        messagingTemplate.convertAndSendToUser(dataMap.getUserId().toString(), WEB_SOCKET_SEND_DATA_DESTINATION, dataMap.getJson().toString());
    }
}

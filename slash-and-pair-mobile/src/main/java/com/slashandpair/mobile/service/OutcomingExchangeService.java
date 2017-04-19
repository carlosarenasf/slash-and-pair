package com.slashandpair.mobile.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.slashandpair.exchange.DataConvert;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by guillermoblascojimenez on 08/04/17.
 */
/*
 aka Mobile => queue => Desktop
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OutcomingExchangeService {

    private final RabbitTemplate rabbitTemplate;
    private static final String WEB_SOCKET_SEND_DATA_DESTINATION = "/desktop/receiveMobileData";
    private static final String WEB_SOCKET_CONNECTION_SUCCESS_DESTINATION = "/desktop/mobileConnectionSuccess";
    
    
    public void notifyMobilePaired(String userId) {
    	log.info("OutcomingExchangeService notifyMobilePaired {}", userId);
        rabbitTemplate.convertAndSend("slash-and-pair-pairing", "pairing", userId);
    }

    public void sendMobileContent(String userId ,String data) {
    	log.info("OutcomingExchangeService sendMobileContent userId {}", userId);
    	log.info("OutcomingExchangeService sendMobileContent data {}", data);
    	rabbitTemplate.convertAndSend("slash-and-pair-data", "newData", DataConvert.mappingUserAndJson(userId, data));
    }

}

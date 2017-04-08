package com.slashandpair.mobile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by guillermoblascojimenez on 08/04/17.
 */
/*
 aka Mobile => queue => Desktop
 */
@Service
@RequiredArgsConstructor
public class OutcomingExchangeService {

    private final RabbitTemplate rabbitTemplate;

    public void notifyMobilePaired(String userId) {
        rabbitTemplate.convertAndSend("slash-and-pair", "pairing", userId);
    }

    public void sendMobileContent(String userId, String data) {

    }

}

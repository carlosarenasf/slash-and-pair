package com.slashandpair.mobile.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.slashandpair.exchange.DataConvert;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is responsible for writing data to RabbitMQ queues
 * 
 * @author Carlos
 * @author Victor
 * @author Guillermo
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OutcomingExchangeService {

	private final RabbitTemplate rabbitTemplate;

	/**
	 * This method sends the userId to the queue to notify the user of the
	 * connection to the desktop.
	 * 
	 * @param userId
	 */
	public void notifyMobilePaired(String userId) {
		log.info("OutcomingExchangeService notifyMobilePaired {}", userId);
		rabbitTemplate.convertAndSend("slash-and-pair-pairing", "pairing", userId);
	}

	/**
	 * This method sends information from the sensors data to the queue to
	 * notify the desktop with the data
	 * 
	 * @param userId
	 * @param data
	 */
	public void sendMobileContent(String userId, String data) {
		log.info("sendMobileContent outcomingexchangeservice {}", data);
		rabbitTemplate.convertAndSend("slash-and-pair-data", "newData", DataConvert.mappingUserAndJson(userId, data));
	}

}

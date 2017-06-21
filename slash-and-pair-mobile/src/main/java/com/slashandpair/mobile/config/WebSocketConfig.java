package com.slashandpair.mobile.config;

import java.security.Principal;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import com.slashandpair.mobile.service.OutcomingExchangeService;
import com.slashandpair.mobile.service.security.SecurityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * This class enables WebSockets through Spring Application. Contains a bean of securityService 
 * and several methods that filters connections.
 * @author Carlos 
 * @author Victor
 * @author Guillermo
 * 
 */
@Configuration
@EnableWebSocketMessageBroker
@Slf4j
@RequiredArgsConstructor
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
	private final OutcomingExchangeService outcomingOutput;
	private final SecurityService securityService;

	/**
	 * configureMessageBroker this method enables brokers
	 * @param MessageBrokerRegistry config 
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/", "/queue");
		config.setApplicationDestinationPrefixes("/app");
	}

	/**
     * registerStompEndPoints Defines an EndPoint for SpringApplication
     */
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/slash-and-pair").setAllowedOrigins("*").withSockJS();
	}

	/**
     * configureClientInboundChannel before Client send a message, what is caught and is used for authenticate it's sender
     * @param ChannelRegistration
     */
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.setInterceptors(new ChannelInterceptorAdapter() {
			@Override
			/**
	           * preSend caught message and returns it
	           * @param channel
	           * @param message
	           * @return message
	           */
			public Message<?> preSend(Message<?> message, MessageChannel channel) {
				StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
				StompCommand command = accessor.getCommand();

				if (StompCommand.CONNECT.equals(command)) {
					if (accessor.getUser() == null) {
						log.debug("user id in presend- {}" , accessor.getUser());
						Principal user = securityService.getAuthentication();
						accessor.setUser(user);
					} else {
						log.debug("User is authenticated - {}" , accessor.getUser());
					}
				}

				return message;
			}
		});
	}
}
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

import com.slashandpair.mobile.service.security.SecurityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@Configuracion spring lo entiende ocmo que es una clase de configuracion 
@Configuration
//Esta notacion, activa el envio/recepcion de mensajes a traves de websockets.
@EnableWebSocketMessageBroker
@Slf4j
@RequiredArgsConstructor
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    private final SecurityService securityService;
	
	//Este metodo sobreescribe el metodo predeterminado
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic","/queue");
        config.setApplicationDestinationPrefixes("/app");
    }
    
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/slash-and-pair").setAllowedOrigins("*").withSockJS();
    }
    
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
      registration.setInterceptors(new ChannelInterceptorAdapter() {
          @Override
          public Message<?> preSend(Message<?> message, MessageChannel channel) {
        	  
              StompHeaderAccessor accessor =
                  MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
              StompCommand command = accessor.getCommand();
              log.debug("DEBUG METHOD CONFIGURE CLIENT PRESEND: - " + accessor.getId());

              if (StompCommand.CONNECT.equals(command)) {
                  if (accessor.getUser() == null) {
                      Principal user = securityService.getAuthenticationOrCreateNewOne();
                      accessor.setUser(user);
                  }
              }
              log.debug("DEBUG METHOD CONFIGURE CLIENT message: - " + message.toString());
              
              return message;
          }
      });
    }
}
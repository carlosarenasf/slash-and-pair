package com.slashandpair.mobile.config;

import com.slashandpair.mobile.service.security.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.security.Principal;
import com.slashandpair.mobile.service.OutcomingExchangeService;
//@Configuracion spring lo entiende ocmo que es una clase de configuracion 
@Configuration
//Esta notacion, activa el envio/recepcion de mensajes a traves de websockets.
@EnableWebSocketMessageBroker
@Slf4j
@RequiredArgsConstructor
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
	private final OutcomingExchangeService outcomingOutput;
    private final SecurityService securityService;
	
	//Este metodo sobreescribe el metodo predeterminado
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/mobile","/queue");
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
        	  //log.info("Doing clientinboundchannel message <<<<<<<<<<<<<<<<<< {}", message.toString());
        	  //log.info("Doing clientinboundchannel channel? <<<<<<<<<<<<<<<<<< {}", channel.toString());
              StompHeaderAccessor accessor =
                  MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
              StompCommand command = accessor.getCommand();
              //log.info("DEBUG METHOD CONFIGURE CLIENT PRESEND1: - {}" , accessor.getId());
              //log.info("DEBUG METHOD CONFIGURE CLIENT PRESEND2: - {}" , accessor);
              //log.info("DEBUG METHOD CONFIGURE CLIENT PRESEND3: - {}" , accessor.getDestination());
              //log.info("DEBUG METHOD CONFIGURE CLIENT PRESEND4: - {}" , accessor.getLogin());
              //log.info("DEBUG METHOD CONFIGURE CLIENT PRESEND5: - {}" , accessor.getSessionId());
              //log.info("DEBUG METHOD CONFIGURE CLIENT PRESEND6: - {}" , accessor.getSessionAttributes());
              //log.info("DEBUG METHOD CONFIGURE CLIENT PRESEND7: - {}" , accessor.getUser());
              //log.info("DEBUG METHOD CONFIGURE CLIENT PRESEND8: - {}" , accessor.getSubscriptionId());
              
              if (StompCommand.CONNECT.equals(command)) {
                  if (accessor.getUser() == null) {
                	  //log.info("USER EQUALS NULL - {}" , accessor.getUser());
                      Principal user = securityService.getAuthentication();
                      accessor.setUser(user);
                  }else{
                	  //log.info("FUCKING USER different EQUALS NULL try to register again or recuperate - {}" , accessor.getUser());
                  }
              }
              //log.info("DEBUG METHOD CONFIGURE CLIENT message: - " + message.toString());
              //log.info("<<<DEBUG METHOD CONFIGURE CLIENT111>>> getfuckingnameofuserfuckinguser: - {}", accessor.getUser().getName());
              //log.info("<<<DEBUG METHOD CONFIGURE CLIENT111>>> getfuckingnameofuserfuckingmesage!!!!!!!: - {}", message.getPayload());

              return message;
          }
      });
    }
}
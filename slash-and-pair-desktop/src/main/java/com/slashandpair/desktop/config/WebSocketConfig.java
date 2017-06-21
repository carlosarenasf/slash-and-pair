package com.slashandpair.desktop.config;

import com.slashandpair.desktop.service.SecurityService;
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

/**
 * This class enables WebSockets through Spring Application. Contains a bean of securityService 
 * and several methods that filters connections.
 * @author Victor 
 * @author Carlos
 * @author Guillermo
 * 
 */



//Notations
@Configuration
@EnableWebSocketMessageBroker
@Slf4j
@RequiredArgsConstructor
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
	//SecurityService's bean
    private final SecurityService securityService;
	
	/**
	 * configureMessageBroker this method enables brokers
	 * @param MessageBrokerRegistry config 
	 */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/user");
        config.enableSimpleBroker("/");
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
                	  //Authenticating user
                      Principal user = securityService.getAuthenticationOrCreateNewOne();
                      accessor.setUser(user);
                  }else{
                	  //log.info("USER different EQUALS NULL try to register again or recuperate - {}" , accessor.getUser());
                  }
                  
              }
              //log.info("DEBUG METHOD CONFIGURE CLIENT message: - " + message.toString());
              
              return message;
          }
      });
    }
}
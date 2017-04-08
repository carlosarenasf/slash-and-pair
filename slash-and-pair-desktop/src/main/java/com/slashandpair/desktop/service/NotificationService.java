package com.slashandpair.desktop.service;

import com.slashandpair.exchange.StringContentExchange;

/**
 * Created by guillermoblascojimenez on 08/04/17.
 */
public interface NotificationService {

    /*

        this.template.convertAndSendToUser("/topic/greetings",new Greeting(text));
     */
    void notifyNewData(String user, StringContentExchange data);
    void notifyMobileConnected(String user);

}

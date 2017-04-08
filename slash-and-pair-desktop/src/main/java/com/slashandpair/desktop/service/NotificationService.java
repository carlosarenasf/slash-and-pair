package com.slashandpair.desktop.service;

/**
 * Created by guillermoblascojimenez on 08/04/17.
 */
public interface NotificationService<E> {

    /*

        this.template.convertAndSendToUser("/topic/greetings",new Greeting(text));
     */
    void notifyNewData(String user, E data);
    void notifyMobileConnected(String user);

}

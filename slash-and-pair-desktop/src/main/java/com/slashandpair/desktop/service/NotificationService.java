package com.slashandpair.desktop.service;

/**
 * 
 * Interface class that defines necessary methods to interact with Rabbit
 * 
 * @author Victor
 * @author Carlos
 * @author Guillermo
 * 
 */
public interface NotificationService {

	void notifyNewData(String data);

	void notifyMobileConnected(String user);

}

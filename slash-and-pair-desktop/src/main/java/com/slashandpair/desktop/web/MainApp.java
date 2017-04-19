package com.slashandpair.desktop.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.slashandpair.desktop.service.SecurityService;
import com.slashandpair.exchange.PairingToken;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Controller
@RequiredArgsConstructor
@Slf4j
public class MainApp {

    private final SecurityService securityService;
    
    
    
    @GetMapping("/desktop")
    public String getIndex(Model model) {
    	log.info("<<<<<<<<<<<<<<<<< First Time getIndex: - {} ", securityService.getAuthenticationOrCreateNewOne().getName());
        PairingToken pairingToken = securityService.generateToken();
        model.addAttribute("token", pairingToken.getToken());
        model.addAttribute("userId", pairingToken.getUserId());
        log.info("<<<<<<<<<<<<<<<<< First Time pairingToken.getToken() {}: - " ,pairingToken.getToken());
        log.info("<<<<<<<<<<<<<<<<< First Time  pairingToken.getUserId() {}: - " , pairingToken.getUserId());
        return "index";
    }

    @MessageMapping("/dataDesktop")
	@SendTo("/desktop/receiveMobileData")
	public String greeting(String data) throws Exception {
    	log.info("Show me that data<<<<<<<<<<<<<<<<<< {}", data);
    	return new String("Que pasa código" + data);
    }
    
}

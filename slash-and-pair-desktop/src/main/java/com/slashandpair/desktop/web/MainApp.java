package com.slashandpair.desktop.web;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.slashandpair.desktop.service.SecurityService;
import com.slashandpair.exchange.PairingToken;
import com.slashandpair.exchange.QRUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Main App that implements all functionalities of Application
 * Defines end points for receiven messages by client-side
 * @author Victor 
 * @author Carlos
 * @author Guillermo
 * 
 */

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainApp {

    private final SecurityService securityService;
    
    @Autowired
    private SimpMessagingTemplate template;
    /**
     * getIndex returns an html that shows main page of application
     * @param model 
     * @return "index" main page of application
     */
    @GetMapping("/")
    public String getIndex(Model model) {
        PairingToken pairingToken = securityService.generateToken();
        try {
			model.addAttribute("token", "data:image/png;base64," +QRUtils.generateQRDynamicByParameterString(pairingToken.getToken()));
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        model.addAttribute("userId", pairingToken.getUserId());
        return "index";
    }
    
    /**
     * sendCode method, receives petition to generate 4 digits code and returns it to client-side
     * @param principal Users that do that petition
     */
    @MessageMapping("/fourCode")
    public void sendCode(Principal principal) {
    	log.info(">>>>>>>>>>> principal name {}", principal.getName());
        PairingToken pairingToken = securityService.generateToken4Digits(principal.getName());
        try {
        	log.info("DIGITS >>>>>>>>> {}", pairingToken.getToken());
			template.convertAndSendToUser(principal.getName(), "/sendFourCode", pairingToken.getToken());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * getApplicationPage do redirects to /game URL
     * @return
     */
    public String getApplicationPage(){
    	return "redirect:/game";
    }
    


}

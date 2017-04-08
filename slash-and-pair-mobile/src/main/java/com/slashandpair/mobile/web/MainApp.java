package com.slashandpair.mobile.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.slashandpair.mobile.service.security.PairingToken;
import com.slashandpair.mobile.service.security.SecurityService;
import com.slashandpair.mobile.service.sensors.GyroscopeData;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Controller
@RequiredArgsConstructor
@Slf4j
public class MainApp {

    private final SecurityService securityService;



    @GetMapping("/mobile")
    public String getIndex(Model model) {
        PairingToken pairingToken = securityService.generateToken();
        model.addAttribute("token", pairingToken.getToken());
        model.addAttribute("userId", pairingToken.getUserId());
        log.info("User conected, token{}, userId{}", pairingToken.getToken(), pairingToken.getUserId());
        return "index";
    }
    
    @MessageMapping("/dataMobile")
	@SendTo("/mobile/sendMobileData")
	public void greeting(GyroscopeData message) throws Exception {;
		log.info("Gyroscope mobile data: gyroscope{}", message.toString());
	    
	}
	
	@MessageMapping("/code")
	@SendTo("/mobile/sendMobileData")
	public void introduceCode(String code) throws Exception {
		log.info("Code introduced from user for pairing code{}", code);
	}
	


}

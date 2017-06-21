package com.slashandpair.mobile.web;

import java.security.Principal;
import java.util.Optional;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.slashandpair.datastructures.ClickData;
import com.slashandpair.datastructures.GyroscopeData;
import com.slashandpair.exchange.PairingToken;
import com.slashandpair.exchange.TokenService;
import com.slashandpair.mobile.service.OutcomingExchangeService;
import com.slashandpair.mobile.service.security.SecurityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * Class controller of the application, receives the requests and executes each function depending on it
 * @author Carlos
 * @author Victor
 * @author Guillermo
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class MainApp {

    private final SecurityService securityService;
    private final TokenService tokenService;
    private final OutcomingExchangeService outcomingExchangeService;
    
    /**
     * Redirect to template "index"
     * @return String
     */
    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    /**
     * 
Synchronize the mobile session with the desktop through the token
     * @param token
     * @return String
     */
    @PostMapping("/synch")
    public String postSynch(@RequestParam("token") String token) {
        log.info("");
        Optional<PairingToken> pairingTokenOptional = tokenService.findPairingTokenByToken(token);
        if (pairingTokenOptional.isPresent()) {
            String userId = pairingTokenOptional.get().getUserId();
            securityService.authenticate(userId);
            outcomingExchangeService.notifyMobilePaired(userId);
            log.info("PostSynch <<<<<<<<<<<<<<<< {} {}", userId, pairingTokenOptional.toString());
            return "redirect:/exchange";
        } else {
            return "redirect:/tokenError";
        }

    }

    /**
     * Redirect the user to the "exchange" template by retrieving the session
     * @param model
     * @return String
     */
    @GetMapping("/exchange")
    public String getExchange(Model model) {
    	String userId = securityService.getAuthentication().getName();
        model.addAttribute("userId", userId);
    	return "exchange";
    }

    /**
     * Send data from gyroscope sensor
     * @param gyrosJson
     * @param principal
     * @throws Exception
     */
    @MessageMapping("/dataMobile/gyroscope")
	public void sendGyroscope(String gyrosJson, Principal principal) throws Exception {
    	GyroscopeData gyros = new GyroscopeData(gyrosJson);
    	log.debug("sendGyroscope Information: {}", gyros.toString());
    	outcomingExchangeService.sendMobileContent(principal.getName(), gyros.convertDataInJson().toString());
    }
    
    /**
	 * Send data from click sensor
	 * 
	 * @param clickJson
	 * @param principal
	 * @throws Exception
	 */
	@MessageMapping("/dataMobile/click")
	public void sendClick(String clickJson, Principal principal) throws Exception {
		ClickData click = new ClickData(clickJson);
    	outcomingExchangeService.sendMobileContent(principal.getName(), click.convertDataInJson().toString());
    }
}

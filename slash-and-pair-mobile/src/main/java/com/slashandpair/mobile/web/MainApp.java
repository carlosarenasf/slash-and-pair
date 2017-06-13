package com.slashandpair.mobile.web;

import java.security.Principal;
import java.util.Optional;

import org.json.JSONObject;
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



@Controller
@RequiredArgsConstructor
@Slf4j
public class MainApp {

    private final SecurityService securityService;
    private final TokenService tokenService;
    private final OutcomingExchangeService outcomingExchangeService;
    
    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @PostMapping("/synch")
    public String postSynch(@RequestParam("token") String token) {
        log.info("Entra aqui <<<<<<<<<<<<<<<<");
        Optional<PairingToken> pairingTokenOptional = tokenService.findPairingTokenByToken(token);
        if (pairingTokenOptional.isPresent()) {
            String userId = pairingTokenOptional.get().getUserId();
            securityService.authenticate(userId);
            log.info("TokenInfor <<<<<<<<<<<<<<<< {}", pairingTokenOptional.toString());
            outcomingExchangeService.notifyMobilePaired(userId);
            log.info("PostSynch <<<<<<<<<<<<<<<< {}", userId);
            return "redirect:/exchange";
        } else {
            return "redirect:/tokenError";
        }

    }

    @GetMapping("/exchange")
    public String getExchange(Model model) {
    	String userId = securityService.getAuthentication().getName();
        model.addAttribute("userId", userId);
    	//log.info("Exchange userId <<<<<<<<<<<<<<<<<<<<<<<<<<<<< {}", userId);
    	return "exchange";
    }

    @MessageMapping("/dataMobile/gyroscope")
	public void sendGyroscope(String gyrosJson, Principal principal) throws Exception {
    	GyroscopeData gyros = new GyroscopeData(gyrosJson);
    	log.info("sendGyroscope Information: {}", gyros.toString());
    	outcomingExchangeService.sendMobileContent(principal.getName(), gyros.convertDataInJson().toString());
    }
    
    @MessageMapping("/dataMobile/click")
	public void sendClick(String clickJson, Principal principal) throws Exception {
    	ClickData click = new ClickData(clickJson);
    	//log.info("Getting some data from mobile <<<<<<<<<<<<<<<<<< {}", click.toString());
    	//log.info("Getting some data from principal can be? <<<<<<<<<<<<<<<<<< {}", principal);
    	outcomingExchangeService.sendMobileContent(principal.getName(), click.convertDataInJson().toString());
    }
}

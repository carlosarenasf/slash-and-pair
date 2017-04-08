package com.slashandpair.mobile.web;

import com.slashandpair.exchange.PairingToken;
import com.slashandpair.exchange.TokenService;
import com.slashandpair.mobile.service.OutcomingExchangeService;
import com.slashandpair.mobile.service.security.SecurityService;
import com.slashandpair.mobile.service.sensors.GyroscopeData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
@RequiredArgsConstructor
@Slf4j
public class MainApp {

    private final SecurityService securityService;
    private final TokenService tokenService;
    private final OutcomingExchangeService outcomingExchangeService;


    @GetMapping("/mobile")
    public String getIndex() {
        return "index";
    }

    @PostMapping("/synch")
    public String postSynch(@RequestParam("token") String token) {
        Optional<PairingToken> pairingTokenOptional = tokenService.findPairingTokenByToken(token);
        if (pairingTokenOptional.isPresent()) {
            String userId = pairingTokenOptional.get().getUserId();
            securityService.authenticate(userId);
            outcomingExchangeService.notifyMobilePaired(userId);
            return "redirect:/exchange";
        } else {
            return "redirect:/tokenError";
        }

    }

    @GetMapping("/exchange")
    public String getExchange(Model model) {
        return "exchange";
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

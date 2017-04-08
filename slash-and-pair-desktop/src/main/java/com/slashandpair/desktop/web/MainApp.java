package com.slashandpair.desktop.web;

import com.slashandpair.desktop.service.NotificationService;
import com.slashandpair.desktop.service.security.PairingToken;
import com.slashandpair.desktop.service.security.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Send messages, apartado 26.4.5 Sending Messages del manual
 * https://docs.spring.io/spring/docs/current/spring-framework-reference/html/websocket.html#websocket-server
 * @author Victor
 *
 */


@Controller
//@SessionScope
@RequiredArgsConstructor
@Slf4j
public class MainApp {

    private final SimpMessagingTemplate template;
    private final NotificationService notificationService;
    private final SecurityService securityService;



    @GetMapping("/index")
    public String getIndex(Model model) {
        PairingToken pairingToken = securityService.generateToken();
        model.addAttribute("token", pairingToken.getToken());
        return "index";
    }


}

package com.slashandpair.desktop.web;

import com.slashandpair.desktop.service.security.PairingToken;
import com.slashandpair.desktop.service.security.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
@RequiredArgsConstructor
@Slf4j
public class MainApp {

    private final SecurityService securityService;



    @GetMapping("/desktop")
    public String getIndex(Model model) {
        PairingToken pairingToken = securityService.generateToken();
        model.addAttribute("token", pairingToken.getToken());
        model.addAttribute("userId", pairingToken.getUserId());
        return "index";
    }


}

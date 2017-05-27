package com.slashandpair.desktop.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.slashandpair.desktop.service.SecurityService;
import com.slashandpair.exchange.PairingToken;
import com.slashandpair.exchange.QRUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Controller
@RequiredArgsConstructor
@Slf4j
public class MainApp {

    private final SecurityService securityService;
    
    @GetMapping("/desktop")
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
    
    
    public String getApplicationPage(){
    	return "redirect:/game";
    }

}

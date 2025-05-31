package com.whatsapp.integretion.Controller;

import com.whatsapp.integretion.Services.WhatsAppNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/whatsapp")
public class WhatsappFromNumController {

    @Autowired
    private WhatsAppNumberService whatsAppNumberService;

    @PostMapping("/register")
    public ResponseEntity<?> registerNumber(
            @RequestHeader("X-Business-ID") String businessId,
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Map<String, String> request) {

        String token = authHeader.replace("Bearer ", "");
        return whatsAppNumberService.registerNumber(
                businessId,
                token,
                request.get("phone_number"),
                request.get("verified_name")
        );
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyNumber(
            @RequestHeader("X-Phone-ID") String phoneId,
            @RequestHeader("Authorizaution") String authHeader,
            @RequestParam String code) {

        String token = authHeader.replace("Bearer ", "");
        return whatsAppNumberService.verifyNumber(phoneId, token, code);
    }
}
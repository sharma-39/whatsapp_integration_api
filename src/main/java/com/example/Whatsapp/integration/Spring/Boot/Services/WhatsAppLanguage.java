package com.example.Whatsapp.integration.Spring.Boot.Services;

import lombok.Data;

@Data
public class WhatsAppLanguage {
    private String code;

    public WhatsAppLanguage(String code) {
        this.code = code;
    }
}

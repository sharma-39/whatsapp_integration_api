package com.whatsapp.integretion.Services;

import lombok.Data;

@Data
public class WhatsAppLanguage {
    private String code;

    public WhatsAppLanguage(String code) {
        this.code = code;
    }
}

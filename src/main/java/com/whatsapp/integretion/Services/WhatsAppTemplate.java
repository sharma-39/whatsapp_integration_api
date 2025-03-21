package com.whatsapp.integretion.Services;

import lombok.Data;

import java.util.List;

@Data
public class WhatsAppTemplate {
    private String name;
    private WhatsAppLanguage language;
    private List<WhatsAppComponent> components;
}

package com.example.Whatsapp.integration.Spring.Boot.Services;

import lombok.Data;

import java.util.List;

@Data
public class WhatsAppTemplate {
    private String name;
    private WhatsAppLanguage language;
    private List<WhatsAppComponent> components;
}

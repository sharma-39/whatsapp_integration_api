package com.example.Whatsapp.integration.Spring.Boot.Services;
import lombok.Data;

@Data
public class WhatsAppMessageRequest {
    private String messaging_product;
    private String to;
    private String type;
    private WhatsAppTemplate template;
    private WhatsAppDocument document;
    private WhatsAppText text;
}


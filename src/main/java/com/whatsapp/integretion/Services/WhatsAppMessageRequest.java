package com.whatsapp.integretion.Services;
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


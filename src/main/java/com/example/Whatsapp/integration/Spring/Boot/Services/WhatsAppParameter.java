package com.example.Whatsapp.integration.Spring.Boot.Services;

import lombok.Data;

@Data
public class WhatsAppParameter {
    private String type;
    private Object document;
    private String text;

    public WhatsAppParameter(String type, Object value) {
        this.type = type;
        if (type.equals("document")) {
            this.document = value;
        } else if (type.equals("text")) {
            this.text = (String) value;
        }
    }
}

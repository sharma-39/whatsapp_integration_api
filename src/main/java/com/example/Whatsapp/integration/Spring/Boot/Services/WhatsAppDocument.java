package com.example.Whatsapp.integration.Spring.Boot.Services;

import lombok.Data;

@Data
public class WhatsAppDocument {
    private String link;
    private String filename;
    private String caption;


    public WhatsAppDocument(String url, String s) {
        this.link=url;
        this.filename=s;
    }

    public WhatsAppDocument() {

    }
}

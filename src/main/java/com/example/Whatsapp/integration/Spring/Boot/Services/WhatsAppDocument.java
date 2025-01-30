package com.example.Whatsapp.integration.Spring.Boot.Services;

import lombok.Data;

@Data
public class WhatsAppDocument {
    private String link;
    private String filename;
    private String id;


    public WhatsAppDocument(String mediaId,String filename) {
        this.id=mediaId;
        this.filename=filename;
    }
}

package com.example.Whatsapp.integration.Spring.Boot.Services;

import lombok.Data;

@Data
public class WhatsAppBase64Document {
    private String filename;
    private String mime_type;
    private String data;

    public WhatsAppBase64Document(String base64File, String filename, String fileType) {
        this.filename = filename;
        this.mime_type = fileType; // e.g., "application/pdf", "image/jpeg"
        this.data = base64File;
    }
}

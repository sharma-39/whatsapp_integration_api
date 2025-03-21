package com.whatsapp.integretion.Services;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "whatsapp.api")
@Data
public class WhatsAppConfig {
    private String url;
    private String phoneNumberId;
    private String accessToken;
    private String templateName;
    private String languageCode;

    // Getters and Setters
}
package com.example.Whatsapp.integration.Spring.Boot.Services;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WhatsAppComponent {
    private String type;
    private List<WhatsAppParameter> parameters;

    public void addParameter(WhatsAppParameter parameter) {
        if (parameters == null) {
            parameters = new ArrayList<>();
        }
        parameters.add(parameter);
    }
}

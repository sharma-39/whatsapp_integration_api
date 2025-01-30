package com.example.Whatsapp.integration.Spring.Boot.Services;

import lombok.Data;

@Data
public class WhatsAppParameter {
    private String type;
    private Object document;
    private String text;
    private String parameter_name;

    public WhatsAppParameter(String type, Object value,Object parameterValue) {
        this.type = type;
        if (type.equals("document")) {
            this.document = value;
        } else if (type.equals("text")) {
            this.text = (String) value;
            this.parameter_name=parameterValue.toString();
        }
    }

    public WhatsAppParameter(String type, Object value) {
        this.type = type;
        if (type.equals("document")) {
            this.document = value;
        } else if (type.equals("text")) {
            this.text = (String) value;
        }
    }
}

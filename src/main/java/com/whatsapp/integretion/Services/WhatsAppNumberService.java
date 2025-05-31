package com.whatsapp.integretion.Services;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class WhatsAppNumberService {
    private final RestTemplate restTemplate;

    private static final String BASE_URL = "https://graph.facebook.com/v19.0/";

    public WhatsAppNumberService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public ResponseEntity<String> registerNumber(String businessId, String token,
                                                 String phoneNumber, String verifiedName) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("phone_number", phoneNumber);
            requestBody.put("verified_name", verifiedName);
            requestBody.put("code_method", "SMS");

            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

            return restTemplate.postForEntity(
                    BASE_URL + businessId + "/phone_numbers",
                    request,
                    String.class
            );

        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(e.getResponseBodyAsString());
        }
    }

    public ResponseEntity<String> verifyNumber(String phoneId, String token, String code) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("code", code);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

            return restTemplate.postForEntity(
                    BASE_URL + phoneId + "/verify_code",
                    request,
                    String.class
            );

        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(e.getResponseBodyAsString());
        }
    }
}

package com.example.Whatsapp.integration.Spring.Boot.Services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class WhatsAppService {

    @Autowired
    private WhatsAppConfig whatsAppConfig;
    @Autowired
    private MediaFileUpload mediaFileUpload;

    private final RestTemplate restTemplate;

    @Autowired
    public WhatsAppService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendTemplateMessageWithBase64File(String recipientPhoneNumber, String base64File, String fileName, String fileType) {
        String url = whatsAppConfig.getUrl() + "/" + whatsAppConfig.getPhoneNumberId() + "/messages";

        // Prepare the request body
        WhatsAppMessageRequest request = new WhatsAppMessageRequest();
        request.setMessaging_product("whatsapp");
        request.setTo(recipientPhoneNumber);
        request.setType("template");

        WhatsAppTemplate template = new WhatsAppTemplate();
        template.setName(whatsAppConfig.getTemplateName());
        template.setLanguage(new WhatsAppLanguage(whatsAppConfig.getLanguageCode()));

        WhatsAppComponent headerComponent = new WhatsAppComponent();
        headerComponent.setType("header");
        headerComponent.addParameter(new WhatsAppParameter("document", new WhatsAppBase64Document(base64File, fileName, fileType)));
        WhatsAppComponent bodyComponent = new WhatsAppComponent();
        bodyComponent.setType("body");
        bodyComponent.addParameter(new WhatsAppParameter("text", "Hi"));

        template.setComponents(List.of(headerComponent, bodyComponent));
        request.setTemplate(template);

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(whatsAppConfig.getAccessToken());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);  // Ignore nulls
            String jsonRequest = objectMapper.writeValueAsString(request);  // Convert object to JSON
            System.out.println("JSON Request: " + jsonRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Send the request
        HttpEntity<WhatsAppMessageRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Message sent successfully: " + response.getBody());
        } else {
            System.out.println("Failed to send message: " + response.getBody());
        }
    }

    public String getTemplateId() {
        String url = whatsAppConfig.getUrl() + "/" + whatsAppConfig.getPhoneNumberId() + "/message_templates?access_token=" + whatsAppConfig.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Templates: " + response.getBody());
        } else {
            System.out.println("Failed to fetch templates: " + response.getBody());
        }
        return null;
    }

    public void sendDocumentMessage(String recipientPhoneNumber, String documentUrl, String caption, String messageBody) {
        String url = whatsAppConfig.getUrl() + "/" + whatsAppConfig.getPhoneNumberId() + "/messages";

        // Prepare the request body
        WhatsAppMessageRequest request = new WhatsAppMessageRequest();
        request.setMessaging_product("whatsapp");  // Required parameter
        request.setTo(recipientPhoneNumber);      // Recipient's phone number
        request.setType("document");             // Message type

        // Setting up document details (URL & Caption)
//        WhatsAppDocument document = new WhatsAppDocument();
//        document.setLink(documentUrl);            // URL to the document// Optional caption
//        request.setDocument(document);

        // Setting up text message body
        WhatsAppText text = new WhatsAppText();
        text.setBody(messageBody);                // Message body (personalized)
        request.setText(text);

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);      // Setting content type to JSON
        headers.setBearerAuth(whatsAppConfig.getAccessToken());  // Authorization token
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);  // Ignore nulls
            String jsonRequest = objectMapper.writeValueAsString(request);  // Convert object to JSON
            System.out.println("JSON Request: " + jsonRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Sending the request to the API
        HttpEntity<WhatsAppMessageRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        // Handling response
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Message sent successfully: " + response.getBody());
        } else {
            System.out.println("Failed to send message: " + response.getBody());
        }
    }

    public void sendTemplateMessageWithFile(String recipientPhoneNumber, String fileUrl, String fileName) {
        String url = whatsAppConfig.getUrl() + "/" + whatsAppConfig.getPhoneNumberId() + "/messages";

        // Prepare the request body
        WhatsAppMessageRequest request = new WhatsAppMessageRequest();
        request.setMessaging_product("whatsapp");
        request.setTo(recipientPhoneNumber);
        request.setType("template");

        WhatsAppTemplate template = new WhatsAppTemplate();
        template.setName(whatsAppConfig.getTemplateName());
        template.setLanguage(new WhatsAppLanguage(whatsAppConfig.getLanguageCode()));

        WhatsAppComponent headerComponent = new WhatsAppComponent();
        headerComponent.setType("header");
        // headerComponent.addParameter(new WhatsAppParameter("document", new WhatsAppDocument(fileUrl, fileName)));

        WhatsAppComponent bodyComponent = new WhatsAppComponent();
        bodyComponent.setType("body");
        bodyComponent.addParameter(new WhatsAppParameter("text", "Sharma Murugaiyan", "patient_name"));

        template.setComponents(List.of(headerComponent, bodyComponent));
        request.setTemplate(template);

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(whatsAppConfig.getAccessToken());

        // Send the request
        HttpEntity<WhatsAppMessageRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Message sent successfully: " + response.getBody());
        } else {
            System.out.println("Failed to send message: " + response.getBody());
        }
    }

    public void sendTemplateMessageWithFileBase64(String recipientPhoneNumber, String base64Url, String fileName) {
        String url = whatsAppConfig.getUrl() + "/" + whatsAppConfig.getPhoneNumberId() + "/messages";

        String mediaId;
        try {
            mediaId = extractMediaId(mediaFileUpload.uploadBase64Pdf(base64Url, fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Media Id-------------------------------" + mediaId);
        // Prepare the request body
        if (mediaId != null) {
            WhatsAppMessageRequest request = new WhatsAppMessageRequest();
            request.setMessaging_product("whatsapp");
            request.setTo(recipientPhoneNumber);
            request.setType("template");

            WhatsAppTemplate template = new WhatsAppTemplate();
            template.setName(whatsAppConfig.getTemplateName());
            template.setLanguage(new WhatsAppLanguage(whatsAppConfig.getLanguageCode()));

            WhatsAppComponent headerComponent = new WhatsAppComponent();
            headerComponent.setType("header");
            headerComponent.addParameter(new WhatsAppParameter("document", new WhatsAppDocument(mediaId, fileName)));

            WhatsAppComponent bodyComponent = new WhatsAppComponent();
            bodyComponent.setType("body");
            bodyComponent.addParameter(new WhatsAppParameter("text", "Sharma Murugaiyan", "patient_name"));

            template.setComponents(List.of(headerComponent, bodyComponent));
            request.setTemplate(template);

            // Prepare headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(whatsAppConfig.getAccessToken());

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);  // Ignore nulls
                String jsonRequest = objectMapper.writeValueAsString(request);  // Convert object to JSON
                System.out.println("JSON Request: " + jsonRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Send the request
            HttpEntity<WhatsAppMessageRequest> entity = new HttpEntity<>(request, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Message sent successfully: " + response.getBody());
            } else {
                System.out.println("Failed to send message: " + response.getBody());
            }
        }
    }

    // Example method to extract the 'media_id' from the response
    private String extractMediaId(String responseBody) {
        // You would typically use a JSON parsing library like Jackson or Gson here.
        // For simplicity, assuming the response is a simple JSON with an 'id' field.
        int startIdx = responseBody.indexOf("\"id\":\"") + 6;
        int endIdx = responseBody.indexOf("\"", startIdx);
        return responseBody.substring(startIdx, endIdx);
    }

    public void sendTemplateMessage(String recipientPhoneNumber) {
        String url = whatsAppConfig.getUrl() + "/" + whatsAppConfig.getPhoneNumberId() + "/messages";

        // Prepare the request body
        WhatsAppMessageRequest request = new WhatsAppMessageRequest();
        request.setMessaging_product("whatsapp");
        request.setTo(recipientPhoneNumber);
        request.setType("template");

        WhatsAppTemplate template = new WhatsAppTemplate();
        template.setName(whatsAppConfig.getTemplateName());
        template.setLanguage(new WhatsAppLanguage(whatsAppConfig.getLanguageCode()));

        // Body component with dynamic parameters

        WhatsAppComponent bodyComponent = new WhatsAppComponent();
        bodyComponent.setType("body");
        bodyComponent.addParameter(new WhatsAppParameter("text", "*Sharma Murugaiyan*", "patient_name"));
        bodyComponent.addParameter(new WhatsAppParameter("text", "https://app.swiftehr.com/swiftehr_v2/"+generateUniqueHash("pharmacy_bill.pdf")+"/", "insertlink"));

        template.setComponents(List.of(bodyComponent));
        request.setTemplate(template);

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(whatsAppConfig.getAccessToken());

        // Send the request
        HttpEntity<WhatsAppMessageRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Message sent successfully: " + response.getBody());
        } else {
            System.out.println("Failed to send message: " + response.getBody());
        }
    }

    private static String generateUniqueSHA1(String input) {
        try {
            // Get the current timestamp
            String timestamp = String.valueOf(Instant.now().toEpochMilli());

            // Combine input and timestamp
            String uniqueInput =  timestamp+"_"+input ;

            // Create a MessageDigest instance for SHA-1
            MessageDigest digest = MessageDigest.getInstance("SHA-1");

            // Generate the hash
            byte[] hashBytes = digest.digest(uniqueInput.getBytes(StandardCharsets.UTF_8));

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-1 algorithm not found", e);
        }
    }
    private static String generateUniqueHash(String filename) {
        try {
            // Get the current timestamp
            String timestamp = String.valueOf(Instant.now().toEpochMilli());

            // Generate a random UUID
            String randomValue = UUID.randomUUID().toString();

            // Combine filename, timestamp, and random value
            String input = filename + "_" + timestamp + "_" + randomValue;

            // Create a MessageDigest instance for SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Generate the hash
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }
}



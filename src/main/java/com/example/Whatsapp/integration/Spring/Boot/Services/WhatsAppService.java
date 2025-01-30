package com.example.Whatsapp.integration.Spring.Boot.Services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class WhatsAppService {

    @Autowired
    private WhatsAppConfig whatsAppConfig;

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
        WhatsAppDocument document = new WhatsAppDocument();
        document.setLink(documentUrl);            // URL to the document
        document.setCaption(caption);             // Optional caption
        request.setDocument(document);

        // Setting up text message body
        WhatsAppText text = new WhatsAppText();
        text.setBody(messageBody);// Message body (personalized)
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

    public void sendTemplateMessageWithFile(String recipientPhoneNumber, String base64File, String fileName, String fileType) {
        String url = whatsAppConfig.getUrl() + "/" + whatsAppConfig.getPhoneNumberId() + "/messages";

// Prepare the request body
        WhatsAppMessageRequest request = new WhatsAppMessageRequest();
        request.setMessaging_product("whatsapp");
        request.setTo(recipientPhoneNumber);
        request.setType("template");

// Prepare template with its components
        WhatsAppTemplate template = new WhatsAppTemplate();
        template.setName(whatsAppConfig.getTemplateName());
        template.setLanguage(new WhatsAppLanguage(whatsAppConfig.getLanguageCode()));

// Prepare header component with document URL
        WhatsAppComponent headerComponent = new WhatsAppComponent();
        headerComponent.setType("header");
        WhatsAppParameter headerParam = new WhatsAppParameter("document",
                new WhatsAppDocument("https://pdfobject.com/pdf/sample.pdf", "pharmacy_bill.pdf")); // Replace with your actual file URL
        headerComponent.addParameter(headerParam);

// Prepare body component with patient name (parameter_name "patient_name")
        WhatsAppComponent bodyComponent = new WhatsAppComponent();
        bodyComponent.setType("body");
        WhatsAppParameter bodyParam = new WhatsAppParameter("patient_name", "Sharma"); // Replace with actual patient name
        bodyComponent.addParameter(bodyParam);

        template.setComponents(List.of(headerComponent, bodyComponent));
        request.setTemplate(template);

// Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(whatsAppConfig.getAccessToken());

        try {
            // Convert the request to JSON
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
        headerComponent.addParameter(new WhatsAppParameter("document", new WhatsAppDocument(fileUrl, fileName)));

        WhatsAppComponent bodyComponent = new WhatsAppComponent();
        bodyComponent.setType("body");
        bodyComponent.addParameter(new WhatsAppParameter("text", "Sharma Murugaiyan","patient_name"));

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


}
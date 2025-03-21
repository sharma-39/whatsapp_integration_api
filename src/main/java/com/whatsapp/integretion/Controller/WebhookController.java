package com.whatsapp.integretion.Controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("/webhook") // This defines the endpoint path
public class WebhookController {

    // Handle verification request (GET)
    @GetMapping
    public String verifyWebhook(
            @RequestParam("hub.mode") String hubMode,
            @RequestParam("hub.challenge") String hubChallenge,
            @RequestParam("hub.verify_token") String hubVerifyToken) {

        // Verify the token (replace with your actual token)
        if (hubMode.equals("subscribe")) {
            return hubChallenge; // Return the challenge to verify the webhook
        } else {
            throw new RuntimeException("Invalid verification token");
        }
    }

    // Handle incoming events (POST)
    @PostMapping
    public ResponseEntity<String> handleIncomingEvent(@RequestBody String payload) {
        // Process the incoming event payload
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(payload);


            System.out.println("full body:--"+rootNode);
            // Extract message details
            JsonNode messagesNode = rootNode.path("entry").get(0).path("changes").get(0).path("value").path("messages");

            System.out.println("JSON node;"+messagesNode);
            if (messagesNode.isArray()) {
                for (JsonNode messageNode : messagesNode) {
                    String sender = messageNode.path("from").asText();
                    String message = messageNode.path("text").path("body").asText();
                    long timestampMilli=messageNode.path("timestamp").asLong();
                    Instant instant = Instant.ofEpochSecond(timestampMilli);

                    // Convert to system default time zone
                    ZonedDateTime dateTime = instant.atZone(ZoneId.systemDefault());

                    // Convert to SQL Timestamp
                    Timestamp timestamp = Timestamp.from(instant);
                    System.out.println("Received message from: " + sender);
                    System.out.println("Message: " + message);
                    System.out.println("Timestamp "+ timestamp);


                    // [{"from":"16315551181","id":"ABGGFlA5Fpa","timestamp":"1504902988","type":"text","text":{"body":"this is a text message"}
                    // Further processing here...
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to process the message");
        }

        return ResponseEntity.ok("Message processed successfully");
    }
}
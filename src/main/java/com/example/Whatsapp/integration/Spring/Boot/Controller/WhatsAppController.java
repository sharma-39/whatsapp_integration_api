package com.example.Whatsapp.integration.Spring.Boot.Controller;

import com.example.Whatsapp.integration.Spring.Boot.Services.WhatsAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/whatsapp")
public class WhatsAppController {

    @Autowired
    private WhatsAppService whatsAppService;

    @PostMapping("sendd-base64")
    public String sendBase64Message(@RequestParam String recipientPhoneNumber,
                                    @RequestParam String base64File,
                                    @RequestParam String fileName,
                                    @RequestParam String fileType) {
        whatsAppService.sendTemplateMessageWithFile(recipientPhoneNumber, base64File, fileName, fileType);
        return "Message sent!";
    }
    @GetMapping("/messageTemplate")
    public  String getMessageTemplate()
    {
        return whatsAppService.getTemplateId();
    }
    @PostMapping("/send-document")
    public String sendDocumentMessage(@RequestParam String recipientPhoneNumber,
                                      @RequestParam String documentUrl,
                                      @RequestParam String caption,
                                      @RequestParam String messageBody) {
        whatsAppService.sendDocumentMessage(recipientPhoneNumber, documentUrl, caption, messageBody);
        return "Document message sent!";
    }
    @PostMapping("/send-document-file")
    public String sendBase64MessageFilenew(@RequestParam String recipientPhoneNumber,
                                    @RequestParam String base64File,
                                    @RequestParam String fileName,
                                    @RequestParam String fileType) {
        whatsAppService.sendTemplateMessageWithFile(recipientPhoneNumber, base64File, fileName, fileType);
        return "Message sent!";
    }

    @PostMapping("/send-base64")
    public String sendMessage(@RequestParam String recipientPhoneNumber,
                              @RequestParam String fileUrl,
                              @RequestParam String fileName) {
        whatsAppService.sendTemplateMessageWithFile(recipientPhoneNumber, fileUrl, fileName);
        return "Message sent!";
    }
}
package com.example.Whatsapp.integration.Spring.Boot.Controller;

import com.example.Whatsapp.integration.Spring.Boot.Services.MediaFileUpload;
import com.example.Whatsapp.integration.Spring.Boot.Services.WhatsAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/whatsapp")
public class WhatsAppController {

    @Autowired
    private WhatsAppService whatsAppService;

    @Autowired
    private MediaFileUpload mediaFileUpload;

    @PostMapping("/send-base64")
    public String sendBase64Message(@RequestParam String recipientPhoneNumber,
                                    @RequestParam String base64File,
                                    @RequestParam String fileName,
                                    @RequestParam String fileType) {
        whatsAppService.sendTemplateMessageWithBase64File(recipientPhoneNumber, base64File, fileName, fileType);
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
    @PostMapping("/send-file")
    public String sendMessage(@RequestParam String recipientPhoneNumber,
                              @RequestParam String fileUrl,
                              @RequestParam String fileName) {
        whatsAppService.sendTemplateMessageWithFile(recipientPhoneNumber, fileUrl, fileName);
        return "Message sent!";
    }


    @PostMapping("/send-base-file")
    public String sendMessageBase64( @RequestBody Map<String, String> request) {

        String recipientPhoneNumber = request.get("toNumber");
        String fileUrl = request.get("fileName");
        String base64file=request.get("base64file");

        whatsAppService.sendTemplateMessageWithFileBase64(recipientPhoneNumber, base64file,fileUrl);
        return "Message sent!";
    }
    @PostMapping("/upload-pdf")
    public String uploadPdf(@RequestBody Map<String, String> request) throws IOException {
        String base64Pdf = request.get("base64Pdf");
        String fileName = request.get("description");

        try {
            return mediaFileUpload.uploadBase64Pdf(base64Pdf, fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
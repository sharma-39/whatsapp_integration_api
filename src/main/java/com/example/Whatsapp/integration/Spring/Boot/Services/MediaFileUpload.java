package com.example.Whatsapp.integration.Spring.Boot.Services;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MediaFileUpload {

    @Autowired
    private WhatsAppConfig whatsAppConfig;

    public String uploadBase64Pdf(String base64Pdf, String fileName) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String uploadUrl = whatsAppConfig.getUrl() +"/"+ whatsAppConfig.getPhoneNumberId() + "/media";
        HttpPost httpPost = new HttpPost(uploadUrl);

        // Convert base64 to byte array
        byte[] pdfBytes = java.util.Base64.getDecoder().decode(base64Pdf);

        // Build the multipart request
        // Build the multipart request
        HttpEntity entity = MultipartEntityBuilder.create()
                .addBinaryBody("file", pdfBytes, ContentType.create("application/pdf"), fileName)
                .addTextBody("messaging_product", "whatsapp")
                .addTextBody("access_token", whatsAppConfig.getAccessToken())
                .build();
        httpPost.setEntity(entity);

        // Execute the request
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            HttpEntity responseEntity = response.getEntity();
            String responseString = EntityUtils.toString(responseEntity);

            // Parse the response to get the file ID
            // Example response: {"id": "123456789"}
            return responseString;
        }
    }

}

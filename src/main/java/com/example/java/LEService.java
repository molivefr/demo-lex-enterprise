package com.example.java;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LEService {

    public <T> ResponseEntity<T> get(String pUrl, String pToken, Class<T> type) throws Exception {
        HttpGet request = new HttpGet(pUrl);
        request.addHeader("Authorization", "Bearer " + pToken);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(request);) {
            HttpEntity entity = response.getEntity();
            T tresult = null;
            HttpHeaders responseHeaders = new HttpHeaders();
            HttpStatus status = HttpStatus.valueOf(response.getStatusLine().getStatusCode());

            if (entity != null) {
                if (type.equals(byte[].class)) {
                    tresult = (T) EntityUtils.toByteArray(entity);
                    responseHeaders.set("Content-Type", "application/pdf"); 
                }
                else if (type.equals(String.class)) {
                    tresult = (T) EntityUtils.toString(entity);
                    responseHeaders.set("Content-Type", "application/json"); 
                }
            }

            return ResponseEntity.status(status).headers(responseHeaders).body(tresult);
        }
    }

    public ResponseEntity<String> post(String pUrl, String pToken, String pPayload) throws Exception {
        HttpPost request = new HttpPost(pUrl);
        request.addHeader("accept", "application/json");
        request.addHeader("authorization", "Bearer " + pToken);
        request.addHeader("Content-Type", "application/json");
        
        StringEntity se = new StringEntity(pPayload, "UTF-8");
        request.setEntity(se);
            
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(request);) {
                HttpEntity entity = response.getEntity();
                String sresult = "";
                HttpStatus status = HttpStatus.valueOf(response.getStatusLine().getStatusCode());
                if (entity != null) {
                    sresult = EntityUtils.toString(entity);
                }

                return ResponseEntity.status(status).body(sresult);
            }
    }  
    
    public ResponseEntity<String> postMultipart(String pUrl, String pToken, MultipartFile document) throws IOException {
        HttpPost request = new HttpPost(pUrl);
        request.addHeader("accept", "application/json");
        request.addHeader("authorization", "Bearer " + pToken);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody("document", document.getBytes(), ContentType.create("application/pdf"), document.getOriginalFilename());

        HttpEntity multipart = builder.build();
        request.setEntity(multipart);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(request);) {
            HttpEntity entity = response.getEntity();
            String sresult = "";
            HttpStatus status = HttpStatus.valueOf(response.getStatusLine().getStatusCode());
            if (entity != null) {
                sresult = EntityUtils.toString(entity);
            }

            return ResponseEntity.status(status).body(sresult);
        }
    }

    public ResponseEntity<String> patch(String pUrl, String pToken, String pPayload) throws Exception {
        HttpPatch request = new HttpPatch(pUrl);
        request.addHeader("accept", "application/json");
        request.addHeader("authorization", "Bearer " + pToken);
        request.addHeader("Content-Type", "application/json");
        
        StringEntity se = new StringEntity(pPayload, "UTF-8");
        request.setEntity(se);
            
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(request);) {
                HttpEntity entity = response.getEntity();
                String sresult = "";
                HttpStatus status = HttpStatus.valueOf(response.getStatusLine().getStatusCode());
                if (entity != null) {
                    sresult = EntityUtils.toString(entity);
                }

                return ResponseEntity.status(status).body(sresult);
            }
    } 
}
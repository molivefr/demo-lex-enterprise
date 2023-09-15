package com.example.java;

import com.example.java.models.Workflow;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import com.google.gson.Gson;
import javax.annotation.PostConstruct;

@RestController
public class DemoController {
    String baseUrl = "";
    String token = "";
    String userId = "";

    @Autowired 
    private LEService leSrv;

    @PostConstruct
    public void init() throws IOException {
        //File file = ResourceUtils.getFile("classpath:static/config.json");
        //InputStream is = new FileInputStream(file);
        InputStream is = getClass().getResourceAsStream("/static/config.json");
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> credentials = mapper.readValue(is,Map.class);

        baseUrl = (String)credentials.get("base_url");
        token = (String)credentials.get("token");
        userId = (String)credentials.get("user_id");
    }

    @PostMapping(value = "/createworkflow", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> createWorkflow(@RequestBody Workflow pWf) throws Exception {
        final String url = baseUrl + "/api/users/" + userId + "/workflows";
        var gson = new Gson();
        final String jpayload = gson.toJson(pWf);
        return leSrv.post(url, token, jpayload); 
    }

    @RequestMapping(value = "/adddocument", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<String> addDocument(@RequestParam(value = "wfid") String pWfid, @RequestPart MultipartFile document) throws IOException {
        //Replace with a signature profile that exists in your tenant or create a new signature profile by calling POST /api/tenants/:tenantId/signatureProfiles
        final String spId = "sip_Cqf9134HF2JQTyxmPMi6ZLCX";
        final String url = baseUrl + "/api/workflows/" + pWfid + "/parts?createDocuments=true&signatureProfileId=" + spId;
        return leSrv.postMultipart(url, token, document);
        // return ResponseEntity.ok("{status: success}");
    }

    @GetMapping("/createviewer")
    public ResponseEntity<String> createViewer(@RequestParam(value = "docid") String pDocId) throws Exception {
        final long expires = System.currentTimeMillis() + 300L;
        final String url = baseUrl + "/api/documents/" + pDocId + "/viewer";
        final String payload = "{\"expired\":\"" + expires + "\"}";
        //final String payload = "{\"redirectUrl\": \"https://www.google.com\"}";
        return leSrv.post(url, token, payload);
    }

    @GetMapping("/startworkflow")
    public ResponseEntity<String> startWorkflow(@RequestParam(value = "wfid") String pWfid) throws Exception {
        final String url = baseUrl + "/api/workflows/" + pWfid;
        final String payload = "{\"workflowStatus\":\"started\"}";
        return leSrv.patch(url, token, payload);
    }

    @GetMapping("/createsignaturelink")
    public ResponseEntity<String> createSignatureLink(@RequestParam(value = "wfid") String pWfid) throws Exception {
        final String url = baseUrl + "/api/workflows/" + pWfid + "/invite";
        final String payload = "{\"recipientEmail\":\"dschoenfel@gmail.com\"}";
        return leSrv.post(url, token, payload);
    }

//Use this method if you whish t oskip the sign books consolidation page

    // @GetMapping("/createsignaturelink")
    // public ResponseEntity<String> createSignatureLink(@RequestParam(value = "wfid") String pWfid) throws Exception {
    //     String url = baseUrl + "/api/workflows/" + pWfid + "/invite";
    //     String payload = "{\"recipientEmail\":\"dschoenfel@gmail.com\"}";
    //     String resp = leSrv.post(url, token, payload).getBody();
    //     Gson gson = new Gson();
    //     String inviteUrl =  gson.fromJson(resp, Invite.class).inviteUrl;
    //     String token = inviteUrl.substring(inviteUrl.indexOf("=") + 1);
    //     url = baseUrl + "/api/requests";
    //     payload = "{\"workflows\": [\"" + pWfid +"\"]}";
    //     return leSrv.post(url, token, payload);
    // }


    @GetMapping("/checkwfstatus")
    public ResponseEntity<String> checkWorkflowStatus(@RequestParam(value = "wfid") String pWfid) throws Exception {
        final String url = baseUrl + "/api/workflows/" + pWfid;
        return leSrv.get(url, token, String.class);
    }

    @GetMapping("/downloaddoc")
    public ResponseEntity<byte[]> downloadDocuments(@RequestParam(value = "wfid") String pWfid) throws Exception {
        final String url = baseUrl + "/api/workflows/" + pWfid + "/downloadDocuments";
        return leSrv.get(url, token, byte[].class);
    }
}

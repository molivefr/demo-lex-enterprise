package com.example.java.models;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Workflow {
    public long created;
    public ArrayList<Object> currentRecipientEmails;
    public ArrayList<Object> currentRecipientUsers;
    public String email;
    public String firstName;
    public String groupId;
    public String id;
    public String lastName;
    public ArrayList<Object> logs;
    public String name;
    public ArrayList<String> notifiedEvents;
    public String pictureResourceId;
    public int progress;
    public ArrayList<Step> steps;
    public String tenantId;
    public long updated;
    public String userId;
    public ArrayList<String> viewAuthorizedGroups;
    public ArrayList<Object> viewAuthorizedUsers;
    public ArrayList<Object> watchers;
    public String workflowStatus; 
}

package com.example.java.models;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Step {
    public boolean allowComments;
    public boolean hideAttachments;
    public boolean hideWorkflowRecipients;
    public String id;
    public int invitePeriod;
    public boolean isFinished;
    public boolean isStarted;
    public ArrayList<Object> logs;
    public int maxInvites;
    public ArrayList<Recipient> recipients;
    public int requiredRecipients;
    public boolean sendDownloadLink;
    public String stepType;
    public int validityPeriod;  
}

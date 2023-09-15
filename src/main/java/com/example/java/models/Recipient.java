package com.example.java.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Recipient{
    public String consentPageId;
    public String country;
    public String email;
    public String firstName;
    public String lastName;
    public String phoneNumber;
    public String pictureResourceId;
    public String preferredLocale;
    public String userId;
    public String organizationId;
}

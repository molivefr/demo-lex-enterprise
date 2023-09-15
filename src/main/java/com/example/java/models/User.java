package com.example.java.models;

import java.util.ArrayList;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class User{
    public boolean approveAllowed;
    public String country;
    public long created;
    public String email;
    public String firstName;
    public String groupId;
    public String id;
    public boolean isDisabled;
    public long lastLogin;
    public String lastName;
    public String name;
    public ArrayList<OrganizationTitle> organizationTitles;
    public String phoneNumber;
    public String pictureResourceId;
    public boolean signAllowed;
    public String subject;
    public String tenantId;
    public long updated;
    public ArrayList<String> viewAuthorizedGroups;
}

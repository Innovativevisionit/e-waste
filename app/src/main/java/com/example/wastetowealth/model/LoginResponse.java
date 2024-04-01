package com.example.wastetowealth.model;

import java.util.List;

public class LoginResponse {
    private String userName;
    private String email;
    private List<String> roles;
    private String accessToken;

    public LoginResponse() {
    }

    public LoginResponse(String userName, String email, List<String> roles, String accessToken) {
        this.userName = userName;
        this.email = email;
        this.roles = roles;
        this.accessToken = accessToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

package com.example.wastetowealth.model;

import java.util.List;

public class LoginModel {
        private String userName;
        private String password;
        private String email;
        private List<String> roles;
        private String accessToken;

        public LoginModel() {
        }

    public LoginModel(String userName, String password, String email, List<String> roles, String accessToken) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.roles = roles;
        this.accessToken = accessToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

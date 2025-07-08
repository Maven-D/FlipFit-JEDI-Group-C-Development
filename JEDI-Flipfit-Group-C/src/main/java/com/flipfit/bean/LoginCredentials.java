package com.flipfit.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginCredentials {
    private String email;
    private String password;

    // Getters and setters
    @JsonProperty
    public String getEmail() { return email; }
    @JsonProperty
    public void setEmail(String email) { this.email = email; }

    @JsonProperty
    public String getPassword() { return password; }
    @JsonProperty
    public void setPassword(String password) { this.password = password; }
}
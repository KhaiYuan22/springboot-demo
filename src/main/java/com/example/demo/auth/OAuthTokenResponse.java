package com.example.demo.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)//ignore the JSON extra field
//DTO Class
public class OAuthTokenResponse {
    private String access_token;
    private String token_type;
    private int expires_in;
    private String scope;

    // Getters and Setters
    public String getAccess_token() { return access_token; }
    public void setAccess_token(String access_token) { this.access_token = access_token; }

    public String getToken_type() { return token_type; }
    public void setToken_type(String token_type) { this.token_type = token_type; }

    public int getExpires_in() { return expires_in; }
    public void setExpires_in(int expires_in) { this.expires_in = expires_in; }

    public String getScope() { return scope; }
    public void setScope(String scope) { this.scope = scope; }
}

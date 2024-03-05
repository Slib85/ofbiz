package com.bigname.core.restful.client.security;

import com.bigname.core.restful.client.request.JWTEndpoint;
import com.bigname.core.restful.client.util.Preconditions;
import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Map;

public class JWTCredential {
    private String clientId;
    private String userName;
    private String password;
    private String connection = "default";
    private String scope = "openid email app_metadata";

    public JWTCredential(String clientId, String userName, String password) {
        Preconditions.checkNotNull(clientId, "The clientId cannot be null");
        Preconditions.checkNotNull(userName, "The userName cannot be null");
        Preconditions.checkNotNull(password, "The password cannot be null");
        this.clientId = clientId;
        this.userName = userName;
        this.password = password;
    }

    public String getClientId() {
        return clientId;
    }

    public String getUserName() {
        return userName;
    }

    private String getPassword() {
        return password;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String toJson(JWTEndpoint endpoint) {
        Map<String, String> jsonMap = new LinkedHashMap<>();
        jsonMap.put("client_id", getClientId());
        jsonMap.put("username", getUserName());
        jsonMap.put("password", getPassword());
        switch(endpoint) {
            case JWT:
                jsonMap.put("connection", getConnection());
                jsonMap.put("scope", getScope());
                break;
            case JWT_PASSWORD_REALM:
                jsonMap.put("grant_type", "http://auth0.com/oauth/grant-type/password-realm");
                jsonMap.put("audience", "https://api.cimpress.io/");
                jsonMap.put("realm", "default");
                break;
        }
        return new Gson().toJson(jsonMap);
    }


}

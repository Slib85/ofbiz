package com.bigname.core.restful.client.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.ofbiz.base.util.UtilValidate;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWT {
    private String idToken;
    private String accessToken;
    private String tokenType;
    @JsonIgnore
    private long tokenExpirationTimeInMilliSeconds = 0;

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
        setExpirationTime();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        setExpirationTime();
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JWT jwt = (JWT) o;

        if (!idToken.equals(jwt.idToken)) return false;
        if (!accessToken.equals(jwt.accessToken)) return false;
        return tokenType.equals(jwt.tokenType);
    }

    @Override
    public int hashCode() {
        int result = idToken.hashCode();
        result = 31 * result + accessToken.hashCode();
        result = 31 * result + tokenType.hashCode();
        return result;
    }

    public  boolean isValidToken() {
        return tokenExpirationTimeInMilliSeconds > 0 && tokenExpirationTimeInMilliSeconds > new Date().getTime();
    }

    private void setExpirationTime() {
        String[] tokenSplit = UtilValidate.isNotEmpty(idToken) ? idToken.split("\\.") : UtilValidate.isNotEmpty(accessToken) ? accessToken.split("\\.") : new String[0];
        if (tokenSplit.length == 3) {
            Map<String, Object> jwtPayLoadMap = parseIdToken(tokenSplit[1]);
            if (jwtPayLoadMap != null && jwtPayLoadMap.containsKey("exp") && jwtPayLoadMap.get("exp") != null) {
                tokenExpirationTimeInMilliSeconds = new Long(jwtPayLoadMap.get("exp").toString()) * 1000;
            }
        }
    }

    private static Map<String, Object> parseIdToken(String tokenPart) {
        try {
            Base64.Decoder decoder =  Base64.getDecoder();
            String tokenDecodedPayLoadString = new String(decoder.decode(tokenPart));
            Map<String,Object> result = new ObjectMapper().readValue(tokenDecodedPayLoadString, HashMap.class);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

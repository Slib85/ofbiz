package com.bigname.core.restful.client.security;

import com.bigname.core.restful.client.domain.JWT;
import com.bigname.core.restful.client.util.Preconditions;
import org.apache.ofbiz.base.util.UtilValidate;

/**
 * Created by Manu on 2/7/2017.
 */
public class Credential {
    private String apiKey;

    private JWT jwt;

    public Credential(String apiKey) {
        Preconditions.checkNotNull(apiKey, "The apiKey cannot be null");
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        if(jwt != null && UtilValidate.isEmpty(apiKey)){
            if(jwt.isValidToken()){
                apiKey = "Bearer " + (UtilValidate.isNotEmpty(jwt.getIdToken()) ? jwt.getIdToken() : jwt.getAccessToken());
            } else {
                return null; //If invalid api key, then runs without auth and from the exception we refresh the auth
            }
        }
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }


    public JWT getJwt() {
        return jwt;
    }

    public void setJwt(JWT jwt) {
        this.jwt = jwt;
    }
}

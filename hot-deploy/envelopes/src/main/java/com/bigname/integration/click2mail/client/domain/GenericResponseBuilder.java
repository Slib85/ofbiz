package com.bigname.integration.click2mail.client.domain;

import com.bigname.core.restful.client.exception.RestfulException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.http.entity.ContentType;
import org.apache.tika.mime.MediaType;
import org.json.JSONObject;
import org.json.XML;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GenericResponseBuilder {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ObjectMapper XML_MAPPER = new XmlMapper();
    static {
        MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        XML_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        XML_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        XML_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
    public static <T> T getBuildResponse(Class<T> returnType, String mimeType, String result) {
        try {
            if(returnType.equals(Boolean.class)) {
                return MAPPER.readValue("true", returnType); //Verify if this can be merged with the below logic
            }
            if (mimeType.equals(ContentType.APPLICATION_XML.getMimeType())) {
                if (returnType.equals(JSONObject.class)) {
                    return (T) XML.toJSONObject(result);
                }
                return XML_MAPPER.readValue(result, returnType);
            } else {
                return MAPPER.readValue(result, returnType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toJsonString(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RestfulException("An error occurred while building the create order request body JSON");
        }
    }
}

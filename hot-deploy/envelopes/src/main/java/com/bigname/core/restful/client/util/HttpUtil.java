package com.bigname.core.restful.client.util;

import com.bigname.core.restful.client.exception.RestfulException;
import com.bigname.core.restful.client.request.AbstractApiRequest;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.poi.ss.formula.functions.T;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpUtil {

    public static List<NameValuePair> convertToNameValuePairs(Map<String, String> params) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        for (String key : params.keySet()) {
            String value = params.get(key);
            nameValuePairs.add(new BasicNameValuePair(key, value));
        }
        return nameValuePairs;
    }

    public static HttpEntity buildXmlEntity(Object obj) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            String xml = xmlMapper.writeValueAsString(obj).toString();
            StringEntity entity = new StringEntity(xml);
            entity.setContentType("application/xml");
            return entity;
        } catch (Exception e) {
            throw new RestfulException("An error occurred while building the xml entity");
        }
    }

    public static StringBuilder replaceTemplateTokens(Map<String, String> templateTokens, String url) {
        Pattern p = Pattern.compile("\\{\\w+\\}");
        Matcher m = p.matcher(url);
        while(m.find()) {
            String token = m.group(0);
            String key = token.replaceAll("\\{|\\}", "");

            url = url.replace(token, templateTokens.getOrDefault(key, ""));
        }
        return new StringBuilder(url.trim());
    }
}

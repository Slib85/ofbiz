package com.bigname.core.restful.client;

import com.bigname.core.restful.client.domain.JWT;
import com.bigname.core.restful.client.exception.RestfulException;
import com.bigname.core.restful.client.request.*;
import com.bigname.core.restful.client.security.Credential;
import com.bigname.core.restful.client.security.JWTCredential;
import com.bigname.core.restful.client.util.HttpUtil;
import com.bigname.integration.click2mail.client.domain.GenericResponseBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.poi.ss.formula.functions.T;
import org.apache.woden.internal.wsdl20.extensions.http.HTTPHeaderImpl;
import org.apache.woden.wsdl20.extensions.http.HTTPHeader;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @deprecated
 */
public class AbstractXmlApiClient {
    // Default connect timeout in 30 seconds
    private static final int DEFAULT_CONNECT_TIMEOUT_MILLISECONDS = 30000;
    // Default connect timeout in 60 seconds
    private static final int DEFAULT_READ_TIMEOUT_MILLISECONDS = 60000;
    // Retry count for JWT Token
    private static final int MAX_RETRIES = 1;

    private static final String MULTIPART_FILENAME_PARAM_NAME = "file";

    //private static final ObjectMapper MAPPER = new ObjectMapper();
    //private static final ObjectMapper XML_MAPPER = new XmlMapper();

    /*static {
        MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }*/

    private String endpoint;
    private String jwtEndpoint;
    private Credential credential;
    private String contentType = "application/json";
    private JWTCredential jwtCredential;

    protected AbstractXmlApiClient(String endpoint, Credential credential) {
        this.endpoint = endpoint;
        this.credential = credential;
    }

    protected AbstractXmlApiClient(String endpoint, Credential credential, String jwtEndPoint, JWTCredential jwtCredential) {
        this(endpoint, credential);
        this.jwtEndpoint = jwtEndPoint;
        this.jwtCredential = jwtCredential;
        getAuthToken();
    }
    protected AbstractXmlApiClient(String endpoint, Credential credential, String contentType) {
        this.endpoint = endpoint;
        this.credential = credential;
        this.contentType = contentType;
    }
    public String getContentType() {
        return this.contentType;
    }

    private void getAuthToken() {
        if (credential.getJwt() == null) {
            credential.setJwt(post(new AbstractApiRequest() {
                @Override
                public ApiEndpoint getEndpoint() {
                    return JWTEndpoint.JWT;
                }

                @Override
                public String getCustomEndpoint() {
                    return jwtEndpoint;
                }
            }, jwtCredential.toJson(JWTEndpoint.JWT), JWT.class));
        }
    }

    protected <T> T get(ApiRequest apiRequest, Class<T> returnType, int... _retryCount) {
        int retryCount = 0;
        if(_retryCount != null && _retryCount.length == 1) {
            retryCount = _retryCount[0];
        }

        if (retryCount > MAX_RETRIES) {
            return null;
        }

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet request = getRequest(apiRequest, HttpGet.class);
            HttpResponse response = httpclient.execute(request);
            String responseType = getMimeType(response);
            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode == HttpStatus.SC_OK || responseCode == HttpStatus.SC_CREATED) {
                StringBuilder result = new StringBuilder();
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                return GenericResponseBuilder.getBuildResponse(returnType, responseType,result.toString());
            } else {
                if(jwtCredential != null && !credential.getJwt().isValidToken()) {
                    getAuthToken();
                    return get(apiRequest, returnType, ++retryCount);
                }
            }
            return null;
        } catch (IOException e) {
            throw new RestfulException(e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ignore) {
            }
        }
    }

    protected HttpResponse post(ApiRequest apiRequest, HttpEntity entity, int... _retryCount) {
        int retryCount = 0;
        if(_retryCount != null && _retryCount.length == 1) {
            retryCount = _retryCount[0];
        }

        if (retryCount > MAX_RETRIES) {
            return null;
        }

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost request = getRequest(apiRequest, HttpPost.class);
            request.setEntity(entity);
            HttpResponse response = httpclient.execute(request);

            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode == HttpStatus.SC_OK) {
                return response;
            } else {
                if (jwtCredential != null && !credential.getJwt().isValidToken()) {
                    getAuthToken();
                    return post(apiRequest, entity, ++retryCount);
                }
            }
            return null;
        } catch (IOException e) {
            throw new RestfulException(e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ignore) {
            }
        }
    }

    protected <T> T post(ApiRequest apiRequest, HttpEntity entity, Class<T> returnType, int... _retryCount) {
        int retryCount = 0;
        if (_retryCount != null && _retryCount.length == 1) {
            retryCount = _retryCount[0];
        }

        if (retryCount > MAX_RETRIES) {
            return null;
        }

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost request = getRequest(apiRequest, HttpPost.class);
            request.setEntity(entity);
            HttpResponse response = httpclient.execute(request);
            int responseCode = response.getStatusLine().getStatusCode();
            String responseType = getMimeType(response);


            if (responseCode == HttpStatus.SC_OK || responseCode == HttpStatus.SC_CREATED) {
                StringBuilder result = new StringBuilder();
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                return GenericResponseBuilder.getBuildResponse(returnType, responseType, result.toString());
            } else {
                StringBuilder result = new StringBuilder();
                if(UtilValidate.isNotEmpty(response.getEntity().getContent())) {
                    BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                    String line = "";
                    while ((line = rd.readLine()) != null) {
                        result.append(line);
                    }
                    System.err.println("post response = " + result.toString());
                    if(result.length() > 1) {
                        return GenericResponseBuilder.getBuildResponse(returnType, responseType, result.toString());
                    }

                }


                if (jwtCredential != null && !credential.getJwt().isValidToken()) {
                    getAuthToken();
                    return post(apiRequest, entity, returnType, ++retryCount);
                }
            }
            return null;
        } catch (IOException e) {
            throw new RestfulException(e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ignore) {
            }
        }
    }

    protected <T> T post(ApiRequest apiRequest, Class<T> returnType) {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost request = getRequest(apiRequest, HttpPost.class);
            HttpResponse response = httpclient.execute(request);
            int responseCode = response.getStatusLine().getStatusCode();
            String responseType = getMimeType(response);
            if (responseCode == HttpStatus.SC_OK || responseCode == HttpStatus.SC_CREATED) {
                StringBuilder result = new StringBuilder();
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                return GenericResponseBuilder.getBuildResponse(returnType, responseType, result.toString());
            }else {
                StringBuilder result = new StringBuilder();
                    if(UtilValidate.isNotEmpty(response.getEntity().getContent())) {
                        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                        String line = "";
                        while ((line = rd.readLine()) != null) {
                            result.append(line);
                        }
                        System.err.println("post response = " + result.toString());
                        if(result.length() > 1) {
                            return GenericResponseBuilder.getBuildResponse(returnType, responseType, result.toString());
                        }

                }
            }
            return null;
        } catch (IOException e) {
            throw new RestfulException(e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ignore) {
            }
        }
    }

    public <T> T post(ApiRequest apiRequest, String entity, Class<T> returnType, int... _retryCount) {
        int retryCount = 0;
        if (_retryCount != null && _retryCount.length == 1) {
            retryCount = _retryCount[0];
        }

        if (retryCount > MAX_RETRIES) {
            return null;
        }

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost request = getRequest(apiRequest, HttpPost.class);
            request.addHeader("content-type", ContentType.APPLICATION_JSON.getMimeType());

            request.setEntity(new StringEntity(entity));
            HttpResponse response = httpclient.execute(request);
            int responseCode = response.getStatusLine().getStatusCode();
            String responseType = getMimeType(response);
            if (responseCode == HttpStatus.SC_OK) {
                StringBuilder result = new StringBuilder();
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                return GenericResponseBuilder.getBuildResponse(returnType, responseType, result.toString());

            } else {
                StringBuilder result = new StringBuilder();
                if(UtilValidate.isNotEmpty(response.getEntity().getContent())) {
                    BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                    String line = "";
                    while ((line = rd.readLine()) != null) {
                        result.append(line);
                    }
                    System.err.println("post response = " + result.toString());
                    if(result.length() > 1) {
                        return GenericResponseBuilder.getBuildResponse(returnType, responseType, result.toString());
                    }

                }
                if (jwtCredential != null && !credential.getJwt().isValidToken()) {
                    getAuthToken();
                    return post(apiRequest, entity, returnType, ++retryCount);
                }
            }
            return null;
        } catch (IOException e) {
            throw new RestfulException(e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ignore) {
            }
        }
    }

    public <T> T delete(ApiRequest apiRequest, Class<T> returnType) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {

            HttpDelete request = getRequest(apiRequest, HttpDelete.class);

            HttpResponse response = httpclient.execute(request);
            int responseCode = response.getStatusLine().getStatusCode();
            String responseType = getMimeType(response);

            //if (responseCode == HttpStatus.SC_OK || responseCode == HttpStatus.SC_CREATED) {
            StringBuilder result = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            return GenericResponseBuilder.getBuildResponse(returnType, responseType, result.toString());
            //}
            //return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                httpclient.close();
            } catch (IOException ignore) {
            }
        }
        return null;
    }

    private <T> T getRequest(ApiRequest apiRequest, Class<T> requestType) {
        String authToken = credential.getApiKey();
        if (authToken == null) {
            this.getAuthToken();
            authToken = credential.getApiKey();
        }
        if (requestType.isAssignableFrom(HttpPost.class)) {
            HttpPost request = new HttpPost(getEndpoint(apiRequest));
            request.addHeader("accept", getContentType());
            request.addHeader("Authorization", authToken);
            return (T) request;
        } else if (requestType.isAssignableFrom(HttpGet.class)) {
            HttpGet request = new HttpGet(getEndpoint(apiRequest));
            request.addHeader("accept", getContentType());
            request.addHeader("Authorization", authToken);
            return (T) request;
        } else {
            HttpDelete request = new HttpDelete(getEndpoint(apiRequest));
            request.addHeader("accept", getContentType());
            request.addHeader("Authorization", authToken);
            return (T) request;
        }

    }

    private URI getEndpoint(ApiRequest apiRequest) {

        try {
            StringBuilder path = new StringBuilder(UtilValidate.isNotEmpty(apiRequest.getCustomEndpoint()) ? apiRequest.getCustomEndpoint() : endpoint);
            for (String str : apiRequest.getEndpoint().getPaths()) {
                path.append("/").append(str);
            }
            path = HttpUtil.replaceTemplateTokens(apiRequest.getRequestTemplates(), path.toString());
            URIBuilder uriBuilder = new URIBuilder(path.toString());
            for (Map.Entry<String, String> param : apiRequest.getQueryParams().entrySet()) {
                uriBuilder.setParameter(param.getKey(), param.getValue());
            }
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new RestfulException("Error occurred while generating the request endpoint due to: ", e);
        }
    }

    protected HttpEntity buildMultipart(AbstractApiRequestWithFile apiRequest) {
        File file = apiRequest.getFile();
        Map<String, String> formFields = apiRequest.getFormParams();
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody(MULTIPART_FILENAME_PARAM_NAME, file, ContentType.APPLICATION_OCTET_STREAM, file.getName());
        //builder.addBinaryBody(MULTIPART_FILENAME_PARAM_NAME, file);
        //builder.seContentType(ContentType.create(getContentType()));
        for (Map.Entry<String, String> formField : formFields.entrySet()) {
//            FormBodyPartBuilder formBuilder = FormBodyPartBuilder.create(formField.getKey(), new StringBody(formField.getKey(), ContentType.TEXT_PLAIN));
//            formBuilder.addField(formField.getKey(), formField.getValue());
//            builder.addPart(formBuilder.build());
            builder.addTextBody(formField.getKey(), formField.getValue());
        }

        return builder.build();
    }

    protected HttpEntity buildUrlEncodedFormEntity(AbstractApiRequest apiRequest) {
        try {
            return new UrlEncodedFormEntity(HttpUtil.convertToNameValuePairs(apiRequest.getFormParams()));
        } catch (UnsupportedEncodingException e) {
            throw new RestfulException("An error occurred while building the url encoded form entity");
        }
    }

    protected HttpEntity buildXmlEntity(Object obj) {
            return HttpUtil.buildXmlEntity(obj);
    }

    protected String toJsonString(Object obj) {
        return GenericResponseBuilder.toJsonString(obj);
    }

    private static String getMimeType(HttpResponse response) {
        ContentType contentType = null;
        String mimeType = null;
        if (response.getEntity() != null) {
            contentType = ContentType.get(response.getEntity());
            mimeType = contentType.getMimeType();
        }
        return mimeType;
    }
}

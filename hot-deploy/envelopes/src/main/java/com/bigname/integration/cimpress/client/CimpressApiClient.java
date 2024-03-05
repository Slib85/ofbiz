package com.bigname.integration.cimpress.client;

import com.bigname.core.restful.client.AbstractApiClient;
import com.bigname.core.restful.client.exception.RestfulException;
import com.bigname.core.restful.client.request.ApiRequest;
import com.bigname.core.restful.client.security.Credential;
import com.bigname.core.restful.client.security.JWTCredential;
import com.bigname.integration.cimpress.client.request.*;
import com.envelopes.http.FileHelper;
import com.envelopes.util.EnvConstantsUtil;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import static com.bigname.integration.cimpress.ConfigKey.*;
public class CimpressApiClient extends AbstractApiClient implements CimpressApi {

    public CimpressApiClient(Map<String, String> config) {
        super(config.get(FULFILLMENT_MANAGER_API_ENDPOINT.key()), new Credential(""), config.get(AUTH_ENDPOINT.key()), new JWTCredential(config.get(CLIENT_ID.key()), config.get(USER_NAME.key()), config.get(PASSWORD.key())));
    }

    @Override @SuppressWarnings("unchecked")
    public Map<String, Object> getItems(){
        return get(new GetItemsRequest(), Map.class);
    }

    @Override @SuppressWarnings("unchecked")
    public Map<String, Object> getNotifications(GetNotificationsRequest request) {
        return get(request, Map.class);
    }

    @Override @SuppressWarnings("unchecked")
    public Map<String, Object> getOrderDetails(GetOrderDetailsRequest request) {
        return get(request, Map.class);
    }

    @Override @SuppressWarnings("unchecked")
    public Map<String, Object> getItemDetails(GetItemDetailsRequest request) {
        return get(request, Map.class);
    }

    @Override @SuppressWarnings("unchecked")
    public Boolean acceptNotification(String link) {
        return post(link, Boolean.class);
    }

    @Override @SuppressWarnings("unchecked")
    public Map<String, Object> getItemStatus(GetItemStatusRequest request) {
        return get(request, Map.class);
    }

    @Override @SuppressWarnings("unchecked")
    public Map<String, Object> getItemPlan(GetItemPlanRequest request) {
        return get(request, Map.class);
    }

    @Override @SuppressWarnings("unchecked")
    public Map<String, String> getArtWork(GetArtworkRequest request) {
        return getMultipart(request, request.getItemId(), Map.class);
    }

    @Override @SuppressWarnings("unchecked")
    public Map<String, String> getPreview(GetPreviewRequest request) {
        return getMultipart(request, request.getItemId(), Map.class);
    }

    @Override @SuppressWarnings("unchecked")
    public Map<String, Object> getOrder(GetOrderRequest request) {
        return get(request, Map.class);
    }

    @Override @SuppressWarnings("unchecked")
    public Map<String, Object> getOrderItems(GetOrderItemsRequest request) {
        return get(request, Map.class);
    }

    @Override @SuppressWarnings("unchecked")
    public boolean updateItemStatus(UpdateItemStatusRequest request) {
        return post(request, toJsonString(request.getParams()), Boolean.class);
    }

    @Override @SuppressWarnings("unchecked")
    public boolean createShipment(CreateShipmentRequest request) {
        return post(request, toJsonString(request.getParams()), Boolean.class);
    }

    @SuppressWarnings("unchecked")
    private <T> T getMultipart(ApiRequest apiRequest, String itemId, Class<T> returnType, int... _retryCount) {
        int retryCount = 0;
        if(_retryCount != null && _retryCount.length == 1) {
            retryCount = _retryCount[0];
        }

        if(retryCount > MAX_RETRIES) {
            return null;
        }

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet request = getRequest(apiRequest, HttpGet.class);
            HttpResponse response = httpclient.execute(request);
            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode == HttpStatus.SC_OK || responseCode == HttpStatus.SC_CREATED) {
                String contentType = response.getFirstHeader("Content-Type").getValue();
                String path = "/tmp";
                String fileName = "";
                if (contentType.equals("application/pdf")) {
                    fileName = "artwork_" + itemId +".pdf";
                } else {
                    fileName = "preview_" + itemId +".png";
                }
                path = path + "/" + fileName;
                File tempFile = new File(path);
                BufferedInputStream bis = new BufferedInputStream(response.getEntity().getContent());
                File contentFile = FileHelper.makeFile(tempFile, FileHelper.getUploadPath(null), null);
                Map<String, String> content;
                try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(contentFile))) {
                    int inByte;
                    while((inByte = bis.read()) != -1) bos.write(inByte);
                    bis.close();
                }

                content = new HashMap<>();
                content.put("fileName", fileName);
                content.put("filePath", contentFile.getAbsolutePath().replaceAll("\\\\", "/").replace(EnvConstantsUtil.OFBIZ_HOME, "/"));
                content.put("contentType", contentType);

                return (T) content;
            } else {
                if(jwtCredential != null && !credential.getJwt().isValidToken()) {
                    getAuthToken();
                    return getMultipart(apiRequest, itemId, returnType, ++retryCount);
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


}

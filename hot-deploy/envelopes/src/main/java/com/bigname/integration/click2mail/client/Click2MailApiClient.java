package com.bigname.integration.click2mail.client;

import com.bigname.core.restful.client.AbstractApiClient;
import com.bigname.core.restful.client.exception.RestfulException;
import com.bigname.core.restful.client.request.ApiRequest;
import com.bigname.core.restful.client.security.Credential;
import com.bigname.integration.click2mail.client.domain.*;
import com.bigname.integration.click2mail.client.request.*;
import com.bigname.integration.click2mail.client.domain.Click2MailCreatedAddressList;
import com.bigname.integration.click2mail.client.domain.Click2MailDocument;
import com.bigname.integration.click2mail.client.domain.JobModel;
import com.bigname.integration.click2mail.client.domain.AddressResponse;
import com.bigname.integration.directmailing.ConfigKey;
import com.envelopes.http.FileHelper;
import com.envelopes.util.EnvConstantsUtil;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Click2MailApiClient extends AbstractApiClient implements Click2MailApi {

    public Click2MailApiClient(Map<String, String> config) {
        super(config.get(ConfigKey.C2M_API_ENDPOINT.key()), new Credential(getFrontApiKey(config.get(ConfigKey.C2M_USER_NAME.key()), config.get(ConfigKey.C2M_PASSWORD.key()))));
    }

    public Click2MailApiClient(Map<String, String> config, String contentType) {

        super(config.get(ConfigKey.C2M_API_ENDPOINT.key()), new Credential(getFrontApiKey(config.get(ConfigKey.C2M_USER_NAME.key()), config.get(ConfigKey.C2M_PASSWORD.key()))), contentType);
    }

    public static String getFrontApiKey(String userName, String password) {
        try {
            return "Basic " + Base64.getEncoder().encodeToString((userName + ":" + password).getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public JSONObject getProductOptions(GetProductOptionsRequest request) {
        return get(request, JSONObject.class);
    }

    @Override
    public CostEstimate getCostEstimate(CostEstimateRequest request) {
        return get(request, CostEstimate.class);
    }

    @Override
    public JobModel createJob(CreateJobRequest request) {
        return post(request, buildUrlEncodedFormEntity(request), JobModel.class);
    }

    @Override
    public JobModel updateJob(UpdateJobRequest request) {
        return post(request, buildUrlEncodedFormEntity(request), JobModel.class);
    }

    @Override
    public JobModel createJobProof(CreateJobProofRequest request) {
        return post(request, JobModel.class);
    }

    @Override
    public void getProof(ApiRequest apiRequest, HttpServletResponse httpServletResponse) {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet request = getRequest(apiRequest, HttpGet.class);
            HttpResponse response = httpclient.execute(request);
            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode == HttpStatus.SC_OK || responseCode == HttpStatus.SC_CREATED) {
                String contentType = response.getFirstHeader("Content-Type").getValue();
                String path = "/tmp";
                String fileName = "proof.pdf";
                path = path + "/" + fileName;
                File tempFile = new File(path);
                BufferedInputStream bis = new BufferedInputStream(response.getEntity().getContent());
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempFile));
                int inByte;
                while ((inByte = bis.read()) != -1) bos.write(inByte);
                bos.close();
                bis.close();
                httpServletResponse.setContentType(contentType);
                httpServletResponse.addHeader("Content-Disposition", "attachment; filename=proof.pdf");
                FileInputStream fileInputStream = new FileInputStream("C:\\tmp\\"+fileName);
                IOUtils.copy(fileInputStream, httpServletResponse.getOutputStream());
                fileInputStream.close();
            }
        } catch (IOException e) {
            throw new RestfulException(e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ignore) {
            }
        }
    }

    @Override
    public Click2MailCreatedAddressList createAddressList(CreateAddressListRequest request) {
        return post(request, buildXmlEntity(request.getAddressList()), Click2MailCreatedAddressList.class);
    }

    @Override
    public Click2MailDocument uploadDocument(UploadDocumentRequest request) {
        return post(request, buildMultipart(request), Click2MailDocument.class);
    }

    @Override
    public AddressResponse deleteAddress(DeleteAddressListRequest request) {
        return delete(request, AddressResponse.class);
    }

    @Override
    public JobCostModel getJobCost(JobCostRequest request) {
        return get(request, JobCostModel.class);
    }

    @Override
    public JobCostModel submitJob(JobSubmitRequest request) {
        return post(request, buildUrlEncodedFormEntity(request), JobCostModel.class);
    }

    @Override
    public JobModel cancelSubmittedJob(JobCancelRequest request) {
        return post(request, JobModel.class);
    }

}

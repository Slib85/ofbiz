package com.bigname.integration.click2mail.client;

import com.bigname.core.restful.client.request.ApiRequest;
import com.bigname.integration.click2mail.client.domain.*;
import com.bigname.integration.click2mail.client.request.*;
import com.bigname.integration.click2mail.client.domain.Click2MailCreatedAddressList;
import com.bigname.integration.click2mail.client.domain.Click2MailDocument;
import com.bigname.integration.click2mail.client.domain.JobModel;
import com.bigname.integration.click2mail.client.request.*;
import com.bigname.integration.click2mail.client.domain.AddressResponse;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;

public interface Click2MailApi {

    JSONObject getProductOptions(GetProductOptionsRequest request);
    CostEstimate getCostEstimate(CostEstimateRequest request);
    JobModel createJob(CreateJobRequest request);
    JobModel updateJob(UpdateJobRequest request);
    JobModel createJobProof(CreateJobProofRequest request);
    void getProof(ApiRequest request, HttpServletResponse response);
    Click2MailCreatedAddressList createAddressList(CreateAddressListRequest request);
    Click2MailDocument uploadDocument(UploadDocumentRequest request);
    AddressResponse deleteAddress(DeleteAddressListRequest request);
    JobCostModel getJobCost(JobCostRequest request);
    JobCostModel submitJob(JobSubmitRequest request);
    JobModel cancelSubmittedJob(JobCancelRequest request);
}

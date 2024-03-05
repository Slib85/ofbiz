package com.bigname.integration.click2mail;

import com.bigname.integration.click2mail.client.Click2MailApi;
import com.bigname.integration.click2mail.client.Click2MailApiClient;
import com.bigname.integration.click2mail.client.domain.*;
import com.bigname.integration.click2mail.client.request.*;

import com.bigname.integration.click2mail.client.domain.Click2MailCreatedAddressList;
import com.bigname.integration.click2mail.client.domain.Click2MailDocument;
import com.bigname.integration.click2mail.client.domain.JobModel;
import com.bigname.integration.click2mail.client.request.*;
import com.bigname.integration.click2mail.client.domain.AddressResponse;
import com.bigname.integration.directmailing.DirectMailingHelper;
import com.google.gson.Gson;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class Click2MailUtil {
    private static BigDecimal defaultMargin = BigDecimal.valueOf(10);

    protected static Click2MailApi getClick2MailClient() {
        return new Click2MailApiClient(DirectMailingHelper.getDirectMailingConfig(), "application/xml");
    }

    protected static Click2MailApi getClick2MailClient(String contentType) {
        return new Click2MailApiClient(DirectMailingHelper.getDirectMailingConfig(), contentType);
    }
    protected static JSONObject getProductOptions(GetProductOptionsRequest request) {
        return getClick2MailClient().getProductOptions(request);
    }

    protected static CostEstimate getCostEstimate(CostEstimateRequest request) {
        return getClick2MailClient().getCostEstimate(request);
    }

    public static JobModel createJob(CreateJobRequest request) {
        return getClick2MailClient().createJob(request);
    }

    protected static JobModel updateJob(UpdateJobRequest request) {
        return getClick2MailClient().updateJob(request);
    }

    protected static JobModel createJobProof(CreateJobProofRequest request) {
        return getClick2MailClient().createJobProof(request);
    }
    protected static AddressResponse deleteAddress(DeleteAddressListRequest request) {
        return getClick2MailClient().deleteAddress(request);
    }

    protected static Click2MailCreatedAddressList createAddressList(CreateAddressListRequest request) {
        return getClick2MailClient().createAddressList(request);
    }

    protected static Click2MailDocument uploadDocument(UploadDocumentRequest request) {
        return getClick2MailClient().uploadDocument(request);
    }

    protected static JobCostModel getJobCost(JobCostRequest request) {
        return getClick2MailClient().getJobCost(request);
    }

    protected static JobCostModel submitJob(JobSubmitRequest request) {
        return getClick2MailClient().submitJob(request);
    }

    protected static JobModel cancelSubmittedJob(JobCancelRequest request) {
        return getClick2MailClient().cancelSubmittedJob(request);
    }

    /** From click2mail we can calculate cost estimate or job cost. originalQuantity is passed from our side. Click2mail returns
     totalprice for specified quantity. For cost estimate, quantity(returning from click2mail) is sligtly different from originalQuantity .
     getEffectivePrice calculates unitPrice for the originalQuantity with respect to margin price and recalculate the total price  **/
    protected static String getEffectivePrice(BigDecimal totalPrice, BigDecimal quantity, String originalQuantity) {
        String pricingDetails = "";
        try{
        Map<String, String> pricing = new HashMap<>();
            BigDecimal unitPrice = totalPrice.divide(quantity, 2, BigDecimal.ROUND_HALF_UP);
            BigDecimal marginPrice = (unitPrice.multiply(defaultMargin)).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
            unitPrice = unitPrice.add(marginPrice);
            totalPrice = unitPrice.multiply(quantity);
            pricing.put("totalPrice", totalPrice.toString());
                pricing.put("unitPrice", unitPrice.toString());
            pricing.put("quantity", originalQuantity);
            pricingDetails = new Gson().toJson(pricing);
            System.out.println("unitPrice = "+unitPrice+", marginPrice = "+marginPrice+", total = "+totalPrice);
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return pricingDetails;
    }

    protected static int getDocumentPageCount(String documentPath) {
        XWPFDocument docx;
        int pages = 0;
        try {
            docx = new XWPFDocument(POIXMLDocument.openPackage(documentPath));
            pages = docx.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pages;
    }
}
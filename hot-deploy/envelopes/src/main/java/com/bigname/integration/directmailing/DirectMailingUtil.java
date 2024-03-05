package com.bigname.integration.directmailing;

import com.bigname.integration.click2mail.client.Click2MailApi;
import com.bigname.integration.click2mail.client.Click2MailApiClient;
import com.bigname.integration.click2mail.client.domain.*;
import com.bigname.integration.click2mail.client.request.*;
import com.google.gson.Gson;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Manu on 5/2/2018.
 */
public class DirectMailingUtil {

    public static final BigDecimal MARGIN = BigDecimal.ZERO;
    private static Click2MailApi getClick2MailClient() {
        return new Click2MailApiClient(DirectMailingHelper.getDirectMailingConfig(), "application/xml");
    }

    static CostEstimate getCostEstimate(CostEstimateRequest request) {
        return getClick2MailClient().getCostEstimate(request);
    }

    static JobModel createJob(CreateJobRequest request) {
        return getClick2MailClient().createJob(request);
    }

    static Click2MailDocument uploadDocument(UploadDocumentRequest request) {
        return getClick2MailClient().uploadDocument(request);
    }

    static Click2MailCreatedAddressList createAddressList(CreateAddressListRequest request) {
        return getClick2MailClient().createAddressList(request);
    }

    static AddressResponse deleteAddressList(DeleteAddressListRequest request) {
        return getClick2MailClient().deleteAddress(request);
    }

    static JobModel updateJob(UpdateJobRequest request) {
        return getClick2MailClient().updateJob(request);
    }

    static JobCostModel getJobCost(JobCostRequest request) {
        return getClick2MailClient().getJobCost(request);
    }

    static JobModel createJobProof(CreateJobProofRequest request) {
        return getClick2MailClient().createJobProof(request);
    }

    static void getProof(GetProofRequest request,HttpServletResponse response) {
        getClick2MailClient().getProof(request, response);
    }

    static JobCostModel submitJob(JobSubmitRequest request) {
        return getClick2MailClient().submitJob(request);
    }

    static String getEffectivePrice(BigDecimal totalPrice, BigDecimal quantity, String originalQuantity) {
        String pricingDetails;
        try{
            Map<String, String> pricing = new HashMap<>();
            BigDecimal unitPrice = totalPrice.divide(quantity, 2, BigDecimal.ROUND_HALF_UP);
            BigDecimal marginPrice = (unitPrice.multiply(MARGIN)).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
            unitPrice = unitPrice.add(marginPrice);
            totalPrice = unitPrice.multiply(quantity);
            pricing.put("totalPrice", totalPrice.toString());
            pricing.put("unitPrice", unitPrice.toString());
            pricing.put("quantity", originalQuantity);
            pricingDetails = new Gson().toJson(pricing);
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

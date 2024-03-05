package com.bigname.integration.click2mail;

import com.bigname.integration.click2mail.client.domain.*;
import com.bigname.integration.click2mail.client.request.*;
import com.bigname.integration.click2mail.Click2MailHelper;

import com.envelopes.http.FileHelper;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.entity.GenericValue;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import static com.bigname.integration.click2mail.Click2MailHelper.getPartyId;

public class Click2MailEvents {

    public static final String module = Click2MailEvents.class.getName();

    /** retrieve product options based on document class from click2mail **/
    public static String getProductOptions(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<>();
        String documentClass = "";
        String productId = (String) context.get("productId");
        if (productId.equals("C2M-L85X11")) {
            documentClass = "Letter 8.5 x 11";
        } else if (productId.equals("C2M-N425X55")) {
            documentClass = "Notecard 4.25 x 5.5";
        } else {
            documentClass = "Note Card - Folded 4.25 x 5.5";
        }
        //Map<String, Object> formParams = new HashMap<>();


        boolean success = false;
        try {
            JSONObject jsonObject = Click2MailUtil.getProductOptions(new GetProductOptionsRequest(documentClass));
            if (UtilValidate.isNotEmpty(jsonObject)) {
                ObjectMapper mapper = new ObjectMapper();
                /** converts json string in to map **/
                Map<String, Object> jsonMap = mapper.readValue(jsonObject.toString(), new TypeReference<Map<String,Object>>() {});
                Map<String, Object> productOptions = (Map<String, Object>) jsonMap.get("productOptions");
                Map<String, Object> product = (Map<String, Object>) productOptions.get("product");
                Map<String, Object> parseProductOptions = Click2MailHelper.parseProductOptions(product);
                jsonResponse.put("productOptions", parseProductOptions);
                success = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            //EnvUtil.reportError(e);
            //Debug.logError(e, "Error occurred while getting all Marketplace Sellers .", module);
        }

        jsonResponse.put("success", success);
        //return jsonResponse;
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    /** retrieve the estimated cost based on the product options from click2mail . estimated cost is considered only if
     addressList is not created. evey time user select number of pages or quantity , after uploading doument estimated cost
     is calculated**/
    public static String getCostEstimate(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        boolean updateJob = false;
        int numberOfPages = 0;

        /** After uploading a document , update job will be triggered and later esimated cost is calculated based on the
         number of pages.for this condition we have boolean updateJob is true, **/
        if (UtilValidate.isNotEmpty(request.getAttribute("updateJob"))) {
            updateJob = (boolean) request.getAttribute("updateJob");
            numberOfPages = (int) request.getAttribute("numberOfPages");
        }
        String originalQuantity = (String) context.get("quantity");
        Map<String, Object> formParams = new HashMap<>();
        formParams.put("documentClass", context.get("documentClass"));
        formParams.put("layout", context.get("layout"));
        formParams.put("productionTime", context.get("productionTime"));
        formParams.put("envelope", context.get("envelope"));
        formParams.put("color", context.get("color"));
        formParams.put("paperType", context.get("paperType"));
        formParams.put("printOption", context.get("printOption"));
        formParams.put("mailClass", context.get("mailClass"));
        formParams.put("quantity", originalQuantity);
        formParams.put("numberOfPages", numberOfPages == 0 ? context.get("numberOfPages") : String.valueOf(numberOfPages));
        String price = "";
        boolean success = false;
        try {
            CostEstimate cost = Click2MailUtil.getCostEstimate(new CostEstimateRequest(formParams));
            if (UtilValidate.isNotEmpty(cost) && cost.getStatus().equals("0")) {
                success = true;
                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> productionCost = mapper.readValue(new Gson().toJson(cost.getProductionCost()), new TypeReference<Map<String,String>>(){});
                String totalCost = productionCost.get("subtotal");
                String quantity = productionCost.get("quantity");

                /** calculate the effective cost by adding the margin price **/
                price = Click2MailUtil.getEffectivePrice(new BigDecimal(totalCost), new BigDecimal(quantity), originalQuantity);
                jsonResponse.put("price", price);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        jsonResponse.put("success", success);
        /** when update job is true **/
        if (updateJob) {
            return price;
        }
        /** when user select no of pages or quantity.. **/
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    /** Create a new job in click2mail based on the options selected  **/
    public static String createJob(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        GenericValue loggedInUser = (GenericValue) request.getSession().getAttribute("userLogin");
        String partyId = "";
        if (UtilValidate.isNotEmpty(loggedInUser)) {
            partyId = (String) loggedInUser.get("partyId");
        }
        boolean isSaveButtonClick = false;
        int ERROR_CODE = 0;
        isSaveButtonClick = Boolean.valueOf((String) context.get("isSaveButtonClick"));
        Map<String, Object> map = new HashMap<>();
        map.put("documentClass", context.get("documentClass"));
        map.put("layout", context.get("layout"));
        map.put("productionTime", context.get("productionTime"));
        map.put("envelope", context.get("envelope"));
        map.put("color", context.get("color"));
        map.put("paperType", context.get("paperType"));
        map.put("printOption", context.get("printOption"));
        map.put("mailClass", context.get("mailClass"));


        boolean success = false;
        try {
            /** if user is not logged in while save &  exit**/
            if (UtilValidate.isEmpty(partyId) && isSaveButtonClick) {
                ERROR_CODE = 501;
                jsonResponse.put("errorCode", ERROR_CODE);
            } else {
                JobModel job = Click2MailUtil.createJob(new CreateJobRequest(map));
                if (UtilValidate.isNotEmpty(job)) {
                    if (!job.getId().equals("0") && isSaveButtonClick) {
                        /** when save & exit is clicked ,saveJob is triggered for saving it to the database **/
                        request.setAttribute("jobId", job.getId());
                        Click2MailHelper.saveJob(request, partyId, delegator);
                    }
                    success = true;
                    jsonResponse.put("response", job);
                }
            }
        } catch (Exception e) {
            ERROR_CODE = 500;
            jsonResponse.put("errorCode", ERROR_CODE);
            e.printStackTrace();
            //EnvUtil.reportError(e);
            //Debug.logError(e, "Error occurred while getting all Marketplace Sellers .", module);
        }

        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    /** updates a job on click2mail based on product options, return address , document and addresslist. Update Job can be triggered on
     clicking next button, after successful upload  of a document, after successful creation of an addresslist etc. **/
    public static String updateJob(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        String documentId = "";
        Integer numberOfPages = 0;

        /** when update job is triggered after successfully uploading a document file **/
        if (UtilValidate.isNotEmpty(request.getAttribute("documentId"))) {
            numberOfPages = (int) request.getAttribute("numberOfPages");
            documentId = (String) request.getAttribute("documentId");
        }

        /** when update job is triggered after successfully creating/updating an addresList **/
        String addressId = "";
        if (UtilValidate.isNotEmpty(request.getAttribute("addressId"))) {
            addressId = (String) request.getAttribute("addressId");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("documentClass", context.get("documentClass"));
        map.put("layout", context.get("layout"));
        map.put("productionTime", context.get("productionTime"));
        map.put("envelope", context.get("envelope"));
        map.put("color", context.get("color"));
        map.put("paperType", context.get("paperType"));
        map.put("printOption", context.get("printOption"));
        map.put("mailClass", context.get("mailClass"));
        map.put("documentId", UtilValidate.isNotEmpty(documentId) ? documentId : context.get("documentId"));
        map.put("addressId", UtilValidate.isNotEmpty(addressId) ? addressId : context.get("addressId"));
        map.put("rtnName", context.get("rtnName"));
        map.put("rtnOrganization", context.get("rtnOrganization"));
        map.put("rtnAddress1", context.get("rtnAddress1"));
        map.put("rtnAddress2", context.get("rtnAddress2"));
        map.put("rtnCity", context.get("rtnCity"));
        map.put("rtnState", context.get("rtnState"));
        map.put("rtnZip", context.get("rtnZip"));
        String jobId = (String) context.get("jobId");
        String quantity = (String) context.get("quantity");
        String price = "";
        boolean success = false;
        try {
            JobModel job = Click2MailUtil.updateJob(new UpdateJobRequest(map, jobId));
            if (UtilValidate.isNotEmpty(job)) {
                jsonResponse.put("response", job);
                /** when an addressList is present, job cost will be calculated **/
                if(UtilValidate.isNotEmpty(context.get("addressId")) || UtilValidate.isNotEmpty(addressId)) {
                    price = getJobCost(jobId, quantity);
                } else {
                    /** when an addressList is not present, estimated cost will be calculated **/
                    request.setAttribute("updateJob", true);
                    request.setAttribute("numberOfPages", numberOfPages);
                    price = getCostEstimate(request, response);
                    }
                jsonResponse.put("price", price);
                success = true;
            }
        } catch (Exception e) {
            jsonResponse.put("errorCode", 500);
            e.printStackTrace();
        }
        jsonResponse.put("success", success);

        /** when update job is triggered after successfully creating/updating an addresList **/
        if(UtilValidate.isNotEmpty(addressId)){
            return price;
        }

        /** when update job is triggered after successfully uploading a document file **/
        if (UtilValidate.isNotEmpty(documentId)){
            return price;
        }
        /** when clicking next button **/
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    /** to calculate the job cost from click2mail **/
    public static String getJobCost(String jobId, String quantity) {
        String price = "";
        try {
            Map<String, Object> formParams = new HashMap<>();
            //formParams.put("details", true);
            formParams.put("paymentType", URLEncoder.encode("Credit Card", "UTF-8"));
            JobCostModel cost = Click2MailUtil.getJobCost(new JobCostRequest(UtilMisc.toMap("jobId", jobId), formParams));
            if (UtilValidate.isNotEmpty(cost) && cost.getStatus().equals("0")) {
                /** effective price is calculated by adding margin price **/
                price = Click2MailUtil.getEffectivePrice(new BigDecimal(cost.getCost()), new BigDecimal(quantity), quantity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return price;
    }

    /** to create job proof  **/
    public static String createJobProof(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        String jobId = (String) context.get("jobId");
        boolean success = false;
        try {
            jsonResponse.put("responseString", Click2MailUtil.createJobProof(new CreateJobProofRequest(jobId)));
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
            //EnvUtil.reportError(e);
            //Debug.logError(e, "Error occurred while getting all Marketplace Sellers .", module);
        }

        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    /** when an addressList is modified, current addressList is deleted from click2mail and new addresslist is created.
     for deleting current addreslist this is triggered**/
    public static boolean deleteAddress(String addressListId) {
        boolean isDeleted = false;
        try {
            AddressResponse response = Click2MailUtil.deleteAddress(new DeleteAddressListRequest(UtilMisc.toMap("addressListId", addressListId)));
            if(UtilValidate.isNotEmpty(response)) {
                if(response.getStatus().equals("0")) {
                    System.err.println("\n\n\n\tTHE ADDRESS LIST DELETED SUCCESSFULLY\n\n");
                    isDeleted = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDeleted;
    }

    /** To calaculate number of pages in a selectd document . every time user select a document , this is triggered **/
    public static String getDocumentPageCount(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        String pathName = EnvConstantsUtil.OFBIZ_HOME + context.get("contentPath").toString();
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        jsonResponse.put("count" , Click2MailUtil.getDocumentPageCount(pathName));
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    /** to upload a document on click2mail **/
    public static String uploadDocument(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<String, Object>();

        String pathName = EnvConstantsUtil.OFBIZ_HOME + context.get("contentPath").toString();
        int pages = Click2MailUtil.getDocumentPageCount(pathName);
        File file = new File(pathName);
        Map<String, Object> map = new HashMap<>();
        map.put("documentClass", context.get("documentClass"));
        map.put("documentFormat", FilenameUtils.getExtension(file.getName()).toString().toUpperCase());

        boolean success = false;
        try {
            Click2MailDocument document = Click2MailUtil.uploadDocument(new UploadDocumentRequest(map, file));
            if (UtilValidate.isNotEmpty(document) && !document.getId().equals("0")) {
                /** when a document is successfully uploaded, update the job and calculate the estimated cost or job cost**/
                request.setAttribute("documentId", document.getId());
                request.setAttribute("numberOfPages", pages);
                jsonResponse.put("price",  updateJob(request, response));
                jsonResponse.put("numberOfPages",  pages);
            }
            jsonResponse.put("response", document);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
            //EnvUtil.reportError(e);
            //Debug.logError(e, "Error occurred while getting all Marketplace Sellers .", module);
        }
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String uploadDocumentFile(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        try {
            jsonResponse = FileHelper.uploadFile(request, EnvConstantsUtil.UPLOAD_DIR + "/" + EnvConstantsUtil.DIRECT_MAILING_DOC_UPLOAD_DIR, false, false);
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError("Error while uploading direct mailing document file. " + e + " : " + e.getMessage(), module);
        }

        if (UtilValidate.isEmpty(jsonResponse)) {
            jsonResponse.put("error", "Could not upload files, please try again.");
            jsonResponse.put("success", false);
        }

        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    /** to create/update a new addressList on click2mail **/
    public static String createAddressList(HttpServletRequest request, HttpServletResponse response) {
        GenericValue loggedInUser = (GenericValue) request.getSession().getAttribute("userLogin");
        String partyId = "";
        if (UtilValidate.isNotEmpty(loggedInUser)) {
            partyId = (String) loggedInUser.get("partyId");
        }
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Boolean isAddressListDeleted = false;
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        String dataGroupId = (String) context.get("dataGroupId");
        String jobId = (String) context.get("jobId");
        String addressListId = (String) context.get("addressId");
        String click2MailDateTime = (String) context.get("click2MailAddressModifiedDateTime");
        boolean success = false;
        boolean isAddressUpdatedOrNotYetCreated = false;

        Map<String, Object> map = new HashMap<>();
        List addressList = new ArrayList();
        try {
            /** when creating an addressList for very first time **/
            if (addressListId.equals("0")) {
                isAddressUpdatedOrNotYetCreated = true;

            /** when updating an addressList (by checking whether addressList is updated based on modified dates)  **/
            } else if (Click2MailHelper.isAddressListUpdated(click2MailDateTime, dataGroupId, delegator)) {
                isAddressUpdatedOrNotYetCreated = true;
                /** delete the existing addressList **/
                isAddressListDeleted = deleteAddress(addressListId);
                jsonResponse.put("isAddressListDeleted", isAddressListDeleted);
            }

            /** if boolean isAddressUpdatedOrNotYetCreated is true , create a new addressList on Click2mail**/
            if(isAddressUpdatedOrNotYetCreated) {
                List<Map<String, String>> addresses = Click2MailHelper.getAddresses(request, dataGroupId);
                if (UtilValidate.isNotEmpty(addresses)) {
                    addresses.forEach(data -> {
                        addressList.add(new AddressModel((data.get("firstName") != null ? data.get("firstName") : ""), (data.get("lastName") != null ? data.get("lastName") : ""),
                                (data.get("organization") != null ? data.get("organization") : ""), (data.get("address1") != null ? data.get("address1") : ""),
                                (data.get("address2") != null ? data.get("address2") : ""), (data.get("address3") != null ? data.get("address3") : ""),
                                (data.get("city") != null ? data.get("city") : ""), (data.get("state") != null ? data.get("state") : ""),
                                (data.get("zip") != null ? data.get("zip") : ""), (data.get("country") != null ? data.get("country") : "")));

                    });
                    map.put("addresses", addressList);
                    Click2MailCreatedAddressList address = Click2MailUtil.createAddressList(new CreateAddressListRequest(new AddressList(addressList, "2", "sample")));
                    if (UtilValidate.isNotEmpty(address)) {
                        if (!address.getId().equals("0")) {
                            /** after successfully creating addressList, update the job and calculate the job cost.**/
                            Click2MailHelper.updateAddressListId(jobId, address.getId(), partyId);
                            request.setAttribute("addressId", address.getId());
                            jsonResponse.put("price", updateJob(request, response));
                        }
                        success = true;
                        jsonResponse.put("response", address);
                    }
                }
            } else {
                /** if boolean isAddressUpdatedOrNotYetCreated is false , trigger the update job for updating return address**/
                request.setAttribute("addressId", addressListId);
                jsonResponse.put("price", updateJob(request, response));
                success = true;
                jsonResponse.put("response", "ADDRESS_NOT_CHANGED");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //EnvUtil.reportError(e);
            //Debug.logError(e, "Error occurred while getting all Marketplace Sellers .", module);
        }

        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);

    }

   public static String getJobCost(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        String price = "";
        String jobId = (String) context.get("jobId");
        String quantity = (String) context.get("quantity");
        boolean success = false;
        try {
            price = getJobCost(jobId, quantity);
            jsonResponse.put("price", price);
                success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    /*public static JobCostModel getJobCost(String jobId) {
        try {
            Boolean detailedCost = false;
            Map<String, Object> formParams = new HashMap<>();
            if (detailedCost != null && detailedCost.equals(true)) {
                formParams.put("details", detailedCost.toString());
            }
            formParams.put("paymentType", URLEncoder.encode("Credit Card", "UTF-8"));
            JobCostModel costDetails = Click2MailUtil.getJobCost(new JobCostRequest(UtilMisc.toMap("jobId", jobId), formParams));
            return costDetails;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }*/

    public static String submitJob(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        String partyId = getPartyId(request);
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        String cardNumber = (String)context.get("card");
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        String jobId = null;
        Map<String, Object> paymentAndSubmit = new HashMap<>();
        paymentAndSubmit.put("billingType", "Credit Card");
        paymentAndSubmit.put("billingName", "Thomas");
        paymentAndSubmit.put("billingAddress1", "Washington");
        paymentAndSubmit.put("billingAddress2", "Washington");
        paymentAndSubmit.put("billingCity", "Washington");
        paymentAndSubmit.put("billingState", "WashingtonDc");
        paymentAndSubmit.put("billingZip", "12345");
        paymentAndSubmit.put("billingCcType", "AE"); // MC -> MASTER CARD
//        paymentAndSubmit.put("billingNumber", "5500005555555559"); // card number
        paymentAndSubmit.put("billingNumber", cardNumber != null ? cardNumber : "370000000000002"); // card number
        paymentAndSubmit.put("billingMonth", "12");
        paymentAndSubmit.put("billingYear", "23");
        paymentAndSubmit.put("billingCvv", "123");

//        jobId = "350189";
        jobId = ((String) context.get("jobId")).trim();
        boolean success = false;

        try {
            JobCostModel jobCostModel = Click2MailUtil.submitJob(new JobSubmitRequest(jobId, paymentAndSubmit));
            jsonResponse.put("responseString", jobCostModel);
            success = true;
        } catch (Exception e) {
            //EnvUtil.reportError(e);
            Debug.logError(e, "Error occurred while submitting the job to Click2Mail.", module);
        }
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    /** when save & exit is clicked, savejob is triggered **/
    public static String saveJob(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<>();
        boolean success = false;
        int ERROR_CODE = 0;
        String partyId = getPartyId(request);

        try {
            if (UtilValidate.isNotEmpty(partyId)) {
                Click2MailHelper.saveJob(request, partyId, delegator);
                success = true;
            } else {
                ERROR_CODE = 501;
            }
        } catch (Exception e) {
            //EnvUtil.reportError(e);
            Debug.logError(e, "Error occurred while saving the job .", module);
            ERROR_CODE = 500;
        }
        jsonResponse.put("success", success);
        jsonResponse.put("errorCode", ERROR_CODE);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String createDirectMailingJobsOrders(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<>();
        boolean success = false;
        try{
            Click2MailHelper.saveJobOrder(context, delegator);
            success = true;
        }catch (Exception e) {
            Debug.logError(e, "Error occurred while saving the job order .", module);
            success = false;
        }
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String cancelSubmittedJob(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<>();
        boolean success = false;
        try{
            String jobId = (String)context.get("jobId");
            if(UtilValidate.isNotEmpty(jobId)) {
                jsonResponse.put("responseString", Click2MailUtil.cancelSubmittedJob(new JobCancelRequest(jobId)));
                success = true;
            }
        } catch (Exception e) {
            Debug.logError(e, "Error occurred while saving the job .", module);
        }
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }
}

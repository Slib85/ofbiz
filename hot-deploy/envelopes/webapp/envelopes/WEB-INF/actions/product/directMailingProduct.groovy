
import com.envelopes.product.Product;
import com.envelopes.product.ProductEvents;
import com.bigname.integration.directmailing.DirectMailingHelper;

import com.bigname.integration.click2mail.Click2MailHelper;

import org.apache.ofbiz.base.util.UtilValidate;

import com.google.gson.Gson;

import java.lang.*;
import java.util.*;


String productId = request.getParameter("productId");
String jobNumber = request.getParameter("jobNumber");

String partyId = DirectMailingHelper.getPartyId(request);

context.FEATURES_TO_SHOW = ProductEvents.FEATURES_TO_SHOW;
Product product = new Product(delegator, dispatcher, productId, request);

context.partyId = partyId;

context.put("product", product);

context.documents = DirectMailingHelper.getSavedDocuments(request);

context.showSavedDocuments = UtilValidate.isNotEmpty(partyId) || context.documents.size() > 0;

Map<String, Object> options = DirectMailingHelper.getProductOptions(delegator, productId);
context.productOptions = options;
context.optionsJSON = new Gson().toJson(options);

//Map<String, Object> jobData = DirectMailingHelper.getJobData(delegator, jobNumber, productId);
Map<String, Object> jobData = DirectMailingHelper.getJobData(delegator, request);
context.jobData = jobData;
context.errorCode = jobData.get("errorCode");
context.jobDataJSON = new Gson().toJson(jobData);

context.productId = productId;
//contect.jobNumber = UtilValidate.isEmpty(jobNumber) ? "0" : jobNumber.trim();

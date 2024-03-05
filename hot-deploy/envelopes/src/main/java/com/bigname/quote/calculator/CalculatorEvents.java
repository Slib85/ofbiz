package com.bigname.quote.calculator;

import com.envelopes.netsuite.NetsuiteHelper;
import com.envelopes.party.PartyHelper;
import com.envelopes.quote.QuoteHelper;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.service.LocalDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Manu on 6/1/2017.
 */
public class CalculatorEvents {
    public static final String module = CalculatorEvents.class.getName();

    public static String getDetailedPrice(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");

        Map<String, Object> context = EnvUtil.getParameterMap(request);
        context.put("userLogin", request.getSession().getAttribute("userLogin"));

        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        boolean success = false;
        Map<String, Object> output = new HashMap<>();
        try {
            output = CalculatorHelper.getPricingResponse(dispatcher, delegator, context);
            success = true;
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error processing Pricing Request due to:" + e.getMessage(), module);
            jsonResponse.put("error", e.getMessage());
        }
        jsonResponse.put("response", output);
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    /**
     * Create the quote request and customer, used primarily when a quote is generated from scratch and not from a quote request
     * @param request
     * @param response
     * @return
     */
    public static String createOrUpdateQuoteCustomer(HttpServletRequest request, HttpServletResponse response) {
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Delegator delegator = (Delegator) request.getAttribute("delegator");

        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<>();
        boolean success = false;
        try {
            String quoteRequestId = null;
            //check and create/update customer contact info if necessary
            if (UtilValidate.isEmpty(context.get("quoteRequestId"))) {
                context.put("emailAddress", (String) context.get("userEmail"));
                context.put("webSiteId", "folders");

                Map pricingRequest = CalculatorHelper.deserializePricingRequest((String) context.get("pricingRequest"));
                context.put("productId", (UtilValidate.isNotEmpty(pricingRequest.get("STYLE"))) ? (String) pricingRequest.get("STYLE") : "CUSTOM-P");
                if(!PartyHelper.isEmailActive(delegator, (String) context.get("userEmail"))) {
                    GenericValue userLogin = PartyHelper.createAccount(delegator, dispatcher, context);
                    GenericValue person = userLogin.getRelatedOne("Person", false);
                    context.put("partyId", userLogin.getString("partyId"));

                    //New Customer, then add as Lead
                    Map<String, Object> data = NetsuiteHelper.getPerson(userLogin, person);
                    data.put("phone", (String) ((Map) pricingRequest.get("CUSTOMER_ADDRESS")).get("phone"));
                    data.put("companyName", (String) ((Map) pricingRequest.get("CUSTOMER_ADDRESS")).get("companyName"));
                    try {
                        NetsuiteHelper.createLead(delegator, dispatcher, data);
                    } catch (Exception e1) {
                        EnvUtil.reportError(e1);
                    }
                } else {
                    GenericValue userLogin = PartyHelper.getUserLogin(delegator, (String) context.get("userEmail"));
                    context.put("partyId", userLogin.getString("partyId"));
                }
                context.put("statusId", "QUO_CREATED");
                context.put("createdDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(UtilDateTime.nowTimestamp()));

                quoteRequestId = QuoteHelper.addCustomEnvelopeOrder(delegator, dispatcher, context);
                jsonResponse.put("quoteId", quoteRequestId);
            } else {
                String quoteId = QuoteHelper.updateCustomerContact(delegator, context);
                jsonResponse.put("quoteId", quoteId);
            }
            success = true;
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error creating or updating quote customer:" + e.getMessage(), module);
            jsonResponse.put("error", e.getMessage());
        }

        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String getStyle(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");

        Map<String, Object> input = EnvUtil.getParameterMap(request);
        Map<String, Object> output = new HashMap<>();
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        boolean success = false;
        jsonResponse.put("input", input);
        try {
            output = CalculatorHelper.getStyle(delegator, input);
            success = true;
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying to get Style due to:" + e.getMessage(), module);
            jsonResponse.put("error", e.getMessage());
        }
        jsonResponse.put("output", output);
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String getStyleGroup(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");

        Map<String, Object> input = EnvUtil.getParameterMap(request);
        Map<String, Object> output = new HashMap<>();
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        boolean success = false;
        jsonResponse.put("input", input);
        try {
            output = CalculatorHelper.getStyleGroup(delegator, input);
            success = true;
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying to get Style Group due to:" + e.getMessage(), module);
            jsonResponse.put("error", e.getMessage());
        }
        jsonResponse.put("output", output);
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String getStock(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");

        Map<String, Object> input = EnvUtil.getParameterMap(request);
        Map<String, Object> output = new HashMap<>();
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        boolean success = false;
        jsonResponse.put("input", input);
        try {
            output = CalculatorHelper.getStock(delegator, input);
            success = true;
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying to get Stock due to:" + e.getMessage(), module);
            jsonResponse.put("error", e.getMessage());
        }
        jsonResponse.put("output", output);
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String getStockType(HttpServletRequest request, HttpServletResponse response) {
            Delegator delegator = (Delegator) request.getAttribute("delegator");

            Map<String, Object> input = EnvUtil.getParameterMap(request);
            Map<String, Object> output = new HashMap<>();
            Map<String, Object> jsonResponse = new HashMap<String, Object>();
            boolean success = false;
            jsonResponse.put("input", input);
            try {
                output = CalculatorHelper.getStockType(delegator, input);
                success = true;
            } catch(Exception e) {
                EnvUtil.reportError(e);
                Debug.logError(e, "Error trying to get Stock Type due to:" + e.getMessage(), module);
                jsonResponse.put("error", e.getMessage());
            }
            jsonResponse.put("output", output);
            jsonResponse.put("success", success);
            return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
        }

    public static String getMaterialType(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");

        Map<String, Object> input = EnvUtil.getParameterMap(request);
        Map<String, Object> output = new HashMap<>();
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        boolean success = false;
        jsonResponse.put("input", input);
        try {
            output = CalculatorHelper.getMaterialType(delegator, input);
            success = true;
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying to get Material Type due to:" + e.getMessage(), module);
            jsonResponse.put("error", e.getMessage());
        }
        jsonResponse.put("output", output);
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String getVendor(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");

        Map<String, Object> input = EnvUtil.getParameterMap(request);
        Map<String, Object> output = new HashMap<>();
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        boolean success = false;
        jsonResponse.put("input", input);
        try {
            output = CalculatorHelper.getVendor(delegator, input);
            success = true;
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying to get Vendor due to:" + e.getMessage(), module);
            jsonResponse.put("error", e.getMessage());
        }
        jsonResponse.put("output", output);
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String saveStyle(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");

        Map<String, Object> input = EnvUtil.getParameterMap(request);
        Map<String, Object> output = new HashMap<>();
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        boolean success = false;
        jsonResponse.put("input", input);
        try {
            output = CalculatorHelper.saveStyle(delegator, input);
            success = true;
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying save Style due to:" + e.getMessage(), module);
            jsonResponse.put("error", e.getMessage());
        }
        jsonResponse.put("output", output);
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String saveStyleGroup(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");

        Map<String, Object> input = EnvUtil.getParameterMap(request);
        Map<String, Object> output = new HashMap<>();
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        boolean success = false;
        jsonResponse.put("input", input);
        try {
            output = CalculatorHelper.saveStyleGroup(delegator, input);
            success = true;
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying save Style Group due to:" + e.getMessage(), module);
            jsonResponse.put("error", e.getMessage());
        }
        jsonResponse.put("output", output);
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String saveStock(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");

        Map<String, Object> input = EnvUtil.getParameterMap(request);
        Map<String, Object> output = new HashMap<>();
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        boolean success = false;
        jsonResponse.put("input", input);
        try {
            output = CalculatorHelper.saveStock(delegator, input);
            success = true;
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying save Stock due to:" + e.getMessage(), module);
            jsonResponse.put("error", e.getMessage());
        }
        jsonResponse.put("output", output);
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String saveStockType(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");

        Map<String, Object> input = EnvUtil.getParameterMap(request);
        Map<String, Object> output = new HashMap<>();
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        boolean success = false;
        jsonResponse.put("input", input);
        try {
            output = CalculatorHelper.saveStockType(delegator, input);
            success = true;
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying save Stock Type due to:" + e.getMessage(), module);
            jsonResponse.put("error", e.getMessage());
        }
        jsonResponse.put("output", output);
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String saveMaterialType(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");

        Map<String, Object> input = EnvUtil.getParameterMap(request);
        Map<String, Object> output = new HashMap<>();
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        boolean success = false;
        jsonResponse.put("input", input);
        try {
            output = CalculatorHelper.saveMaterialType(delegator, input);
            success = true;
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying save Material Type due to:" + e.getMessage(), module);
            jsonResponse.put("error", e.getMessage());
        }
        jsonResponse.put("output", output);
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String saveVendor(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");

        Map<String, Object> input = EnvUtil.getParameterMap(request);
        Map<String, Object> output = new HashMap<>();
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        boolean success = false;
        jsonResponse.put("input", input);
        try {
            output = CalculatorHelper.saveVendor(delegator, input);
            success = true;
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying save Vendor due to:" + e.getMessage(), module);
            jsonResponse.put("error", e.getMessage());
        }
        jsonResponse.put("output", output);
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String isIdValid(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");

        Map<String, Object> input = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        boolean valid = false;
        boolean success = false;
        try {
            valid = CalculatorHelper.isIdUnique(delegator, (String) input.get("id"), (String) input.get("type"));
            success = true;
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying to validate " + input.get("type") + " Id due to:" + e.getMessage(), module);
            jsonResponse.put("error", e.getMessage());
        }
        jsonResponse.put("valid", valid);
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String hasUpcharge(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");

        Map<String, Object> input = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        boolean upcharge = false;
        boolean success = false;
        try {
            upcharge = CalculatorHelper.hasItemUpcharge(delegator, (String) input.get("id"));
            success = true;
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error finding upcharge for StyleId '" + input.get("id") + "' due to:" + e.getMessage(), module);
            jsonResponse.put("error", e.getMessage());
        }
        jsonResponse.put("upcharge", upcharge);
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String getAssignedAndAvailableVendorStyleGroups(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");

        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        boolean success = false;
        try {
            jsonResponse.put("output", CalculatorHelper.getAssignedAndAvailableVendorStyleGroups(delegator, (String) context.get("vendorId")));
            success = true;
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying to get Assigned and Available Style Groups for the given Vendor due to:" + e.getMessage(), module);
            jsonResponse.put("error", e.getMessage());
        }
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String getAssignedAndAvailablePricingAttributes(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");

        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        boolean success = false;
        try {
            jsonResponse.put("output", CalculatorHelper.getAssignedAndAvailablePricingAttributes(delegator, (String) context.get("styleGroupId")));
            success = true;
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying to get Assigned and Available Pricing Attributes for the given Style Group due to:" + e.getMessage(), module);
            jsonResponse.put("error", e.getMessage());
        }
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String addOrRemoveVendorStyleGroups(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");

        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        boolean success = false;
        try {
            String[] groupIds = context.get("styleGroupId") instanceof String ? new String[] {(String) context.get("styleGroupId")} : ((List<String>)context.get("styleGroupId")).toArray(new String[0]);
            boolean removeFlag = ((String)context.get("removeFlag")).equalsIgnoreCase("true");
            CalculatorHelper.addOrRemoveVendorStyleGroups(delegator, (String) context.get("vendorId"), groupIds, removeFlag);
            success = true;
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying to add Style Groups for the given Vendor due to:" + e.getMessage(), module);
            jsonResponse.put("error", e.getMessage());
        }
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String addOrRemoveStyleGroupPricingAttributes(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");

        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        boolean success = false;
        try {
            String[] attributeIds = context.get("attributeId") instanceof String ? new String[] {(String) context.get("attributeId")} : ((List<String>)context.get("attributeId")).toArray(new String[0]);
            boolean removeFlag = ((String)context.get("removeFlag")).equalsIgnoreCase("true");
            CalculatorHelper.addOrRemoveStyleGroupPricingAttributes(delegator, (String) context.get("styleGroupId"), attributeIds, removeFlag);
            success = true;
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying to add Pricing Attributes for the given Style Group due to:" + e.getMessage(), module);
            jsonResponse.put("error", e.getMessage());
        }
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String getMinMaxQuantities(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");

        Map<String, Object> context = EnvUtil.getParameterMap(request);

        Map<String, Object> jsonResponse = new HashMap<String, Object>();
        boolean success = false;
        Map<String, Object> output = new HashMap<>();
        try {
            output = CalculatorHelper.getMinMaxQuantities(delegator, context);
            success = true;
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error getting MIN MAX Quantities due to:" + e.getMessage(), module);
            jsonResponse.put("error", e.getMessage());
        }
        jsonResponse.put("response", output);
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }
}

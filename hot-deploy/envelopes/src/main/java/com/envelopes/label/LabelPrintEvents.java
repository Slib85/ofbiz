package com.envelopes.label;

import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.service.LocalDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Manu on 3/17/2016.
 */
public class LabelPrintEvents {
	protected static final String module = LabelPrintEvents.class.getName();

	public static String rebuildLabel(HttpServletRequest request, HttpServletResponse response) {
		boolean success = false;
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> jsonResponse = new HashMap<>();
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		try{
			LabelPrintHelper.invalidateLabel((String)context.get("productId"));
//			jsonResponse.put("labelURL", LabelPrintHelper.getLabel(delegator, (String)context.get("productId"), "png", true, (String)context.get("productId"), context.containsKey("miniLabel") && ((String)context.get("miniLabel")).equalsIgnoreCase("true")));
			jsonResponse.put("labelURL", "uploads/productLabels/" + LabelPrintHelper.getLabelData(delegator, (String)context.get("productId"), false, true).get(0).get("labelPath"));
			success = true;
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "An error occurred while rebuilding the label for SKU:" + context.get("productId"), module);
		}
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String invalidateLabel(HttpServletRequest request, HttpServletResponse response) {
		boolean success = false;
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> jsonResponse = new HashMap<>();
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		try{
			success = LabelPrintHelper.invalidateLabel((String)context.get("productId"));
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "An error occurred while invalidating the label for SKU:" + context.get("productId"), module);
		}
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String getLabelData(HttpServletRequest request, HttpServletResponse response) {
		boolean success = false;
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();
		if(LabelPrintHelper.hasAccess((String)context.get("apiToken"))) {
			Delegator delegator = (Delegator) request.getAttribute("delegator");

			try {
				java.util.List<Map<String, String>> labelData = LabelPrintHelper.getLabelData(delegator, (String) context.get("orderOrProductIdWithQty"), context.containsKey("miniLabel") && ((String)context.get("miniLabel")).equalsIgnoreCase("true"));
				jsonResponse.put("data", labelData);
				success = !labelData.isEmpty();
			} catch (Exception e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "An error occurred while getting the label data for SKU:" + context.get("productId"), module);
			}
		}
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String getFolderSampleLabelData(HttpServletRequest request, HttpServletResponse response) {
		boolean success = false;
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();
		if(LabelPrintHelper.hasAccess((String)context.get("apiToken"))) {
			Delegator delegator = (Delegator) request.getAttribute("delegator");
			LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
			try {
				String jobNumber = (String) context.get("jobNumber");
				if(UtilValidate.isEmpty(jobNumber)) {
					jobNumber = "";
				}
				java.util.List<Map<String, String>> labelData = LabelPrintHelper.getFolderSampleLabelData(delegator, dispatcher, jobNumber.trim());
				jsonResponse.put("data", labelData);
				success = !labelData.isEmpty();
			} catch (Exception e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "An error occurred while getting the label data for Job#:" + context.get("jobNumber"), module);
			}
		}
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String overrideJobData(HttpServletRequest request, HttpServletResponse response) {
		boolean success = false;
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();
		try {
			success = LabelPrintHelper.overrideJobData(delegator, context);
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "An error occurred while overriding the job data for Job#:" + context.get("jobNumber"), module);
		}
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String overrideLabelData(HttpServletRequest request, HttpServletResponse response) {
		boolean success = false;
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();
		try {
			success = LabelPrintHelper.overrideLabelData(delegator, dispatcher, context);
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "An error occurred while overriding the label data for Job#:" + context.get("jobNumber"), module);
		}
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String getFolderName(HttpServletRequest request, HttpServletResponse response) {
		boolean success = false;
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();
		try {
			String name =  LabelPrintHelper.getFolderName(delegator, (String)context.get("styleId"));
			if(UtilValidate.isNotEmpty(name)) {
				jsonResponse.put("name", name);
				success = true;
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "An error occurred while getting product name for style:" + context.get("styleId"), module);
		}
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}
}

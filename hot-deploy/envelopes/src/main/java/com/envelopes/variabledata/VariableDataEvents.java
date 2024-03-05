package com.envelopes.variabledata;

import com.envelopes.http.FileHelper;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import com.google.gson.Gson;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Manu on 11/27/2015.
 */
public class VariableDataEvents {
	public static final String module = VariableDataEvents.class.getName();

	public static String getVariableDataGroupNames(HttpServletRequest request, HttpServletResponse response) {
		boolean success = false;
		Map<String, Object> jsonResponse = new HashMap<>();
		Map<String, String> dataGroupNames = new LinkedHashMap<>();

		try {
			dataGroupNames = VariableDataHelper.getVariableDataGroupNames(request);
			success = true;
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error while finding the saved variable data group names.", module);
		}

		jsonResponse.put("dataGroups", dataGroupNames);
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String findVariableDataGroup(HttpServletRequest request, HttpServletResponse response) {
		boolean success = false;
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> variableDataGroup = new HashMap<>();
		try {
			if(context.containsKey("groupId")) {
				variableDataGroup = VariableDataHelper.getVariableDataGroup((String) context.get("groupId"), delegator);
			} else {
				variableDataGroup = VariableDataHelper.getNewVariableDataGroup((String) context.get("attributeSet"));
			}
			success = true;
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error while finding the variable data group.", module);
		}
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("dataGroup", variableDataGroup);
		jsonResponse.put("success", success);

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String uploadVariableDataFile(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> context = EnvUtil.getParameterMap(request);

		boolean success = false;
		String fileLocation = "";
		String[][] variableData = new String[0][];

		Map<String, Object> jsonResponse = new HashMap<>();
		String message = "";
		try {
			jsonResponse = new HashMap<String, Object>();
			List<Map> pathList = new ArrayList<Map>();
			Map<String, Object> fileData = FileHelper.uploadFile(request, EnvConstantsUtil.UPLOAD_DIR + "/" + EnvConstantsUtil.PRODUCT_UPLOAD_DIR, false, false);
			if (UtilValidate.isNotEmpty(fileData) && UtilValidate.isNotEmpty(fileData.get("files"))) {
				List<Map> files = (List<Map>) fileData.get("files");
				for (Map file : files) {
					Iterator iterator = file.entrySet().iterator();
					while (iterator.hasNext()) {
						Map.Entry pairs = (Map.Entry) iterator.next();
						if ("path".equalsIgnoreCase((String) pairs.getKey())) {
							fileLocation = EnvConstantsUtil.OFBIZ_HOME + ((String) pairs.getValue());
							break;
						}
					}
				}
			}

			if(UtilValidate.isNotEmpty(fileLocation)) {
				variableData = VariableDataHelper.parseVariableDataFile(fileLocation, true);
				if(variableData.length < 2) {
					variableData = new String[0][];
					message = "EMPTY_SHEET";
				} else if(variableData[1].length > VariableDataHelper.MAX_COLUMNS_ALLOWED) {
					variableData = new String[0][];
					message = "TOO_MANY_COLUMNS";
				} else {
					success = true;
				}
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to upload variable data file. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("success", success);
		jsonResponse.put("message", message);
		jsonResponse.put("variableData", variableData);

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String saveVariableData(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;

		try {
			Map<String, Object> resultMap = VariableDataHelper.saveVariableDataGroup(request, response);
			jsonResponse.put("rowDataIds", resultMap.get("rowDataIds"));
			jsonResponse.put("dataGroupId", resultMap.get("dataGroupId"));
			success = true;
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to save variable data. " + e + " : " + e.getMessage(), module);
		}
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String saveVariableDataForLater(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;
		try {
			success = VariableDataHelper.saveDataGroupForLater(request, response);
		} catch(GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to save variable data for later. " + e + " : " + e.getMessage(), module);
		}
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String deleteVariableData(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> jsonResponse = new HashMap<>();
		boolean success = false;

		try {
			Map<String, Object> resultMap = VariableDataHelper.removeVariableData(request);
			success = (boolean) resultMap.get("success");
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to removing variable data. " + e + " : " + e.getMessage(), module);
		}
		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

}

/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.envelopes.http.FileHelper;
import com.envelopes.product.importer.ProductImporter;
import com.envelopes.product.importer.ProductValidation;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.LocalDispatcher;

import com.envelopes.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProductImportEvents {
	public static final String module = ProductImportEvents.class.getName();

	private static final List<String> NO_RUSH;

	static {
		NO_RUSH = new ArrayList<String>();
		NO_RUSH.add("ADD_TO_ME");
	}

	public static String uploadProductFile(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		String error = null;

		boolean success = false;
		try {
			String fileLocation = null;
			Map<String, Object> fileData = FileHelper.uploadFile(request, EnvConstantsUtil.UPLOAD_DIR + "/" + EnvConstantsUtil.PRODUCT_UPLOAD_DIR, false, false);
			if (UtilValidate.isNotEmpty(fileData) && UtilValidate.isNotEmpty(fileData.get("files"))) {
				List<Map> files = (List<Map>) fileData.get("files");
				for (Map file : files) {
					Iterator iter = file.entrySet().iterator();
					while (iter.hasNext()) {
						Map.Entry pairs = (Map.Entry) iter.next();
						if ("path".equalsIgnoreCase((String) pairs.getKey())) {
							fileLocation = EnvConstantsUtil.OFBIZ_HOME + ((String) pairs.getValue());
							break;
						}
					}
				}
			}

			//get products
			ProductImporter productImporter = new ProductImporter(delegator, request);
			productImporter.setProductData(fileLocation);

			//validate products
			ProductValidation validator = new ProductValidation(productImporter.getProductData());
			if(validator.isValid()) {
				List<Map<String, Object>> products = productImporter.getProductData();
				for(Map<String, Object> product : products) {
					productImporter.insertProduct(product);
				}
				if(productImporter.isSuccess()) {
					success = true;
				} else {
					error = productImporter.getError();
				}
			} else {
				for(String validationError : validator.getErrors()) {
					error = error + validationError;
				}
			}
		} catch (Exception e) {
			Debug.logError(e, "Error: " + e.getMessage(), module);
			error = e.getMessage();
		}

		jsonResponse.put("success", success);
		request.setAttribute("success", success);
		request.setAttribute("error", error);
		request.setAttribute("saveResponse", true);
		request.setAttribute("responseName", "productImport");
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String uploadProductPriceFile(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		String error = null;

		boolean success = false;
		try {
			String fileLocation = null;
			Map<String, Object> fileData = FileHelper.uploadFile(request, EnvConstantsUtil.UPLOAD_DIR + "/" + EnvConstantsUtil.PRODUCT_UPLOAD_DIR, false, false);
			if (UtilValidate.isNotEmpty(fileData) && UtilValidate.isNotEmpty(fileData.get("files"))) {
				List<Map> files = (List<Map>) fileData.get("files");
				for (Map file : files) {
					Iterator iter = file.entrySet().iterator();
					while (iter.hasNext()) {
						Map.Entry pairs = (Map.Entry) iter.next();
						if ("path".equalsIgnoreCase((String) pairs.getKey())) {
							fileLocation = EnvConstantsUtil.OFBIZ_HOME + ((String) pairs.getValue());
							break;
						}
					}
				}
			}

			//get products
			ProductImporter productImporter = new ProductImporter(delegator, request);
			productImporter.setProductData(fileLocation);

			//validate products
			ProductValidation validator = new ProductValidation(productImporter.getProductData());
			if(validator.isValid()) {
				List<Map<String, Object>> products = productImporter.getProductData();
				for(Map<String, Object> product : products) {
					productImporter.updateProduct(product);
				}
				if(productImporter.isSuccess()) {
					success = true;
				} else {
					error = productImporter.getError();
				}
			} else {
				for(String validationError : validator.getErrors()) {
					error = error + validationError;
				}
			}
		} catch (Exception e) {
			Debug.logError(e, "Error: " + e.getMessage(), module);
			error = e.getMessage();
		}

		jsonResponse.put("success", success);
		request.setAttribute("success", success);
		request.setAttribute("error", error);
		request.setAttribute("saveResponse", true);
		request.setAttribute("responseName", "productImport");
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Create new product feature
	 */
	public static String createProductFeature(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();

		boolean success = false;

		try {
			if(UtilValidate.isNotEmpty(context.get("productFeatureTypeId")) && UtilValidate.isNotEmpty(context.get("description"))) {
				GenericValue feature = getProductFeature(delegator, (String) context.get("productFeatureTypeId"), (String) context.get("description"));
				if(feature == null) {
					createProductFeature(delegator, (String) context.get("productFeatureTypeId"), (String) context.get("description"));
				}
				success = true;
			}
		} catch(GenericEntityException e) {
			Debug.logError(e, "Error trying to create product feature.", module);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Create product feature
	 */
	private static GenericValue createProductFeature(Delegator delegator, String productFeatureTypeId, String description) throws GenericEntityException {
		GenericValue pf = delegator.makeValue("ProductFeature", UtilMisc.toMap("productFeatureId", delegator.getNextSeqId("ProductFeature"), "productFeatureCategoryId", "10000", "productFeatureTypeId", productFeatureTypeId, "description", description));
		delegator.create(pf);
		return pf;
	}

	/*
	 * Check if feature exists
	 */
	private static GenericValue getProductFeature(Delegator delegator, String productFeatureTypeId, String description) throws GenericEntityException {
		return EntityQuery.use(delegator).from("ProductFeature").where("productFeatureCategoryId", "10000", "productFeatureTypeId", productFeatureTypeId, "description", description).queryFirst();
	}

	/*
	 * check if category exists
	 */
	private static GenericValue getProductCategory(Delegator delegator, String productCategoryId, String productCategoryTypeId) throws GenericEntityException {
		return EntityQuery.use(delegator).from("ProductCategory").where("productCategoryId", productCategoryId).queryOne();
	}

	/*
	 * create product category
	 */
	private static GenericValue createProductCategory(Delegator delegator, String productCategoryId, String productCategoryTypeId) throws GenericEntityException {
		GenericValue pc = delegator.makeValue("ProductCategory", UtilMisc.toMap("productCategoryId", productCategoryId, "productCategoryTypeId", "productCategoryTypeId"));
		delegator.create(pc);
		return pc;
	}

	/*
	 * create product category
	 */
	private static GenericValue createProductCategoryRollup(Delegator delegator, String productCategoryId, String parentProductCategoryId) throws GenericEntityException {
		GenericValue pc = delegator.makeValue("ProductCategoryRollup", UtilMisc.toMap("productCategoryId", productCategoryId, "parentProductCategoryId", parentProductCategoryId, "fromDate", UtilDateTime.nowTimestamp()));
		delegator.create(pc);
		return pc;
	}
}
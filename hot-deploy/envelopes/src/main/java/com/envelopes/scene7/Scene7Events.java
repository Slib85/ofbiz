/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.scene7;

import java.io.*;
import java.net.*;
import java.lang.*;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.jdbc.SQLProcessor;
import org.apache.ofbiz.entity.model.ModelKeyMap;
import org.apache.ofbiz.entity.transaction.GenericTransactionException;
import org.apache.ofbiz.entity.transaction.TransactionUtil;
import org.apache.ofbiz.entity.model.DynamicViewEntity;
import org.apache.ofbiz.entity.util.EntityFindOptions;
import org.apache.ofbiz.entity.util.EntityListIterator;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.order.shoppingcart.ShoppingCart;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartEvents;
import org.apache.ofbiz.security.Security;
import org.apache.ofbiz.service.LocalDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileUploadException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import com.envelopes.http.FileHelper;
import com.envelopes.product.ProductHelper;
import com.envelopes.util.*;

public class Scene7Events {
	public static final String module = Scene7Events.class.getName();

	/*
	 * Get the product name of a given sku
	 */
	public static String getDesignProductName(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator)request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		String scene7TemplateId = (String) context.get("id");
		String productId = (String) context.get("productId");
		if(UtilValidate.isEmpty(productId)) {
			productId = (String) request.getAttribute("productId");
		}

		GenericValue product = null;
		GenericValue scene7Template = null;
		Map<String, String> size = null;
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		try {
			scene7Template = delegator.findOne("Scene7Template", UtilMisc.toMap("scene7TemplateId", scene7TemplateId), true);
			if(UtilValidate.isNotEmpty(productId)) {
				product = delegator.findOne("Product", UtilMisc.toMap("productId", productId), true);
				size = ProductHelper.getProductFeatures(delegator, product, UtilMisc.toList("SIZE_CODE"));
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
		}

		if(UtilValidate.isNotEmpty(scene7Template)) {
			jsonResponse.put("success", true);
			jsonResponse.put("productId", (UtilValidate.isNotEmpty(product)) ? product.getString("productId") : null);
			jsonResponse.put("productName", ((UtilValidate.isNotEmpty(scene7Template.getString("templateDescription"))) ? scene7Template.getString("templateDescription") : scene7Template.getString("scene7TemplateId")) + ((UtilValidate.isNotEmpty(scene7Template.getString("productDesc"))) ? " - " + scene7Template.getString("productDesc") : ""));
			jsonResponse.put("tagLine", (UtilValidate.isNotEmpty(product)) ? product.getString("productName") : null);
			jsonResponse.put("size", (UtilValidate.isNotEmpty(size)) ? size.get("SIZE_CODE") : null);
		} else {
			jsonResponse.put("success", false);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get the underlying products for a scene7 design
	 */
	public static String getProductsForDesign(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator)request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		String templateId = (String) context.get("id");
		if(UtilValidate.isEmpty(templateId)) {
			templateId = (String) context.get("designId");
		}
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		List<Map> productData = new ArrayList<Map>();
		jsonResponse.put("startInkColors", 4); //default it incase below crashes in catch

		try {
			GenericValue template = delegator.findOne("Scene7Template", UtilMisc.toMap("scene7TemplateId", templateId), true);
			if(UtilValidate.isNotEmpty(template) && UtilValidate.isNotEmpty(template.get("productTypeId")) && UtilValidate.isNotEmpty(template.get("height")) && UtilValidate.isNotEmpty(template.get("width"))) {
				if(UtilValidate.isNotEmpty(template.getLong("colors"))) {
					if(template.getString("productTypeId").equalsIgnoreCase("PAPER")) {
						jsonResponse.put("startInkColors", 4);
					} else {
						jsonResponse.put("startInkColors", template.getLong("colors"));
					}
				} else {
					jsonResponse.put("startInkColors", 4);
				}

				List<GenericValue> productAssocList = delegator.findByAnd("Scene7ProdAssoc", UtilMisc.toMap("scene7TemplateId", templateId), null, true);
				List<String> productIds = EntityUtil.getFieldListFromEntityList(productAssocList, "productId", true);

				List<String> paperProdIds = null;
				if(template.getString("productTypeId").equals("PAPER")) {
					List<GenericValue> paperProds = EntityUtil.filterByDate(delegator.findByAnd("ProductCategoryMember", UtilMisc.toMap("productCategoryId", "DESIGN_PRODS"), null, true));
					paperProdIds = EntityUtil.getFieldListFromEntityList(paperProds, "productId", true);
				}

				List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
				conditionList.add(EntityCondition.makeCondition("virtualProductId", EntityOperator.IN, productIds));
				//conditionList.add(EntityCondition.makeCondition("minColors", EntityOperator.LESS_THAN_EQUAL_TO, template.getLong("colors")));
				//conditionList.add(EntityCondition.makeCondition("maxColors", EntityOperator.GREATER_THAN_EQUAL_TO, template.getLong("colors")));
				List<GenericValue> productList = delegator.findList("ColorWarehouse", EntityCondition.makeCondition(conditionList, EntityOperator.AND), null, UtilMisc.toList("sequenceNum ASC"), null, true);

				//determine if a product with the number of colors is available, if not start reducing the colors
				if(UtilValidate.isEmpty(productList)) {
					int currentTry = 0;
					Long fxgColor = template.getLong("colors");
					int startColor = fxgColor.intValue();

					while(currentTry < 3) {
						conditionList.clear();
						conditionList.add(EntityCondition.makeCondition("virtualProductId", EntityOperator.IN, productIds));
						conditionList.add(EntityCondition.makeCondition("minColors", EntityOperator.LESS_THAN_EQUAL_TO, fxgColor));
						conditionList.add(EntityCondition.makeCondition("maxColors", EntityOperator.GREATER_THAN_EQUAL_TO, fxgColor));
						productList = delegator.findList("ColorWarehouse", EntityCondition.makeCondition(conditionList, EntityOperator.AND), null, UtilMisc.toList("sequenceNum ASC"), null, true);

						if(UtilValidate.isNotEmpty(productList)) {
							break;
						} else {
							fxgColor = new Long(ProductHelper.getNextColor(startColor, fxgColor.intValue()));
						}
						currentTry++;
					}
				}

				if(template.getString("productTypeId").equalsIgnoreCase("PAPER")) {
					List<GenericValue> productListCopy = new ArrayList<GenericValue>(productList);
					Iterator prodListIter = productListCopy.iterator();
					while(prodListIter.hasNext()) {
						GenericValue product = (GenericValue) prodListIter.next();
						if(UtilValidate.isNotEmpty(paperProdIds) && !paperProdIds.contains(product.getString("variantProductId"))) {
							prodListIter.remove();
						}
					}
					if(UtilValidate.isNotEmpty(productListCopy)) {
						productList = new ArrayList<GenericValue>(productListCopy);
					}
				}

				if(UtilValidate.isNotEmpty(productList) && template.getString("productTypeId").equals("PAPER")) {
					List<GenericValue> templateAttributes = delegator.findByAnd("Scene7TemplateAttr", UtilMisc.toMap("scene7TemplateId", templateId), null, true);
					productList = Scene7Helper.filterOutAttribute(templateAttributes, productList);
				}

				if(UtilValidate.isNotEmpty(productList)) {
					productList = Scene7Helper.filterOutProducts(productList);
				}

				if(UtilValidate.isEmpty(productList) && template.getString("productTypeId").equalsIgnoreCase("PAPER")) {
					productList = delegator.findByAnd("ColorWarehouse", UtilMisc.toMap("virtualProductId", "8_5_X_11_PAPER"), null, true);
				}

				Map<String, Object> tempData = null;
				if(UtilValidate.isNotEmpty(productList)) {
					jsonResponse.put("success", true);
					for(GenericValue product : productList) {
						tempData = new HashMap<String, Object>();
						String swatch = (UtilValidate.isNotEmpty(product.getString("colorDescription"))) ? EnvUtil.toImageUrl(product.getString("colorDescription")) + ".jpg" : "";
						tempData.put("vId", product.getString("virtualProductId"));
						tempData.put("id", product.getString("variantProductId"));
						tempData.put("type", product.getString("productTypeId"));
						tempData.put("color", product.getString("colorDescription"));
						tempData.put("weight", product.getString("paperWeightDescription"));
						tempData.put("hex", product.getString("colorHexCode"));
						tempData.put("swatch", swatch);
						productData.add(tempData);
					}
				}
				jsonResponse.put("products", productData);
			} else {
				jsonResponse.put("success", false);
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get the designs for a product
	 */
	public static String getDesignsForProductNew(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator)request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		String variantProductId = (String) context.get("id");
		String virtualProductId = (String) context.get("vId");
		String designId = (String) context.get("designId");
		if(UtilValidate.isEmpty(variantProductId)) {
			variantProductId = (String) request.getAttribute("id");
		}
		if(UtilValidate.isEmpty(virtualProductId)) {
			virtualProductId = (String) request.getAttribute("vId");
		}
		Map<String, Object> jsonResponse = new HashMap<>();
		List<Map<String, Object>> primitiveDesigns = new ArrayList<>();
		Map<String, Map<String, Object>> primitiveDesignsMap = new HashMap<>();
		List<Map<String, Object>> advanceDesigns = new ArrayList<>();
		Map<String, Map<String, Object>> advancedDesignsMap = new HashMap<>();

		try {
			List<String> variantProductIds = new ArrayList<>();

			SQLProcessor du = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));
			String sqlCommand = null;
			try {

				du = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));
				sqlCommand = "select distinct a.product_id from (select p.product_id as product_id from scene7_prod_assoc spa inner join product p on spa.product_id = p.product_id where p.is_virtual='N') a where a.product_id in (select product_id from product where parent_product_id = '" + virtualProductId + "')";
				ResultSet rs = null;
				rs = du.executeQuery(sqlCommand);
				if(rs != null) {
					while(rs.next()) {
						variantProductIds.add(rs.getString(1));
					}
				}
			} finally {
				if(du != null) {
					du.close();
				}
			}

			List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
			conditionList.add(EntityCondition.makeCondition(UtilMisc.toList(
							EntityCondition.makeCondition("productId", EntityOperator.IN, variantProductIds),
							EntityCondition.makeCondition("productId", EntityOperator.EQUALS, virtualProductId)
					), EntityOperator.OR)
			);
			EntityCondition condition = EntityCondition.makeCondition(conditionList, EntityOperator.AND);
			List<GenericValue> scene7ProdAssoc = delegator.findList("Scene7TemplateAndProdAssoc", condition, null, null, null,true);
			if(UtilValidate.isNotEmpty(scene7ProdAssoc)) {
				Map<String, Object> tempData = null;
				for(GenericValue template : scene7ProdAssoc) {
					if(UtilValidate.isNotEmpty(template.getString("templateTypeId")) && template.getString("templateTypeId").equals("PRIMITIVE")) {
						if(UtilValidate.isNotEmpty(template.getString("spaTemplateAssocTypeId")) && template.getString("spaTemplateAssocTypeId").equals("TEMPLATE_FRONT")) {
							String templateId = template.getString("scene7TemplateId");
							if(primitiveDesignsMap.containsKey(templateId)) {
								String productVariantId;
								if(variantProductIds.contains(productVariantId = template.getString("productId"))) {
									((List)((Map)primitiveDesignsMap.get(templateId)).get("productVariantIds")).add(productVariantId);
								}
							} else {
								tempData = new HashMap<>();
								Map<String, String> otherSides = Scene7Helper.findOtherSideDesigns(delegator, template.getString("scene7TemplateId"), virtualProductId, null);
								tempData.put("scene7TemplateId", template.getString("scene7TemplateId"));
								tempData.put("thumbnailPath", "//texel.envelopes.com/getBasicImage?id=" + template.getString("scene7TemplateId") + "&fmt=png-alpha");
								tempData.put("blank", "//texel.envelopes.com/getBasicImage?id=" + template.getString("scene7TemplateId") + "&templateType=BLANK");
								tempData.put("return", "//texel.envelopes.com/getBasicImage?id=" + template.getString("scene7TemplateId") + "&templateType=RETURN");
								tempData.put("reply", "//texel.envelopes.com/getBasicImage?id=" + template.getString("scene7TemplateId") + "&templateType=REPLY");
								tempData.put("flap", "//texel.envelopes.com/getBasicImage?id=" + otherSides.get("back") + "&templateType=FLAP");
								tempData.put("productDesc", template.getString("productDesc"));
								tempData.put("quickDesc", template.getString("quickDesc"));
								tempData.put("backDesignId", otherSides.get("back"));
								tempData.put("hasAddressing", (UtilValidate.isEmpty(template.getString("hasAddressing")) || "Y".equalsIgnoreCase(template.getString("hasAddressing"))) ? "Y" : "N");
								tempData.put("hasVariableData", (UtilValidate.isEmpty(template.getString("hasVariableData")) || "Y".equalsIgnoreCase(template.getString("hasVariableData"))) ? "Y" : "N");
								tempData.put("exists", ((UtilValidate.isNotEmpty(designId) && designId.equals(template.getString("scene7TemplateId"))) ? "Y" : "N"));
								String productVariantId;
								if(variantProductIds.contains(productVariantId = template.getString("productId"))) {
									tempData.put("productVariantIds", UtilMisc.toList(productVariantId));
								}
								primitiveDesignsMap.put(templateId, tempData);
								primitiveDesigns.add(tempData);
							}
						}
					} else if(UtilValidate.isNotEmpty(template.getString("templateTypeId")) && template.getString("templateTypeId").equals("ADVANCE")) {
						String templateId = template.getString("scene7TemplateId");
						if(advancedDesignsMap.containsKey(templateId)) {
							String productVariantId;
							if(variantProductIds.contains(productVariantId = template.getString("productId"))) {
								((List)((Map)advancedDesignsMap.get(templateId)).get("productVariantIds")).add(productVariantId);
							}
						} else {
							tempData = new HashMap<>();
							Map<String, String> otherSides = Scene7Helper.findOtherSideDesigns(delegator, template.getString("scene7TemplateId"), virtualProductId, null);
							tempData.put("scene7TemplateId", template.getString("scene7TemplateId"));
							tempData.put("thumbnailPath", "//texel.envelopes.com/getBasicImage?id=" + template.getString("scene7TemplateId") + "&fmt=png-alpha" + "&setElement.COLOR_bgcolor_Path=%3Cfill%3E%3CSolidColor%20color%3D%22%23ENV_HEX_PLACE_HOLDER%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor=%3Cfill%3E%3CSolidColor%20color%3D%22%23ENV_HEX_PLACE_HOLDER%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor1=%3Cfill%3E%3CSolidColor%20color%3D%22%23ENV_HEX_PLACE_HOLDER%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor2=%3Cfill%3E%3CSolidColor%20color%3D%22%23ENV_HEX_PLACE_HOLDER%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor2=%3Cfill%3E%3CSolidColor%20color%3D%22%23ENV_HEX_PLACE_HOLDER%22%2F%3E%3C%2Ffill%3E");
							tempData.put("productTypeId", template.getString("productTypeId"));
							tempData.put("productDesc", template.getString("productDesc"));
							tempData.put("quickDesc", template.getString("quickDesc"));
							tempData.put("backDesignId", otherSides.get("back"));
							tempData.put("hasAddressing", (UtilValidate.isEmpty(template.getString("hasAddressing")) || "Y".equalsIgnoreCase(template.getString("hasAddressing"))) ? "Y" : "N");
							tempData.put("hasVariableData", (UtilValidate.isEmpty(template.getString("hasVariableData")) || "Y".equalsIgnoreCase(template.getString("hasVariableData"))) ? "Y" : "N");
							tempData.put("exists", ((UtilValidate.isNotEmpty(designId) && designId.equals(template.getString("scene7TemplateId"))) ? "Y" : "N"));
							tempData.put("colors", template.getString("colors"));
							String productVariantId;
							if(variantProductIds.contains(productVariantId = template.getString("productId"))) {
								tempData.put("productVariantIds", UtilMisc.toList(productVariantId));
							}
							advancedDesignsMap.put(templateId, tempData);
							advanceDesigns.add(tempData);
						}
					}
				}
				jsonResponse.put("primitives", primitiveDesigns);
				jsonResponse.put("templates", advanceDesigns);
			} else {
				jsonResponse.put("success", false);
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
		}

		if(UtilValidate.isNotEmpty(primitiveDesigns) || UtilValidate.isNotEmpty(advanceDesigns)) {
			jsonResponse.put("success", true);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/**
	 * Old method
	 * @deprecated use {getDesignsForProductNew} instead.
	 */
	@Deprecated
	public static String getDesignsForProduct(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator)request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		String variantProductId = (String) context.get("id");
		String virtualProductId = (String) context.get("vId");
		String designId = (String) context.get("designId");
		if(UtilValidate.isEmpty(variantProductId)) {
			variantProductId = (String) request.getAttribute("id");
		}
		if(UtilValidate.isEmpty(virtualProductId)) {
			virtualProductId = (String) request.getAttribute("vId");
		}
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		List<Map<String, String>> advanceDesigns = new ArrayList<Map<String, String>>();

		try {
			List<GenericValue> scene7ProdAssoc = delegator.findByAnd("Scene7TemplateAndProdAssoc", UtilMisc.toMap("productId", variantProductId), null, true);
			if(UtilValidate.isEmpty(scene7ProdAssoc)) {
				scene7ProdAssoc = delegator.findByAnd("Scene7TemplateAndProdAssoc", UtilMisc.toMap("productId", virtualProductId), null, true);
			}
			if(UtilValidate.isNotEmpty(scene7ProdAssoc)) {
				//capture the different basic pricing for the lowest quantity and in each color group
				Map<Long, BigDecimal> pricing = new HashMap<Long, BigDecimal>();
				if(UtilValidate.isNotEmpty(variantProductId)) {
					DynamicViewEntity dve = new DynamicViewEntity();
					dve.addMemberEntity("PP", "ProductPrice");
					dve.addAliasAll("PP", "", null);
					dve.setGroupBy(UtilMisc.<String>toList("colors"));

					List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
					conditionList.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, variantProductId));
					conditionList.add(EntityCondition.makeCondition("colors", EntityOperator.GREATER_THAN, Long.valueOf("0")));

					EntityListIterator eli = null;
					boolean beganTransaction = false;
					try {
						beganTransaction = TransactionUtil.begin();
						try {
							eli = delegator.findListIteratorByCondition(dve, EntityCondition.makeCondition(conditionList, EntityOperator.AND), null, UtilMisc.toList("colors","price","quantity"), UtilMisc.toList("quantity ASC"), null);
							GenericValue productPrice = null;
							while((productPrice = eli.next()) != null) {
								pricing.put(productPrice.getLong("colors"), productPrice.getBigDecimal("price"));
							}
						} catch (GenericEntityException e1) {
							TransactionUtil.rollback(beganTransaction, "Error looking up price", e1);
							EnvUtil.reportError(e1);
						} finally {
							if(eli != null) {
								try {
									eli.close();
									TransactionUtil.commit(beganTransaction);
								} catch (GenericEntityException e2) {
									EnvUtil.reportError(e2);
								}
							}
						}
					} catch (GenericTransactionException e3) {
						EnvUtil.reportError(e3);
					}
				}

				Map<String, String> tempData = null;
				//we only want templates that have the less than and equal to the number of colors the product supports
				Long colors = null;
				String hex = "FFFFFF";
				GenericValue warehouseProd = EntityUtil.getFirst(delegator.findByAnd("ColorWarehouse", UtilMisc.toMap("variantProductId", variantProductId), null, true));
				if(warehouseProd != null) {
					colors = warehouseProd.getLong("maxColors");
				}
				if(warehouseProd.getString("colorHexCode") != null) {
					hex = warehouseProd.getString("colorHexCode");
				}
				if(UtilValidate.isNotEmpty(warehouseProd.getString("isPrintable")) && warehouseProd.getString("isPrintable").equalsIgnoreCase("Y")) {
					for(GenericValue template : scene7ProdAssoc) {
						if(UtilValidate.isNotEmpty(template.getString("templateTypeId")) && template.getString("templateTypeId").equals("PRIMITIVE")) {
							if(UtilValidate.isNotEmpty(template.getString("spaTemplateAssocTypeId")) && template.getString("spaTemplateAssocTypeId").equals("TEMPLATE_FRONT")) {
								tempData = new HashMap<String, String>();
								Map<String, String> otherSides = Scene7Helper.findOtherSideDesigns(delegator, template.getString("scene7TemplateId"), virtualProductId, null);
								tempData.put("scene7TemplateId", template.getString("scene7TemplateId"));
								tempData.put("thumbnailPath", template.getString("thumbnailPath"));
								tempData.put("blank", template.getString("thumbnailPath") + "&setElement.COLOR_bgcolor_Path=%3Cfill%3E%3CSolidColor%20color%3D%22%23" + hex + "%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor=%3Cfill%3E%3CSolidColor%20color%3D%22%23" + hex + "%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor1=%3Cfill%3E%3CSolidColor%20color%3D%22%23" + hex + "%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor2=%3Cfill%3E%3CSolidColor%20color%3D%22%23" + hex + "%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor2=%3Cfill%3E%3CSolidColor%20color%3D%22%23" + hex + "%22%2F%3E%3C%2Ffill%3E&setAttr.IMAGE_return_logo_left={visible=false}&setAttr.TEXT_return_name_logo_left={visible=false}&setAttr.TEXT_return_address_logo_left={visible=false}&setAttr.IMAGE_return_logo_top={visible=false}&setAttr.TEXT_return_name_logo_top={visible=false}&setAttr.TEXT_return_address_logo_top={visible=false}&setAttr.IMAGE_reply_logo_top={visible=false}&setAttr.TEXT_reply_name_logo_top={visible=false}&setAttr.TEXT_reply_address_logo_top={visible=false}&setAttr.IMAGE_reply_logo_left={visible=false}&setAttr.TEXT_reply_name_logo_left={visible=false}&setAttr.TEXT_reply_address_logo_left={visible=false}&setAttr.TEXT_reply_name_no_logo={visible=false}&setAttr.TEXT_reply_address_no_logo={visible=false}&setAttr.COLOR_reply_border={visible=false}&setAttr.TEXT_reply_stamp_mark={visible=false}&setAttr.TEXT_universal_bottom_text={visible=false}&setAttr.TEXT_universal_bottom_text1={visible=false}&setAttr.TEXT_universal_bottom_text2={visible=false}&setAttr.TEXT_return_name_no_logo={visible=false}&setAttr.TEXT_return_address_no_logo={visible=false}&setAttr.IMAGE_logo_back={visible=false}&setAttr.IMAGE_flap_logo_left={visible=false}&setAttr.IMAGE_logo_front={visible=false}");
								tempData.put("return", template.getString("thumbnailPath") + "&setElement.COLOR_bgcolor_Path=%3Cfill%3E%3CSolidColor%20color%3D%22%23" + hex + "%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor=%3Cfill%3E%3CSolidColor%20color%3D%22%23" + hex + "%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor1=%3Cfill%3E%3CSolidColor%20color%3D%22%23" + hex + "%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor2=%3Cfill%3E%3CSolidColor%20color%3D%22%23" + hex + "%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor2=%3Cfill%3E%3CSolidColor%20color%3D%22%23" + hex + "%22%2F%3E%3C%2Ffill%3E&setAttr.IMAGE_return_logo_left={visible=false}&setAttr.TEXT_return_name_logo_left={visible=false}&setAttr.TEXT_return_address_logo_left={visible=false}&setAttr.IMAGE_return_logo_top={visible=false}&setAttr.TEXT_return_name_logo_top={visible=false}&setAttr.TEXT_return_address_logo_top={visible=false}&setAttr.IMAGE_reply_logo_top={visible=false}&setAttr.TEXT_reply_name_logo_top={visible=false}&setAttr.TEXT_reply_address_logo_top={visible=false}&setAttr.IMAGE_reply_logo_left={visible=false}&setAttr.TEXT_reply_name_logo_left={visible=false}&setAttr.TEXT_reply_address_logo_left={visible=false}&setAttr.TEXT_reply_name_no_logo={visible=false}&setAttr.TEXT_reply_address_no_logo={visible=false}&setAttr.COLOR_reply_border={visible=false}&setAttr.TEXT_reply_stamp_mark={visible=false}&setAttr.TEXT_universal_bottom_text={visible=false}&setAttr.TEXT_universal_bottom_text1={visible=false}&setAttr.TEXT_universal_bottom_text2={visible=false}");
								tempData.put("reply", template.getString("thumbnailPath") + "&setElement.COLOR_bgcolor_Path=%3Cfill%3E%3CSolidColor%20color%3D%22%23" + hex + "%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor=%3Cfill%3E%3CSolidColor%20color%3D%22%23" + hex + "%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor1=%3Cfill%3E%3CSolidColor%20color%3D%22%23" + hex + "%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor2=%3Cfill%3E%3CSolidColor%20color%3D%22%23" + hex + "%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor2=%3Cfill%3E%3CSolidColor%20color%3D%22%23" + hex + "%22%2F%3E%3C%2Ffill%3E&setAttr.IMAGE_reply_logo_left={visible=false}&setAttr.TEXT_reply_name_logo_left={visible=false}&setAttr.TEXT_reply_address_logo_left={visible=false}&setAttr.IMAGE_reply_logo_top={visible=false}&setAttr.TEXT_reply_name_logo_top={visible=false}&setAttr.TEXT_reply_address_logo_top={visible=false}&setAttr.IMAGE_return_logo_top={visible=false}&setAttr.TEXT_return_name_logo_top={visible=false}&setAttr.TEXT_return_address_logo_top={visible=false}&setAttr.IMAGE_return_logo_left={visible=false}&setAttr.TEXT_return_name_logo_left={visible=false}&setAttr.TEXT_return_address_logo_left={visible=false}&setAttr.TEXT_return_name_no_logo={visible=false}&setAttr.TEXT_return_address_no_logo={visible=false}&setAttr.COLOR_reply_border={visible=false}&setAttr.TEXT_reply_stamp_mark={visible=false}&setAttr.TEXT_universal_bottom_text={visible=false}&setAttr.TEXT_universal_bottom_text1={visible=false}&setAttr.TEXT_universal_bottom_text2={visible=false}");
								tempData.put("flap", "http://s7d7.scene7.com/is/agm/ActionEnvelope/" + otherSides.get("back") + "?fmt=png" + "&setElement.COLOR_bgcolor_Path=%3Cfill%3E%3CSolidColor%20color%3D%22%23" + hex + "%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor=%3Cfill%3E%3CSolidColor%20color%3D%22%23" + hex + "%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor1=%3Cfill%3E%3CSolidColor%20color%3D%22%23" + hex + "%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor2=%3Cfill%3E%3CSolidColor%20color%3D%22%23" + hex + "%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor2=%3Cfill%3E%3CSolidColor%20color%3D%22%23" + hex + "%22%2F%3E%3C%2Ffill%3E&setAttr.IMAGE_flap_logo_top_FilledPath={visible=false}&setAttr.IMAGE_flap_logo_left={visible=false}&setAttr.TEXT_flap_name_logo_left={visible=false}&setAttr.TEXT_flap_return_address_logo_left={visible=false}&setAttr.IMAGE_flap_logo_top={visible=false}&setAttr.TEXT_flap_name_logo_top={visible=false}&setAttr.TEXT_flap_return_address_logo_top={visible=false}&setAttr.TEXT_universal_bottom_text={visible=false}&setAttr.TEXT_universal_bottom_text1={visible=false}&setAttr.TEXT_universal_bottom_text2={visible=false}");
								tempData.put("productDesc", template.getString("productDesc"));
								tempData.put("quickDesc", template.getString("quickDesc"));
								tempData.put("backDesignId", otherSides.get("back"));
								tempData.put("price", pricing.get(pricing.keySet().toArray()[0]).toString());
								tempData.put("hasAddressing", (UtilValidate.isEmpty(template.getString("hasAddressing")) || "Y".equalsIgnoreCase(template.getString("hasAddressing"))) ? "Y" : "N");
								tempData.put("hasVariableData", (UtilValidate.isEmpty(template.getString("hasVariableData")) || "Y".equalsIgnoreCase(template.getString("hasVariableData"))) ? "Y" : "N");
								tempData.put("exists", ((UtilValidate.isNotEmpty(designId) && designId.equals(template.getString("scene7TemplateId"))) ? "Y" : "N"));
								jsonResponse.put("primitive", tempData);
							}
						} else if(UtilValidate.isNotEmpty(template.getString("templateTypeId")) && template.getString("templateTypeId").equals("ADVANCE")) {
							if(colors == null || (colors != null && template.getLong("colors") != null && template.getLong("colors") <= colors)) {
								tempData = new HashMap<String, String>();
								Map<String, String> otherSides = Scene7Helper.findOtherSideDesigns(delegator, template.getString("scene7TemplateId"), virtualProductId, null);
								tempData.put("scene7TemplateId", template.getString("scene7TemplateId"));
								tempData.put("thumbnailPath", template.getString("thumbnailPath") + "&setElement.COLOR_bgcolor_Path=%3Cfill%3E%3CSolidColor%20color%3D%22%23" + hex + "%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor=%3Cfill%3E%3CSolidColor%20color%3D%22%23" + hex + "%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor1=%3Cfill%3E%3CSolidColor%20color%3D%22%23" + hex + "%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor2=%3Cfill%3E%3CSolidColor%20color%3D%22%23" + hex + "%22%2F%3E%3C%2Ffill%3E&setElement.COLOR_bgcolor2=%3Cfill%3E%3CSolidColor%20color%3D%22%23" + hex + "%22%2F%3E%3C%2Ffill%3E");
								tempData.put("productTypeId", template.getString("productTypeId"));
								tempData.put("productDesc", template.getString("productDesc"));
								tempData.put("quickDesc", template.getString("quickDesc"));
								tempData.put("backDesignId", otherSides.get("back"));
								tempData.put("price", (template.getLong("colors") != null && pricing.get(template.getLong("colors")) != null) ? pricing.get(template.getLong("colors")).toString() : null);
								tempData.put("hasAddressing", (UtilValidate.isEmpty(template.getString("hasAddressing")) || "Y".equalsIgnoreCase(template.getString("hasAddressing"))) ? "Y" : "N");
								tempData.put("hasVariableData", (UtilValidate.isEmpty(template.getString("hasVariableData")) || "Y".equalsIgnoreCase(template.getString("hasVariableData"))) ? "Y" : "N");
								tempData.put("exists", ((UtilValidate.isNotEmpty(designId) && designId.equals(template.getString("scene7TemplateId"))) ? "Y" : "N"));
								advanceDesigns.add(tempData);
							}
						}
					}
				}
				jsonResponse.put("templates", advanceDesigns);
			} else {
				jsonResponse.put("success", false);
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
		}

		if(UtilValidate.isNotEmpty(advanceDesigns)) {
			jsonResponse.put("success", true);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get the addon product for a design
	 */
	public static String getAddOnProductsForDesign(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator)request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		String templateId = (String) context.get("id");
		if(UtilValidate.isEmpty(templateId)) {
			templateId = (String) context.get("designId");
		}
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		List<Map> productData = new ArrayList<Map>();
		String productTypeId = "ENVELOPE";

		List<String> productsToIgnore = new ArrayList<String>();
		productsToIgnore.add("A7_VERTICAL");
		productsToIgnore.add("A7_POCKETS");
		productsToIgnore.add("A7_PETALS");
		productsToIgnore.add("6_BY_6_POCKETS");
		productsToIgnore.add("6_6_HALF_SQR_PETAL");
		productsToIgnore.add("5_5_POSTAGE_SAVER");
		productsToIgnore.add("A7FB");
		//productsToIgnore.add("6_3_QUAR_REMTNCE");
		//productsToIgnore.add("6_QUAR_REMITNCE");
		//productsToIgnore.add("9_REMITTANCE");

		try {
			GenericValue s7Template = delegator.findOne("Scene7Template", UtilMisc.toMap("scene7TemplateId", templateId), true);
			GenericValue s7TemplateAssoc = EntityUtil.getFirst(delegator.findByAnd("Scene7ProdAssoc", UtilMisc.toMap("scene7TemplateId", templateId), null, true));

			Map<String, Object> tempData = null;
			if(UtilValidate.isNotEmpty(s7Template) && UtilValidate.isNotEmpty(s7TemplateAssoc)) {
				GenericValue product = EntityUtil.getFirst(delegator.findByAnd("ColorWarehouse", UtilMisc.toMap("virtualProductId", s7TemplateAssoc.getString("productId")), null, true));
				if(UtilValidate.isEmpty(product)) {
					product = EntityUtil.getFirst(delegator.findByAnd("ColorWarehouse", UtilMisc.toMap("variantProductId", s7TemplateAssoc.getString("productId")), null, true));
				}
				if(UtilValidate.isNotEmpty(product)) {
					jsonResponse.put("success", true);
					if(UtilValidate.isEmpty(product.getBigDecimal("productWidth")) || UtilValidate.isEmpty(product.getBigDecimal("productHeight"))) {
						jsonResponse.put("success", false);
					} else {
						if(UtilValidate.isNotEmpty(product.get("productTypeId")) && product.getString("productTypeId").equalsIgnoreCase("ENVELOPE")) {
							productTypeId = "ENVELOPE";
						} else {
							productTypeId = "PAPER";
						}

						if((product.getBigDecimal("productWidth").compareTo(new BigDecimal(8.5)) == 0 && product.getBigDecimal("productHeight").compareTo(new BigDecimal(11.0)) == 0) || (product.getBigDecimal("productHeight").compareTo(new BigDecimal(8.5)) == 0 && product.getBigDecimal("productWidth").compareTo(new BigDecimal(11.0)) == 0)) {
							tempData = new HashMap<String, Object>();
							tempData.put("id", "10_SQUARE_FLAP");
							tempData.put("name", "#10 Square Flap Envelope");
							productData.add(tempData);
						} else {
							List<GenericValue> productList = new ArrayList<GenericValue>();

							BigDecimal maxWidth = product.getBigDecimal("productWidth").multiply(new BigDecimal(1.1)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING);
							BigDecimal maxHeight = product.getBigDecimal("productHeight").multiply(new BigDecimal(1.1)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING);

							List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
							List<EntityCondition> cond1 = new ArrayList<EntityCondition>();
							List<EntityCondition> cond2 = new ArrayList<EntityCondition>();
							List<EntityCondition> cond3 = new ArrayList<EntityCondition>();

							if(productTypeId.equals("ENVELOPE")) {
								conditionList.add(EntityCondition.makeCondition("productTypeId", EntityOperator.EQUALS, "PAPER"));
								cond1.add(EntityCondition.makeCondition("productWidth", EntityOperator.LESS_THAN, product.getBigDecimal("productWidth")));
								cond1.add(EntityCondition.makeCondition("productHeight", EntityOperator.LESS_THAN, product.getBigDecimal("productHeight")));
								cond1.add(EntityCondition.makeCondition("productWidth", EntityOperator.GREATER_THAN, maxWidth));
								cond1.add(EntityCondition.makeCondition("productHeight", EntityOperator.GREATER_THAN, maxHeight));
								cond2.add(EntityCondition.makeCondition("productWidth", EntityOperator.LESS_THAN, product.getBigDecimal("productHeight")));
								cond2.add(EntityCondition.makeCondition("productHeight", EntityOperator.LESS_THAN, product.getBigDecimal("productWidth")));
								cond2.add(EntityCondition.makeCondition("productWidth", EntityOperator.GREATER_THAN, maxHeight));
								cond2.add(EntityCondition.makeCondition("productHeight", EntityOperator.GREATER_THAN, maxWidth));
								cond3.add(EntityCondition.makeCondition(cond1, EntityOperator.AND));
								cond3.add(EntityCondition.makeCondition(cond2, EntityOperator.AND));
								conditionList.add(EntityCondition.makeCondition(cond3, EntityOperator.OR));
								productList = delegator.findList("ColorWarehouse", EntityCondition.makeCondition(conditionList, EntityOperator.AND), null, UtilMisc.toList("productWidth ASC", "productHeight ASC"), null, true);
							} else {
								conditionList.add(EntityCondition.makeCondition("productTypeId", EntityOperator.EQUALS, "ENVELOPE"));
								cond1.add(EntityCondition.makeCondition("productWidth", EntityOperator.GREATER_THAN, product.getBigDecimal("productWidth")));
								cond1.add(EntityCondition.makeCondition("productHeight", EntityOperator.GREATER_THAN, product.getBigDecimal("productHeight")));
								cond1.add(EntityCondition.makeCondition("productWidth", EntityOperator.LESS_THAN, maxWidth));
								cond1.add(EntityCondition.makeCondition("productHeight", EntityOperator.LESS_THAN, maxHeight));
								cond2.add(EntityCondition.makeCondition("productWidth", EntityOperator.GREATER_THAN, product.getBigDecimal("productHeight")));
								cond2.add(EntityCondition.makeCondition("productHeight", EntityOperator.GREATER_THAN, product.getBigDecimal("productWidth")));
								cond2.add(EntityCondition.makeCondition("productWidth", EntityOperator.LESS_THAN, maxHeight));
								cond2.add(EntityCondition.makeCondition("productHeight", EntityOperator.LESS_THAN, maxWidth));
								cond3.add(EntityCondition.makeCondition(cond1, EntityOperator.AND));
								cond3.add(EntityCondition.makeCondition(cond2, EntityOperator.AND));
								conditionList.add(EntityCondition.makeCondition(cond3, EntityOperator.OR));
								productList = delegator.findList("ColorWarehouse", EntityCondition.makeCondition(conditionList, EntityOperator.AND), null, UtilMisc.toList("productWidth ASC", "productHeight ASC"), null, true);
							}

							ArrayList<String> virtualList = new ArrayList<String>();
							virtualList.addAll(productsToIgnore);
							if(UtilValidate.isNotEmpty(productList)) {
								for(GenericValue warehouseProd : productList) {
									if(!virtualList.contains(warehouseProd.getString("virtualProductId"))) {
										tempData = new HashMap<String, Object>();
										tempData.put("id", warehouseProd.getString("virtualProductId"));
										tempData.put("name", warehouseProd.getString("productName"));
										productData.add(tempData);
										virtualList.add(warehouseProd.getString("virtualProductId"));
										productsToIgnore.add(warehouseProd.getString("virtualProductId"));
									}
								}
							} else {
								if(productTypeId.equals("ENVELOPE")) {
									tempData = new HashMap<String, Object>();
									tempData.put("id", "8_5_X_11_PAPER");
									tempData.put("name", "8 1/2 x 11 Paper");
									productData.add(tempData);
								} else {
									tempData = new HashMap<String, Object>();
									tempData.put("id", "10_SQUARE_FLAP");
									tempData.put("name", "#10 Square Flap Envelope");
									productData.add(tempData);
								}
							}
						}
					}
				}
			} else {
				jsonResponse.put("success", false);
			}
			jsonResponse.put("products", productData);
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String getLowestPricedVariant(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator)request.getAttribute("delegator");
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		String productId = null;

		request.setAttribute("saveResponse", true);
		getAddOnProductsForDesign(request, response);

		boolean success = false;
		Map<String, Object> addOnProduct = (Map<String, Object>) request.getAttribute("savedResponse");
		if(addOnProduct.containsKey("products")) {
			List<Map> productData = (List<Map>) addOnProduct.get("products");
			List<String> productIds = new ArrayList<>();
			for(Map products: productData) {
				productIds.add((String) products.get("id"));
			}

			List<EntityCondition> conditionList = new ArrayList<>();
			conditionList.add(EntityCondition.makeCondition("productName", EntityOperator.NOT_LIKE, "%printerior%"));
			conditionList.add(EntityCondition.makeCondition("colorDescription", EntityOperator.LIKE, "%white%"));
			conditionList.add(EntityCondition.makeCondition("virtualProductId", EntityOperator.IN, productIds));

			try {
				List<GenericValue> productList = delegator.findList("ColorWarehouse", EntityCondition.makeCondition(conditionList, EntityOperator.AND), null, null, null, true);
				productIds = new ArrayList<>();
				for(GenericValue products: productList) {
					productIds.add(products.getString("variantProductId"));
				}

				DynamicViewEntity dve = new DynamicViewEntity();
				dve.addMemberEntity("PP", "ProductPrice");
				dve.addAlias("PP", "productId");
				dve.addAlias("PP", "price");
				dve.addMemberEntity("CW", "ColorWarehouse");
				dve.addAlias("CW", "virtualProductId");
				dve.addAlias("CW", "variantProductId");
				dve.addViewLink("PP", "CW", false, ModelKeyMap.makeKeyMapList("productId", "variantProductId"));
				EntityFindOptions efo = new EntityFindOptions();
				efo.setMaxRows(1);

				EntityListIterator eli = null;
				boolean beganTransaction = false;
				try {
					beganTransaction = TransactionUtil.begin();
					try {
						eli = delegator.findListIteratorByCondition(dve, EntityCondition.makeCondition("variantProductId", EntityOperator.IN, productIds), null, null, UtilMisc.toList("price ASC"), efo);
						GenericValue product = null;
						while((product = eli.next()) != null) {
							productId = product.getString("productId");
							success = true;
						}
					} catch (GenericEntityException e1) {
						TransactionUtil.rollback(beganTransaction, "Error looking up products", e1);
						EnvUtil.reportError(e1);
					} finally {
						if(eli != null) {
							try {
								eli.close();
								TransactionUtil.commit(beganTransaction);
							} catch (GenericEntityException e2) {
								EnvUtil.reportError(e2);
							}
						}
					}
				} catch (GenericTransactionException e3) {
					EnvUtil.reportError(e3);
				}
			} catch(Exception e) {
				EnvUtil.reportError(e);
			}
		}

		jsonResponse.put("success", success);
		jsonResponse.put("productId", productId);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get associated back for template
	 */
	public static String getOtherSideDesigns(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator)request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		String templateId = (String) context.get("designId");
		if(UtilValidate.isEmpty(templateId)) {
			templateId = (String) context.get("id");
		}
		String virtualProductId = (String) context.get("vId");
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		Map<String, String> otherSides = null;

		try {
			otherSides = Scene7Helper.findOtherSideDesigns(delegator, templateId, virtualProductId, null);
			jsonResponse.put("backDesignId", otherSides.get("back"));
			jsonResponse.put("leftDesignId", otherSides.get("left"));
			jsonResponse.put("rightDesignId", otherSides.get("right"));
			jsonResponse.put("topDesignId", otherSides.get("top"));
			jsonResponse.put("bottomDesignId", otherSides.get("bottom"));
			jsonResponse.put("success", true);
		} catch(GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
		} catch(SQLException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
		}

		if(UtilValidate.isEmpty(otherSides)) {
			jsonResponse.put("success", false);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Save Product Data
	 */
	public static String saveProject(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator)request.getAttribute("delegator");
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		String partyId = (UtilValidate.isNotEmpty(userLogin) && UtilValidate.isNotEmpty(userLogin.getString("partyId"))) ? userLogin.getString("partyId") : "Company";

		if(partyId.equals("Company")) {
			request.getSession().setAttribute("scene7PartyId", "Company");
		} else {
			request.getSession().removeAttribute("scene7PartyId");
		}

		boolean newProject = false;
		String projectId = (String) context.get("id");
		String productData = (String) request.getParameter("productData"); //IMPORTANT, do not get this from getParameterMap(), it will url decode the string

		Map<String, Object> dataToSave = new Gson().fromJson(productData, HashMap.class);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		if(UtilValidate.isEmpty(projectId)) {
			newProject = true;
			projectId = delegator.getNextSeqId("Scene7Design");
		}

		List<String> designIds = new ArrayList<String>();

		try {
			List<Map<String, Object>> products = (ArrayList<Map<String, Object>>) (((Map<String, Object>) dataToSave.get("settings")).get("product"));
			Iterator iter = products.iterator();
			while(iter.hasNext()) {
				Map<String, Object> product = (LinkedTreeMap<String, Object>) iter.next();
				//pop the s7data from individual product and save it
				if(((Boolean) product.get("enabled")).booleanValue() && UtilValidate.isNotEmpty(product.get("s7Data")) && ((Double) product.get("colorsFront") > 0 || (Double) product.get("colorsBack") > 0 || ((Boolean) product.get("isFullBleed")).booleanValue() || UtilValidate.isNotEmpty(product.get("selectionData")))) {
					Map<String, Object> s7Data = (LinkedTreeMap<String, Object>) product.remove("s7Data");

					if(UtilValidate.isNotEmpty(product.get("scene7DesignId"))) {
						GenericValue scene7Design = delegator.findOne("Scene7Design", UtilMisc.toMap("scene7DesignId", (String) product.get("scene7DesignId")), false);
						if(scene7Design != null && EnvUtil.isOwner(partyId, request.getSession().getId(), scene7Design, (Security) request.getAttribute("security"), userLogin)) {
							scene7Design.set("data", (new GsonBuilder().serializeNulls().create()).toJson((Map<String, Object>) s7Data.get("scene7Data")));
							if(UtilValidate.isEmpty(context.get("ignorePartyId")) || (UtilValidate.isNotEmpty(context.get("ignorePartyId")) && "N".equalsIgnoreCase((String) context.get("ignorePartyId")))) {
								scene7Design.set("partyId", (UtilValidate.isNotEmpty(scene7Design) && UtilValidate.isNotEmpty(scene7Design.getString("partyId"))) ? scene7Design.getString("partyId") : "Company");
							}
							scene7Design.set("parentId", projectId);
							scene7Design.set("sessionId", request.getSession().getId());
							delegator.store(scene7Design);
							jsonResponse.put("success", true);
						} else {
							jsonResponse.put("success", false);
							jsonResponse.put("error", "The user does not have permission to update this design.");
						}
					} else {
						String scene7DesignId = delegator.getNextSeqId("Scene7Design");
						GenericValue scene7Design = delegator.makeValue("Scene7Design", UtilMisc.toMap("scene7DesignId", scene7DesignId));
						scene7Design.set("data", (new GsonBuilder().serializeNulls().create()).toJson((Map<String, Object>) s7Data.get("scene7Data")));
						scene7Design.set("partyId", (UtilValidate.isNotEmpty(scene7Design) && UtilValidate.isNotEmpty(scene7Design.getString("partyId"))) ? scene7Design.getString("partyId") : "Company");
						scene7Design.set("parentId", projectId);
						scene7Design.set("sessionId", request.getSession().getId());
						delegator.create(scene7Design);
						jsonResponse.put("success", true);
						product.put("scene7DesignId", scene7DesignId);
					}

					designIds.add((String) product.get("scene7DesignId"));
				}
			}

			//save the main project object
			if(newProject) {
				GenericValue scene7Design = delegator.makeValue("Scene7Design", UtilMisc.toMap("scene7DesignId", projectId));
				scene7Design.put("data", (new GsonBuilder().serializeNulls().create()).toJson(dataToSave));
				scene7Design.put("partyId", partyId);
				scene7Design.put("sessionId", request.getSession().getId());
				delegator.create(scene7Design);
				jsonResponse.put("success", true);
			} else {
				GenericValue scene7Design = delegator.findOne("Scene7Design", UtilMisc.toMap("scene7DesignId", projectId), false);
				if(scene7Design != null && EnvUtil.isOwner(partyId, request.getSession().getId(), scene7Design, (Security) request.getAttribute("security"), userLogin)) {
					scene7Design.set("data", (new GsonBuilder().serializeNulls().create()).toJson(dataToSave));
					if(UtilValidate.isEmpty(context.get("ignorePartyId")) || (UtilValidate.isNotEmpty(context.get("ignorePartyId")) && "N".equalsIgnoreCase((String) context.get("ignorePartyId")))) {
						scene7Design.set("partyId", partyId);
					}
					scene7Design.set("sessionId", request.getSession().getId());
					delegator.store(scene7Design);

					if (UtilValidate.isNotEmpty(request.getParameter("orderId")) && UtilValidate.isNotEmpty(request.getParameter("orderItemSeqId"))) {
						try {
							Map<String, Object> s7Map = new HashMap<String, Object>();
							s7Map.put("orderId", (String) request.getParameter("orderId"));
							s7Map.put("orderItemSeqId", (String) request.getParameter("orderItemSeqId"));
							s7Map.put("scene7DesignId", (String) request.getParameter("designId"));
							s7Map.put("userLogin", (GenericValue) request.getSession().getAttribute("userLogin"));
							s7Map.put("previewFiles", Boolean.TRUE);
							s7Map.put("proofFile", Boolean.TRUE);
							s7Map.put("printFiles", Boolean.TRUE);
							if(UtilValidate.isNotEmpty(request.getParameter("statusId"))) {
								s7Map.put("statusId", (String) request.getParameter("statusId"));
							}
							dispatcher.runAsync("generateScene7Files", s7Map);

							if (UtilValidate.isNotEmpty((String) request.getParameter("numberOfAddresses"))) {
								GenericValue orderItemAddresses = delegator.findOne("OrderItemAttribute", UtilMisc.toMap("orderId", (String) request.getParameter("orderId"), "orderItemSeqId", (String) request.getParameter("orderItemSeqId"), "attrName", "addresses"), false);
								orderItemAddresses.set("attrValue", (String) request.getParameter("numberOfAddresses"));
								delegator.store(orderItemAddresses);
							}
						} catch(Exception e) {
							EnvUtil.reportError(e);
							Debug.logError("Error while trying to generate Scene7 Files: " + e + " : " + e.getMessage(), module);
						}
					}

					jsonResponse.put("success", true);
				} else {
					jsonResponse.put("success", false);
					jsonResponse.put("error", "The user does not have permission to update this design.");
				}
			}
		} catch(GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
			jsonResponse.put("success", false);
		}

		jsonResponse.put("projectId", projectId);
		jsonResponse.put("savedDesignIds", designIds);

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get Scene7 data object
	 */
	public static String loadProject(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator)request.getAttribute("delegator");
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		String partyId = (UtilValidate.isNotEmpty(userLogin) && UtilValidate.isNotEmpty(userLogin.getString("partyId"))) ? userLogin.getString("partyId") : "Company";

		String saveDesignId = (String) context.get("id");

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		try {
			GenericValue scene7Design = delegator.findOne("Scene7Design", UtilMisc.toMap("scene7DesignId", saveDesignId), false);
			if(scene7Design != null/* && EnvUtil.isOwner(partyId, request.getSession().getId(), scene7Design)*/) {
				try {
					jsonResponse = new Gson().fromJson(scene7Design.getString("data"), HashMap.class);
				} catch (Exception e) {
					Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
				}
				jsonResponse.put("success", true);
			} else/* if(scene7Design == null)*/ {
				jsonResponse.put("success", false);
				jsonResponse.put("error", "Unable to find saved design.");
			} /*else {
				jsonResponse.put("success", false);
				jsonResponse.put("error", "The user does not have permission to update this design.");
			}*/
		} catch(GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
		}

		jsonResponse.put("saveDesignId", saveDesignId);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * THIS SERVICE Extracts XML from Scene7 files
	 */
	public static String getScene7TemplateXML(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator)request.getAttribute("delegator");
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		String scene7TemplateId = (String) context.get("id");
		String scene7DesignParentId = (String) context.get("scene7DesignParentId");
		String side = (String) context.get("side");
		String ugcId = (String) context.get("ugcId");

		String fxgXML = null;
		try {
			fxgXML = Scene7Helper.getScene7FXGXML(delegator, scene7TemplateId, scene7DesignParentId, side, ugcId);
		} catch(GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve scene7 data. " + e + " : " + e.getMessage(), module);
		} catch(UnsupportedEncodingException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve scene7 data. " + e + " : " + e.getMessage(), module);
		} catch(MalformedURLException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve scene7 data. " + e + " : " + e.getMessage(), module);
		} catch(IOException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve scene7 data. " + e + " : " + e.getMessage(), module);
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve scene7 data. " + e + " : " + e.getMessage(), module);
		}

		return EnvUtil.doResponse(request, response, null, fxgXML, EnvConstantsUtil.RESPONSE_XML);
	}

	/*
	 * THIS SERVICE generates a blank xml object for a text,path,image
	 */
	public static String generateCustomFXGContent(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		String type = (String) context.get("type");
		String index = (String) context.get("index");
		Double templateWidth = Double.valueOf((String) context.get("templateWidth"));
		Double templateHeight = Double.valueOf((String) context.get("templateHeight"));

		Map<String, String> params = new HashMap<String, String>();
		response.setContentType("text/xml");

		if(templateWidth > 0) {
			Double xPos = (type.equals("RichText")) ? (templateWidth/2.0)-100.0 : (templateWidth/2.0)-75.0;
			params.put("x", xPos.toString());
		} else {
			params.put("x", "10");
		}

		if(templateHeight > 0) {
			Double yPos = (templateHeight/2.0)-50.0;
			params.put("y", yPos.toString());
		} else {
			params.put("y", "10");
		}

		if(type.equals("RichText")) {
			params.put("textAlign", "left");
			params.put("fontSize", "12");
			params.put("lineHeight", "14");
			params.put("width", "200");
			params.put("height", "14");
			params.put("trackingRight", "2%");
			params.put("s7:colorName", "Black");
			params.put("s7:colorValue", "#ff");
			params.put("s7:colorspace", "defined");
		} else if(type.equals("BitmapImage")) {
			params.put("width", "600");
			params.put("height", "300");
			params.put("scaleX", "0.23999999");
			params.put("scaleY", "0.23999999");
			params.put("source", "@Embed('8001A.assets/images/image_library_icon.jpg')");
			params.put("s7:fillOverprint", "false");
			params.put("s7:fillOverprintMode", "true");
			params.put("s7:fit", "stretch");
			params.put("s7:referencePoint", "inherit");
			params.put("s7:strokeOverprint", "false");
			params.put("s7:strokeOverprintMode", "true");
		}
		params.put("s7:elementID", type + "_" + index);

		PrintWriter os = null;
		try {
			String customXML = Scene7Helper.generateCustomFXGContent(params, type);
			os = response.getWriter();
			os.print(customXML);
			os.flush();
		} catch (IOException e) {
			Debug.logError("Error in Scene7DesignEvents : CAUGHT A IOException: " + e + " : " + e.getMessage(), module);
			return "error";
		} catch (Exception e) {
			Debug.logError("Error in Scene7DesignEvents : CAUGHT A Exception: " + e + " : " + e.getMessage(), module);
			return "error";
		} finally {
			if(os != null) {
				try {
					os.close();
				} catch (Exception fe) {
					return "error";
				}
			}
		}

		return "success";
	}

	/*
	 * Get info from scene7 for an image
	 */
	public static String getImgInfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> context = EnvUtil.getParameterMap(request);

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		String fxgXML = null;
		try {
			jsonResponse = Scene7Helper.getImgInfo(context);
		} catch(GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve scene7 data. " + e + " : " + e.getMessage(), module);
		} catch(UnsupportedEncodingException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve scene7 data. " + e + " : " + e.getMessage(), module);
		} catch(MalformedURLException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve scene7 data. " + e + " : " + e.getMessage(), module);
		} catch(IOException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve scene7 data. " + e + " : " + e.getMessage(), module);
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve scene7 data. " + e + " : " + e.getMessage(), module);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get info from scene7 for an image
	 */
	public static String getImageLibrary(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
		HttpSession session = request.getSession();

		Map<String, Object> context = EnvUtil.getParameterMap(request);
		String partyId = (UtilValidate.isNotEmpty(cart.getPartyId())) ? cart.getPartyId() : null;

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		List<Map> array = new ArrayList<Map>();
		String fxgXML = null;
		try {
			List<GenericValue> library = null;
			if(UtilValidate.isEmpty((String) request.getParameter("catId"))) {
				if(UtilValidate.isEmpty(partyId)) {
					library = delegator.findByAnd("Scene7UserContent", UtilMisc.toMap("sessionId", session.getId()), UtilMisc.toList("createdStamp DESC"), false);
				} else {
					library = delegator.findByAnd("Scene7UserContent", UtilMisc.toMap("partyId", partyId), UtilMisc.toList("createdStamp DESC"), false);
				}
			} else {
				library = delegator.findByAnd("Scene7UserContent", UtilMisc.toMap("partyId", "Company", "sessionId", null, "folder", (String) request.getParameter("catId")), null, true);
			}

			if(UtilValidate.isNotEmpty(library)) {
				jsonResponse.put("success" , true);
				for(GenericValue content : library) {
					array.add(
						UtilMisc.<String, String>toMap(
							"id", content.getString("id"),
							"assetUrl", content.getString("assetUrl"),
							"folder", content.getString("folder"),
							"created", content.getString("createdStamp")
						)
					);
				}
			} else {
				jsonResponse.put("success" , false);
			}
			jsonResponse.put("content", array);
			jsonResponse.put("partyId", partyId);
		} catch(GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve image library data. " + e + " : " + e.getMessage(), module);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Upload files for scene7 "Upload Your Own Design"
	 */
	public static String uploadScene7Files(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		try {
			jsonResponse = FileHelper.uploadFile(request, null, false, false);
		} catch(FileUploadException e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
		}

		if(UtilValidate.isEmpty(jsonResponse)) {
			jsonResponse.put("error", "Could not upload files, please try again.");
			jsonResponse.put("success", false);
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 *	Get spot colors
	 */
	public static String getSpotColors(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher)request.getAttribute("dispatcher");

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		ArrayList<Map> spotColors = new ArrayList<Map>();

		Properties fontProps = UtilProperties.getProperties("scene7");
		Iterator fontPropKeys = fontProps.keySet().iterator();
		while(fontPropKeys.hasNext()) {
			String key = (String)fontPropKeys.next();
			if(key.startsWith("new.od.color")){
				String colorName = ((key.split("\\."))[3]).replaceAll("(\\p{Ll})(\\p{Lu})","$1 $2");
				String[] cmykHex = (fontProps.getProperty(key)).split("\\.");

				Map<String, Object> color = new HashMap<String, Object>();
				color.put("name", colorName);
				color.put("cmyk", (cmykHex)[0]);
				color.put("hex", (cmykHex)[1]);
				color.put("pms", (cmykHex)[2]);
				color.put("order", (cmykHex)[3]);
				spotColors.add(color);
			}
		}

		jsonResponse.put("success", true);
		jsonResponse.put("spotColors", spotColors);

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 *	Upload UGC File
	 */
	public static String uploadUGCFile(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
		String partyId = (UtilValidate.isNotEmpty(cart.getPartyId())) ? cart.getPartyId() : "Company";

		boolean success = false;
		if(partyId.equals("Company")) {
			request.getSession().setAttribute("scene7PartyId", "Company");
		} else {
			request.getSession().removeAttribute("scene7PartyId");
		}

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		List<Map> pathList = new ArrayList<Map>();
		try {
			String galleryId = (String) context.get("galleryId");
			Map<String, Object> data = FileHelper.uploadFile(request, null, false, false);
			if(UtilValidate.isNotEmpty(data) && UtilValidate.isNotEmpty((List<Map>) data.get("files"))) {
				for(Map fileData : (List<Map>) data.get("files")) {
					File item = (File) fileData.get("file");
					String fileName = ((String) fileData.get("name")).toLowerCase();
					jsonResponse.put("fileName", fileData.get("name"));
					jsonResponse.put("filePath", item.getAbsolutePath().replace('\\', '/').replaceAll(EnvConstantsUtil.OFBIZ_HOME, ""));

					String imageType = null;
					String fileExt = null;

					if(fileName.endsWith("ai") || fileName.endsWith("eps") || fileName.endsWith("pdf")) {
						imageType = "vector";
						fileExt = "ai,eps,pdf";

						String newPath = FileHelper.getUploadPath(null) + "/" + RandomStringUtils.randomAlphanumeric(EnvConstantsUtil.PASSWORD_LENGTH) + ".png";
						if(Scene7Helper.convertImage(item.getAbsolutePath(), newPath)) {
							imageType = "image";
							fileExt = "bmp,gif,jpg,jpeg,png,tiff,tif";
							item = new File(newPath);
						}
					} else {
						imageType = "image";
						fileExt = "bmp,gif,jpg,jpeg,png,tiff,tif";
					}

					//convert the image to a flat file and sent it to ugc

					String path = Scene7Helper.uploadUGCFile(imageType, fileExt, UtilMisc.<String, File>toMap("filename", item));
					if(UtilValidate.isNotEmpty(path)) {
						try {
							String contentId = delegator.getNextSeqId("Scene7UserContent");
							GenericValue newS7UserContent = delegator.makeValue("Scene7UserContent", UtilMisc.toMap("id", contentId));
							newS7UserContent.set("partyId", partyId);
							newS7UserContent.set("galleryId", galleryId);
							newS7UserContent.set("sessionId", request.getSession().getId());
							newS7UserContent.set("assetUrl", path);
							newS7UserContent.put("folder", (String) fileData.get("path"));

							newS7UserContent.create();
							pathList.add(UtilMisc.<String, String>toMap("path", path, "id", contentId));
						} catch (GenericEntityException e) {
							EnvUtil.reportError(e);
							Debug.logError(e, "Error trying to store UGC to user gallery.", module);
						}
					}

					//item.delete(); //delete the file after being used
				}
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error while trying to send UGC file to S7. " + e + " : " + e.getMessage(), module);
		}

		if(UtilValidate.isNotEmpty(pathList)) {
			success = true;
			jsonResponse.put("paths", pathList);
		}

		jsonResponse.put("success", success);
		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Get a image from a scene7 saved design
	 */
	public static String scene7Image(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		response.setContentType(EnvConstantsUtil.RESPONSE_PNG);

		Map<String, Object> context = EnvUtil.getParameterMap(request);

		String scene7DesignId = (String) context.get("id");
		String setWidth = (UtilValidate.isNotEmpty(context.get("setWidth"))) ? (String) context.get("setWidth") : "";
		String setHeight = (UtilValidate.isNotEmpty(context.get("setHeight"))) ? (String) context.get("setHeight") : "";
		String setSide = (String) context.get("setSide");
		String bgColor = (UtilValidate.isNotEmpty(context.get("bgColor"))) ? (String) context.get("bgColor") : "";
		String scene7AssetUrl = "";
		StringBuilder urlParams = new StringBuilder("");
		boolean buildFromDB = false;

		GenericValue scene7Design = null;
		if(UtilValidate.isNotEmpty(scene7DesignId)) {
			try {
				scene7Design = delegator.findOne("Scene7Design", UtilMisc.toMap("scene7DesignId", scene7DesignId), false);
			} catch (GenericEntityException gee) {
				Debug.logError("Error trying to get scene7 design id: " + scene7DesignId, module);
				EnvUtil.reportError(gee);
			}
		}

		if(UtilValidate.isNotEmpty(scene7Design) && UtilValidate.isNotEmpty(scene7Design.getString("data"))) {
			HashMap<String, Object> jsonMap = new Gson().fromJson(scene7Design.getString("data"), HashMap.class);
			buildFromDB = true;

			String s7Url = null;
			if(UtilValidate.isNotEmpty(setSide)) {
				s7Url = (String) ((Map) ((Map) jsonMap.get("designs")).get(setSide)).get("scene7url");
			} else {
				//check front
				s7Url = (String) ((Map) ((Map) jsonMap.get("designs")).get("0")).get("scene7url");
				if(UtilValidate.isEmpty(s7Url)) {
					s7Url = (String) ((Map) ((Map) jsonMap.get("designs")).get("1")).get("scene7url");
				}
			}

			if(UtilValidate.isEmpty(s7Url) || (UtilValidate.isNotEmpty(s7Url) && s7Url.equals("null"))) {
				s7Url = "http://s7d7.scene7.com/is/agm/ActionEnvelope/" + ((String) ((Map) ((Map) jsonMap.get("designs")).get(setSide)).get("templateId")) + "?fmt=png-alpha";
			}

			scene7AssetUrl = s7Url.substring(0, s7Url.indexOf("?"));
			s7Url = s7Url.substring(s7Url.indexOf("?")+1);
			s7Url = s7Url.replaceAll("(?:wid=(?:[0-9]+)|hei=(?:[0-9]+))&?", "");
			s7Url = s7Url + "&wid=" + setWidth + "&hei=" + setHeight;
			urlParams.append(s7Url);
		}

		if(UtilValidate.isNotEmpty(context) && !buildFromDB) {
			Iterator iter = context.entrySet().iterator();
			while (iter.hasNext()) {
				urlParams.append("&");
				Map.Entry entry = (Map.Entry)iter.next();
				String key = (String) entry.getKey();
				if(key.contains("insertAfter")) {
					String[] valueList = request.getParameterValues(key);
					for(int i = 0; i < valueList.length; i++) {
						String value = valueList[i];
						if(i != 0) {
							urlParams.append("&");
						}
						urlParams.append(key.replace("[]","")).append("=").append(value);
					}
				} else {
					String value = (String) entry.getValue();
					if(key.equals("url")) {
						scene7AssetUrl = value;
					} else {
						urlParams.append(key).append("=").append(value);
					}
				}
			}
			urlParams.deleteCharAt(0);
		}

		OutputStream os = null;
		try {
			URL url = new URL(scene7AssetUrl);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
			writer.write(urlParams.toString());
			writer.flush();

			InputStream in = conn.getInputStream();
			os = response.getOutputStream();

			byte[] buf = new byte[1024];
			int count = 0;
			while ((count = in.read(buf)) >= 0) {
				os.write(buf, 0, count);
			}
			os.flush();
		} catch (IOException e) {
			Debug.logError("Error in Scene7Events : CAUGHT A IOException: " + e + " : " + e.getMessage(), module);
			EnvUtil.reportError(e);
			return "error";
		} catch (Exception e) {
			Debug.logError("Error in Scene7Events : CAUGHT A Exception: " + e + " : " + e.getMessage(), module);
			EnvUtil.reportError(e);
			return "error";
		} finally {
			if(os != null) {
				try {
					os.close();
				} catch (Exception fe) {
					EnvUtil.reportError(fe);
					return "error";
				}
			}
		}
		return "success";
	}
}
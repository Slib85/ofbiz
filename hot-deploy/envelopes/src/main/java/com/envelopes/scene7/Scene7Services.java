/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.scene7;

import java.io.*;
import java.net.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Set;
import java.util.TreeSet;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.GeneralException;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntity;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityConditionList;
import org.apache.ofbiz.entity.condition.EntityExpr;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.transaction.GenericTransactionException;
import org.apache.ofbiz.entity.transaction.TransactionUtil;
import org.apache.ofbiz.entity.util.EntityFindOptions;
import org.apache.ofbiz.entity.util.EntityListIterator;
import org.apache.ofbiz.entity.util.EntityTypeUtil;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import com.scene7.www.ipsapi.xsd.Asset;

import com.envelopes.util.*;

public class Scene7Services {
	public static final String module = Scene7Services.class.getName();
	private static final String APP_REMOTEURL = UtilProperties.getPropertyValue("envelopes", "scene7.remoteUrl");

	/*
	 * Get scene7 fxgs
	 */
	public static Map<String, Object> getScene7DOMFXGs(DispatchContext dctx, Map<String, ? extends Object> context) {
		LocalDispatcher dispatcher = (LocalDispatcher) dctx.getDispatcher();
		Delegator delegator = dctx.getDelegator();

		Map<String, Object> result = ServiceUtil.returnSuccess();

		Debug.logInfo("Retreiving S7 designs.", module);
		List<String> retrievedFXGs = new ArrayList<String>();
		List<Asset> assetList = new ArrayList<Asset>();
		try {
			assetList = Scene7Helper.searchAssets(new String[] {"Fxg"}, 1000, Scene7Helper.APP_FOLDERPATH);
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error while trying to get scene7 fxgs from scene7.", module);
			return ServiceUtil.returnError(e.getMessage());
		}

		for(Asset asset : assetList) {
			if(UtilValidate.isNotEmpty(asset.getType()) && ((String) asset.getType()).equals("Fxg")) {
				String remoteFXGUrl = APP_REMOTEURL+((String)asset.getName());
				String remotePNGUrl = APP_REMOTEURL+((String)asset.getName())+"?fmt=png";

				try {
					if(((String)asset.getName()).length() > 30) {
						Debug.logInfo("Scene7 ID is too long for asset [" + ((String)asset.getName()) + "]. Not inserting.", module);
					} else {
						Debug.logInfo("Storing Scene7 [" + ((String)asset.getName()) + "]..", module);
						GenericValue scene7TemplateRecord = delegator.makeValue("Scene7Template", UtilMisc.toMap("scene7TemplateId", ((String)asset.getName())));
						scene7TemplateRecord.put("fxgType", "DOM");
						scene7TemplateRecord.put("thumbnailId", "_NA_");
						scene7TemplateRecord.put("templateName", ((String)asset.getName()));
						scene7TemplateRecord.put("templateUrl", remoteFXGUrl);
						scene7TemplateRecord.put("thumbnailPath", remotePNGUrl);
						//scene7TemplateRecord.put("productTypeId", Scene7Helper.getFXGProductType(((String)asset.getName()), null, null, null));
						delegator.createOrStore(scene7TemplateRecord);
						retrievedFXGs.add(((String)asset.getName()));
					}
				} catch (GenericEntityException e) {
					EnvUtil.reportError(e);
					Debug.logError(e, "Error while trying to insert scene7 genericvalue.", module);
				}
			}
		}

		Debug.logInfo("Removing S7 designs that were not updated.", module);

		if(UtilValidate.isNotEmpty(retrievedFXGs)) {
			try {
				List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
				conditionList.add(EntityCondition.makeCondition("templateTypeId", EntityOperator.EQUALS, "ADVANCE"));
				conditionList.add(EntityCondition.makeCondition("scene7TemplateId", EntityOperator.NOT_IN, retrievedFXGs));
				EntityCondition condition = EntityCondition.makeCondition(conditionList, EntityOperator.AND);
				List<GenericValue> s7List = delegator.findList("Scene7ProdAssoc", condition, null, null, null, false);
				delegator.removeAll(s7List);

				conditionList.clear();
				conditionList.add(EntityCondition.makeCondition("scene7TemplateId", EntityOperator.NOT_IN, retrievedFXGs));
				condition = EntityCondition.makeCondition(conditionList, EntityOperator.AND);
				s7List = delegator.findList("Scene7TemplateCategory", condition, null, null, null, false);
				delegator.removeAll(s7List);

				s7List = delegator.findList("Scene7TemplateAttr", condition, null, null, null, false);
				delegator.removeAll(s7List);

				conditionList.clear();
				conditionList.add(EntityCondition.makeCondition("fxgType", EntityOperator.EQUALS, "DOM"));
				conditionList.add(EntityCondition.makeCondition("scene7TemplateId", EntityOperator.NOT_IN, retrievedFXGs));
				condition = EntityCondition.makeCondition(conditionList, EntityOperator.AND);
				s7List = delegator.findList("Scene7Template", condition, null, null, null, false);
				delegator.removeAll(s7List);
			} catch (GenericEntityException e) {
				EnvUtil.reportError(e);
				Debug.logError(e, "Error while trying to delete old scene7 data.", module);
				return ServiceUtil.returnError(e.getMessage());
			}
		}

		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.SECOND, 15);
			long startTime = cal.getTimeInMillis();
			dispatcher.schedule("extractScene7Params", context, startTime);
		} catch (GenericServiceException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "An error occurred while trying to get roleType data for party", module);
		}

		return result;
	}

	/*
	 * Get additional parameters for scene7 templates, this is retreived from the s7 xml
	 */
	public static Map<String, Object> extractScene7Params(DispatchContext dctx, Map<String, ? extends Object> context) {
		Delegator delegator = dctx.getDelegator();

		Map<String, Object> result = ServiceUtil.returnSuccess();
		String scene7TemplateId = (String) context.get("scene7TemplateId");

		try {
			List<GenericValue> fxgList = (UtilValidate.isNotEmpty(scene7TemplateId)) ? delegator.findByAnd("Scene7Template", UtilMisc.toMap("fxgType", "DOM", "scene7TemplateId", scene7TemplateId), null, false) : delegator.findByAnd("Scene7Template", UtilMisc.toMap("fxgType", "DOM"), null, false);
			for(GenericValue fxg : fxgList) {
				String fxgXML = Scene7Helper.getScene7FXGXML(delegator, fxg.getString("scene7TemplateId"), null, null, null);
				if(UtilValidate.isEmpty(fxgList)) {
					continue;
				}

				BigDecimal width = BigDecimal.ZERO;
				BigDecimal height = BigDecimal.ZERO;
				Integer colors = 0;
				List<String> colorNames = new ArrayList<String>();

				// Build XML Document from XML string
				try {
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					factory.setNamespaceAware(true);
					factory.setValidating(false);
					DocumentBuilder builder = factory.newDocumentBuilder();
					Document XMLDoc = builder.parse(new InputSource(new StringReader(new String(fxgXML))));

					XPathFactory xpathFact = XPathFactory.newInstance();
					XPath xpath = xpathFact.newXPath();

					String w = (String) xpath.evaluate("/*/@viewWidth", XMLDoc, XPathConstants.STRING);
					String h = (String) xpath.evaluate("/*/@viewHeight", XMLDoc, XPathConstants.STRING);
					if(w != null) {
						width = (new BigDecimal(w)).divide(new BigDecimal(72.0), EnvConstantsUtil.ENV_SCALE_L, EnvConstantsUtil.ENV_ROUNDING);
					}
					if(h != null) {
						height = (new BigDecimal(h)).divide(new BigDecimal(72.0), EnvConstantsUtil.ENV_SCALE_L, EnvConstantsUtil.ENV_ROUNDING);
					}

					//get a list of all nodes called "BitmapImage"
					NodeList nodes = XMLDoc.getElementsByTagName("BitmapImage");
					Map<String, String> colorMap = new HashMap<String, String>();

					if(nodes.getLength() > 0) {
						//fxg contains graphic automatically 4 color
						colors = 4;
					} else {
						//generate color map
						nodes = XMLDoc.getElementsByTagName("s7:Color");
						for(int i = 0; i < nodes.getLength(); i++) {
							String colorName = ((Element)nodes.item(i)).getAttribute("colorName");
							if(colorName.equals("Black")){
								colorMap.put(colorName, "#000000");
							} else if (colorName.equals("Paper")){
								colorMap.put(colorName, "#FFFFFF");
							} else {
								Pattern pattern = Pattern.compile("C=([0-9]+)\\sM=([0-9]+)\\sY=([0-9]+)\\sK=([0-9]+)");
								Matcher matcher = pattern.matcher(colorName);
								while (matcher.find()) {
									colorMap.put(colorName, Scene7Helper.cmykToHex(Integer.valueOf(matcher.group(1)), Integer.valueOf(matcher.group(2)), Integer.valueOf(matcher.group(3)), Integer.valueOf(matcher.group(4))));
									continue;
								}
								pattern = Pattern.compile("([0-9]+),([0-9]+),([0-9]+),([0-9]+)");
								matcher = pattern.matcher(colorName);
								while (matcher.find()) {
									colorMap.put(colorName, Scene7Helper.cmykToHex(Integer.valueOf(matcher.group(1)), Integer.valueOf(matcher.group(2)), Integer.valueOf(matcher.group(3)), Integer.valueOf(matcher.group(4))));
									continue;
								}
								pattern = Pattern.compile("R=([0-9]+)\\sG=([0-9]+)\\sB=([0-9]+)");
								matcher = pattern.matcher(colorName);
								while (matcher.find()) {
									colorMap.put(colorName, Scene7Helper.rgbToHex(Integer.valueOf(matcher.group(1)), Integer.valueOf(matcher.group(2)), Integer.valueOf(matcher.group(3))));
									continue;
								}
							}

						}
						//get a list of all nodes called "path"
						nodes = XMLDoc.getElementsByTagName("Path");
						Scene7Helper.parseNodeListColors(nodes, colorNames, colorMap);
						nodes = XMLDoc.getElementsByTagName("RichText");
						Scene7Helper.parseNodeListColors(nodes, colorNames, colorMap);
						colors = colorNames.size();
						//1,2,4
						if (colors >=3) {
							colors = 4;
						}
					}
				} catch (IOException e) {
					EnvUtil.reportError(e);
					Debug.logError("Error in Scene7Services : CAUGHT A IOException: " + e + " : " + e.getMessage(), module);
				} catch (Exception e) {
					EnvUtil.reportError(e);
					Debug.logError(e, "Error in Scene7Services : CAUGHT A Exception: " + " : " + e.getMessage(), module);
				}

				fxg.set("width", width);
				fxg.set("height", height);
				if(UtilValidate.isEmpty(fxg.getString("productTypeId"))) {
					fxg.set("productTypeId", Scene7Helper.getFXGProductType(fxg.getString("scene7TemplateId"), delegator, width, height));
				}
				Map<String, Boolean> hasAddressingOrVariableData = Scene7Helper.hasAddressingOrVariableData(fxg.getString("scene7TemplateId"), width, height);
				if(UtilValidate.isEmpty(fxg.getString("hasAddressing"))) {
					fxg.put("hasAddressing", (hasAddressingOrVariableData != null && hasAddressingOrVariableData.get("hasAddressing") != null && hasAddressingOrVariableData.get("hasAddressing") == false) ? "N" : "Y");
				}
				if(UtilValidate.isEmpty(fxg.getString("hasVariableData"))) {
					fxg.put("hasVariableData", (hasAddressingOrVariableData != null && hasAddressingOrVariableData.get("hasAddressing") != null && hasAddressingOrVariableData.get("hasVariableData") == false) ? "N" : "Y");
				}
				fxg.set("colors", Long.valueOf(colors));
				fxg.store();
				Debug.logInfo("Set attributes for: " + fxg.getString("scene7TemplateId"), module);
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to get xml", module);
			return ServiceUtil.returnError(e.getMessage());
		}

		return result;
	}

	/*
	 * Get the default images that we allow for use in the online designer
	 */
	public static Map<String, Object> getScene7DefaultImages(DispatchContext dctx, Map<String, ? extends Object> context) {
		Delegator delegator = (Delegator) dctx.getDelegator();

		Map<String, Object> result = ServiceUtil.returnSuccess();

		Debug.logInfo("Retreiving S7 Images.", module);
		List<Asset> assetList = new ArrayList<Asset>();
		try {
			assetList = Scene7Helper.searchAssets(new String[] {"Image"}, 1000, Scene7Helper.APP_IMAGEFPATH);
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error while trying to get scene7 images from scene7.", module);
			return ServiceUtil.returnError(e.getMessage());
		}

		for(Asset asset : assetList) {
			if(UtilValidate.isNotEmpty(asset.getType()) && ((String) asset.getType()).equals("Image")) {
				try {
					if(((String)asset.getName()).length() > 30) {
						Debug.logInfo("Scene7 Image ID is too long for asset [" + ((String)asset.getName()) + "]. Not inserting.", module);
					} else {
						Debug.logInfo("Storing Scene7 [" + ((String)asset.getName()) + "]..", module);
						GenericValue scene7TemplateRecord = delegator.makeValue("Scene7UserContent",  UtilMisc.toMap("id", delegator.getNextSeqId("Scene7UserContent")));
						scene7TemplateRecord.put("partyId", "Company");
						scene7TemplateRecord.put("assetUrl",  (String) asset.getName());
						scene7TemplateRecord.put("folder", ((String)asset.getFolder()).split("/")[((String)asset.getFolder()).split("/").length-1]);
						delegator.create(scene7TemplateRecord);
					}
				} catch (GenericEntityException e) {
					EnvUtil.reportError(e);
					Debug.logError(e, "Error while trying to insert scene7 images genericvalue.", module);
				}
			}
		}

		return result;
	}

	/*
	 * Get the associative product for a given s7 design
	 */
	public static Map<String, Object> getTemplateProdAssoc(DispatchContext dctx, Map<String, ? extends Object> context) {
		Delegator delegator = (Delegator) dctx.getDelegator();

		Map<String, Object> result = ServiceUtil.returnSuccess();

		String scene7TemplateId = (String) context.get("scene7TemplateId");
		List<GenericValue> fxgList = null;

		//first delete all advance template associations
		try {
			fxgList = (UtilValidate.isNotEmpty(scene7TemplateId)) ? delegator.findByAnd("Scene7ProdAssoc", UtilMisc.toMap("scene7TemplateId", scene7TemplateId), null, false) : delegator.findByAnd("Scene7ProdAssoc", UtilMisc.toMap("templateTypeId", "ADVANCE"), null, false);
			delegator.removeAll(fxgList);
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			return ServiceUtil.returnError(e.getMessage());
		}

		try {
			fxgList = EntityUtil.filterByDate((UtilValidate.isNotEmpty(scene7TemplateId)) ? delegator.findByAnd("Scene7Template", UtilMisc.toMap("scene7TemplateId", scene7TemplateId), null, false) : delegator.findByAnd("Scene7Template", UtilMisc.toMap("fxgType", "DOM"), null, false));
			if(fxgList != null) {
				for (GenericValue fxg : fxgList) {
					String templateAssocTypeId = null;
					List<GenericValue> templateAttributes = delegator.findByAnd("Scene7TemplateAttr", UtilMisc.toMap("scene7TemplateId", fxg.getString("scene7TemplateId")), null, true);
					if (UtilValidate.isNotEmpty(fxg.get("width")) && UtilValidate.isNotEmpty(fxg.get("width")) && UtilValidate.isNotEmpty(fxg.get("productTypeId")) && !fxg.getString("scene7TemplateId").startsWith("8")) {
						BigDecimal width = fxg.getBigDecimal("width");
						BigDecimal height = fxg.getBigDecimal("height");
						String fxgNumericID = null;

						String fxgBaseID = (fxg.getString("scene7TemplateId").indexOf("_") != -1) ? fxg.getString("scene7TemplateId").substring(0, fxg.getString("scene7TemplateId").indexOf("_")) : fxg.getString("scene7TemplateId");
						Matcher matcher = Pattern.compile("^(\\d+)").matcher(fxg.getString("scene7TemplateId"));
						while (matcher.find()) {
							fxgNumericID = matcher.group(1);
						}

						if (fxg.getString("scene7TemplateId").toLowerCase().contains("back")) {
							templateAssocTypeId = "TEMPLATE_BACK";
						} else if (fxgNumericID.length() == 4 && fxg.getString("scene7TemplateId").startsWith("3") && fxg.getString("scene7TemplateId").toLowerCase().contains("b")) {
							templateAssocTypeId = "TEMPLATE_BACK";
						} else if (fxgNumericID.length() == 4 && fxg.getString("scene7TemplateId").startsWith("1") && fxg.getString("scene7TemplateId").toLowerCase().contains("d")) {
							templateAssocTypeId = "TEMPLATE_BACK";
						} else if (fxgNumericID.length() == 4 && fxg.getString("scene7TemplateId").startsWith("9") && fxg.getString("scene7TemplateId").toLowerCase().contains("b")) {
							templateAssocTypeId = "TEMPLATE_BACK";
						} else if (fxg.getString("scene7TemplateId").toLowerCase().contains("inside")) {
							templateAssocTypeId = "TEMPLATE_INSIDE";
						} else {
							templateAssocTypeId = "TEMPLATE_FRONT";
						}

						List<GenericValue> productList = null;
						//this is manual override for remittance since they size of the fxg includes the flap
						if (fxg.getString("scene7TemplateId").startsWith("3")) {
							if (fxg.getString("scene7TemplateId").toLowerCase().contains("b")) {
								templateAssocTypeId = "TEMPLATE_BACK";
							}
							if (width.compareTo(new BigDecimal("6.0")) == 0) {
								productList = delegator.findByAnd("Product", UtilMisc.toMap("productId", "6_QUAR_REMITNCE"), null, false);
							} else if (width.compareTo(new BigDecimal("6.5")) == 0 && height.compareTo(new BigDecimal("7")) == 0) {
								productList = delegator.findByAnd("Product", UtilMisc.toMap("productId", "6_3_QUAR_REMTNCE"), null, false);
							} else if (width.compareTo(new BigDecimal("6.5")) == 0 && height.compareTo(new BigDecimal("8.4")) == 0) {
								productList = delegator.findByAnd("Product", UtilMisc.toMap("productId", "6_TWO_WAY"), null, false);
							} else if (width.compareTo(new BigDecimal("8.8813")) == 0) {
								productList = delegator.findByAnd("Product", UtilMisc.toMap("productId", "9_REMITTANCE"), null, false);
							} else if (width.compareTo(new BigDecimal("8.9")) == 0 && height.compareTo(new BigDecimal("8.4")) == 0) {
								productList = delegator.findByAnd("Product", UtilMisc.toMap("productId", "9_TWO_WAY"), null, false);
							}
						} else {
							List<EntityCondition> conditionList1 = new ArrayList<EntityCondition>();
							List<EntityCondition> conditionList2 = new ArrayList<EntityCondition>();
							List<EntityCondition> conditionList3 = new ArrayList<EntityCondition>();
							List<EntityCondition> conditionList4 = new ArrayList<EntityCondition>();

							conditionList1.add(EntityCondition.makeCondition("productWidth", EntityOperator.EQUALS, width));
							conditionList1.add(EntityCondition.makeCondition("productHeight", EntityOperator.EQUALS, height));

							conditionList2.add(EntityCondition.makeCondition("productWidth", EntityOperator.EQUALS, height));
							conditionList2.add(EntityCondition.makeCondition("productHeight", EntityOperator.EQUALS, width));

							conditionList3.add(EntityCondition.makeCondition(conditionList1, EntityOperator.AND));
							conditionList3.add(EntityCondition.makeCondition(conditionList2, EntityOperator.AND));

							conditionList4.add(EntityCondition.makeCondition("productTypeId", EntityOperator.EQUALS, fxg.getString("productTypeId")));
							conditionList4.add(EntityCondition.makeCondition("isVirtual", EntityOperator.EQUALS, "Y"));
							conditionList4.add(EntityCondition.makeCondition("salesDiscontinuationDate", EntityOperator.EQUALS, null));
							conditionList4.add(EntityCondition.makeCondition(conditionList3, EntityOperator.OR));

							EntityCondition condition = EntityCondition.makeCondition(conditionList4, EntityOperator.AND);
							productList = delegator.findList("Product", condition, null, UtilMisc.toList("productHeight DESC", "productWidth DESC"), null, false);
						}

						if (UtilValidate.isNotEmpty(productList)) {
							productList = Scene7Helper.filterOutAttribute(templateAttributes, productList);
						}

						if (UtilValidate.isNotEmpty(productList)) {
							List<String> virtualProductIds = EntityUtil.getFieldListFromEntityList(productList, "productId", true);
							for (String virtualProductId : virtualProductIds) {
								if (templateAssocTypeId.equals("TEMPLATE_FRONT")) {
									GenericValue s7ProdAssoc = delegator.makeValue("Scene7ProdAssoc", UtilMisc.toMap("scene7TemplateId", fxg.getString("scene7TemplateId")));
									s7ProdAssoc.put("productId", virtualProductId);
									s7ProdAssoc.put("templateAssocTypeId", templateAssocTypeId);
									s7ProdAssoc.put("templateTypeId", "ADVANCE");
									delegator.createOrStore(s7ProdAssoc);
								}
							}
						}
					}
					fxg.set("templateAssocTypeId", templateAssocTypeId);
					fxg.store();
				}
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to run service: getTemplateProdAssoc", module);
		}

		return result;
	}

	/*
	 * Get lowest quantity price for the first item in the sequence of matching products
	 */
	public static Map<String, Object> getTemplateBasePrices(DispatchContext dctx, Map<String, ? extends Object> context) {
		Delegator delegator = (Delegator) dctx.getDelegator();

		Map<String, Object> result = ServiceUtil.returnSuccess();

		String scene7TemplateId = (String) context.get("scene7TemplateId");
		List<GenericValue> fxgList = null;

		try {
			fxgList = (UtilValidate.isNotEmpty(scene7TemplateId)) ? delegator.findByAnd("Scene7Template", UtilMisc.toMap("scene7TemplateId", scene7TemplateId, "fxgType", "DOM"), null, false) : delegator.findByAnd("Scene7Template", UtilMisc.toMap("fxgType", "DOM"), null, false);
			for(GenericValue fxg : fxgList) {
				String priceStr = null;
				Long smallQty = null;
				BigDecimal price = BigDecimal.ZERO;

				List<GenericValue> productAssocList = delegator.findByAnd("Scene7ProdAssoc", UtilMisc.toMap("scene7TemplateId", fxg.getString("scene7TemplateId")), null, false);
				List<String> productIds = EntityUtil.getFieldListFromEntityList(productAssocList, "productId", true);

				if(UtilValidate.isNotEmpty(productIds) && UtilValidate.isNotEmpty(fxg.getString("productTypeId"))) {
					List<String> paperProdIds = null;
					if(fxg.getString("productTypeId").equalsIgnoreCase("PAPER")) {
						if(fxg.getString("productTypeId").equalsIgnoreCase("PAPER")) {
							List<GenericValue> paperProds = EntityUtil.filterByDate(delegator.findByAnd("ProductCategoryMember", UtilMisc.toMap("productCategoryId", "DESIGN_PRODS"), null, true));
							paperProdIds = EntityUtil.getFieldListFromEntityList(paperProds, "productId", true);
						}
					}

					List<GenericValue> productList = null;
					List<EntityCondition> conditionList = new ArrayList<EntityCondition>();
					conditionList.add(EntityCondition.makeCondition("virtualProductId", EntityOperator.IN, productIds));
					EntityCondition condition = EntityCondition.makeCondition(conditionList, EntityOperator.AND);
					productList = delegator.findList("ColorWarehouse", condition, null, UtilMisc.toList("sequenceNum ASC"), null, false);

					if(fxg.getString("productTypeId").equalsIgnoreCase("PAPER")) {
						List<GenericValue> productListCopy = new ArrayList<GenericValue>(productList);
						Iterator prodListIter = productListCopy.iterator();
						while(prodListIter.hasNext()) {
							GenericValue product = (GenericValue) prodListIter.next();
							if(!paperProdIds.contains(product.getString("variantProductId"))) {
								prodListIter.remove();
							}
						}
						if(UtilValidate.isNotEmpty(productListCopy)) {
							productList = new ArrayList<GenericValue>(productListCopy);
						}
					}

					if(UtilValidate.isNotEmpty(productList) && fxg.getString("productTypeId").equals("PAPER")) {
						List<GenericValue> templateAttributes = delegator.findByAnd("Scene7TemplateAttr", UtilMisc.toMap("scene7TemplateId", fxg.getString("scene7TemplateId")), null, false);
						productList = Scene7Helper.filterOutAttribute(templateAttributes, productList);
					}

					if(UtilValidate.isNotEmpty(productList)) {
						productList = Scene7Helper.filterOutProducts(productList);
					}

					GenericValue firstVariant = null;
					if(UtilValidate.isNotEmpty(productList)) {
						firstVariant = productList.get(0);
					}

					if(UtilValidate.isNotEmpty(firstVariant) && UtilValidate.isNotEmpty(fxg.getLong("colors"))) {
						int currentTry = 0;
						boolean foundPrice = false;
						Long fxgColor = fxg.getLong("colors");
						boolean goUpInColor = (fxgColor.equals(Long.valueOf("1")) || fxgColor.equals(Long.valueOf("2"))) ? true : false;
						while(!foundPrice && currentTry < 3) {
							//get the smallest quantity first
							List<GenericValue> productPrices = delegator.findByAnd("ProductPrice", UtilMisc.toMap("productId", firstVariant.getString("variantProductId"), "productPriceTypeId", "DEFAULT_PRICE", "colors", fxgColor), null, true);
							Long minQty = null;
							for(GenericValue productPrice : productPrices) {
								if(minQty == null || productPrice.getLong("quantity") < minQty) {
									minQty = productPrice.getLong("quantity");
									price = productPrice.getBigDecimal("price");
								}
							}

							if(minQty != null) {
								priceStr = "from $" + price.toString() + " / " + minQty.toString();
								smallQty = minQty;
								foundPrice = true;
							} else {
								if(goUpInColor) {
									if(fxgColor.equals(Long.valueOf("1"))) {
										fxgColor = Long.valueOf("2");
									} else if(fxgColor.equals(Long.valueOf("2"))) {
										fxgColor = Long.valueOf("4");
									}
								} else {
									if(fxgColor.equals(Long.valueOf("4"))) {
										fxgColor = Long.valueOf("2");
									} else if(fxgColor.equals(Long.valueOf("2"))) {
										fxgColor = Long.valueOf("1");
									}
								}
							}
							currentTry++;
						}
					}
				}

				fxg.set("printPriceDescription", priceStr);
				fxg.set("baseQuantity", smallQty);
				fxg.set("basePrice", price);
				fxg.store();
				Debug.logInfo("Set price for: " + fxg.getString("scene7TemplateId") + " : " + price, module);
			}
		} catch (GenericEntityException e) {
			EnvUtil.reportError(e);
			return ServiceUtil.returnError(e.getMessage());
		} catch (NullPointerException e) {
			EnvUtil.reportError(e);
			return ServiceUtil.returnError(e.getMessage());
		}

		return result;
	}

	/*
	 * Get lowest quantity price for the first item in the sequence of matching products
	 */
	public static Map<String, Object> generateScene7Files(DispatchContext dctx, Map<String, ? extends Object> context) {
		LocalDispatcher dispatcher = (LocalDispatcher) dctx.getDispatcher();
		Delegator delegator = (Delegator) dctx.getDelegator();

		Map<String, Object> result = ServiceUtil.returnSuccess();

		String scene7DesignId = (String) context.get("scene7DesignId");
		String orderId = (String) context.get("orderId");
		String orderItemSeqId = (String) context.get("orderItemSeqId");
		String statusId = (String) context.get("statusId");

		boolean previewFiles = (UtilValidate.isNotEmpty(context.get("previewFiles"))) ? ((Boolean) context.get("previewFiles")).booleanValue() : false;
		boolean proofFile = (UtilValidate.isNotEmpty(context.get("proofFile"))) ? ((Boolean) context.get("proofFile")).booleanValue() : false;
		boolean printFiles = (UtilValidate.isNotEmpty(context.get("printFiles"))) ? ((Boolean) context.get("printFiles")).booleanValue() : false;

		try {
			Scene7Helper.generateScene7Files(delegator, scene7DesignId, orderId, orderItemSeqId, previewFiles, proofFile, printFiles);
		} catch (Exception e) {
			EnvUtil.reportError(e);
			return ServiceUtil.returnError(e.getMessage());
		}

		//if a status change is requested do it now
		if(UtilValidate.isNotEmpty(statusId)) {
			try {
				dispatcher.runAsync("changeOrderItemStatus", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId, "statusId", statusId, "userLogin", EnvUtil.getSystemUser(delegator)));
			} catch (Exception e) {
				EnvUtil.reportError(e);
				//return ServiceUtil.returnError(e.getMessage());
			}
		}

		return result;
	}
}
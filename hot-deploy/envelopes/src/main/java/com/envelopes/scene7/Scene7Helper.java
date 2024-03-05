/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.scene7;

import java.io.*;
import java.net.*;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.GenericDataSourceException;
import org.apache.ofbiz.entity.jdbc.SQLProcessor;
import org.apache.ofbiz.entity.util.EntityUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.DOMSource;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;

import com.scene7.www.ipsapi.*;
import com.scene7.www.ipsapi.xsd.*;

import com.envelopes.http.*;
import com.envelopes.order.OrderHelper;
import com.envelopes.util.*;

public class Scene7Helper {
	/*
	 * SCENE 7 SIDES
	 * 0 = FRONT
	 * 1 = BACK
	 * 2 = INSIDE LEFT
	 * 3 = INSIDE RIGHT
	 * 4 = INSIDE TOP
	 * 5 = INSIDE BOTTOM
	 */

	public static final String module = Scene7Helper.class.getName();
	private static final String APP_NAME = UtilProperties.getPropertyValue("envelopes", "scene7.appName");
	private static final String APP_VERSION = UtilProperties.getPropertyValue("envelopes", "scene7.appVersion");
	private static final String APP_USER = UtilProperties.getPropertyValue("envelopes", "scene7.user");
	private static final String APP_PASSWORD = UtilProperties.getPropertyValue("envelopes", "scene7.password");
	private static final String APP_COMPANYNAME = UtilProperties.getPropertyValue("envelopes", "scene7.companyName");
	public static final String APP_FOLDERPATH = UtilProperties.getPropertyValue("envelopes", "scene7.folderPath");
	public static final String APP_IMAGEFPATH = UtilProperties.getPropertyValue("envelopes", "scene7.imageFolderPath");
	public static final String APP_REMOTEURL = UtilProperties.getPropertyValue("envelopes", "scene7.remoteUrl");
	public static final String APP_IMGTOKEN = UtilProperties.getPropertyValue("envelopes", "scene7.imgToken");
	public static final String APP_VECTORTOKEN = UtilProperties.getPropertyValue("envelopes", "scene7.vectorToken");
	public static final String APP_KEY = UtilProperties.getPropertyValue("envelopes", "scene7.sharedkey");
	private static final String APP_COMPANY = UtilProperties.getPropertyValue("envelopes", "scene7.company");
	private static final String APP_IMGURL = UtilProperties.getPropertyValue("envelopes", "scene7.imgURL");
	private static final String APP_VECTORURL = UtilProperties.getPropertyValue("envelopes", "scene7.vectorURL");

	/*
	 * Login to Scene7
	 */
	private static AuthHeader getAuthHeader() throws Exception {
		AuthHeader authHeader = new AuthHeader();
		authHeader.setAppName(APP_NAME);
		authHeader.setAppVersion(APP_VERSION);
		authHeader.setUser(APP_USER);
		authHeader.setPassword(APP_PASSWORD);

		return authHeader;
	}

	/*
	 * Get search results
	 */
	public static List<Asset> searchAssets(String[] assetTypeList, int recordsPerPage, String folderPath) throws Exception {
		int resultsPage = 1;
		Integer totalPages = null;
		int timeout = 60000;

		IpsApiServiceStub ipsServiceStub = new IpsApiServiceStub();
		ipsServiceStub._getServiceClient().getOptions().setTimeOutInMilliSeconds(timeout);
		ipsServiceStub._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.SO_TIMEOUT, new Integer(timeout));
		ipsServiceStub._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.CONNECTION_TIMEOUT, new Integer(timeout));

		AuthHeader authHeader = getAuthHeader();

		GetCompanyInfoParam getCompanyInfoParam = new GetCompanyInfoParam();
		getCompanyInfoParam.setCompanyName(APP_COMPANYNAME);

		GetCompanyInfoReturn getCompalnyInfoReturn = ipsServiceStub.getCompanyInfo(getCompanyInfoParam, authHeader);
		Company company = getCompalnyInfoReturn.getCompanyInfo();

		GetAssetsParam getAssetsParam = new GetAssetsParam();
		String companyHandle = company.getCompanyHandle();
		getAssetsParam.setCompanyHandle(companyHandle);

		List<Asset> assetList = new ArrayList<Asset>();

		while(resultsPage <= ((totalPages == null) ? 1 : totalPages.intValue())) {
			SearchAssetsParam searchParam = new SearchAssetsParam();
			searchParam.setCompanyHandle(companyHandle);
			searchParam.setIncludeSubfolders(true);
			searchParam.setFolder(folderPath);

			StringArray assetTypes = new StringArray();
			assetTypes.setItems(assetTypeList);
			searchParam.setAssetTypeArray(assetTypes);
			searchParam.setRecordsPerPage(recordsPerPage);
			searchParam.setResultsPage(resultsPage);

			SearchAssetsReturn retVal = ipsServiceStub.searchAssets(searchParam, authHeader);
			int totalRows = retVal.getTotalRows();

			if(totalPages == null && totalRows > recordsPerPage) {
				totalPages = new Integer(new Double(Math.ceil((double)totalRows / recordsPerPage)).intValue());
			} else if (totalPages == null) {
				totalPages = new Integer(1);
			}

			Debug.logInfo("Getting page: " + resultsPage + " of " + totalPages, module);

			AssetArray assetArray = retVal.getAssetArray();
			Asset[] assets = assetArray.getItems();
			if(assets != null) {
				for(int i = 0; i < assets.length; i++) {
					assetList.add(assets[i]);
				}
			}

			resultsPage++;
		}

		return assetList;
	}

	public static String getFXGProductType(String scene7TemplateId, Delegator delegator, BigDecimal width, BigDecimal height) throws GenericEntityException {
		if(UtilValidate.isEmpty(scene7TemplateId)) {
			return null;
		}

		String fxgNumericID = null;
		Matcher matcher = Pattern.compile("^(\\d+)").matcher(scene7TemplateId);
		while(matcher.find()) {
			fxgNumericID = matcher.group(1);
		}

		if(UtilValidate.isNotEmpty(fxgNumericID)) {
			if(fxgNumericID.length() == 4 && scene7TemplateId.startsWith("1") && (scene7TemplateId.contains("a") || scene7TemplateId.contains("c") || scene7TemplateId.contains("d"))) {
				return "PAPER";
			} else if(fxgNumericID.length() == 4 && scene7TemplateId.startsWith("1") && scene7TemplateId.contains("b")) {
				return "ENVELOPE";
			} else if(scene7TemplateId.startsWith("2")) {
				return "ENVELOPE";
			} else if(scene7TemplateId.startsWith("3")) {
				return "ENVELOPE";
			} else if(scene7TemplateId.startsWith("5")) {
				return "PAPER";
			} else if(scene7TemplateId.startsWith("6") && scene7TemplateId.contains("a")) {
				return "PAPER";
			} else if(scene7TemplateId.startsWith("6") && scene7TemplateId.contains("b")) {
				return "ENVELOPE";
			} else if(scene7TemplateId.startsWith("9")) {
				return "PAPER";
			} else if(fxgNumericID.length() == 5 && scene7TemplateId.startsWith("10") && (scene7TemplateId.contains("envelope")) || scene7TemplateId.contains("currency")) {
				return "ENVELOPE";
			} else if(fxgNumericID.length() == 5 && scene7TemplateId.startsWith("10")) {
				return "PAPER";
			} else if(delegator != null && UtilValidate.isNotEmpty(width) && UtilValidate.isNotEmpty(height)) {
				List<EntityCondition> conditionList1 = new ArrayList<EntityCondition>();
				List<EntityCondition> conditionList2 = new ArrayList<EntityCondition>();
				List<EntityCondition> conditionList3 = new ArrayList<EntityCondition>();

				conditionList1.add(EntityCondition.makeCondition("productWidth", EntityOperator.EQUALS, width));
				conditionList1.add(EntityCondition.makeCondition("productHeight", EntityOperator.EQUALS, height));

				conditionList2.add(EntityCondition.makeCondition("productWidth", EntityOperator.EQUALS, height));
				conditionList2.add(EntityCondition.makeCondition("productHeight", EntityOperator.EQUALS, width));

				conditionList3.add(EntityCondition.makeCondition(conditionList1, EntityOperator.AND));
				conditionList3.add(EntityCondition.makeCondition(conditionList2, EntityOperator.AND));

				EntityCondition condition = EntityCondition.makeCondition(conditionList3, EntityOperator.OR);
				GenericValue product = EntityUtil.getFirst(delegator.findList("ColorWarehouse", condition, null, null, null, true));
				if(UtilValidate.isNotEmpty(product)) {
					return product.getString("productTypeId");
				}
			}
		}

		return null;
	}

	/*
	 * Convert CMYK value to HEX
	 */
	public static String cmykToHex(Integer c, Integer m, Integer y, Integer k){
		Integer r = Integer.parseInt( String.valueOf(255 - Math.round(2.55 * (c+k))));
		Integer g = Integer.parseInt( String.valueOf(255 - Math.round(2.55 * (m+k))));
		Integer b = Integer.parseInt( String.valueOf(255 -  Math.round(2.55 * (y+k))));
		if(r<0) { r = 0 ;}
		if(g<0) { g = 0 ;}
		if(b<0) { b = 0 ;}
		return rgbToHex(r, g, b);
	}

	/*
	 * Convert RGB value to HEX
	 */
	public static String rgbToHex(Integer r, Integer g, Integer b){
		return "#" + intToHex(r) + intToHex(g) + intToHex(b);
	}

	/*
	 * Convert int value to HEX
	 */
	public static String intToHex(int n) {
		String hex = String.valueOf(Integer.valueOf(String.valueOf(n), 16));
		return ("00" + hex).substring(hex.length());
	}

	/*
	 * Update color list
	 */
	public static void parseNodeListColors(NodeList nodes, List<String> colorNames, Map<String, String> colorMap) {
		for(int i = 0; i < nodes.getLength(); i++) {
			if(((Element)nodes.item(i)).getAttribute("s7:elementID").equals("COLOR_bgcolor")) {
				continue;
			}
			String nodeColor = getColor(nodes.item(i), colorMap);
			if(!colorNames.contains(nodeColor) && nodeColor != "KEYLINE") {
				colorNames.add(nodeColor);
			}
		}
	}

	/*
	 * Returns whether the template allows plain addressing or if it allows variable data
	 */
	public static Map<String, Boolean> hasAddressingOrVariableData(String scene7TemplateId, BigDecimal width, BigDecimal height) {
		if(UtilValidate.isEmpty(scene7TemplateId) || UtilValidate.isEmpty(width) || UtilValidate.isEmpty(height)) {
			return null;
		}

		Map<String, Boolean> dataMap = new HashMap<String, Boolean>();
		if((width.compareTo(new BigDecimal(3.625)) < 0 && height.compareTo(new BigDecimal(5.125)) < 0) || (height.compareTo(new BigDecimal(3.625)) < 0 && width.compareTo(new BigDecimal(5.125)) < 0)) {
			dataMap.put("hasAddressing", false);
			dataMap.put("hasVariableData", false);
		} else {
			dataMap.put("hasAddressing", true);
			dataMap.put("hasVariableData", true);
		}

		return dataMap;
	}

	/*
	 * Create a full hex
	 */
	public static String makeFullHex(String input){
		if(!input.equals("")){
			Integer length = input.length();
			String result = "#";
			if (length == 7) { return input; }
			if (length != 4 && length != 3) { return "#000000";	}
			for (int i = 1; i < length; i++) {
				if (length == 3) { result += input.charAt(i) + input.charAt(i) + input.charAt(i); }
				else { result += input.charAt(i) + input.charAt(i); }
			}
			return result;
		} else {
			return "#000000";
		}
	}

	/*
	 * Get colors from scene7 elements
	 */
	public static String getColor(Node element, Map<String, String> colorMap){
		String color = "Black";

		if ( ((Element)element).getElementsByTagName("SolidColorStroke").getLength() > 0  && ((Element)((Element)element).getElementsByTagName("SolidColorStroke").item(0)).getAttribute("s7:colorName").equals("KEYLINE")){
			return "KEYLINE";
		} else if ( ((Element)element).getElementsByTagName("SolidColor").getLength() > 0  && ((Element)((Element)element).getElementsByTagName("SolidColor").item(0)).getAttribute("s7:colorName").equals("KEYLINE")){
			return "KEYLINE";
		} else if (!((Element)element).getAttribute("color").equals("")){
			return makeFullHex(((Element)element).getAttribute("color"));
		} else if (!((Element)element).getAttribute("s7:colorName").equals("")){
			return colorMap.get(((Element)element).getAttribute("s7:colorName"));
		} else if ( ((Element)element).getElementsByTagName("SolidColorStroke").getLength() > 0  && !((Element)((Element)element).getElementsByTagName("SolidColorStroke").item(0)).getAttribute("color").equals("")){
			return makeFullHex(((Element)((Element)element).getElementsByTagName("SolidColorStroke").item(0)).getAttribute("color"));
		} else if ( ((Element)element).getElementsByTagName("SolidColorStroke").getLength() > 0  && !((Element)((Element)element).getElementsByTagName("SolidColorStroke").item(0)).getAttribute("s7:colorName").equals("")){
			return makeFullHex(((Element)((Element)element).getElementsByTagName("SolidColorStroke").item(0)).getAttribute("s7:colorName"));
		} else if ( ((Element)element).getElementsByTagName("SolidColor").getLength() > 0  && !((Element)((Element)element).getElementsByTagName("SolidColor").item(0)).getAttribute("color").equals("")){
			return makeFullHex(((Element)((Element)element).getElementsByTagName("SolidColor").item(0)).getAttribute("color"));
		} else if ( ((Element)element).getElementsByTagName("SolidColor").getLength() > 0  && !((Element)((Element)element).getElementsByTagName("SolidColor").item(0)).getAttribute("s7:colorName").equals("")){
			return makeFullHex(((Element)((Element)element).getElementsByTagName("SolidColor").item(0)).getAttribute("s7:colorName"));
		}

		return "#000000";
	}

	/*
	 * Get the xml from a scene7 template
	 */
	public static String getScene7FXGXML(Delegator delegator, String scene7TemplateId, String scene7DesignId, String side, String ugcId) throws GenericEntityException, UnsupportedEncodingException, MalformedURLException, IOException, Exception {
		String fxgXML = null;
		String currentTimeMillis = UtilDateTime.nowAsString();
		GenericValue fxg = null;
		String url = null;

		if(UtilValidate.isNotEmpty(scene7DesignId)) {
			GenericValue scene7Design = delegator.findOne("Scene7Design", UtilMisc.toMap("scene7DesignId", scene7DesignId), false);
			if(scene7Design != null) {
				HashMap<String, Object> jsonMap = new Gson().fromJson(scene7Design.getString("data"), HashMap.class);
				//loop through all sides
				Map<String, Object> allSides = (Map<String, Object>) jsonMap.get("designs");
				Iterator iter = allSides.entrySet().iterator();
				while(iter.hasNext()) {
					Map.Entry pairs = (Map.Entry) iter.next();
					String designSide = (String) pairs.getKey();
					if(side.equals(designSide)) {
						Map<String, Object> sideMap = (Map<String, Object>) pairs.getValue();
						url = (String) sideMap.get("scene7url");
					}
				}
			}
		}

		if(url == null && UtilValidate.isNotEmpty(ugcId)) {
			GenericValue ugcContent = delegator.findOne("Scene7UserContent", UtilMisc.toMap("id", ugcId), false);
			if(ugcContent != null && UtilValidate.isNotEmpty(ugcContent.getString("assetUrl")) && ugcContent.getString("assetUrl").contains(".fxg")) {
				url = "http://s7w2p1.scene7.com/is/agm/ActionEnvelope/" + ugcContent.getString("assetUrl") + "?" + "time=" + currentTimeMillis + "&fmt=fxgraw";
			}
		}

		if(url != null) {
			url = url.replace("fmt=png", "fmt=fxg");
		} else {
			fxg = delegator.findOne("Scene7Template", UtilMisc.toMap("scene7TemplateId", scene7TemplateId), false);
			if(UtilValidate.isNotEmpty(fxg) && UtilValidate.isNotEmpty(fxg.getString("templateUrl"))) {
				url = fxg.getString("templateUrl") + "?" + "time=" + currentTimeMillis + "&fmt=fxgraw";
			}
		}

		if(url != null) {
			String baseUrl = url.substring(0, url.indexOf("?"));
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("body", url.substring(url.indexOf("?")+1));

			fxgXML = HTTPHelper.getURL(baseUrl, "POST", null, null, paramMap, null, false, EnvConstantsUtil.RESPONSE_PLAIN, EnvConstantsUtil.RESPONSE_PLAIN);
		}

		return fxgXML;
	}

	/*
	 * Get the json data for an image
	 */
	public static Map<String, Object> getImgInfo(Map<String, Object> context) throws GenericEntityException, UnsupportedEncodingException, MalformedURLException, IOException, Exception {
		StringBuilder urlParams = new StringBuilder("");
		String fxgXML = null;
		String scene7AssetUrl = "";

		if(UtilValidate.isNotEmpty(context)) {
			Iterator iter = context.entrySet().iterator();
			while (iter.hasNext()) {
				urlParams.append("&");
				Map.Entry entry = (Map.Entry)iter.next();
				String key = (String) entry.getKey();

				String value = (String) entry.getValue();
				if(key.equals("url")) {
					scene7AssetUrl = value;
				} else {
					urlParams.append(key).append("=").append(value);
				}
			}
			urlParams.deleteCharAt(0);
		}

		if(scene7AssetUrl.contains(".fxg")) {
			urlParams = new StringBuilder("fmt=fxgraw");
		}

		fxgXML = HTTPHelper.getURL(scene7AssetUrl, "POST", null, null, UtilMisc.<String, Object>toMap("body", urlParams.toString()), null, false, EnvConstantsUtil.RESPONSE_XML, EnvConstantsUtil.RESPONSE_PLAIN);
		Document xml = EnvUtil.stringToXML(fxgXML);

		XPathFactory xpathFact = XPathFactory.newInstance();
		XPath xpath = xpathFact.newXPath();

		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("type", "xml");
		if(scene7AssetUrl.contains(".fxg")) {
			jsonResponse.put("height", (String) xpath.evaluate("/*/@viewHeight", xml, XPathConstants.STRING));
			jsonResponse.put("width", (String) xpath.evaluate("/*/@viewWidth", xml, XPathConstants.STRING));
		} else {
			jsonResponse.put("height", (String) xpath.evaluate("/prop-group/prop-group/property[@name='height']/@value", xml, XPathConstants.STRING));
			jsonResponse.put("width", (String) xpath.evaluate("/prop-group/prop-group/property[@name='width']/@value", xml, XPathConstants.STRING));
		}

		return jsonResponse;
	}

	/*
	 * Get the json data for an image
	 */
	public static String generateCustomFXGContent(Map<String, String> params, String type) throws Exception {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		docFactory.setNamespaceAware(false);
		docFactory.setValidating(false);

		Document doc = docBuilder.newDocument();
		Element graphic = doc.createElement("Graphic");
		graphic.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "http://ns.adobe.com/fxg/2008");
		graphic.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:s7", "http://ns.adobe.com/S7FXG/2008");
		doc.appendChild(graphic);

		if(type.equals("RichText")) {
			Element richText = doc.createElement("RichText");

			Iterator paramsIter = params.entrySet().iterator();
			while (paramsIter.hasNext()) {
				Map.Entry<String, String> pairs = (Map.Entry<String, String>) paramsIter.next();
				richText.setAttribute(pairs.getKey(), pairs.getValue());
			}

			graphic.appendChild(richText);

			Element content = doc.createElement("content");
			richText.appendChild(content);
			Element para = doc.createElement("p");
			content.appendChild(para);
			Element span = doc.createElement("span");
			span.setTextContent("Enter your text here");
			para.appendChild(span);
		} else if (type.equals("BitmapImage")) {
			Element group = doc.createElement("Group");
			Iterator paramsIter = params.entrySet().iterator();
			while (paramsIter.hasNext()) {
				Map.Entry<String, String> pairs = (Map.Entry<String, String>) paramsIter.next();
				if(pairs.getKey().equals("x") || pairs.getKey().equals("y") || pairs.getKey().equals("s7:referencePoint")) {
					group.setAttribute(pairs.getKey(), pairs.getValue());
				}
			}

			graphic.appendChild(group);
			Element bitmapImage = doc.createElement("BitmapImage");
			paramsIter = params.entrySet().iterator();
			while (paramsIter.hasNext()) {
				Map.Entry<String, String> pairs = (Map.Entry<String, String>) paramsIter.next();
				if(!(pairs.getKey()).equals("x") && !(pairs.getKey()).equals("y")) {
					bitmapImage.setAttribute(pairs.getKey(), pairs.getValue());
				}
			}

			group.appendChild(bitmapImage);
		}

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		DOMSource source = new DOMSource(doc);

		StringWriter writer = new StringWriter();
		transformer.transform(source, new StreamResult(writer));
		String output = writer.getBuffer().toString();

		return output;
	}

	/*
	 * Filter out products from list based on template attributes
	 */
	public static List<GenericValue> filterOutAttribute(List<GenericValue> attributes, List<GenericValue> products) {
		boolean removeReverse = false;
		GenericValue firstProduct = products.get(0);
		String productTypeId = (UtilValidate.isNotEmpty(firstProduct.getString("productTypeId"))) ? firstProduct.getString("productTypeId") : null;
		List<String> attributeList = EntityUtil.getFieldListFromEntityList(attributes, "attributeId", true);
		if(UtilValidate.isEmpty(attributeList) && UtilValidate.isNotEmpty(productTypeId)) {
			removeReverse = true;
			if(productTypeId.equals("PAPER")) {
				attributeList.add("FOLDED");
				attributeList.add("CARDSTOCK");
			} else {
				attributeList.add("WINDOW");
			}
		}
		Iterator iter = products.iterator();
		while(iter.hasNext()) {
			boolean remove = false;
			GenericValue product = (GenericValue) iter.next();
			for(String attr : attributeList) {
				if(removeReverse && UtilValidate.isNotEmpty(product.getString("productName")) && (product.getString("productName").toLowerCase()).contains(attr.toLowerCase())) {
					remove = true;
				} else if(!removeReverse && UtilValidate.isNotEmpty(product.getString("productName")) && !((product.getString("productName").toLowerCase()).contains(attr.toLowerCase()))) {
					remove = true;
				}
			}
			if(remove) {
				iter.remove();
			}
		}
		return products;
	}

	/*
	 * Filter out products from list based on template attributes
	 */
	public static List<GenericValue> filterOutProducts(List<GenericValue> products) {
		boolean removeReverse = false;
		GenericValue firstProduct = products.get(0);
		String productTypeId = (UtilValidate.isNotEmpty(firstProduct.getString("productTypeId"))) ? firstProduct.getString("productTypeId") : null;
		List<String> attributeList = getStaticAttributeList(products);
		if(UtilValidate.isNotEmpty(attributeList)) {
			Iterator iter = products.iterator();
			while(iter.hasNext()) {
				boolean remove = false;
				GenericValue product = (GenericValue) iter.next();
				for(String attr : attributeList) {
					if(UtilValidate.isNotEmpty(product.getString("productName")) && !product.getString("productName").toLowerCase().contains(attr.toLowerCase())) {
						remove = true;
					}
				}
				if(remove) {
					iter.remove();
				}
			}
		}
		return products;
	}

	/*
	 * Make specific exclusions for certain products
	 */
	public static List<String> getStaticAttributeList(List<GenericValue> products) {
		List<String> attributeList = new ArrayList<String>();
		List<String> productNames = EntityUtil.getFieldListFromEntityList(products, "productName", true);
		boolean has10Square = false;
		for(String productName : productNames) {
			if(productName.toLowerCase().contains("#10") && productName.toLowerCase().contains("square")) {
				has10Square = true;
			}
		}
		GenericValue firstProduct = products.get(0);
		String productTypeId = (UtilValidate.isNotEmpty(firstProduct.getString("productTypeId"))) ? firstProduct.getString("productTypeId") : null;
		if(UtilValidate.isNotEmpty(productTypeId)) {
			if(productTypeId.equals("ENVELOPE") && UtilValidate.isNotEmpty(firstProduct.getString("productName")) && has10Square) {
				attributeList.add("SQUARE");
			}
		}
		return attributeList;
	}

	/*
	 * Get all the other sides for a design
	 */
	public static Map<String, String> findOtherSideDesigns(Delegator delegator, String templateId, String virtualProductId, GenericValue template) throws GenericEntityException, SQLException, GenericDataSourceException {
		Map<String, String> otherSideDesigns = new HashMap<String, String>();
		String backDesignId = null;
		String insideLeftDesignId = null;
		String insideRightDesignId = null;
		String insideTopDesignId = null;
		String insideBottomDesignId = null;

		if(UtilValidate.isEmpty(template) && UtilValidate.isNotEmpty(templateId)) {
			template = delegator.findOne("Scene7Template", UtilMisc.toMap("scene7TemplateId", templateId), true);
		}

		if(UtilValidate.isNotEmpty(template)) {
			if(UtilValidate.isNotEmpty(template.getString("productTypeId")) && UtilValidate.isNotEmpty(template.getBigDecimal("height")) && UtilValidate.isNotEmpty(template.getBigDecimal("width"))) {
				String fxgNumericID = null;
				String fxgBaseID = (template.getString("scene7TemplateId").indexOf("_") != -1) ? template.getString("scene7TemplateId").substring(0, template.getString("scene7TemplateId").indexOf("_")) : template.getString("scene7TemplateId");
				Matcher matcher = Pattern.compile("^(\\d+)").matcher(template.getString("scene7TemplateId"));
				while(matcher.find()) {
					fxgNumericID = matcher.group(1);
				}

				if(UtilValidate.isNotEmpty(fxgNumericID)) {
					List<EntityCondition> conditions = new ArrayList<EntityCondition>();
					conditions.add(EntityCondition.makeCondition("scene7TemplateId", EntityOperator.NOT_EQUAL, template.getString("scene7TemplateId")));
					conditions.add(EntityCondition.makeCondition("scene7TemplateId", EntityOperator.LIKE, fxgNumericID + "%"));
					if(UtilValidate.isNotEmpty(template.getString("productTypeId"))) {
						conditions.add(EntityCondition.makeCondition("productTypeId", EntityOperator.EQUALS, template.getString("productTypeId")));
					}
					conditions.add(EntityCondition.makeCondition("width", EntityOperator.EQUALS, template.getBigDecimal("width")));
					conditions.add(EntityCondition.makeCondition("height", EntityOperator.EQUALS, template.getBigDecimal("height")));
					List<GenericValue> otherDesigns = delegator.findList("Scene7Template", EntityCondition.makeCondition(conditions, EntityOperator.AND), null, UtilMisc.toList("scene7TemplateId ASC"), null, true);

					if(UtilValidate.isEmpty(otherDesigns)) {
						conditions.clear();
						conditions.add(EntityCondition.makeCondition("scene7TemplateId", EntityOperator.NOT_EQUAL, template.getString("scene7TemplateId")));
						conditions.add(EntityCondition.makeCondition("scene7TemplateId", EntityOperator.LIKE, fxgNumericID + "%"));
						if(UtilValidate.isNotEmpty(template.getString("productTypeId"))) {
							conditions.add(EntityCondition.makeCondition("productTypeId", EntityOperator.EQUALS, template.getString("productTypeId")));
						}
						conditions.add(EntityCondition.makeCondition("width", EntityOperator.EQUALS, template.getBigDecimal("height")));
						conditions.add(EntityCondition.makeCondition("height", EntityOperator.EQUALS, template.getBigDecimal("width")));
						otherDesigns = delegator.findList("Scene7Template", EntityCondition.makeCondition(conditions, EntityOperator.AND), null, UtilMisc.toList("scene7TemplateId ASC"), null, true);
					}

					if(UtilValidate.isNotEmpty(otherDesigns)) {
						for(GenericValue fxg : otherDesigns) {
							if(UtilValidate.isEmpty(backDesignId) && (fxg.getString("scene7TemplateId").toLowerCase().equals(template.getString("scene7TemplateId") + "_back") || fxg.getString("scene7TemplateId").toLowerCase().equals(fxgNumericID + "b"))) {
								backDesignId = fxg.getString("scene7TemplateId");
							}
							if(UtilValidate.isEmpty(insideLeftDesignId) && fxg.getString("scene7TemplateId").toLowerCase().equals(template.getString("scene7TemplateId") + "_insideL")) {
								insideLeftDesignId = fxg.getString("scene7TemplateId");
							}
							if(UtilValidate.isEmpty(insideRightDesignId) && fxg.getString("scene7TemplateId").toLowerCase().equals(template.getString("scene7TemplateId") + "_insideR")) {
								insideRightDesignId = fxg.getString("scene7TemplateId");
							}
							if(UtilValidate.isEmpty(insideTopDesignId) && fxg.getString("scene7TemplateId").toLowerCase().equals(template.getString("scene7TemplateId") + "_insideT")) {
								insideTopDesignId = fxg.getString("scene7TemplateId");
							}
							if(UtilValidate.isEmpty(insideBottomDesignId) && fxg.getString("scene7TemplateId").toLowerCase().equals(template.getString("scene7TemplateId") + "_insideB")) {
								insideBottomDesignId = fxg.getString("scene7TemplateId");
							}
						}

						if(UtilValidate.isEmpty(backDesignId) || UtilValidate.isEmpty(insideLeftDesignId) || UtilValidate.isEmpty(insideRightDesignId) || UtilValidate.isEmpty(insideTopDesignId) || UtilValidate.isEmpty(insideBottomDesignId)) {
							for(GenericValue fxg : otherDesigns) {
								if(UtilValidate.isEmpty(backDesignId) && fxg.getString("scene7TemplateId").toLowerCase().contains(fxgBaseID) && fxg.getString("scene7TemplateId").toLowerCase().contains("back")) {
									backDesignId = fxg.getString("scene7TemplateId");
								} else if(UtilValidate.isEmpty(backDesignId) && fxg.getString("scene7TemplateId").toLowerCase().contains("back")) {
									backDesignId = fxg.getString("scene7TemplateId");
								} else if(UtilValidate.isEmpty(backDesignId) && fxgNumericID.length() == 4 && (fxg.getString("scene7TemplateId").startsWith("3") || fxg.getString("scene7TemplateId").startsWith("8") || fxg.getString("scene7TemplateId").startsWith("9")) && fxg.getString("scene7TemplateId").toLowerCase().contains("b")) {
									backDesignId = fxg.getString("scene7TemplateId");
								} else if(UtilValidate.isEmpty(backDesignId) && fxgNumericID.length() == 4 && fxg.getString("scene7TemplateId").startsWith("1") && fxg.getString("scene7TemplateId").toLowerCase().contains("d")) {
									backDesignId = fxg.getString("scene7TemplateId");
								}
								if(UtilValidate.isEmpty(insideLeftDesignId) && fxg.getString("scene7TemplateId").toLowerCase().contains(fxgBaseID) && fxg.getString("scene7TemplateId").toLowerCase().contains("_insideL")) {
									insideLeftDesignId = fxg.getString("scene7TemplateId");
								}
								if(UtilValidate.isEmpty(insideRightDesignId) && fxg.getString("scene7TemplateId").toLowerCase().contains(fxgBaseID) && fxg.getString("scene7TemplateId").toLowerCase().contains("_insideR")) {
									insideRightDesignId = fxg.getString("scene7TemplateId");
								}
								if(UtilValidate.isEmpty(insideTopDesignId) && fxg.getString("scene7TemplateId").toLowerCase().contains(fxgBaseID) && fxg.getString("scene7TemplateId").toLowerCase().contains("_insideT")) {
									insideTopDesignId = fxg.getString("scene7TemplateId");
								}
								if(UtilValidate.isEmpty(insideBottomDesignId) && fxg.getString("scene7TemplateId").toLowerCase().contains(fxgBaseID) && fxg.getString("scene7TemplateId").toLowerCase().contains("_insideB")) {
									insideBottomDesignId = fxg.getString("scene7TemplateId");
								}
							}
						}
					}
				}

				//if we still cant find a back lets look in the prodassoc for the size and is a 8000 series back

				SQLProcessor du = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));
				String sqlCommand = null;
				try {
					if(UtilValidate.isEmpty(backDesignId)) {
						du = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));
						if(UtilValidate.isNotEmpty(virtualProductId)) {
							sqlCommand = "select s7t.scene7_template_id, s7pa.product_id from scene7_template s7t inner join scene7_prod_assoc s7pa on s7t.scene7_template_id = s7pa.scene7_template_id where ((s7t.width = '" + template.getDouble("width").toString() + "' and s7t.height = '" + template.getDouble("height").toString() + "') or (s7t.width = '" + template.getDouble("height").toString() + "' and s7t.height = '" + template.getDouble("width").toString() + "')) and s7pa.template_assoc_type_id = 'TEMPLATE_BACK' and s7pa.product_id = '"  + virtualProductId + "'";
						} else {
							sqlCommand = "select s7t.scene7_template_id, s7pa.product_id from scene7_template s7t inner join scene7_prod_assoc s7pa on s7t.scene7_template_id = s7pa.scene7_template_id where ((s7t.width = '" + template.getDouble("width").toString() + "' and s7t.height = '" + template.getDouble("height").toString() + "') or (s7t.width = '" + template.getDouble("height").toString() + "' and s7t.height = '" + template.getDouble("width").toString() + "')) and s7pa.template_assoc_type_id = 'TEMPLATE_BACK'";
						}
						ResultSet rs = null;
						rs = du.executeQuery(sqlCommand);
						if(rs != null) {
							while(rs.next()) {
								backDesignId = rs.getString(1);
								break;
							}
						}
					}
					du.close();

					if(UtilValidate.isEmpty(insideLeftDesignId)) {
						if(UtilValidate.isNotEmpty(virtualProductId)) {
							sqlCommand = "select s7t.scene7_template_id, s7pa.product_id from scene7_template s7t inner join scene7_prod_assoc s7pa on s7t.scene7_template_id = s7pa.scene7_template_id where ((s7t.width = '" + template.getDouble("width").toString() + "' and s7t.height = '" + template.getDouble("height").toString() + "') or (s7t.width = '" + template.getDouble("height").toString() + "' and s7t.height = '" + template.getDouble("width").toString() + "')) and s7pa.template_assoc_type_id = 'TEMPLATE_LEFT' and s7pa.product_id = '"  + virtualProductId + "'";
						} else {
							sqlCommand = "select s7t.scene7_template_id, s7pa.product_id from scene7_template s7t inner join scene7_prod_assoc s7pa on s7t.scene7_template_id = s7pa.scene7_template_id where ((s7t.width = '" + template.getDouble("width").toString() + "' and s7t.height = '" + template.getDouble("height").toString() + "') or (s7t.width = '" + template.getDouble("height").toString() + "' and s7t.height = '" + template.getDouble("width").toString() + "')) and s7pa.template_assoc_type_id = 'TEMPLATE_LEFT'";
						}
						ResultSet rs = null;
						rs = du.executeQuery(sqlCommand);
						if(rs != null) {
							while(rs.next()) {
								insideLeftDesignId = rs.getString(1);
								break;
							}
						}
					}
					du.close();

					if(UtilValidate.isEmpty(insideRightDesignId)) {
						if(UtilValidate.isNotEmpty(virtualProductId)) {
							sqlCommand = "select s7t.scene7_template_id, s7pa.product_id from scene7_template s7t inner join scene7_prod_assoc s7pa on s7t.scene7_template_id = s7pa.scene7_template_id where ((s7t.width = '" + template.getDouble("width").toString() + "' and s7t.height = '" + template.getDouble("height").toString() + "') or (s7t.width = '" + template.getDouble("height").toString() + "' and s7t.height = '" + template.getDouble("width").toString() + "')) and s7pa.template_assoc_type_id = 'TEMPLATE_RIGHT' and s7pa.product_id = '"  + virtualProductId + "'";
						} else {
							sqlCommand = "select s7t.scene7_template_id, s7pa.product_id from scene7_template s7t inner join scene7_prod_assoc s7pa on s7t.scene7_template_id = s7pa.scene7_template_id where ((s7t.width = '" + template.getDouble("width").toString() + "' and s7t.height = '" + template.getDouble("height").toString() + "') or (s7t.width = '" + template.getDouble("height").toString() + "' and s7t.height = '" + template.getDouble("width").toString() + "')) and s7pa.template_assoc_type_id = 'TEMPLATE_RIGHT'";
						}
						ResultSet rs = null;
						rs = du.executeQuery(sqlCommand);
						if(rs != null) {
							while(rs.next()) {
								insideRightDesignId = rs.getString(1);
								break;
							}
						}
					}
					du.close();

					if(UtilValidate.isEmpty(insideTopDesignId)) {
						if(UtilValidate.isNotEmpty(virtualProductId)) {
							sqlCommand = "select s7t.scene7_template_id, s7pa.product_id from scene7_template s7t inner join scene7_prod_assoc s7pa on s7t.scene7_template_id = s7pa.scene7_template_id where ((s7t.width = '" + template.getDouble("width").toString() + "' and s7t.height = '" + template.getDouble("height").toString() + "') or (s7t.width = '" + template.getDouble("height").toString() + "' and s7t.height = '" + template.getDouble("width").toString() + "')) and s7pa.template_assoc_type_id = 'TEMPLATE_TOP' and s7pa.product_id = '"  + virtualProductId + "'";
						} else {
							sqlCommand = "select s7t.scene7_template_id, s7pa.product_id from scene7_template s7t inner join scene7_prod_assoc s7pa on s7t.scene7_template_id = s7pa.scene7_template_id where ((s7t.width = '" + template.getDouble("width").toString() + "' and s7t.height = '" + template.getDouble("height").toString() + "') or (s7t.width = '" + template.getDouble("height").toString() + "' and s7t.height = '" + template.getDouble("width").toString() + "')) and s7pa.template_assoc_type_id = 'TEMPLATE_TOP'";
						}
						ResultSet rs = null;
						rs = du.executeQuery(sqlCommand);
						if(rs != null) {
							while(rs.next()) {
								insideTopDesignId = rs.getString(1);
								break;
							}
						}
					}
					du.close();

					if(UtilValidate.isEmpty(insideBottomDesignId)) {
						if(UtilValidate.isNotEmpty(virtualProductId)) {
							sqlCommand = "select s7t.scene7_template_id, s7pa.product_id from scene7_template s7t inner join scene7_prod_assoc s7pa on s7t.scene7_template_id = s7pa.scene7_template_id where ((s7t.width = '" + template.getDouble("width").toString() + "' and s7t.height = '" + template.getDouble("height").toString() + "') or (s7t.width = '" + template.getDouble("height").toString() + "' and s7t.height = '" + template.getDouble("width").toString() + "')) and s7pa.template_assoc_type_id = 'TEMPLATE_BOTTOM' and s7pa.product_id = '"  + virtualProductId + "'";
						} else {
							sqlCommand = "select s7t.scene7_template_id, s7pa.product_id from scene7_template s7t inner join scene7_prod_assoc s7pa on s7t.scene7_template_id = s7pa.scene7_template_id where ((s7t.width = '" + template.getDouble("width").toString() + "' and s7t.height = '" + template.getDouble("height").toString() + "') or (s7t.width = '" + template.getDouble("height").toString() + "' and s7t.height = '" + template.getDouble("width").toString() + "')) and s7pa.template_assoc_type_id = 'TEMPLATE_BOTTOM'";
						}
						ResultSet rs = null;
						rs = du.executeQuery(sqlCommand);
						if(rs != null) {
							while(rs.next()) {
								insideBottomDesignId = rs.getString(1);
								break;
							}
						}
					}
					du.close();
				} finally {
					if(du != null) {
						du.close();
					}
				}
			}
		} else {
			return otherSideDesigns;
		}

		otherSideDesigns.put("back", backDesignId);
		otherSideDesigns.put("left", insideLeftDesignId);
		otherSideDesigns.put("right", insideRightDesignId);
		otherSideDesigns.put("top", insideTopDesignId);
		otherSideDesigns.put("bottom", insideBottomDesignId);

		return otherSideDesigns;
	}

	/*
	 * Format URL for processing, remove bg, format
	 */
	public static String formatURL(String url, boolean removeBG, String format, int width, int height) {
		if(UtilValidate.isNotEmpty(url)) {
			url = url.replaceAll("(?:wid=(?:[0-9]+)|hei=(?:[0-9]+))&?", ""); //remove and wid and height
			if(removeBG) {
				url = url.replaceAll("(?:setElement.COLOR_bgcolor(?:[0-9a-zA-Z\\_])*=(?:%3Cfill[^&]*fill%3E|%3Cstroke.*stroke%3E))&?", "");
			}
			if(UtilValidate.isNotEmpty(format)) {
				url = url.replaceAll("fmt=.*?(?:&|$)", "");
				url = url + "&fmt=" + format;
			}
			url = url + "&wid=" + width + "&hei=" + height;
			url = url + "&time=" + UtilDateTime.nowAsString(); //bypass cache
		}
		return url;
	}

	/*
	 * Get Scene7Design row
	 */
	public static GenericValue getScene7Design(Delegator delegator, String scene7DesignId) throws GenericEntityException {
		if(UtilValidate.isNotEmpty(scene7DesignId)) {
			return delegator.findOne("Scene7Design", UtilMisc.toMap("scene7DesignId", scene7DesignId), false);
		}

		return null;
	}

	/*
	 * Get Scene7Design project first product
	 */
	public static String getMainProjectDesign(Delegator delegator, String scene7DesignId) throws GenericEntityException {
		String designId = null;

		if(UtilValidate.isNotEmpty(scene7DesignId)) {
			GenericValue s7D = getScene7Design(delegator, scene7DesignId);
			if(s7D != null) {
				HashMap<String, Object> jsonMap = new Gson().fromJson(s7D.getString("data"), HashMap.class);
				List productList = ((List) ((Map<String, Object>) jsonMap.get("settings")).get("product"));
				if(UtilValidate.isNotEmpty(productList)) {
					Map<String, Object> product = (Map<String, Object>) productList.get(0);
					if(UtilValidate.isNotEmpty(product.get("designId"))) {
						designId = (String) product.get("designId");
					}
				}
			}
		}

		return designId;
	}

	/*
	 Get Scene7TemplateId from a saved design
	 */
	public static String getScene7TemplateId(HashMap<String, Object> scene7Design) {
		if(scene7Design.get("designs") instanceof Map){
			Map<String, Object> allSides = (Map<String, Object>) scene7Design.get("designs");
			Iterator iter = allSides.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry pairs = (Map.Entry) iter.next();
				Map<String, Object> sideMap = (Map<String, Object>) pairs.getValue();

				if (UtilValidate.isEmpty(sideMap.get("scene7url"))) {
					continue;
				}

				if (UtilValidate.isNotEmpty(sideMap.get("templateId"))) {
					return (String) sideMap.get("templateId");
				}
			}
		}

		return null;
	}

	/*
	 * Get all the urls for a specific design request organized by side and url
	 */
	public static Map<String, List> generateScene7URLs(Delegator delegator, HashMap<String, Object> scene7Design, boolean removeBG, String format, boolean onlyFirstPage, int totalPages) throws GenericEntityException {
		Map<String, List> urlMap = new HashMap<String, List>();

		GenericValue s7Template = null;
		//loop through all sides
		Map<String, Object> allSides = (Map<String, Object>) scene7Design.get("designs");

		if(allSides == null) {
			allSides = new HashMap<>();
		}

		Iterator iter = allSides.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry pairs = (Map.Entry) iter.next();
			String side = (String) pairs.getKey();
			Map<String, Object> sideMap = (Map<String, Object>) pairs.getValue();

			if(UtilValidate.isEmpty(sideMap.get("scene7url"))) {
				continue;
			}

			s7Template = delegator.findOne("Scene7Template", UtilMisc.toMap("scene7TemplateId", sideMap.get("templateId")), true);
			if(UtilValidate.isEmpty(sideMap.get("addressBookId")) || onlyFirstPage) {
				urlMap.put(side, UtilMisc.toList(formatURL((String) sideMap.get("scene7url"), removeBG, format, s7Template.getBigDecimal("width").multiply(new BigDecimal("72")).intValue(), s7Template.getBigDecimal("height").multiply(new BigDecimal("72")).intValue())));
			} else {
				urlMap.put(side, generateScene7AddressingURLs(delegator, getAddressElement((List<Map>) sideMap.get("elements")), (String) sideMap.get("cleanAddressUrl"), (String) sideMap.get("addressBookId"), removeBG, format, totalPages, s7Template.getBigDecimal("width").multiply(new BigDecimal("72")).intValue(), s7Template.getBigDecimal("height").multiply(new BigDecimal("72")).intValue()));
			}
		}

		return urlMap;
	}

	/*
	 * Generate a list of scene7 urls for an addressing design
	 */
	public static List<String> generateScene7AddressingURLs(Delegator delegator, Map<String, Object> addressElement, String cleanAddressUrl, String variableDataGroupId, boolean removeBG, String format, int totalPages, int width, int height) throws GenericEntityException {
		List<String> urls = new ArrayList<String>();
		if(UtilValidate.isNotEmpty(variableDataGroupId)) {
			Pattern p = Pattern.compile("^(.*%3CRichText[a-z|A-Z|0-9|(?:%22)|\\s|\\.]*RichText_addressing[a-z|A-Z|0-9|\\-(?:%22)|\\s|\\.]*%3E)(%3C%2FRichText%3E.*)$");
			Matcher m = p.matcher(cleanAddressUrl);

			List<GenericValue> addressList = delegator.findByAnd("VariableData", UtilMisc.toMap("variableDataGroupId", variableDataGroupId), null, false);
			for(GenericValue address : addressList) {
				String[] rowData = new Gson().fromJson(address.getString("data"), String[].class);

				String innerAddressText = encodeURIComponent(createInnerRichText(addressElement, rowData));
				String url = null;
				if(m.matches()) {
					url = m.group(1) + innerAddressText + m.group(2);
					urls.add(formatURL(url, removeBG, format, width, height));
				} else {
					Debug.logError("REGEX MATCHER FAILED", module);
				}
			}

			//the pdf needs to include the remaining printed data if its available
			if(UtilValidate.isNotEmpty(addressList)) {
				int remainder = totalPages - addressList.size();
				for(int i = 1; i <= remainder; i++) {
					urls.add(formatURL(cleanAddressUrl, removeBG, format, width, height));
				}
			}
		}

		return urls;
	}

	/*
	 * Get PDF version of Scene7 Design(s)
	 */
	public static String saveScene7File(List<String> urls, String fileUploadDir, String format) throws IOException {
		String fileUploadPath = FileHelper.getUploadPath(fileUploadDir);
		File file = null;

		List<Map> processedURLs = HTTPHelper.splitURL(urls);
		//if pdf is a multipage pdf we generate using PDF Merge
		if(urls.size() > 1) {
			PDFMergerUtility mergePDF = new PDFMergerUtility();
			file = FileHelper.makeFile(null, fileUploadPath, format); //name of final pdf to be created

			List<File> tempPDFBlocks = new ArrayList<>(); //list of temporary files to be merged

			List<InputStream> sourcePDFs = new ArrayList<InputStream>();
			int iterations = 1;
			for(Map urlMap : processedURLs) {
				boolean success = false;
				int attempts = 0;
				do {
					OutputStreamWriter writer = null;
					try {
						String base = (String) urlMap.get("url");
						String params = (String) urlMap.get("body");

						//Debug.logInfo("Processing: " + (String) urlMap.get("url") + "?" + (String) urlMap.get("body"), module);

						URL scene7Page = new URL(base);
						URLConnection connection = scene7Page.openConnection();

						//if the url is too long we will be passing it via POST
						if(params != null) {
							connection.setDoOutput(true);
							writer = new OutputStreamWriter(connection.getOutputStream());
							writer.write(params);
							writer.flush();
						}

						sourcePDFs.add(connection.getInputStream());
						success = true;
					} catch (IOException e) {
						Debug.logError(e, urlMap.get("url") + "?" + urlMap.get("body"), module);
					} finally {
						if(writer != null) {
							try {
								writer.close();
							} catch (Exception wE) {
								Debug.logError(wE, "Error closing writer.", module);
							}
						}
					}
				} while (!success && ++attempts < 3);

				if(!success) {
					throw new IOException("An error occurred while generating the scene7File");
				}

				if(iterations == urls.size() || iterations%250 == 0) {
					File newFile = FileHelper.makeFile(null, fileUploadPath, format);
					PDFMergerUtility tempPDF = new PDFMergerUtility();
					tempPDF.addSources(sourcePDFs);
					tempPDF.setDestinationFileName(newFile.getAbsolutePath());
					tempPDF.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
					tempPDFBlocks.add(newFile);
					sourcePDFs.clear();
				}
				iterations++;
			}

			//loop through all created files and merge them
			if(tempPDFBlocks.size() > 1) {
				for(File pdf : tempPDFBlocks) {
					mergePDF.addSource(pdf);
				}
				mergePDF.setDestinationFileName(file.getAbsolutePath());
				mergePDF.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());

				//loop through the files and remove the temporary ones
				for(File pdf : tempPDFBlocks) {
					pdf.delete();
				}
			} else {
				file = tempPDFBlocks.get(0);
			}
		} else if(urls.size() == 1) { //else generate a 1 page pdf
			file = FileHelper.makeFile(null, fileUploadPath, format);
			Map urlMap = (Map) processedURLs.get(0);
			String base = (String) urlMap.get("url");
			String params = (String) urlMap.get("body");

			URL scene7Page = new URL(base);
			URLConnection connection = scene7Page.openConnection();

			//if the url is too long we will be passing it via POST
			if(params != null) {
				connection.setDoOutput(true);
				OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
				writer.write(params);
				writer.flush();
			}

			InputStream is = connection.getInputStream();
			OutputStream os = new FileOutputStream(file.getAbsolutePath());

			byte[] b = new byte[2048];
			int length;

			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}

			is.close();
			os.close();
		}

		return fileUploadPath.replace(EnvConstantsUtil.OFBIZ_HOME, "") + "/" + file.getName();
	}

	/*
	 * Method to generate scene7 files, this method should always get wrapped in a TRANSACTION unless called from a service.
	 *
	 * @param delegator         Delegator class
	 * @param scene7DesignId    Scene7Design row id
	 * @param orderId           Order id
	 * @param orderIdItemSeqId  Order item id
	 * @param previewFiles      Preview images
	 * @param proofFile         Proof PDF
	 * @param printFiles        PDFs used for printing
	 */
	public static Map<String, String> generateScene7Files(Delegator delegator, String scene7DesignId, String orderId, String orderItemSeqId, boolean previewFiles, boolean proofFile, boolean printFiles) throws GenericEntityException, IOException {
		Map<String, String> contentData = new HashMap<String, String>();

		GenericValue scene7Design = getScene7Design(delegator, scene7DesignId);
		GenericValue orderItem = delegator.findOne("OrderItem", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId), false);
		if(scene7Design != null) {
			HashMap<String, Object> jsonMap = new Gson().fromJson(scene7Design.getString("data"), HashMap.class);

			if(previewFiles) {
				Map<String, List> urlMap = generateScene7URLs(delegator, jsonMap, false, "png", true, orderItem.getBigDecimal("quantity").intValue());
				if(UtilValidate.isNotEmpty(urlMap.get("0"))) {
					OrderHelper.discOrderItemContent(delegator, orderId, orderItemSeqId, "OIACPRP_FRONT");
					contentData.put("OIACPRP_FRONT", OrderHelper.setOrderItemContent(delegator, orderId, orderItemSeqId, "OIACPRP_FRONT", UtilMisc.toMap("path", saveScene7File((List) urlMap.get("0"), null, "png"), "name", orderId + "_" + orderItemSeqId + "_FRONT.png")).getString("orderItemContentId"));
				}
				if(UtilValidate.isNotEmpty(urlMap.get("1"))) {
					OrderHelper.discOrderItemContent(delegator, orderId, orderItemSeqId, "OIACPRP_BACK");
					contentData.put("OIACPRP_BACK", OrderHelper.setOrderItemContent(delegator, orderId, orderItemSeqId, "OIACPRP_BACK", UtilMisc.toMap("path", saveScene7File((List) urlMap.get("1"), null, "png"), "name", orderId + "_" + orderItemSeqId + "_BACK.png")).getString("orderItemContentId"));
				}
			}

			if(proofFile) {
				Map<String, List> urlMap = generateScene7URLs(delegator, jsonMap, false, "pdf", false, orderItem.getBigDecimal("quantity").intValue());
				//merge the front and back files
				List<String> allURLs = new ArrayList<String>();
				if(UtilValidate.isNotEmpty(urlMap.get("0"))) {
					allURLs.addAll((List) urlMap.get("0"));
				}
				if(UtilValidate.isNotEmpty(urlMap.get("1"))) {
					allURLs.addAll((List) urlMap.get("1"));
				}
				OrderHelper.discOrderItemContent(delegator, orderId, orderItemSeqId, "OIACPRP_PDF");
				contentData.put("OIACPRP_PDF", OrderHelper.setOrderItemContent(delegator, orderId, orderItemSeqId, "OIACPRP_PDF", UtilMisc.toMap("path", saveScene7File(allURLs, null, "pdf"), "name", orderId + "_" + orderItemSeqId + "_PROOF.pdf")).getString("orderItemContentId"));
			}

			if(printFiles) {
				Map<String, List> urlMap = generateScene7URLs(delegator, jsonMap, true, "pdf", false, orderItem.getBigDecimal("quantity").intValue());
				if(UtilValidate.isNotEmpty(urlMap.get("0"))) {
					OrderHelper.discOrderItemContent(delegator, orderId, orderItemSeqId, "OIACPRP_SC7_FRNT_PDF");
					contentData.put("OIACPRP_SC7_FRNT_PDF", OrderHelper.setOrderItemContent(delegator, orderId, orderItemSeqId, "OIACPRP_SC7_FRNT_PDF", UtilMisc.toMap("path", saveScene7File((List) urlMap.get("0"), null, "pdf"), "name", orderId + "_" + orderItemSeqId + "_FRONT.pdf")).getString("orderItemContentId"));
				}
				if(UtilValidate.isNotEmpty(urlMap.get("1"))) {
					OrderHelper.discOrderItemContent(delegator, orderId, orderItemSeqId, "OIACPRP_SC7_BACK_PDF");
					contentData.put("OIACPRP_SC7_BACK_PDF", OrderHelper.setOrderItemContent(delegator, orderId, orderItemSeqId, "OIACPRP_SC7_BACK_PDF", UtilMisc.toMap("path", saveScene7File((List) urlMap.get("1"), null, "pdf"), "name", orderId + "_" + orderItemSeqId + "_BACK.pdf")).getString("orderItemContentId"));
				}
			}

		}

		return contentData;
	}

	/*
	 * Security check to see if the design belongs to the user
	 */
	public static boolean isDesignOwner(HttpServletRequest request, GenericValue userLogin, GenericValue scene7Design) {
		if(request != null && request.getSession().getId() != null && UtilValidate.isNotEmpty(scene7Design.get("sessionId")) && (request.getSession().getId()).equals(scene7Design.getString("sessionId"))) {
			return true;
		} else if(userLogin != null && UtilValidate.isNotEmpty(userLogin.get("partyId")) && scene7Design != null && UtilValidate.isNotEmpty(scene7Design.get("partyId"))) {
			if(userLogin.getString("partyId").equals(scene7Design.getString("partyId"))) {
				return true;
			} else {
				return false;
			}
		} else if(scene7Design != null && UtilValidate.isEmpty(scene7Design.get("partyId"))) {
			return true; //if the design has no partyId in it, then it was saved anonymously
		} else {
			return false;
		}
	}

	/*
	 * Get address element from list
	 */
	public static Map<String, Object> getAddressElement(List<Map> elements) {
		if(UtilValidate.isNotEmpty(elements)) {
			Iterator iter = elements.iterator();
			while(iter.hasNext()) {
				Map<String, Object> element = (Map<String, Object>) iter.next();
				if(!((Boolean) element.get("deleted")) && ((String) element.get("id")).equals("RichText_addressing")) {
					return element;
				}
			}
		}

		return null;
	}

	/**
	 * Create the inner richtext for a textbox, this is used for addressing
	 */
	private static String createInnerRichText(Map<String, Object> addressElement, String[] address) {
		StringBuilder scene7ValidTextElement = new StringBuilder("<content>");
		String innerParams = createInnerRichTextParams(addressElement);

		if(UtilValidate.isNotEmpty(address[0])) {
			scene7ValidTextElement.append("<p ").append(innerParams).append(">");
			scene7ValidTextElement.append("<span>").append(charEncodingForScene7(address[0])).append("</span>").append("</p>");
		}
		if(UtilValidate.isNotEmpty(address[1])) {
			scene7ValidTextElement.append("<p ").append(innerParams).append(">");
			scene7ValidTextElement.append("<span>").append(charEncodingForScene7(address[1])).append("</span>").append("</p>");
		}
		if(UtilValidate.isNotEmpty(address[2])) {
			scene7ValidTextElement.append("<p ").append(innerParams).append(">");
			scene7ValidTextElement.append("<span>").append(charEncodingForScene7(address[2])).append("</span>").append("</p>");
		}
		if(UtilValidate.isNotEmpty(address[3])) {
			scene7ValidTextElement.append("<p ").append(innerParams).append(">");
			scene7ValidTextElement.append("<span>").append(charEncodingForScene7(address[3])).append("</span>").append("</p>");
		}
		if(UtilValidate.isNotEmpty(address[4]) || UtilValidate.isNotEmpty(address[5]) || UtilValidate.isNotEmpty(address[6])) {
			scene7ValidTextElement.append("<p ").append(innerParams).append(">");
			scene7ValidTextElement.append("<span>");
			if(UtilValidate.isNotEmpty(address[4])) {
				scene7ValidTextElement.append(charEncodingForScene7(address[4]));
			}
			if(UtilValidate.isNotEmpty(address[5])) {
				scene7ValidTextElement.append(", ").append(charEncodingForScene7(address[5]));
			}
			if(UtilValidate.isNotEmpty(address[6])) {
				scene7ValidTextElement.append("  ").append(charEncodingForScene7(address[6]));
			}
			scene7ValidTextElement.append("</span>").append("</p>");
		}
		if(UtilValidate.isNotEmpty(address[7])) {
			scene7ValidTextElement.append("<p ").append(innerParams).append(">");
			scene7ValidTextElement.append("<span>").append(charEncodingForScene7(address[7])).append("</span>").append("</p>");
		}

		scene7ValidTextElement.append("</content>");
		return scene7ValidTextElement.toString();
	}

	/**
	 * Create the inner params
	 */
	private static String createInnerRichTextParams(Map<String, Object> addressElement) {
		StringBuffer string = new StringBuffer("");
		if(UtilValidate.isNotEmpty(addressElement.get("fontSize"))) {
			if(addressElement.get("fontSize") instanceof String) {
				string.append(" fontSize='").append((String) addressElement.get("fontSize")).append("'").append(" s7:maxFontSize='").append((String) addressElement.get("fontSize")).append("'");
			} else if(addressElement.get("fontSize") instanceof Double) {
				string.append(" fontSize='").append(((Double) addressElement.get("fontSize")).toString()).append("'").append(" s7:maxFontSize='").append(((Double) addressElement.get("fontSize")).toString()).append("'");
			}
		}
		if(UtilValidate.isNotEmpty((String) addressElement.get("textAlign"))) {
			string.append(" textAlign='").append((String) addressElement.get("textAlign")).append("'");
		}
		if(UtilValidate.isNotEmpty((String) addressElement.get("fontFamily"))) {
			string.append(" fontFamily='").append((String) addressElement.get("fontFamily")).append("'");
		}
		if(UtilValidate.isNotEmpty(addressElement.get("lineHeight"))) {
			if(addressElement.get("lineHeight") instanceof String) {
				string.append(" lineHeight='").append((String) addressElement.get("lineHeight")).append("'");
			} else if(addressElement.get("lineHeight") instanceof Double) {
				string.append(" lineHeight='").append(((Double) addressElement.get("lineHeight")).toString()).append("'");
			}
		}
		if(UtilValidate.isNotEmpty(addressElement.get("tracking"))) {
			if(addressElement.get("tracking") instanceof String) {
				string.append(" trackingRight='").append((String) addressElement.get("tracking")).append("%25'");
			} else if(addressElement.get("tracking") instanceof Double) {
				string.append(" trackingRight='").append(((Double) addressElement.get("tracking")).toString()).append("%25'");
			}
		}
		if(UtilValidate.isNotEmpty((String) addressElement.get("textDecoration"))) {
			string.append(" textDecoration='").append((String) addressElement.get("textDecoration")).append("'");
		}
		if(UtilValidate.isNotEmpty((String) addressElement.get("color"))) {
			string.append(" color='").append((String) addressElement.get("color")).append("'");
		}
		return string.toString();
	}

	/**
	 * Create the inner params
	 */
	private static String charEncodingForScene7(String line) {
		return line.replaceAll("’", "'")
					.replaceAll("&", "&amp;")
					.replaceAll("<", "&lt;")
					.replaceAll(">", "&gt;")
					.replaceAll("\"", "&quot;")
					.replaceAll("'", "&apos;")
					.replaceAll("%", "&#37;")
					.replaceAll("\\+", "&#43;");
	}

	/**
   * Encodes the passed String as UTF-8 using an algorithm that's compatible
   * with JavaScript's <code>encodeURIComponent</code> function. Returns
   * <code>null</code> if the String is <code>null</code>.
   *
   * @param s The String to be encoded
   * @return the encoded String
   */
	private static String encodeURIComponent(String s) {
		String result = null;
		try {
			result = URLEncoder.encode(s, "UTF-8")
				 .replaceAll("\\+", "%20")
				 .replaceAll("\\%21", "!")
				 .replaceAll("\\%27", "'")
				 .replaceAll("\\%28", "(")
				 .replaceAll("\\%29", ")")
				 .replaceAll("\\%7E", "~");
		} catch (UnsupportedEncodingException e) {
			result = s;
		}
		return result;
	}

	/*
	 * Get UTC Token
	 */
	public static String getUGCToken(String imageType)  throws UnsupportedEncodingException, MalformedURLException, IOException, Exception {
		String url = ((imageType.equals("vector")) ? APP_VECTORTOKEN : APP_IMGTOKEN) + "&shared_secret=" + APP_KEY;
		String response = HTTPHelper.getURL(url, "POST", null, null, null, null, false, EnvConstantsUtil.RESPONSE_XML, EnvConstantsUtil.RESPONSE_HTML);

		Document xml = EnvUtil.stringToXML(response);
		NodeList returnNode = xml.getElementsByTagName("uploadtoken");
		String token = returnNode.item(0).getChildNodes().item(0).getNodeValue();

		return token;
	}

	/*
	 * Upload file to scene7 ugc
	 */
	public static String uploadUGCFile(String imageType, String fileExt, Map<String, File> fileList) throws UnsupportedEncodingException, MalformedURLException, IOException, Exception {
		StringBuffer url = new StringBuffer();
		url.append((imageType.equals("vector")) ? APP_VECTORURL : APP_IMGURL).append("&company_name=").append(APP_COMPANY).append("&file_limit=20480000").append("&file_exts=").append(fileExt).append("&upload_token=").append(getUGCToken(imageType));

		boolean isSuccessful = false;
		int tries = 1;
		String path = null;
		while(!isSuccessful && tries <= 2) {
			try {
				String response = HTTPHelper.getURL(url.toString(), "POST", null, null, null, fileList, false, EnvConstantsUtil.RESPONSE_XML, EnvConstantsUtil.RESPONSE_BINARY);
				if(UtilValidate.isNotEmpty(response)) {
					Document xml = EnvUtil.stringToXML(response);
					NodeList returnNode = xml.getElementsByTagName("path");
					path = returnNode.item(0).getChildNodes().item(0).getNodeValue();
					if(UtilValidate.isNotEmpty(path)) {
						isSuccessful = true;
					}
				}
			} catch (UnsupportedEncodingException e) {
				Debug.logError("Error while trying to send UGC file to S7. " + e + " : " + e.getMessage(), module);
			} catch (MalformedURLException e) {
				Debug.logError("Error while trying to send UGC file to S7. " + e + " : " + e.getMessage(), module);
			} catch (IOException e) {
				Debug.logError("Error while trying to send UGC file to S7. " + e + " : " + e.getMessage(), module);
			} catch (Exception e) {
				Debug.logError("Error while trying to send UGC file to S7. " + e + " : " + e.getMessage(), module);
			}
			tries++;
		}

		return path;
	}

	/**
	 * Converts an image to another format
	 *
	 * @param inputImagePath Path of the source image
	 * @param outputImagePath Path of the destination image
	 * @return true if successful, false otherwise
	 * @throws Exception if errors occur during writing
	 */
	public static boolean convertImage(String inputImagePath, String outputImagePath) throws Exception {
		String url = "http://localhost:3001/convertImage?oIP=" + inputImagePath + "&nIP=" + outputImagePath;
		String conversionResponse = HTTPHelper.getURL(url, "GET", null, null, null, null, false, EnvConstantsUtil.RESPONSE_PLAIN, EnvConstantsUtil.RESPONSE_PLAIN);

		if(conversionResponse.startsWith("{")) {
			HashMap<String, Object> response = new Gson().fromJson(conversionResponse, HashMap.class);
			if(response != null) {
				if(response.containsKey("newImagePath") && UtilValidate.isNotEmpty(response.get("newImagePath"))) {
					return true;
				}
			}
		}

		return false;
	}

	/*
	 * This method will get other designs in the matching group
	 */
	public static List<GenericValue> getMatchingDesigns(Delegator delegator, String templateId) throws GenericEntityException {
		List<GenericValue> matchingDesignList = new ArrayList();
		GenericValue template = null;

		if(UtilValidate.isEmpty(templateId)) {
			return null;
		} else {
			template = delegator.findOne("Scene7Template", UtilMisc.toMap("scene7TemplateId", templateId), true);
		}

		if(UtilValidate.isNotEmpty(template)) {
			String fxgNumericID = null;
			String fxgBaseID = (template.getString("scene7TemplateId").indexOf("_") != -1) ? template.getString("scene7TemplateId").substring(0, template.getString("scene7TemplateId").indexOf("_")) : template.getString("scene7TemplateId");
			Matcher matcher = Pattern.compile("^(\\d+)").matcher(template.getString("scene7TemplateId"));
			while(matcher.find()) {
				fxgNumericID = matcher.group(1);
			}

			if(UtilValidate.isNotEmpty(fxgNumericID)) {
				List<EntityCondition> conditions = new ArrayList<EntityCondition>();
				conditions.add(EntityCondition.makeCondition("scene7TemplateId", EntityOperator.NOT_EQUAL, template.getString("scene7TemplateId")));
				conditions.add(EntityCondition.makeCondition("scene7TemplateId", EntityOperator.LIKE, fxgNumericID + "%"));
				List<GenericValue> otherDesigns = delegator.findList("Scene7Template", EntityCondition.makeCondition(conditions, EntityOperator.AND), null, UtilMisc.toList("scene7TemplateId ASC"), null, true);
				if(UtilValidate.isNotEmpty(otherDesigns)) {
					Pattern compiledPattern = Pattern.compile("_back|_insideL|_insideR|_insideT|_insideB");
					for(GenericValue fxg : otherDesigns) {
						Matcher matchBackStrings = compiledPattern.matcher(fxg.getString("scene7TemplateId").toLowerCase());
						if(matcher.find()) {
							continue;
						} else if(fxg.getString("scene7TemplateId").toLowerCase().contains(fxgBaseID) && fxg.getString("scene7TemplateId").toLowerCase().contains("back")) {
							continue;
						} else if(fxg.getString("scene7TemplateId").toLowerCase().contains("back")) {
							continue;
						} else if(fxgNumericID.length() == 4 && fxg.getString("scene7TemplateId").startsWith("3") && fxg.getString("scene7TemplateId").toLowerCase().contains("b")) {
							continue;
						} else if(fxgNumericID.length() == 4 && fxg.getString("scene7TemplateId").startsWith("1") && fxg.getString("scene7TemplateId").toLowerCase().contains("d")) {
							continue;
						} else if(fxgNumericID.length() == 4 && fxg.getString("scene7TemplateId").startsWith("9") && fxg.getString("scene7TemplateId").toLowerCase().contains("b")) {
							continue;
						} else if(fxg.getString("scene7TemplateId").toLowerCase().contains(fxgBaseID) && fxg.getString("scene7TemplateId").toLowerCase().contains("_insideL")) {
							continue;
						} else if(fxg.getString("scene7TemplateId").toLowerCase().contains(fxgBaseID) && fxg.getString("scene7TemplateId").toLowerCase().contains("_insideR")) {
							continue;
						} else if(fxg.getString("scene7TemplateId").toLowerCase().contains(fxgBaseID) && fxg.getString("scene7TemplateId").toLowerCase().contains("_insideT")) {
							continue;
						} else if(fxg.getString("scene7TemplateId").toLowerCase().contains(fxgBaseID) && fxg.getString("scene7TemplateId").toLowerCase().contains("_insideB")) {
							continue;
						}
						matchingDesignList.add(fxg);
					}
				}
			}
		}

		return matchingDesignList;
	}

	public static List<String> getDoogmaFiles(Delegator delegator, String scene7DesignId) {
		List<String> files = new ArrayList<>();
		if(UtilValidate.isNotEmpty(scene7DesignId)) {
			try {
				GenericValue scene7Design = delegator.findOne("Scene7Design", UtilMisc.toMap("scene7DesignId", scene7DesignId), false);
				if(UtilValidate.isNotEmpty(scene7Design) && UtilValidate.isNotEmpty(scene7Design.getString("data"))) {
					HashMap<String, Object> jsonMap = new Gson().fromJson(scene7Design.getString("data"), HashMap.class);

					if(UtilValidate.isNotEmpty(jsonMap.get("doogmauploadedimage"))) {
						files.add(((String) jsonMap.get("doogmauploadedimage")).replaceAll("^.*?url.*?\\:(?:\\s+|)(.*?)(?:\\s+|)\\;.*?$", "$1"));
					}
					if(UtilValidate.isNotEmpty(jsonMap.get("doogmathumb"))) {
						files.add((String) jsonMap.get("doogmathumb"));
					}
				}
			} catch (Exception e) {
				Debug.logError(e, "Error trying to retrieve scene7 design data", module);
			}
		}

		return files;
	}

	public static List<GenericValue> getUGCFiles(Delegator delegator, String scene7DesignId) {
		List<GenericValue> files = new ArrayList<>();
		if(UtilValidate.isNotEmpty(scene7DesignId)) {
			try {
				GenericValue scene7Design = delegator.findOne("Scene7Design", UtilMisc.toMap("scene7DesignId", scene7DesignId), false);
				if(UtilValidate.isNotEmpty(scene7Design) && UtilValidate.isNotEmpty(scene7Design.getString("data"))) {
					HashMap<String, Object> jsonMap = new Gson().fromJson(scene7Design.getString("data"), HashMap.class);

					Map<String, Object> allSides = (Map<String, Object>) jsonMap.get("designs");
					if (UtilValidate.isNotEmpty(allSides)) {
						Iterator iter = allSides.entrySet().iterator();
						while(iter.hasNext()) {
							Map.Entry pairs = (Map.Entry) iter.next();
							String designSide = (String) pairs.getKey();

							Map<String, Object> sideMap = (Map<String, Object>) pairs.getValue();
							List<Map> elements = (List<Map>) sideMap.get("elements");

							if(UtilValidate.isNotEmpty(elements)) {
								Iterator iter2 = elements.iterator();
								while(iter2.hasNext()) {
									Map<String, Object> element = (Map<String, Object>) iter2.next();
									if(!((Boolean) element.get("deleted")) && ((String) element.get("type")).equals("BitmapImage")) {
										String url = (String) element.get("url");
										if(!url.startsWith("ActionEnvelope")) {
											url = "ActionEnvelope/" + url;
										}
										GenericValue ugcFile = EntityUtil.getFirst(delegator.findByAnd("Scene7UserContent", UtilMisc.toMap("assetUrl", url), null, false));
										if(ugcFile != null) {
											files.add(ugcFile);
										}
									}
								}
							}
						}
					}
				}
			} catch (Exception e) {
				Debug.logError(e, "Error trying to retrieve scene7 design data", module);
			}
		}

		return files;
	}
}
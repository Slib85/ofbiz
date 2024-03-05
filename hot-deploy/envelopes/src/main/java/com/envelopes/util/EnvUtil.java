/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.*;
import java.util.concurrent.TimeUnit;

import com.bigname.navigation.NavigationEvents;
import com.bigname.pricingengine.PricingEngineEvents;
import com.bigname.search.SearchEvents;
import com.envelopes.http.HTTPHelper;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilHttp;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilValidate;

import org.apache.ofbiz.base.util.cache.UtilCache;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.model.ModelEntity;
import org.apache.ofbiz.entity.model.ModelField;
import org.apache.ofbiz.entity.util.EntityQuery;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.WordUtils;

import org.apache.ofbiz.security.Security;
import org.apache.ofbiz.webapp.control.RequestHandler;
import org.apache.shiro.codec.Base64;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.mindscapehq.raygun4java.core.RaygunClient;

public class EnvUtil {
	public static final String module = EnvUtil.class.getName();

	/*
	 * A Method that calculates the timestamp for N entire days before or after this moment and returns it.
	 *
	 * @return A Timestamp for exactly N days from the current time
	 */
	public static java.sql.Timestamp getNDaysBeforeOrAfterNow(int numDays, boolean startOfDay) {
		return getNDaysBeforeOrAfterNow(numDays, startOfDay, false);
	}

	public static java.sql.Timestamp getNDaysBeforeOrAfterNow(int numDays, boolean startOfDay, boolean onlyBusinessDays) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(UtilDateTime.nowTimestamp().getTime()));

		cal.add(Calendar.DAY_OF_MONTH, (numDays));
		if (onlyBusinessDays) {
			while (EnvUtil.isNonWorkDay(new Timestamp(cal.getTimeInMillis()))) {
				cal.add(Calendar.DAY_OF_MONTH, (numDays > 0) ? 1 : -1);
			}
		}

		Timestamp result = new Timestamp(cal.getTime().getTime());
		if (startOfDay) {
			String timestampString = result.toString();
			result = Timestamp.valueOf(new StringBuffer(timestampString.substring(0, timestampString.indexOf(" "))).append(" 00:00:00.0").toString());
		}
		return result;
	}

	/*
	 * A Method that calculates the timestamp for N entire days before or after the given date and returns it.
	 *
	 * @return A Timestamp for exactly N days from the given date
	 */
	public static java.sql.Timestamp getNDaysBeforeOrAfterDate(Timestamp date, int numDays, boolean startOfDay) {
		return getNDaysBeforeOrAfterDate(date, numDays, startOfDay, false);
	}

	public static java.sql.Timestamp getNDaysBeforeOrAfterDate(Timestamp date, int numDays, boolean startOfDay, boolean onlyBusinessDays) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(date.getTime()));

		cal.add(Calendar.DAY_OF_MONTH, (numDays));
		if (onlyBusinessDays) {
			while (EnvUtil.isNonWorkDay(new Timestamp(cal.getTimeInMillis()))) {
				cal.add(Calendar.DAY_OF_MONTH, (numDays > 0) ? 1 : -1);
			}
		}

		Timestamp result = new Timestamp(cal.getTime().getTime());
		if (startOfDay) {
			String timestampString = result.toString();
			result = Timestamp.valueOf(new StringBuffer(timestampString.substring(0, timestampString.indexOf(" "))).append(" 00:00:00.0").toString());
		}
		return result;
	}

	/*
	 * A Method that calculates the timestamp for N entire months before or after this moment and returns it.
	 *
	 * @return A Timestamp for exactly N days from the current time
	 */
	public static java.sql.Timestamp getNMonthsBeforeOrAfterNow(int numMonths, boolean startOfDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(UtilDateTime.nowTimestamp().getTime()));

		cal.add(Calendar.MONTH, (numMonths));
		Timestamp result = new Timestamp(cal.getTime().getTime());
		if (startOfDay) {
			String timestampString = result.toString();
			result = Timestamp.valueOf(new StringBuffer(timestampString.substring(0, timestampString.indexOf(" "))).append(" 00:00:00.0").toString());
		}
		return result;
	}

	/*
	 * A Method that calculates the timestamp for N entire months before or after this moment and returns it.
	 *
	 * @return A Timestamp for exactly N days from the current time
	 */
	public static java.sql.Timestamp getNYearsBeforeOrAfterNow(int numYears, boolean startOfDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(UtilDateTime.nowTimestamp().getTime()));

		cal.add(Calendar.YEAR, (numYears));
		Timestamp result = new Timestamp(cal.getTime().getTime());
		if (startOfDay) {
			String timestampString = result.toString();
			result = Timestamp.valueOf(new StringBuffer(timestampString.substring(0, timestampString.indexOf(" "))).append(" 00:00:00.0").toString());
		}
		return result;
	}

	/*
	 * A Method that calculates the timestamp for N minutes before this moment and returns it.
	 *
	 * @return A Timestamp for exactly N minutes before the current time
	 */
	public static java.sql.Timestamp getNMinBeforeOrAfterNow(int minutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(UtilDateTime.nowTimestamp().getTime()));

		cal.add(Calendar.MINUTE, (minutes));
		return new Timestamp(cal.getTime().getTime());
	}


	/*
	 * A Method that to convert a String to Timestamp
	 *
	 * @return A Timestamp
	 */
	public static java.sql.Timestamp convertStringToTimestamp(String timeStamp) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		java.util.Date date = sdf.parse(timeStamp);
		return new java.sql.Timestamp(date.getTime());
	}

	public static String getFriendlyURL(HttpServletRequest request) {
		String friendlyURL = "";
		try {
			if(UtilValidate.isNotEmpty(request.getParameter("af"))) {
				String unfriendlyURL = request.getRequestURI() + "?af=" + request.getParameter("af").replaceAll("\\s", "%20");
				if(UtilValidate.isNotEmpty(request.getParameter("sort"))) {
					unfriendlyURL += "&sort=" + request.getParameter("sort");
				}
				friendlyURL = RequestHandler.proxyUrl(unfriendlyURL, request);
				if(!friendlyURL.contains("?af=")) {
					return friendlyURL;
				}
			}
		} catch (Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error finding the friendlyURL. " + e + " : " + e.getMessage(), module);
		}
		return "";
	}

	/*
	 * A Method that to converts timestamp to simple string data
	 *
	 * @return A String
	 */
	public static String convertTimestampToString(Timestamp timeStamp, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(timeStamp);
	}

	/*
	 * Check if two maps have the same key value pairs
	 */
	public static boolean areMapsEqual(Map<?, ?> firstMap, Map<?, ?> secondMap) {
		return areMapsEqual(firstMap, secondMap, false);
	}
	public static boolean areMapsEqual(Map<?, ?> firstMap, Map<?, ?> secondMap, boolean matchKeys) {
		boolean isEqual = false;

		//check nulls
		if((firstMap == null && secondMap != null) || (firstMap != null && secondMap == null)) {
			return false;
		}

		//check if they have the same amount of keys
		if(matchKeys) {
			if(firstMap.keySet().size() != secondMap.keySet().size()) {
				return false;
			}
		}

		//check keys/values
		for(Entry entry : firstMap.entrySet()) {
			if(!secondMap.containsKey(entry.getKey())) {
				isEqual = false;
				break;
			} else if(entry.getValue() == null && secondMap.get(entry.getKey()) == null) {
				isEqual = true;
			} else if(entry.getValue() != null && entry.getValue().equals(secondMap.get(entry.getKey()))) {
				isEqual = true;
			} else {
				isEqual = false;
				break;
			}
		}

		return isEqual;
	}

	/*
	 * Template helper for image URLs
	 */
	public static String toImageUrl(String str) {
		if(UtilValidate.isNotEmpty(str)) {
			str = str.toLowerCase();
			str = str.replace("w/", "");
			str = str.replaceAll("[\\s\\-]", "_");
			str = str.replaceAll("[^_a-z0-9]", "");
			return str;
		}
		return null;
	}

	/*
	 * Get the hashtype for password encryption
	 */
	public static String getHashType() {
		String hashType = UtilProperties.getPropertyValue("security.properties", "password.encrypt.hash.type");

		if (UtilValidate.isEmpty(hashType)) {
			Debug.logWarning("Password encrypt hash type is not specified in security.properties, use SHA", module);
			hashType = "SHA";
		}

		return hashType;
	}

	/*
	 * Convert String containing XML to XML Object
	 */
	public static Document stringToXML(String str) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(str)));
		document.getDocumentElement().normalize();
		return document;
	}

	/*
	 * Remove chars from string
	 */
	public static String removeChars(String[] chars, String str, boolean removeFromLeading, boolean removeTrailing, boolean removeAll) throws Exception {
		for(String s: chars) {
			if(removeAll) {
				str = str.replaceAll(s, "");
			} else {
				if(removeFromLeading) {
					String regex = "^" + s + "+";
					str = str.replaceAll(regex, "");
				}
				if(removeTrailing) {
					String regex = s + "+$";
					str = str.replaceAll(regex, "");
				}
			}
		}

		return str;
	}

	/*
	 * Remove char from string
	 */
	public static String removeChar(String s, String str, boolean removeFromLeading, boolean removeTrailing, boolean removeAll) throws Exception {
		if(removeAll) {
			str = str.replaceAll(s, "");
		} else {
			if(removeFromLeading) {
				String regex = "^" + s + "+";
				str = str.replaceAll(regex, "");
			}
			if(removeTrailing) {
				String regex = s + "+$";
				str = str.replaceAll(regex, "");
			}
		}

		return str;
	}

	/**
	 * Format a string to ofbiz order item seq number
	 * @param s
	 * @return
	 */
	public static String formatOrderItemSeqNumber(String s) {
		if(s != null) {
			for(int i = s.length(); i < 5; i++) {
				s = "0" + s;
			}
		}

		return s;
	}

	/*
	 * Get the name at the end of the URL
	 */
	public static String getPageName(HttpServletRequest request, boolean getProperName) {
		String pageName = null;

		pageName = request.getRequestURI().toString().replaceAll(".*/", "");

		if (getProperName) {
			return WordUtils.capitalizeFully(pageName.replace("-", " "));
		}
		else {
			return pageName;
		}
	}

	/*
	 * Get context from multipart
	 */
	public static Map<String, Object> getParameterMap(HttpServletRequest request) {
		Map<String, Object> context = UtilHttp.getParameterMap(request);
		if(ServletFileUpload.isMultipartContent(request)) {
			try {
				List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				for(FileItem item : items) {
					if(item.isFormField()) {
						context.put(item.getFieldName(), item.getString());
					} else {
						if(UtilValidate.isNotEmpty(item.getName())) {
							context.put(item.getFieldName(), item);
						}
					}
				}
				request.setAttribute("multiPartMap", context);
			} catch(FileUploadException e) {
				reportError(e);
				Debug.logError("Error while trying to retrieve multipart data. " + e + " : " + e.getMessage(), module);
			}
		}

		return context;
	}

	public static String doResponse(HttpServletRequest request, HttpServletResponse response, Map<String, Object> jsonResponse, String xml, String contentType) {
		boolean saveResponse = (UtilValidate.isEmpty(request.getAttribute("saveResponse"))) ? false : ((Boolean) request.getAttribute("saveResponse")).booleanValue();
		saveResponse = saveResponse ? saveResponse: Boolean.valueOf(request.getParameter("saveResponse"));

		String responseName = (UtilValidate.isEmpty(request.getParameter("responseName"))) ? "" : ((String) request.getParameter("responseName"));
		if(UtilValidate.isEmpty(responseName) && UtilValidate.isNotEmpty(request.getAttribute("responseName"))) {
			responseName = (String) request.getAttribute("responseName");
		}

		addHeaders(request, response, contentType);

		PrintWriter output = null;

		if(saveResponse) {
			request.setAttribute("savedResponse", jsonResponse);
		} else {
			try {
				output = response.getWriter();
				if(UtilValidate.isNotEmpty(jsonResponse)) {
					Gson gson = new GsonBuilder().serializeNulls().create();
					output.print(gson.toJson(jsonResponse));
				} else {
					output.print(xml);
				}
				output.flush();
			} catch (IOException e) {
				reportError(e);
				Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
				return responseName.isEmpty() ? "error" : responseName;
			} finally {
				if(output != null) {
					try {
						output.close();
					} catch (Exception fe) {
						return responseName.isEmpty() ? "error" : responseName;
					}
				}
			}
		}
		if(jsonResponse != null && jsonResponse.containsKey("success") && ((Boolean)jsonResponse.get("success"))) {
			return responseName.isEmpty() ? "success" : responseName;
		} else {
			return responseName.isEmpty() ? "error" : responseName;
		}
	}

	private static void addHeaders(HttpServletRequest request, HttpServletResponse response, String contentType) {
		response.setContentType(contentType);
		response.setCharacterEncoding(EnvConstantsUtil.ENV_CHAR_ENCODE);

		String requestFrom = request.getHeader("origin"); //ex: https://www.envelopes.com
		String endPoint = (String) request.getAttribute("_SERVER_ROOT_URL_");

		if(UtilValidate.isNotEmpty(endPoint) && endPoint.contains(EnvConstantsUtil.CART_ENDPOINT)) {
			try {
				if (UtilValidate.isNotEmpty(requestFrom)) {
					List<GenericValue> websites = EntityQuery.use((Delegator) request.getAttribute("delegator")).from("WebSite").cache().queryList();
					for (GenericValue website : websites) {
						if (UtilValidate.isNotEmpty(website.getString("cookieDomain")) && requestFrom.contains(website.getString("cookieDomain"))) {
							response.addHeader("Access-Control-Allow-Origin", requestFrom);
							response.addHeader("Access-Control-Allow-Credentials", "true");
						}
					}
				}
			} catch (GenericEntityException e) {
				reportError(e);
				Debug.logError("Error while trying to add header data data. " + e + " : " + e.getMessage(), module);
			}
		}
	}

	//global method to get system user, some services require auths, use this to get the system auth
	public static GenericValue getSystemUser(Delegator delegator) throws GenericEntityException {
		return EntityQuery.use(delegator).from("UserLogin").where("userLoginId", "system").cache().queryOne();
	}

	//global method to get delayService user, some services require auths, use this to get the delayService auth to random schedule jobs
	public static GenericValue getDelayedUser(Delegator delegator) throws GenericEntityException {
		return EntityQuery.use(delegator).from("UserLogin").where("userLoginId", "delayService").cache().queryOne();
	}

	//universal raygun error reporting
	public static void reportError(Throwable throwable) {
		RaygunClient client = new RaygunClient(EnvConstantsUtil.ENV_RAYGUN_API_KEY);
		client.Send(throwable);
	}

	//ofbiz string representation of boolean
	public static String getBooleanAsString(Boolean bool, boolean returnNull) {
		if(bool == true) {
			return "Y";
		} else if(bool == false && !returnNull) {
			return "N";
		}

		return null;
	}

	/*
	 * See if its an authorized switch request
	 */
	public static boolean authorizedSwitchRequest(String apiKey) {
		if(UtilValidate.isNotEmpty(apiKey) && apiKey.equals(EnvConstantsUtil.SWITCH_KEY)) {
			return true;
		}

		return true;
	}

	public static boolean authorizedRESTRequest(String apiKey) {
		if(UtilValidate.isNotEmpty(apiKey) && apiKey.equals(EnvConstantsUtil.REST_KEY)) {
			return true;
		}

		return false;
	}

	/*
	 * Check to see if the user is an owner of the data row
	 */
	public static boolean isOwner(String partyId, String sessionId, GenericValue data, Security security, GenericValue userLogin) {
		if(security != null && userLogin != null && security.hasEntityPermission("ORDERMGR", "_CREATE", userLogin)) {
			return true;
		}
		if((UtilValidate.isNotEmpty(partyId) && UtilValidate.isNotEmpty(data.getString("partyId")) && data.getString("partyId").equals(partyId)) || (UtilValidate.isNotEmpty(sessionId) && UtilValidate.isNotEmpty(data.getString("sessionId")) && data.getString("sessionId").equals(sessionId))) {
			return true;
		}

		return false;
	}

	/*
	 * Encode String Value to UTF-8
	 */
	public static String encodeStringToUTF8(String str) throws UnsupportedEncodingException {
		if(UtilValidate.isNotEmpty(str)) {
			str = new String(str.getBytes("ISO-8859-1"), "UTF-8");
		}

		return str;
	}

	/*
	 * Helper method to return null when context value is empty
	 */
	public static String validateContextValue(String context) {
		if(UtilValidate.isEmpty(context)) {
			return null;
		}

		return context;
	}

	//convert list of strings into a long string
	public static String listToString(List<String> list, String delimiter) {
		StringBuilder sb = new StringBuilder();
		for(String str : list) {
			if(UtilValidate.isEmpty(sb.toString())) {
				sb.append(str);
			} else {
				sb.append(delimiter).append(str);
			}
		}

		if(UtilValidate.isNotEmpty(sb.toString())) {
			return sb.toString();
		}

		return null;
	}

	//get link for tracking number
	public static String getTrackingURL(String trackingNumber) {
		if(UtilValidate.isNotEmpty(trackingNumber)) {
			trackingNumber = trackingNumber.toUpperCase().replaceAll("\\s+", "");

			String regex = EnvConstantsUtil.UPS_REGEX;
			Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(trackingNumber);
			if(m.matches()) {
				return "http://wwwapps.ups.com/WebTracking/processInputRequest?TypeOfInquiryNumber=T&loc=en_US&InquiryNumber1=" + trackingNumber;
			}

			regex = EnvConstantsUtil.USPS_REGEX;
			p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			m = p.matcher(trackingNumber);
			if(m.matches()) {
				return "http://trkcnfrm1.smi.usps.com/PTSInternetWeb/InterLabelInquiry.do?origTrackNum=" + trackingNumber;
			}

			regex = EnvConstantsUtil.FEDEX_REGEX;
			p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			m = p.matcher(trackingNumber);
			if(m.matches()) {
				return "https://www.fedex.com/apps/fedextrack/?action=track&tracknumbers=" + trackingNumber + "&locale=en_US&cntry_code=us";
			}

			regex = EnvConstantsUtil.IPARCEL_REGEX;
			p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			m = p.matcher(trackingNumber);
			if(m.matches()) {
				return "https://tracking.i-parcel.com/Home/Index?trackingnumber=" + trackingNumber;
			}
		}

		return null;
	}

	public static boolean isUPSTrackingNumber(String trackingNumber) {
		if(UtilValidate.isNotEmpty(trackingNumber)) {
			trackingNumber = trackingNumber.toUpperCase().replaceAll("\\s+", "");

			String regex = EnvConstantsUtil.UPS_REGEX;
			Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(trackingNumber);
			if (m.matches()) {
				return true;
			}
		}

		return false;
	}

	/*
	 * HEALTH CHECK FOR SERVER
	 * THIS WILL TEST A FEW THINGS TO MAKE SURE THE OFBIZ INSTANCE IS RESPONDING
	 */
	public static String healthCheck(HttpServletRequest request, HttpServletResponse response) {
		Delegator delegator = (Delegator)request.getAttribute("delegator");
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		boolean success = false;

		//TEST DB CONNECTION - NO CACHE
		try {
			GenericValue findUser = EntityQuery.use(delegator).from("UserLogin").where("userLoginId", "admin").queryOne();
			success = true;
		} catch (Exception e) {
			reportError(e);
		}

		//ADD MORE IF NECESSARY
		jsonResponse.put("success", success);
		return doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/*
	 * Fills a GenericValue object with necessary fields
	 */
	public static GenericValue insertGenericValueData(Delegator delegator, GenericValue entityRow, Map<String, Object> context) {
		return insertGenericValueData(delegator, entityRow, context, false);
	}
	public static GenericValue insertGenericValueData(Delegator delegator, GenericValue entityRow, Map<String, Object> context, boolean processNulls) {
		if(UtilValidate.isNotEmpty(context) && entityRow != null) {
			ModelEntity entity = delegator.getModelEntity(entityRow.getEntityName());
			Iterator<ModelField> fieldIter = entity.getFieldsIterator();
			while(fieldIter != null && fieldIter.hasNext()) {
				ModelField field = fieldIter.next();
				if(UtilValidate.isNotEmpty(context.get(field.getName()))) {
					if(context.get(field.getName()) instanceof String) {
						entityRow.put(field.getName(), EnvUtil.convertValue(field.getName(), field.getType(), (String) context.get(field.getName())));
					} else {
						entityRow.put(field.getName(), context.get(field.getName()));
					}
				} else if(UtilValidate.isEmpty(context.get(field.getName())) && processNulls) {
					entityRow.put(field.getName(), null);
				}
			}
		}

		return entityRow;
	}

	/*
	 * Convert Data received from request parameters to corresponding database type
	 */
	public static Object convertValue(String fieldName, String fieldType, String value) {
		if(UtilValidate.isNotEmpty(value)) {
			if(fieldType.equals("name")
				|| fieldType.equals("description")
				|| fieldType.equals("indicator")
				|| fieldType.equals("id")
				|| fieldType.equals("id-ne")
				|| fieldType.equals("value")
				|| fieldType.equals("comment")
				|| fieldType.equals("email")
				|| fieldType.equals("url")
				|| fieldType.equals("tel-number")
				|| fieldType.contains("varchar")
				|| fieldType.contains("long")
				|| fieldType.contains("very")
				|| fieldType.contains("credit-card")
			) {
				return value;
			} else if(fieldType.equals("numeric")) {
				return Long.valueOf(value);
			} else if(fieldType.contains("currency-precise") || fieldType.contains("currency-amount") || fieldType.equals("fixed-point")) {
				return new BigDecimal(value);
			} else if(fieldType.equals("floating-point")) {
				return Double.valueOf(value);
			} else if(fieldType.equals("date-time")) {
				if(!value.contains(" ")) {
					value = value + ((fieldName.contains("fromDate")) ? (" 00:00:00.0") : (" 23:59:59.0"));
				}
				return Timestamp.valueOf(value);
				//} else if(fieldType.equals("date")) {
				//	return Date.valueOf(value);
			} else if(fieldType.equals("time")) {
				return Time.valueOf(value);
			} else if(fieldType.equals("time")) {
				return Time.valueOf(value);
			}
		}

		return null;
	}

	/*
	 * Convert BigDecimal to decimal places
	 */
	public static BigDecimal convertBigDecimal(BigDecimal bD, int decimal) {
		return bD.setScale(decimal, EnvConstantsUtil.ENV_ROUNDING);
	}

	/*
	 * Convert BigDecimal to String
	 */
	public static String convertBigDecimal(BigDecimal bd, boolean wholeNumber) {
		if(wholeNumber) {
			return EnvConstantsUtil.WHOLE_NUMBER.format(bd);
		}

		return EnvConstantsUtil.STANDARD_DECIMAL.format(bd);
	}

	/*
	 * Convert all values in map to a long string
	 * Beware this should only be used when the value is not a map/array itself
	 */
	public static String convertMapToString(Map context) {
		StringBuffer str = new StringBuffer();
		str.append("{");
		Iterator mapIter = context.keySet().iterator();
		while(mapIter.hasNext()) {
			String key = (String) mapIter.next();
			str.append("\"").append(key).append("\"").append(":").append("\"").append(convertToString(context.get(key))).append("\"");
			if(mapIter.hasNext()) {
				str.append(",");
			}
		}
		str.append("}");

		return str.toString();
	}

	/*
	 * Convert all values in map to string
	 * Beware this should only be used when the value is not a map/array itself
	 */
	public static void convertMapValuesToString(Map context) {
		Iterator mapIter = context.keySet().iterator();
		while(mapIter.hasNext()) {
			String key = (String) mapIter.next();
			context.put(key, convertToString(context.get(key)));
		}
	}

	/*
	 * Convert data to String (return empty string if null)
	 */
	public static String convertToString(Object obj) {
		if(UtilValidate.isEmpty(obj)) {
			return "";
		} else if(obj instanceof BigDecimal) {
			return ((BigDecimal) obj).toPlainString();
		} else if(obj instanceof Double) {
			return obj.toString();
		} else if(obj instanceof Integer) {
			return obj.toString();
		} else if(obj instanceof Timestamp) {
			return obj.toString();
		} else if(obj instanceof Time) {
			return obj.toString();
		} else if(obj instanceof Boolean) {
			return obj.toString();
		} else if(obj instanceof Long) {
			return obj.toString();
		} else if(obj instanceof String) {
			return obj.toString();
		} else {
			Debug.logError("Error casting to string: " + obj, module);
			return "";
		}
	}

	/*
	 * Get the difference in days between 2 dates
	 */
	public static long getDaysBetweenDates(Timestamp startDate, Timestamp endDate) throws Exception {
		return getDaysBetweenDates(startDate, endDate, false, false);
	}
	public static long getDaysBetweenDates(Timestamp startDate, Timestamp endDate, boolean ignoreTime) throws Exception {
		return getDaysBetweenDates(startDate, endDate, ignoreTime, false);
	}
	public static long getDaysBetweenDates(Timestamp startDate, Timestamp endDate, boolean ignoreTime, boolean onlyBusinessDays) throws Exception {
		String format = (ignoreTime) ? "yyyy-MM-dd" : "yyyy-MM-dd HH:mm:ss.SSS";
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		Date dateObj1 = sdf.parse(startDate.toString());
		Date dateObj2 = sdf.parse(endDate.toString());

		long diffInMillies = dateObj2.getTime() - dateObj1.getTime();
		long totalDays = TimeUnit.DAYS.convert(diffInMillies,TimeUnit.MILLISECONDS);

		if(onlyBusinessDays) {
			totalDays = 0;
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(startDate.getTime());

			while(new Timestamp(cal.getTimeInMillis()).compareTo(endDate) <= 0) {
				cal.add(Calendar.DAY_OF_MONTH, 1);
				if(!EnvUtil.isNonWorkDay(new Timestamp(cal.getTimeInMillis()))) {
					totalDays++;
				}
			}
		}

		return totalDays;
	}

	/*
	 * Get the difference in days between 2 dates
	 */
	public static long getTimeBetweenDates(Timestamp startDate, Timestamp endDate) throws Exception {
		String format = "yyyy-MM-dd HH:mm:ss.SSS";
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		Date dateObj1 = sdf.parse(startDate.toString());
		Date dateObj2 = sdf.parse(endDate.toString());

		return dateObj2.getTime() - dateObj1.getTime();
	}

	/*
	 * Get the difference in min between 2 dates
	 * This will round up the minutes for an remaining seconds
	 */
	public static long getMillisecondsBetweenDates(Timestamp startDate, Timestamp endDate) {
		return endDate.getTime() - startDate.getTime();
	}

	/*
	 * Get the difference in milliseconds between 2 dates
	 */
	public static long getMinutesBetweenDates(Timestamp startDate, Timestamp endDate) {
		long duration  = endDate.getTime() - startDate.getTime();
		long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
		long remainingSeconds = TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));
		if(remainingSeconds > 0) {
			minutes = minutes + 1;
		}

		return minutes;
	}

	public static String formatTime(long milliseconds) {
		return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(milliseconds), TimeUnit.MILLISECONDS.toMinutes(milliseconds) % TimeUnit.HOURS.toMinutes(1), TimeUnit.MILLISECONDS.toSeconds(milliseconds) % TimeUnit.MINUTES.toSeconds(1));
	}

	/*
	 * Check if given timestamp is a holiday or weekend
	 */
	public static boolean isNonWorkDay(Timestamp dateTime) {
		SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
		if(EnvConstantsUtil.HOLIDAYS.contains(yyyyMMdd.format(dateTime))) {
			return true;
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(dateTime.getTime());
			if(cal.get(Calendar.DAY_OF_WEEK) == 1 || cal.get(Calendar.DAY_OF_WEEK) == 7) {
				return true;
			} else {
				return false;
			}
		}
	}

	/*
	 * Check if given timestamp is a holiday or weekend or non business hours
	 */
	public static boolean isNonWorkHours(Timestamp dateTime) {
		SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
		if(EnvConstantsUtil.HOLIDAYS.contains(yyyyMMdd.format(dateTime))) {
			return true;
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(dateTime.getTime());
			if(cal.get(Calendar.DAY_OF_WEEK) == 1 || cal.get(Calendar.DAY_OF_WEEK) == 7 || cal.get(Calendar.HOUR_OF_DAY) >= 17) {
				return true;
			} else {
				return false;
			}
		}
	}

	/*
	 Add minutes to Timestamp
	 */
	public static Timestamp addMinutesToTime(Timestamp starttime, int minutes) {
		return new Timestamp(starttime.getTime() + (minutes*60*1000));
	}

	/*
	 Sum up String time values
	 */
	public static String averageTime(List<Long> timeList, long days) {
		long secs = 0;
		long mins = 0;
		long hrs = 0;
		for(Long time : timeList) {
			hrs += TimeUnit.MILLISECONDS.toHours(time);
			mins += TimeUnit.MILLISECONDS.toMinutes(time) % TimeUnit.HOURS.toMinutes(1);
			secs += TimeUnit.MILLISECONDS.toSeconds(time) % TimeUnit.MINUTES.toSeconds(1);
		}

		//convert to seconds
		mins += hrs * 60;
		secs += mins * 60;

		long avgSecs = secs / days;

		return String.format("%02d:%02d:%02d", avgSecs / 3600, (avgSecs % 3600) / 60, avgSecs % 60);
	}

	public static HashMap<String, Object> stringToMap(String data) {
		return new Gson().fromJson(data, HashMap.class);
	}

	public static String getWebsiteId(String salesChannelEnumId) {
		String webSiteId = null;

		if(UtilValidate.isEmpty(salesChannelEnumId) || "ENV_SALES_CHANNEL".equalsIgnoreCase(salesChannelEnumId)) {
			webSiteId = "envelopes";
		} else if("AE_SALES_CHANNEL".equalsIgnoreCase(salesChannelEnumId)) {
			webSiteId = "ae";
		} else if("FOLD_SALES_CHANNEL".equalsIgnoreCase(salesChannelEnumId)) {
			webSiteId = "folders";
		} else if("BNAME_SALES_CHANNEL".equalsIgnoreCase(salesChannelEnumId)) {
			webSiteId = "bigname";
		} else if("BAGS_SALES_CHANNEL".equalsIgnoreCase(salesChannelEnumId)) {
			webSiteId = "bags";
		} else {
			webSiteId = "envelopes";
		}

		return webSiteId;
	}

	public static String getSalesChannelEnumId(String webSiteId) {
		String salesChannelEnumId = null;

		if(UtilValidate.isEmpty(webSiteId) || "envelopes".equalsIgnoreCase(webSiteId)) {
			salesChannelEnumId = "ENV_SALES_CHANNEL";
		} else if("ae".equalsIgnoreCase(webSiteId)) {
			salesChannelEnumId = "AE_SALES_CHANNEL";
		} else if("folders".equalsIgnoreCase(webSiteId)) {
			salesChannelEnumId = "FOLD_SALES_CHANNEL";
		} else if("bigname".equalsIgnoreCase(webSiteId)) {
			salesChannelEnumId = "BNAME_SALES_CHANNEL";
		} else if("bags".equalsIgnoreCase(webSiteId)) {
			salesChannelEnumId = "BAGS_SALES_CHANNEL";
		} else {
			salesChannelEnumId = "ENV_SALES_CHANNEL";
		}

		return salesChannelEnumId;
	}

	public static String getPropertiesFile(String webSiteId) {
		if("ae".equalsIgnoreCase(webSiteId)) {
			return "ae";
		} else if("folders".equalsIgnoreCase(webSiteId)) {
			return "folders";
		} else if("bigname".equalsIgnoreCase(webSiteId)) {
			return "bigname";
		} else if("bags".equalsIgnoreCase(webSiteId)) {
			return "bags";
		} else {
			return "envelopes";
		}
	}

	public static String getOrderIdPrefix(String orderId, String webSiteId) {
		if(UtilValidate.isEmpty(webSiteId) || "envelopes".equalsIgnoreCase(webSiteId)) {
			return orderId;
		} else if("ae".equalsIgnoreCase(webSiteId)) {
			return orderId.replace("ENV3", "AE3");
		} else if("folders".equalsIgnoreCase(webSiteId)) {
			return orderId.replace("ENV3", "F3");
		} else if("bigname".equalsIgnoreCase(webSiteId)) {
			return orderId.replace("ENV3", "BN3");
		} else if("bags".equalsIgnoreCase(webSiteId)) {
			return orderId.replace("ENV3", "B3");
		} else {
			return orderId;
		}
	}

	public static String getProxyFile(String webSiteId) {
		if("ae".equalsIgnoreCase(webSiteId)) {
			return "ae-proxy";
		} else if("folders".equalsIgnoreCase(webSiteId)) {
			return "folders-proxy";
		} else if("bigname".equalsIgnoreCase(webSiteId)) {
			return "bigname-proxy";
		} else if("bags".equalsIgnoreCase(webSiteId)) {
			return "bags-proxy";
		} else {
			return "proxy";
		}
	}

	public static String MD5(String md5) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
			EnvUtil.reportError(e);
		}
		return null;
	}

	/**
	 * Clear all caches from all servers
	 * @param request
	 * @param response
	 * @return
	 */
	public static String clearAllCache(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<>();

		boolean success = false;
		try {
			request.setAttribute("saveResponse", true);

			//clear utilcache
			EnvUtil.clearCache(request, response);

			//clear pricing engine cache
			PricingEngineEvents.clearCache(request, response);

			//clear menu cache
			NavigationEvents.clearMegaMenu(request, response);

			//clear search cache
			SearchEvents.clearSearchCache(request, response);

			success = true;
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "Error trying to clear all cache", module);
		}

		jsonResponse.put("success", success);
		return doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	/**
	 * Clear OfBiz UtilCache and DataStore Cache
	 * @param request
	 * @param response
	 * @return
	 */
	public static String clearCache(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		if(UtilValidate.isNotEmpty(context.get("cacheName"))) {
			String[] cacheNames = ((String) context.get("cacheName")).trim().split("\\s*,\\s*");
			for(String cacheName : cacheNames) {
				UtilCache.clearCache(cacheName);
			}
		} else {
			UtilCache.clearAllCaches();
		}

		jsonResponse.put("success", true);
		return doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public static String encodeURIComponent(String s) {
		String result = null;

		try {
			result = URLEncoder.encode(s, "UTF-8")
					.replaceAll("\\+", "%20")
					.replaceAll("\\%21", "!")
					.replaceAll("\\%27", "'")
					.replaceAll("\\%28", "(")
					.replaceAll("\\%29", ")")
					.replaceAll("\\%7E", "~");
		}

		// This exception should never occur.
		catch (Exception e)	{
			result = s;
		}

		return result;
	}

	public static String generateHmacSHA256Signature(String input, String key) {
		String result = "";
		try {
			SecretKeySpec signingKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA1");
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(input.getBytes("UTF-8"));
			result = Base64.encodeToString(rawHmac);
		} catch (Exception e) {
			Debug.logError(e, module);
		}
		return result;
	}
	public enum RuntimeEnvironment {
		DEV, QA, PROD;
		public static RuntimeEnvironment getEnvironment() {
			String environmentProperty = UtilProperties.getPropertyValue("envelopes", "envelopes.app.environment");
			if(UtilValidate.isEmpty(environmentProperty)) {
				environmentProperty = "DEV";
			}
			try {
				return RuntimeEnvironment.valueOf(environmentProperty);
			} catch(Exception e) {
				EnvUtil.reportError(e);
				Debug.logError("Invalid Environment Key, falling back to DEV", module);
				return RuntimeEnvironment.DEV;
			}
		}
	}

	public enum ShippingCarrier {
		UPS, FEDEX;
		public static ShippingCarrier getShippingCarrier() {
			String shippingCarrier = UtilProperties.getPropertyValue("envelopes", "envelopes.shipping.carrier");
			if(UtilValidate.isEmpty(shippingCarrier)) {
				shippingCarrier = UPS.toString();
			}
			try {
				return ShippingCarrier.valueOf(shippingCarrier);
			} catch(Exception e) {
				EnvUtil.reportError(e);
				Debug.logError("Invalid Shipping Carrier, falling back to UPS", module);
				return ShippingCarrier.UPS;
			}
		}
	}
}
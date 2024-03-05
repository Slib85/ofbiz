/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.netsuite;

import com.envelopes.http.HTTPHelper;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import com.google.gson.Gson;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.base.util.UtilValidate;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.*;
import java.math.BigDecimal;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import static com.envelopes.util.EnvConstantsUtil.IS_PRODUCTION;

public class NetsuiteUserAgent {
	public static final String module = NetsuiteUserAgent.class.getName();

	private List<String> cookies;
	private HttpsURLConnection conn;
	private String html;

	private static final String USER_AGENT = "PPS/1.0 (+http://www.envelopes.com.com/)";
	private static final String ORDER_URL = "https://system.netsuite.com/app/accounting/transactions/salesord.nl?id=XXXX&whence=";
	private static final String PO_URL = "https://system.netsuite.com/app/accounting/transactions/purchord.nl?";
	private static final String LOGIN_URL = "https://system.netsuite.com/pages/login.jsp";
	private static final String LOGIN_POST_URL = "https://system.netsuite.com/app/login/nllogin.nl";
	private static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8";
	private static final String ACCEPT_ENC = "gzip, deflate, sdch";
	private static final String ACCEPT_LANG = "en-US,en;q=0.8";

	private static final Map<String, String> loginParams;
	private static final Map<String, String> securityQuestions;

	static {
		loginParams = new HashMap<>();
		loginParams.put("email", UtilProperties.getPropertyValue("envelopes.properties", (IS_PRODUCTION ? "" : "sb.") + "suiteTalk.userName"));
		loginParams.put("password", UtilProperties.getPropertyValue("envelopes.properties", (IS_PRODUCTION ? "" : "sb.") + "suiteTalk.password"));
		loginParams.put("errorredirect", "/pages/login.jsp");
		loginParams.put("rememberme", "T");
		loginParams.put("jsenabled", "T");
		loginParams.put("ct", "-2");

		securityQuestions = new HashMap<>();
		securityQuestions.put("childhood", "Action1");
		securityQuestions.put("significant", "Action2");
		securityQuestions.put("maternal", "Action3");
	}

	public static String testNetsuiteUA(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> context = EnvUtil.getParameterMap(request);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();

		NetsuiteUserAgent netsuiteUS = new NetsuiteUserAgent();
		try {
			if(netsuiteUS.isLoggedIn(netsuiteUS.getHtml())) {
				HashMap<String, Object> netsuiteOrderData = netsuiteUS.getOrder(HTTPHelper.getURL(NetsuiteHelper.methodCall.get("findOrder"), NetsuiteHelper.getMethodType(NetsuiteHelper.methodCall.get("findOrder")), null, NetsuiteHelper.getAuthString(), UtilMisc.toMap("orderId", (String) context.get("orderId")), null, true, EnvConstantsUtil.RESPONSE_JSON, EnvConstantsUtil.RESPONSE_JSON));
				if(UtilValidate.isNotEmpty(netsuiteOrderData.get("orderId")) && UtilValidate.isNotEmpty(netsuiteOrderData.get("custId"))) {
					String html = netsuiteUS.getOrderHtml((String) netsuiteOrderData.get("orderId"));
					if(UtilValidate.isNotEmpty(html) && html.contains("/app/accounting/transactions/purchord.nl?soid=")) {
						html = netsuiteUS.getPOHtml((String) netsuiteOrderData.get("orderId"), (String) netsuiteOrderData.get("custId"));
						Document doc = Jsoup.parse(html);
						Elements links = doc.select("input");
					}
				}
			}
		} catch(Exception e) {
			//
		}

		return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
	}

	public NetsuiteUserAgent() {
		CookieHandler.setDefault(new CookieManager());

		//fetch login url to get cookies
		try {
			getBaseCookies();
			this.html = doLogin();

			if(requiresSecurityQuestion(html)) {
				this.html = doSecurityAuth(html);
			}
		} catch(Exception e) {
			//
		}
	}

	public String getHtml() {
		return this.html;
	}

	public HashMap<String, Object> getOrder(String response) {
		if(response.startsWith("{")) {
			HashMap<String, Object> respJson = new Gson().fromJson(response, HashMap.class);
			return respJson;
		}
		return null;
	}

	public String createPurchaseOrder(String orderId, String orderItemSeqId, String vendorId, BigDecimal cost) {

		return null;
	}

	protected String getOrderHtml(String orderId) throws IOException {
		URL obj = new URL(ORDER_URL.replace("XXXX", orderId));

		this.conn = (HttpsURLConnection) obj.openConnection();
		conn.setRequestMethod("GET");
		conn.setUseCaches(false);
		conn.setRequestProperty("User-Agent", USER_AGENT);
		conn.setRequestProperty("Accept", ACCEPT);
		conn.setRequestProperty("Accept-Encoding", ACCEPT_ENC);
		conn.setRequestProperty("Accept-Language", ACCEPT_LANG);

		if(UtilValidate.isNotEmpty(this.cookies)) {
			for(String cookie : this.cookies) {
				conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
			}
		}

		int responseCode = conn.getResponseCode();

		InputStream input = conn.getInputStream();
		if("gzip".equals(conn.getContentEncoding())) {
			input = new GZIPInputStream(input);
		}

		StringBuffer response = new StringBuffer();
		BufferedReader in = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
		String inputLine;

		while((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}

		in.close();

		return response.toString();
	}

	protected String getPOHtml(String orderId, String custId) throws IOException {
		URL obj = new URL(PO_URL + "soid=" + orderId + "&shipgroup=1&dropship=T&custid=" + custId);

		this.conn = (HttpsURLConnection) obj.openConnection();
		conn.setRequestMethod("GET");
		conn.setUseCaches(false);
		conn.setRequestProperty("User-Agent", USER_AGENT);
		conn.setRequestProperty("Accept", ACCEPT);
		conn.setRequestProperty("Accept-Encoding", ACCEPT_ENC);
		conn.setRequestProperty("Accept-Language", ACCEPT_LANG);

		if(UtilValidate.isNotEmpty(this.cookies)) {
			for(String cookie : this.cookies) {
				conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
			}
		}

		int responseCode = conn.getResponseCode();

		InputStream input = conn.getInputStream();
		if("gzip".equals(conn.getContentEncoding())) {
			input = new GZIPInputStream(input);
		}

		StringBuffer response = new StringBuffer();
		BufferedReader in = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
		String inputLine;

		while((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}

		in.close();

		return response.toString();
	}

	protected void getBaseCookies() throws IOException {
		URL obj = new URL(LOGIN_URL);

		this.conn = (HttpsURLConnection) obj.openConnection();
		conn.setRequestMethod("GET");
		conn.setUseCaches(false);
		conn.setRequestProperty("User-Agent", USER_AGENT);
		conn.setRequestProperty("Accept", ACCEPT);
		conn.setRequestProperty("Accept-Encoding", ACCEPT_ENC);
		conn.setRequestProperty("Accept-Language", ACCEPT_LANG);

		int responseCode = conn.getResponseCode();

		InputStream input = conn.getInputStream();
		if("gzip".equals(conn.getContentEncoding())) {
			input = new GZIPInputStream(input);
		}

		StringBuffer response = new StringBuffer();
		BufferedReader in = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
		String inputLine;

		while((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}

		in.close();

		setCookies(conn.getHeaderFields().get("Set-Cookie"));
	}

	protected String doLogin() throws IOException {
		URL obj = new URL(LOGIN_POST_URL);
		this.conn = (HttpsURLConnection) obj.openConnection();
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Host", "system.netsuite.com");
		conn.setRequestProperty("User-Agent", USER_AGENT);
		conn.setRequestProperty("Accept", ACCEPT);
		conn.setRequestProperty("Accept-Encoding", ACCEPT_ENC);
		conn.setRequestProperty("Accept-Language", ACCEPT_LANG);

		if(UtilValidate.isNotEmpty(this.cookies)) {
			for(String cookie : this.cookies) {
				conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
			}
		}

		conn.setRequestProperty("Connection", "keep-alive");
		conn.setRequestProperty("Referer", LOGIN_URL);
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", Integer.toString(paramsToString(loginParams).length()));

		conn.setDoOutput(true);
		conn.setDoInput(true);

		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(paramsToString(loginParams));
		wr.flush();
		wr.close();

		int responseCode = conn.getResponseCode();

		InputStream input = conn.getInputStream();
		if("gzip".equals(conn.getContentEncoding())) {
			input = new GZIPInputStream(input);
		}

		StringBuffer response = new StringBuffer();
		BufferedReader in = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
		String inputLine;

		while((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}

		in.close();

		setCookies(conn.getHeaderFields().get("Set-Cookie"));

		return response.toString();
	}

	protected String doSecurityAuth(String html) throws IOException {
		Map<String, String> loginParamsSecurity = new HashMap<>();
		loginParamsSecurity.put("email", UtilProperties.getPropertyValue("envelopes.properties", (IS_PRODUCTION ? "" : "sb.") + "suiteTalk.userName"));
		loginParamsSecurity.put("answer", getSecurityAnswer(html));
		loginParamsSecurity.put("pwdstate", "on");
		loginParamsSecurity.put("submitter", "Submit");

		URL obj = new URL(LOGIN_POST_URL);
		this.conn = (HttpsURLConnection) obj.openConnection();
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Host", "system.netsuite.com");
		conn.setRequestProperty("User-Agent", USER_AGENT);
		conn.setRequestProperty("Accept", ACCEPT);
		conn.setRequestProperty("Accept-Encoding", ACCEPT_ENC);
		conn.setRequestProperty("Accept-Language", ACCEPT_LANG);

		if(UtilValidate.isNotEmpty(this.cookies)) {
			for(String cookie : this.cookies) {
				conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
			}
		}

		conn.setRequestProperty("Connection", "keep-alive");
		conn.setRequestProperty("Referer", "https://system.netsuite.com/pages/twofactorqa.jsp?locale=en_US&whence=");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", Integer.toString(paramsToString(loginParamsSecurity).length()));

		conn.setDoOutput(true);
		conn.setDoInput(true);

		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(paramsToString(loginParamsSecurity));
		wr.flush();
		wr.close();

		int responseCode = conn.getResponseCode();

		InputStream input = conn.getInputStream();
		if("gzip".equals(conn.getContentEncoding())) {
			input = new GZIPInputStream(input);
		}

		StringBuffer response = new StringBuffer();
		BufferedReader in = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
		String inputLine;

		while((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}

		in.close();

		setCookies(conn.getHeaderFields().get("Set-Cookie"));

		return response.toString();
	}

	public boolean isLoggedIn(String html) {
		if(UtilValidate.isNotEmpty(html) && html.contains("Action Envelope")) {
			return true;
		}

		return false;
	}

	private boolean requiresSecurityQuestion(String html) {
		if(UtilValidate.isNotEmpty(html) && html.contains("Additional Authentication Required")) {
			return true;
		}

		return false;
	}

	private String paramsToString(Map<String, String> params) throws UnsupportedEncodingException {
		StringBuilder str = new StringBuilder();

		Iterator paramsIter = params.keySet().iterator();
		while(paramsIter.hasNext()) {
			String param = (String) paramsIter.next();
			if(str.length() == 0) {
				str.append(param + "=" + URLEncoder.encode(params.get(param), "UTF-8"));
			} else {
				str.append("&" + param + "=" + URLEncoder.encode(params.get(param), "UTF-8"));
			}
		}

		return str.toString();
	}

	private void setCookies(List<String> cookies) {
		this.cookies = cookies;
	}

	private String getSecurityAnswer(String html) {
		if(UtilValidate.isNotEmpty(html)) {
			Iterator securityIter = securityQuestions.keySet().iterator();
			while(securityIter.hasNext()) {
				String question = (String) securityIter.next();
				if(html.contains(question)) {
					return securityQuestions.get(question);
				}
			}
		}

		return null;
	}
}
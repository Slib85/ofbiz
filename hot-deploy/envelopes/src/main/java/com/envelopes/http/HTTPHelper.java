/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.http;

import java.io.*;
import java.net.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ofbiz.base.util.UtilValidate;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.envelopes.util.*;

public class HTTPHelper {
	public static final String module = HTTPHelper.class.getName();

	private static final int timeout = 180;
	/*
	 * Open an HttpClient connection and get the results
	 */
	public static String getURL(String url, String requestType, Map<String, String> oAuthParameters, String preBuiltAuthString, Map<String, Object> bodyParameters, Map<String, File> fileList, boolean doKeyValue, String setAccept, String setContent) throws UnsupportedEncodingException, MalformedURLException, IOException, Exception {
		HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(RequestConfig.custom().setSocketTimeout(timeout * 1000).setConnectTimeout(timeout * 1000).setConnectionRequestTimeout(timeout * 1000).build()).build();
		HttpPost httpPost = null;
		HttpGet httpGet = null;
		HttpResponse response = null;
		StringBuilder result = new StringBuilder("");

		if(UtilValidate.isNotEmpty(requestType) && requestType.equalsIgnoreCase("POST")) {
			httpPost = new HttpPost(url);
			if(UtilValidate.isNotEmpty(fileList)) {
				MultipartEntity mpBody = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
				Iterator fileIter = fileList.entrySet().iterator();
				while(fileIter.hasNext()) {
					Map.Entry pairs = (Map.Entry) fileIter.next();
					FileBody fbFile = new FileBody((File) pairs.getValue());
					mpBody.addPart((String) pairs.getKey(), fbFile);
				}
				httpPost.setEntity(mpBody);
			}
			if(UtilValidate.isNotEmpty(bodyParameters)) {
				StringEntity body = new StringEntity(buildBody(bodyParameters, doKeyValue, setContent));
				setMethodContentType(body, setContent);
				httpPost.setEntity(body);
			}
			setMethodResponse(httpPost, null, setAccept);
			if(UtilValidate.isNotEmpty(oAuthParameters)) {
				setHeader(httpPost, null, "Authorization", buildOAuth(oAuthParameters));
			} else if(UtilValidate.isNotEmpty(preBuiltAuthString)) {
				setHeader(httpPost, null, "Authorization", preBuiltAuthString);
			}
			if(UtilValidate.isNotEmpty(setContent) && !setContent.equalsIgnoreCase("application/octet-stream")) { //for whatever reason setting a content type for multipart does not work
				setHeader(httpPost, null, "Content-Type", setContent);
			}
			response = httpClient.execute(httpPost);
		} else {
			httpGet = new HttpGet(url);
			setMethodResponse(null, httpGet, setAccept);
			if(UtilValidate.isNotEmpty(oAuthParameters)) {
				setHeader(null, httpGet, "Authorization", buildOAuth(oAuthParameters));
			} else if(UtilValidate.isNotEmpty(preBuiltAuthString)) {
				setHeader(null, httpGet, "Authorization", preBuiltAuthString);
			}
			setHeader(null, httpGet, "Content-Type", setContent);
			response = httpClient.execute(httpGet);
		}

		if(response.getStatusLine().getStatusCode() != 200) {
			result.append(("Failed : HTTP error code : " + response.getStatusLine().getStatusCode())).append(". ");
		}

		InputStream instream = response.getEntity().getContent();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(instream, "UTF-8"));
			String line;
			while((line = in.readLine()) != null) {
				result = result.append(line);
			}
		} finally {
			instream.close();
			httpClient.getConnectionManager().shutdown();
		}

		return result.toString();
	}

	/*
	 * Set the response of the url connection
	 */
	private static void setMethodResponse(HttpPost httpPost, HttpGet httpGet, String setAccept) {
		if(UtilValidate.isNotEmpty(setAccept) && setAccept.equalsIgnoreCase(EnvConstantsUtil.RESPONSE_PLAIN)) {
			if(httpPost != null) {
				httpPost.addHeader("accept", EnvConstantsUtil.RESPONSE_PLAIN);
			} else {
				httpGet.addHeader("accept", EnvConstantsUtil.RESPONSE_PLAIN);
			}
		} else {
			if(httpPost != null) {
				httpPost.addHeader("accept", EnvConstantsUtil.RESPONSE_JSON);
			} else {
				httpGet.addHeader("accept", EnvConstantsUtil.RESPONSE_JSON);
			}
		}
	}

	/*
	 * Add a value to a request header
	 */
	private static void setHeader(HttpPost httpPost, HttpGet httpGet, String headerName, String headerValue) {
		if(UtilValidate.isNotEmpty(headerName) && UtilValidate.isNotEmpty(headerValue)) {
			if(httpPost != null) {
				httpPost.addHeader(headerName, headerValue);
			} else {
				httpGet.addHeader(headerName, headerValue);
			}
		}
	}

	/*
	 * Set the content type of the url connection
	 */
	private static void setMethodContentType(StringEntity input, String setContent) {
		if(UtilValidate.isEmpty(setContent)) {
			input.setContentType(EnvConstantsUtil.RESPONSE_PLAIN);
		} else {
			input.setContentType(setContent);
		}
	}

	/*
	 * Build string from body parameters
	 */
	private static String buildBody(Map<String, Object> bodyParameters, boolean doKeyValue, String setContent) {
		StringBuilder body = new StringBuilder("");

		if(UtilValidate.isNotEmpty(setContent) && setContent.equalsIgnoreCase(EnvConstantsUtil.RESPONSE_JSON)) {
			Gson gson = new GsonBuilder().serializeNulls().create();
			body.append(gson.toJson(bodyParameters));
		} else {
			Iterator bodyParametersIter = bodyParameters.entrySet().iterator();
			while(bodyParametersIter.hasNext()) {
				Map.Entry pairs = (Map.Entry) bodyParametersIter.next();
				if(doKeyValue) {
					body.append((String) pairs.getKey()).append("=").append((String) pairs.getValue());
					if(bodyParametersIter.hasNext()) {
						body.append("&");
					}
				} else {
					body.append((String) pairs.getValue());
				}
			}
		}

		return body.toString();
	}

	/*
	 * Build string from body parameters
	 */
	private static String buildOAuth(Map<String, String> oAuthParameters) throws UnsupportedEncodingException {
		StringBuilder oAuth = new StringBuilder("OAuth ");

		Iterator oAuthParametersIter = oAuthParameters.entrySet().iterator();
		while(oAuthParametersIter.hasNext()) {
			Map.Entry pairs = (Map.Entry) oAuthParametersIter.next();
			oAuth.append(URLEncoder.encode((String) pairs.getKey(), EnvConstantsUtil.ENV_CHAR_ENCODE)).append("=").append("\"").append(URLEncoder.encode((String) pairs.getValue(), EnvConstantsUtil.ENV_CHAR_ENCODE)).append("\"");
			if(oAuthParametersIter.hasNext()) {
				oAuth.append(",");
			}
		}

		return oAuth.toString();
	}

	/*
	 * Build a map of the URL and body if the URL is too long
	 */
	public static List<Map> splitURL(List<String> urls) {
		List<Map> urlList = new ArrayList<Map>();

		for(String url : urls) {
			int splitLocation = url.indexOf("?");
			Map<String, String> urlMap = new HashMap<String, String>();
			if(splitLocation != -1) {
				urlMap.put("url", url.substring(0, splitLocation));
				urlMap.put("body", url.substring(splitLocation+1));
			} else {
				urlMap.put("url", url);
				urlMap.put("body", null);
			}
			urlList.add(urlMap);
		}

		return urlList;
	}

	/*
	 * Determine of a url request should return a stream or a string
	 */
	public static boolean returnString(String setAccept) {
		if(setAccept.toLowerCase().contains("text")) {
			return true;
		}
		return false;
	}
}
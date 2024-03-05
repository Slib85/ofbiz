/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.http;

import java.io.*;
import java.net.*;
import java.lang.*;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import com.envelopes.util.*;

/*
 * Generic Class created to return different types from a url request
 * Example, if you want to get string results or if you want to get an inputstream for a file
 */
public class URLResponseHandler {
	public static final String module = URLResponseHandler.class.getName();

	public String string = null;
	public InputStream inputStream = null;
	public OutputStream outputStream = null;
	public DefaultHttpClient httpClient = null;

	public URLResponseHandler() {
	}

	public void setString(String str) {
		this.string = str;
	}

	public void setInputStream(InputStream stream) {
		this.inputStream = stream;
	}

	public void setOutputStream(OutputStream stream) {
		this.outputStream = stream;
	}

	public String getString() {
		return this.string;
	}

	public InputStream getInputStream() {
		return this.inputStream;
	}

	public OutputStream getOutputStream() {
		return this.outputStream;
	}

	protected String convertStreamToString(InputStream stream) throws Exception {
		StringBuilder result = new StringBuilder("");
		BufferedReader in = new BufferedReader(new InputStreamReader((stream)));
		String line;
		while((line = in.readLine()) != null) {
			result = result.append(line);
		}

		this.setString(result.toString());
		return this.string;
	}

	public void close() throws Exception {
		if(inputStream != null) {
			inputStream.close();
		}
		httpClient.getConnectionManager().shutdown();
	}
}
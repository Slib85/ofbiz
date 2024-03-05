package com.envelopes.service;

import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.service.LocalDispatcher;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Manu on 3/13/2015.
 */
public class ServiceHelper {
	public static final String module = ServiceHelper.class.getName();

	public static Map<String, Object> runSync(LocalDispatcher dispatcher, String serviceName, Map<String, ? extends Object> context, String errorMessage, boolean reportError) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = dispatcher.runSync(serviceName, context);
		} catch (Exception e) {
			if(reportError) {
				EnvUtil.reportError(e);
			}
			Debug.logError(e, errorMessage + " " + e + " : " + e.getMessage(), module);
		}
		return result;
	}

	public static void runAsync(LocalDispatcher dispatcher, String serviceName, Map<String, ? extends Object> context, String errorMessage, boolean reportError) {
		try {
			dispatcher.runAsync(serviceName, context);
		} catch (Exception e) {
			if(reportError) {
				EnvUtil.reportError(e);
			}
			Debug.logError(e, errorMessage + " " + e + " : " + e.getMessage(), module);
		}
	}

	public static void schedule(LocalDispatcher dispatcher, String serviceName, Map<String, ? extends Object> context, long startTime, String errorMessage, boolean reportError) {

		try {
			dispatcher.schedule(serviceName, context, startTime);
		} catch (Exception e) {
			if(reportError) {
				EnvUtil.reportError(e);
			}
			Debug.logError(e, errorMessage + " " + e + " : " + e.getMessage(), module);
		}
	}
}

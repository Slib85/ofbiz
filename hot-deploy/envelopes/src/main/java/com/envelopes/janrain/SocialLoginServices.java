/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.janrain;

import java.io.*;
import java.net.*;
import java.lang.*;
import java.util.Map;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntity;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.envelopes.util.*;

public class SocialLoginServices {
	public static final String module = SocialLoginServices.class.getName();

	/** Login service to authenticate token for janrain
	 * @return Map of results including (userLogin) GenericValue object
	 */
	public static Map<String, Object> getSocialUserLogin(DispatchContext ctx, Map<String, ?> context) {
		LocalDispatcher dispatcher = ctx.getDispatcher();
		Delegator delegator = ctx.getDelegator();

		Map<String, Object> result = null;
		Map<String, GenericValue> loginData = null;
		try {
			Map<String, String> socialContext = SocialLoginHelper.getAuthInfo(UtilMisc.<String, String>toMap("apiKey", SocialLoginHelper.API_KEY, "token", context.get("token")));

			if(UtilValidate.isNotEmpty(socialContext)) {
				loginData = SocialLoginHelper.getUserLogin(delegator, dispatcher, socialContext);
			}
		} catch(Exception e) {
			EnvUtil.reportError(e);
			return ServiceUtil.returnError("Error getting janrain data.");
		}

		result = ServiceUtil.returnSuccess();
		result.put("username", UtilValidate.isNotEmpty(loginData) && UtilValidate.isNotEmpty(loginData.get("userLogin")) ? ((GenericValue) loginData.get("userLogin")).getString("userLoginId") : null);
		result.put("externalUserLogin", UtilValidate.isNotEmpty(loginData) && UtilValidate.isNotEmpty(loginData.get("externalUserLogin")) ? loginData.get("externalUserLogin") : null);

		return result;
	}
}
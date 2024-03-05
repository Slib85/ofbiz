/**
 * Created by Mike on 1/29/2018.
 */

package com.bigname.navigation;

import com.envelopes.party.PartyHelper;
import com.envelopes.product.ProductHelper;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import com.google.gson.Gson;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.transaction.GenericTransactionException;
import org.apache.ofbiz.entity.transaction.TransactionUtil;
import org.apache.ofbiz.service.LocalDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import static com.envelopes.util.EnvConstantsUtil.clearNavContent;

public class NavigationEvents {
    public static final String module = NavigationEvents.class.getName();

    public static String updateMegaMenu(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        boolean success = false;

        Map<String, Object> jsonResponse = new HashMap<String, Object>();

        boolean beganTransaction = false;

        try {
            beganTransaction = TransactionUtil.begin();
            success = NavigationHelper.updateMegaMenu(delegator, new Gson().fromJson(request.getParameter("itemsToSave"), ArrayList.class), new Gson().fromJson(request.getParameter("itemsToDelete"), ArrayList.class));
        } catch (Exception e) {
            try {
                TransactionUtil.rollback(beganTransaction, "Error saving Mega Menu Navigation", e);
            } catch (GenericEntityException gee) {
                EnvUtil.reportError(gee);
            }

            EnvUtil.reportError(e);
        } finally {
            try {
                TransactionUtil.commit(beganTransaction);
            } catch(GenericEntityException gee) {
                Debug.logError(gee, "Could not commit Mega Menu Navigation data.", module);
            }
        }

        jsonResponse.put("success" , success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    public static String clearMegaMenu(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        boolean success = false;

        Map<String, Object> jsonResponse = new HashMap<String, Object>();

        try {
            clearNavContent(delegator, (String) context.get("webSiteId"));
            success = true;
        } catch (Exception e) {
            EnvUtil.reportError(e);
        }

        jsonResponse.put("success" , success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }
}

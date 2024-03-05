/**
 * Created by Mike on 1/29/2018.
 */

package com.bigname.navigation;

import java.util.*;
import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.util.EntityQuery;
import com.envelopes.util.*;
import com.google.gson.internal.LinkedTreeMap;

public class NavigationHelper {
    public static final String module = NavigationHelper.class.getName();

    public static List<GenericValue> getMegaMenuData(Delegator delegator, String webSiteId) {
        List<GenericValue> megaMenuData = new ArrayList<>();

        try {
            megaMenuData = EntityQuery.use(delegator).from("NavigationContent").where("webSiteId", webSiteId).orderBy("sequenceNum ASC").queryList();
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError("Error while trying to set Envelopes Navigation Content.", module);
        }

        return megaMenuData;
    }

    public static Boolean updateMegaMenu(Delegator delegator, ArrayList itemsToSave, ArrayList itemsToDelete) throws GenericEntityException {
        Boolean success = true;

        delegator.removeByCondition("NavigationContent", EntityCondition.makeCondition("id", EntityOperator.IN, itemsToDelete));

        List<GenericValue> valuesToInsert = new ArrayList<>();

        for (int i = 0; i < itemsToSave.size(); i++) {
            Map item = (LinkedTreeMap) itemsToSave.get(i);
            item.put("id", delegator.getNextSeqId("NavigationContent"));
            valuesToInsert.add(EnvUtil.insertGenericValueData(delegator, delegator.makeValue("NavigationContent"), item, true));
        }

        if (UtilValidate.isNotEmpty(valuesToInsert)) {
            delegator.storeAll(valuesToInsert);
        }

        return success;
    }
}

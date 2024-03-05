

import java.util.*;
import java.sql.Timestamp;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.*;
import org.apache.ofbiz.entity.condition.*;
import org.apache.ofbiz.entity.util.EntityFindOptions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.envelopes.util.*;

String module = "cartAssistance.groovy";

String cartId = request.getParameter("id");

List<GenericValue> pCarts = null;
if(UtilValidate.isNotEmpty(cartId)) {
	EntityCondition cond = EntityCondition.makeCondition([EntityCondition.makeCondition("id", EntityOperator.EQUALS, cartId)],EntityOperator.AND);
	EntityFindOptions efo = new EntityFindOptions();
	efo.setMaxRows(25);
	pCarts = delegator.findList("PersistentCart", cond, null, UtilMisc.toList("createdStamp DESC"), efo, false);
} else {
	EntityFindOptions efo = new EntityFindOptions();
	efo.setMaxRows(25);
	pCarts = delegator.findList("PersistentCart", null, null, UtilMisc.toList("createdStamp DESC"), efo, false);
}


context.put("pCarts", pCarts);
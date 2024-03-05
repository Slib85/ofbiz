import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.base.util.UtilValidate;

import com.envelopes.plating.PlateHelper;

String module = "plateEdit.groovy";

Delegator delegator = (Delegator) request.getAttribute("delegator");
GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

String plateId = null;
if(UtilValidate.isNotEmpty(request.getParameter("plateId"))) {
	plateId = request.getParameter("plateId")
}

context.plate = PlateHelper.getPlateDetails(delegator, userLogin, plateId);
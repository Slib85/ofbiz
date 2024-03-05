import com.envelopes.cxml.CXMLHelper;
import com.envelopes.util.*;

String module = "vendorOrderList.groovy";
Map<String, Object> params = EnvUtil.getParameterMap(request);

context.orderList = CXMLHelper.vendorOrders(delegator, (String) params.get("partyId"));
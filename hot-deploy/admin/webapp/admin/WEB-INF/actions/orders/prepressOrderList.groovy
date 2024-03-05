import java.util.*;
import org.apache.ofbiz.entity.*;

import com.envelopes.order.*;

String module = "prepressOrderList.groovy";

request.setAttribute("saveResponse", true);

OrderEvents.prepressOrderAndItemList(request, response);
List<GenericValue> orderList = (List<GenericValue>) ((Map) request.getAttribute("savedResponse")).get("orders");

context.put("orderList", orderList);
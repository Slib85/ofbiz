import java.util.*;

import com.envelopes.order.*;
import com.envelopes.quote.*;

String module = "prepress.groovy";

request.setAttribute("saveResponse", true);

OrderEvents.getPrepressQueue(request, response);
List<Map> prepress = (List<Map>) ((Map) request.getAttribute("savedResponse")).get("queues");

context.put("offset", prepress.get(0));
context.put("digital", prepress.get(1));
context.put("proofReady", prepress.get(2));
context.put("fullStatus", prepress.get(3));

context.put("prepressQueueFilterList", OrderHelper.getPrepressQueueFilterList(delegator, request.getSession().getAttribute("userLogin")));
context.put("userPrepressQueue", QuoteHelper.getUserPrepressQueue(delegator, prepress));
context.put("foldersAssignedToUsers", QuoteHelper.foldersAssignedToUsers);
import org.apache.ofbiz.entity.*;
import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.base.util.UtilValidate;
import com.envelopes.label.*;
import java.util.*;

LabelPrintHelper.generateLabels(delegator, false);

List<Object> result = LabelPrintHelper.getLabels(request.getParameter("pageIndex"), "500", request.getParameter("id"));

context.pages = (int)result.get(0);
context.pageIndex = (int)result.get(1);
context.labels = (List<String>)result.get(2);
context.productId = request.getParameter("id");


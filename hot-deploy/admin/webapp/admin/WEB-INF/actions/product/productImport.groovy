import java.util.*;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import com.envelopes.email.*;
import com.envelopes.order.*;

String module = "productImport.groovy";

if(UtilValidate.isNotEmpty(request.getAttribute("savedResponse"))) {
	context.error = request.getAttribute("error");
	context.success = request.getAttribute("success");
}
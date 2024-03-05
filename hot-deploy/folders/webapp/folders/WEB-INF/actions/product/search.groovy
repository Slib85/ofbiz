import org.apache.ofbiz.base.util.UtilValidate;

import com.envelopes.util.*;
import com.envelopes.category.*;

String module = "search.groovy";

Map<String, Object> params = EnvUtil.getParameterMap(request);
String searchString = (String) params.get("w");

if (UtilValidate.isNotEmpty(searchString)) {
	context.searchItemList = FoldersCategory.getSearchData(searchString);
	context.searchString = searchString;
}


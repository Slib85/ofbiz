import org.apache.ofbiz.base.util.UtilValidate;

import com.envelopes.util.*;
import com.envelopes.product.*;

String module = "topBlanks.groovy";

context.productList = ProductHelper.getTopBlankFoldersData(delegator, dispatcher, request);
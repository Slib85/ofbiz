import org.apache.ofbiz.base.util.UtilValidate;

import com.envelopes.util.*;
import com.envelopes.product.*;

String module = "foilGuide.groovy";

context.metallicFoilList = FolderProductHelper.getProductFoils(delegator, Boolean.TRUE);
context.nonMetallicFoilList = FolderProductHelper.getProductFoils(delegator, Boolean.FALSE);

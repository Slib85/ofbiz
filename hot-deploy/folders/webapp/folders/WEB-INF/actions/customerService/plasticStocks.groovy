import org.apache.ofbiz.base.util.UtilValidate;

import com.envelopes.util.*;
import com.envelopes.product.*;

String module = "plasticStocks.groovy";

context.polyethyleneMaterialList = FolderProductHelper.getProductMaterials(delegator, "Polyethylene Colors", '');
context.polypropyleneMaterialList = FolderProductHelper.getProductMaterials(delegator, "Polypropylene", '');

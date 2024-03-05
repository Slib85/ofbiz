import org.apache.ofbiz.base.util.UtilValidate;

import com.envelopes.util.*;
import com.envelopes.product.*;

String module = "indexTagStockColors.groovy";

context.vinylMaterialList = FolderProductHelper.getProductMaterials(delegator, "Index/Tag Stock Colors", '');

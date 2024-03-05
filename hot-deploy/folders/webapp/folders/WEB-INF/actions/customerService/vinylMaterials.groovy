import org.apache.ofbiz.base.util.UtilValidate;

import com.envelopes.util.*;
import com.envelopes.product.*;

String module = "vinylMaterials.groovy";

context.vinylMaterialList = FolderProductHelper.getProductMaterials(delegator, "Vinyl Colors", '');

import java.sql.ResultSet;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.GenericDataSourceException;
import com.envelopes.util.*;
import com.envelopes.product.*;

import org.apache.ofbiz.entity.jdbc.SQLProcessor;

String module = "paperStocks.groovy";

context.paperStockList = FolderProductHelper.getPaperStocks(delegator);
Product product = new Product(delegator, dispatcher, "LUX-PF-13", request);
context.product = product;


SQLProcessor du = null;
String sqlCommand = null;
List productList = new ArrayList();

try {
	// Get all colors for blank and custom standard folders.
    du = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));

    sqlCommand = "" +
    	"SELECT distinct " +
			"(SELECT pf2.description FROM product_feature pf2 INNER JOIN product_feature_appl pfa2 ON pfa2.product_feature_id = pf2.product_feature_id WHERE pf2.product_feature_type_id = 'COLOR_GROUP' AND pfa2.product_id = p.product_id) AS COLOR_GROUP, " +
			"(SELECT pf2.description FROM product_feature pf2 INNER JOIN product_feature_appl pfa2 ON pfa2.product_feature_id = pf2.product_feature_id WHERE pf2.product_feature_type_id = 'COLOR' AND pfa2.product_id = p.product_id) AS COLOR, " +
			"(SELECT pf2.description FROM product_feature pf2 INNER JOIN product_feature_appl pfa2 ON pfa2.product_feature_id = pf2.product_feature_id WHERE pf2.product_feature_type_id = 'PAPER_WEIGHT' AND pfa2.product_id = p.product_id) AS PAPER_WEIGHT, " +
			"(SELECT pf2.description FROM product_feature pf2 INNER JOIN product_feature_appl pfa2 ON pfa2.product_feature_id = pf2.product_feature_id WHERE pf2.product_feature_type_id = 'PAPER_TEXTURE' AND pfa2.product_id = p.product_id) AS PAPER_TEXTURE " +
		"FROM " +
			"product_feature pf " +
		"INNER JOIN " +
			"product_feature_appl pfa ON pfa.product_feature_id = pf.product_feature_id " +
		"INNER JOIN " +
			"product p ON p.product_id = pfa.product_id " +
		"INNER JOIN " +
			"product_web_site pws ON p.product_id = pws.product_id " +
		"WHERE " +
			"pws.folders = 'Y' " +
			"AND p.primary_product_category_id IN ('STANDARD_FOLDER' , 'BLANK_ST_PR_FOLDERS') " +
			"AND pf.product_feature_type_id IN ('COLOR_GROUP' , 'COLOR', 'PAPER_TEXTURE', 'PAPER_WEIGHT') " +
			"AND pf.description != 'ASSORTED' " +
			"AND pfa.product_id NOT IN ('PF-WLI', 'SF-101-AW100', 'PF-100W', 'OR-144-SG12', 'PF-130W', 'SF-101-CMBUR12', 'SF-101-RGLOSS', 'SF-101-DB100', 'SF-101-DDP100', 'SF-101-DE100', 'PF-NLI', 'SF-101-CSG100', 'OR-144-DDBLK100', 'PF-BLI', 'PF-202', 'SF-101-DDBLK100', 'OR-145-DDBLK100', 'PF-DBLI', 'SF-102-DDBLU100', 'OR-144-DDBLU100', 'OR-145-DDBLU100', '912-538-C') " +
			"AND p.sales_discontinuation_date IS NULL " +
			"AND p.is_printable " + (UtilValidate.isNotEmpty(request.getParameter("printed")) && request.getParameter("printed") == "Y" ? "= 'Y' " : "IN ('N', NULL) ") + 
		"ORDER BY COLOR_GROUP"

    ResultSet rs = null;
    rs = du.executeQuery(sqlCommand);
    if(rs != null) {
        while(rs.next()) {
        	Map<String, Object> productData = new HashMap<String, Object>();
            productData.put("color", rs.getString(1));
            productData.put("name", rs.getString(2) + (UtilValidate.isNotEmpty(rs.getString(3)) ? " " + rs.getString(3) : "") + (UtilValidate.isNotEmpty(rs.getString(4)) ? " " + rs.getString(4) : ""));
            if (UtilValidate.isNotEmpty(productData)) {
            	productList.add(productData);
        	}
        }
    }
} finally {
	try {
		if (du != null) {
			du.close();
		}
	} catch (GenericDataSourceException gdse) {
		EnvUtil.reportError(gdse);
		Debug.logError(gdse, "There was an issue trying to close SQLProcessor", module);
	}
}

context.put("productList", productList);
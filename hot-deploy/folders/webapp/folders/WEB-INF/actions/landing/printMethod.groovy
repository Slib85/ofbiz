import com.envelopes.util.*;
import java.util.*;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.webapp.website.WebSiteWorker;
import com.bigname.search.elasticsearch.SearchBuilder;
import com.bigname.search.elasticsearch.SearchResult;
import com.envelopes.util.EnvUtil;

Map<String, Object> printMethodInfo = new HashMap<>();

Map<String, Object> params = EnvUtil.getParameterMap(request);
String printMethodType = (String) params.get("id");

printMethodInfo.put("imageName", printMethodType);

if (printMethodType.equals("foilStamp")) {
	printMethodInfo.put("h1", "Foil Stamping");
	printMethodInfo.put("bannerText", "Foil Stamping adds a polished and professional look to any custom product. Foil Stamped Folders use the application of heat, pressure, and metallic or non-metallic foil to create stunning results for your brand. If you're looking to add a touch of flair to your artwork, Foil Stamping is a great way to achieve it.");
	printMethodInfo.put("bannerImage", "foilStampBanner");
	printMethodInfo.put("standardFolderProductImage", "SF-101_FOIL_5_UP");
	printMethodInfo.put("standardFolderProductLink", "product/~category_id=STANDARD_FOLDER/~product_id=912-501-C/~pocket_type=STANDARD/~print_method=FOIL");
	printMethodInfo.put("legalProductImage", "LF_118_FOIL_2_UP");
	printMethodInfo.put("legalProductLink", "product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-501-C/~pocket_type=STANDARD/~print_method=FOIL");
	printMethodInfo.put("certificateHolderProductImage", "CHEL-185_FOIL_1_GROUP_2");
	printMethodInfo.put("certificateHolderProductLink", "product/~category_id=CERTIFICATE_HOLDERS/~product_id=CHEL-185-501-C/~pocket_type=STANDARD/~print_method=FOIL");
	printMethodInfo.put("cardHolderProductImage", "CKH_2709_FOIL_2_GROUP");
	printMethodInfo.put("cardHolderProductLink", "customFolders?categoryId=Small%20Folder");
	printMethodInfo.put("sidebarCopyHeader", "Metallic & Non-Metallic Foil Colors");
	printMethodInfo.put("sidebarCopy", "Browse a variety of both metallic and non-metallic foil colors. Metallic foils shimmer and shine to have your artwork pop, while Non-Metallic foils are vibrant with just a hint of gloss to compliment your design.");
	printMethodInfo.put("swatchBookLink", "foil-color-guide");
	printMethodInfo.put("type", "foilStamp");

} else if (printMethodType.equals("spotColor")) {
	printMethodInfo.put("h1", "Spot Color Printing");
	printMethodInfo.put("bannerText", "Spot Color Printing makes your logo or design stand out and keeps you on brand. For designs that need an exact color printed, spot color printing is used as the most efficient method of controlling color for the design you need.");
	printMethodInfo.put("bannerImage", "spotColorBanner");
	printMethodInfo.put("standardFolderProductImage", "SF-101_COL_3");
	printMethodInfo.put("standardFolderProductLink", "product/~category_id=STANDARD_FOLDER/~product_id=912-501-C/~pocket_type=STANDARD/~print_method=SPOT");
	printMethodInfo.put("legalProductImage", "LF-1417_COL_1_UP");
	printMethodInfo.put("legalProductLink", "product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-501-C/~pocket_type=STANDARD/~print_method=SPOT");
	printMethodInfo.put("certificateHolderProductImage", "CHE-185_COL_2_GROUP_1");
	printMethodInfo.put("certificateHolderProductLink", "product/~category_id=CERTIFICATE_HOLDERS/~product_id=CHEL-185-501-C/~pocket_type=STANDARD/~print_method=SPOT");
	printMethodInfo.put("cardHolderProductImage", "CKH-2711-B-1");
	printMethodInfo.put("cardHolderProductLink", "customFolders?categoryId=Small%20Folder");
	printMethodInfo.put("sidebarCopyHeader", "Popular Paper Stock Colors");
	printMethodInfo.put("sidebarCopy", "Browse a variety of our most popular stock colors. With a selection of plain, vibrant and rich colors, you will be sure to match your brand and look professional.");
	printMethodInfo.put("swatchBookLink", "paperStocks");
	printMethodInfo.put("type", "spotColor");

} else if (printMethodType.equals("fourColor")) {
	printMethodInfo.put("h1", "Four Color Printing");
	printMethodInfo.put("bannerText", "Four Color Printed Folders are the perfect method for reproducing images and artwork. If your design includes full-color photographs, Four Color Printing is necessary. The process is also known as CMYK Printing and is sure to bring your artwork to life.");
	printMethodInfo.put("bannerImage", "fourColorBanner");
	printMethodInfo.put("standardFolderProductImage", "SF-101_COL_12");
	printMethodInfo.put("standardFolderProductLink", "product/~category_id=STANDARD_FOLDER/~product_id=912-501-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR");
	printMethodInfo.put("legalProductImage", "LFT-2913_COL_1_UP");
	printMethodInfo.put("legalProductLink", "product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-501-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR");
	printMethodInfo.put("certificateHolderProductImage", "CHEPF_185_COL_UP_2_DOC");
	printMethodInfo.put("certificateHolderProductLink", "product/~category_id=/~product_id=CHEL-185-501-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR");
	printMethodInfo.put("cardHolderProductImage", "CKH-2705_COL_UP_1");
	printMethodInfo.put("cardHolderProductLink", "customFolders?categoryId=Small%20Folder");
	printMethodInfo.put("sidebarCopyHeader", "Paper Stock Colors");
	printMethodInfo.put("sidebarCopy", "Browse a variety of our most popular stock colors. With a selection of plain, vibrant and rich colors, you will be sure to match your brand and look professional.");
	printMethodInfo.put("swatchBookLink", "paperStocks");
	printMethodInfo.put("type", "fourColor");

} else if (printMethodType.equals("embossing")) {
	printMethodInfo.put("h1", "Embossing");
	printMethodInfo.put("bannerText", "Embossing is a great way to customize your products in a subtle and professional way. Embossed Folders use heat and pressure, along with metal dies that fit together to squeeze the fibers of the paper stock, and creates raised artwork for a classic custom look.");
	printMethodInfo.put("bannerImage", "embossingBanner");
	printMethodInfo.put("standardFolderProductImage", "ACC_EMB_1");
	printMethodInfo.put("standardFolderProductLink", "product/~category_id=STANDARD_FOLDER/~product_id=912-501-C/~pocket_type=STANDARD/~print_method=EMBOSS");
	printMethodInfo.put("legalProductImage", "LEGAL_EMB_1");
	printMethodInfo.put("legalProductLink", "product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-501-C/~pocket_type=STANDARD/~print_method=EMBOSS");
	printMethodInfo.put("certificateHolderProductImage", "CHEL-185_EMB_1_GROUP_1");
	printMethodInfo.put("certificateHolderProductLink", "product/~category_id=CERTIFICATE_HOLDERS/~product_id=CHEL-185-501-C/~pocket_type=STANDARD/~print_method=EMBOSS");
	printMethodInfo.put("cardHolderProductImage", "CKH-2705_EMB_1_UP");
	printMethodInfo.put("cardHolderProductLink", "customFolders?categoryId=Small%20Folder");
	printMethodInfo.put("sidebarCopyHeader", "Popular Paper Stock Colors");
	printMethodInfo.put("sidebarCopy", "Browse a variety of our most popular stock colors. With a selection of plain, vibrant and rich colors, you will be sure to match your brand and look professional.");
	printMethodInfo.put("swatchBookLink", "paperStocks");
	printMethodInfo.put("type", "embossing");

}

List<String> spotColorEmboss = new ArrayList<>();
spotColorEmboss.add("912-501-C");
spotColorEmboss.add("912-505-C");
spotColorEmboss.add("912-510-C");
spotColorEmboss.add("912-511-C");
spotColorEmboss.add("912-508-C");
spotColorEmboss.add("912-546-C");
spotColorEmboss.add("912-560-C");
spotColorEmboss.add("912-540-C");
spotColorEmboss.add("912-533-C");
spotColorEmboss.add("912-554-C");

try {
	StringBuilder docids = new StringBuilder("");
	for(String sku : spotColorEmboss) {
		docids.append((UtilValidate.isNotEmpty(docids.toString())) ? "," + sku : sku);
	}

	params.put("docids", docids.toString());
	SearchBuilder builder = new SearchBuilder(delegator, params, WebSiteWorker.getWebSiteId(request));
	builder.executeSearch();

	SearchResult result = builder.getSearchResult();
	context.put("spotColorEmboss", result.getHits());
} catch(Exception e) {
	EnvUtil.reportError(e);
}

List<String> fourColor = new ArrayList<>();
fourColor.add("912-502-C");
fourColor.add("912-507-C");
fourColor.add("912-519-C");
fourColor.add("912-514-C");
fourColor.add("912-515-C");
fourColor.add("912-516-C");
fourColor.add("912-517-C");
fourColor.add("912-519-C");
fourColor.add("912-521-C");
fourColor.add("912-503-C");
fourColor.add("912-512-C");
fourColor.add("912-513-C");
fourColor.add("912-520-C");
fourColor.add("912-523-C");
fourColor.add("912-525-C");
fourColor.add("912-522-C");
fourColor.add("912-524-C");
fourColor.add("912-526-C");
fourColor.add("912-527-C");

try {
	StringBuilder docids = new StringBuilder("");
	for(String sku : fourColor) {
		docids.append((UtilValidate.isNotEmpty(docids.toString())) ? "," + sku : sku);
	}

	params.put("docids", docids.toString());
	SearchBuilder builder = new SearchBuilder(delegator, params, WebSiteWorker.getWebSiteId(request));
	builder.executeSearch();

	SearchResult result = builder.getSearchResult();
	context.put("fourColor", result.getHits());
} catch(Exception e) {
	EnvUtil.reportError(e);
} 

context.printMethodInfo = printMethodInfo; 
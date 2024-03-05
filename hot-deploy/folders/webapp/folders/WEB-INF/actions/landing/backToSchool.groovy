import java.util.*;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.webapp.website.WebSiteWorker;
import com.bigname.search.elasticsearch.SearchBuilder;
import com.bigname.search.elasticsearch.SearchResult;
import com.envelopes.util.EnvUtil;

String module = "backToSchool.groovy";

Map<String, Object> params = EnvUtil.getParameterMap(request);

List<String> folders = new ArrayList<>();
folders.add("912-501-C");
folders.add("912-502-C");
folders.add("912-510-C");
folders.add("912-508-C");
folders.add("912-546-C");
folders.add("912-540-C");
folders.add("912-534-C");
folders.add("912-523-C");
folders.add("912-555-C");
folders.add("912-535-C");
folders.add("912-528-C");
folders.add("3DF-0853");
folders.add("SF-111");
folders.add("SF-102");
folders.add("OR-144");
folders.add("FT-0822");
folders.add("HF-6802");
folders.add("HF-6801");
folders.add("CKH-2711");
folders.add("PHP-0303");
folders.add("PF-DBLI");
folders.add("SF-101-AW100");
folders.add("SF-101-CMBLK12");
folders.add("SF-101-CS80");
folders.add("SF-101-546-TANG");
folders.add("SF-101-BT80");
folders.add("SF-101-SG12");
folders.add("08-96");
folders.add("08-96-FOIL");
folders.add("OR-145-DDBLU100");
folders.add("OR-144-SG12");
folders.add("SF-102-DDBLU100");
folders.add("PF-25PACKASST");
folders.add("PF-25PACKBURG");
folders.add("WEL-DDBLU100-GF");
folders.add("WEL-10PACK");
folders.add("49F-NLI");
folders.add("MF-144-DB100");
folders.add("KCH-22");
folders.add("CON-912F-BLI");
folders.add("PF-201");
folders.add("PF-200");
folders.add("PLYFDSNAVY");
folders.add("PF912BLACK-BRAD");
folders.add("PLYF3HPCPCLEAR");
folders.add("PLYF912RED-BRAD");
folders.add("PLYFVFBLUE");
folders.add("PLYF9124PMBLUE");
folders.add("PLYFWAVE-ASST");

try {
	StringBuilder docids = new StringBuilder("");
	for(String sku : folders) {
		docids.append((UtilValidate.isNotEmpty(docids.toString())) ? "," + sku : sku);
	}

	params.put("docids", docids.toString());
	SearchBuilder builder = new SearchBuilder(delegator, params, WebSiteWorker.getWebSiteId(request));
	builder.executeSearch();

	SearchResult result = builder.getSearchResult();
	context.put("folders", result.getHits());
} catch(Exception e) {
	EnvUtil.reportError(e);
}

List<String> holdersCovers = new ArrayList<>();
holdersCovers.add("CHEL-185-DDBLK100-F");
holdersCovers.add("CHEL-185-DDBLU100-F");
holdersCovers.add("CH-SF-300");
holdersCovers.add("CH-SF-301");
holdersCovers.add("CHEL-185-DDBLU100-FLORALGF");
holdersCovers.add("CHEL-185-DDBLK100-FLORALGF");
holdersCovers.add("CHEL-185-DDBLU100-SQGF");
holdersCovers.add("CHEL-185-DDBLK100-SQGF");
holdersCovers.add("CHEL-185-DDBLK100-SQSF");
holdersCovers.add("CHEL-185-DDBLU100-SQSF");
holdersCovers.add("CHEL-185-DDBLU100");
holdersCovers.add("CHEL-185-DDBLK100");
holdersCovers.add("CHEL-185-DDP100");
holdersCovers.add("CHEL-185-DB100");
holdersCovers.add("CHEL-185-BN100");
holdersCovers.add("CH91212-WLI");
holdersCovers.add("CH91212-GB");
holdersCovers.add("CHEL-185-SG12");
holdersCovers.add("CH91212-WG120");
holdersCovers.add("CH91212-22");
holdersCovers.add("CH91212-M06");
holdersCovers.add("CH91212-M07");
holdersCovers.add("SCH-BLI");
holdersCovers.add("SCH-WLI");
holdersCovers.add("SCH-BULI");
holdersCovers.add("SCH-NLI");
holdersCovers.add("SCH-M07");
holdersCovers.add("SCH-M06");
holdersCovers.add("SCH-22");
holdersCovers.add("SCH-26");
holdersCovers.add("SCH-10");
holdersCovers.add("SCH-WG120");
holdersCovers.add("SCH-GB");
holdersCovers.add("PDCL-85X11-DB");
holdersCovers.add("PDCL-85X11-NB");
holdersCovers.add("CH-313");
holdersCovers.add("CH-315");
holdersCovers.add("CH-311");
holdersCovers.add("CH-317");
holdersCovers.add("CH-312");
holdersCovers.add("CH-322");
holdersCovers.add("CH-325");
holdersCovers.add("CH-348");
holdersCovers.add("CH-321");
holdersCovers.add("CH-318");
holdersCovers.add("CH-319");
holdersCovers.add("CH-320");
holdersCovers.add("CH-326");
holdersCovers.add("CH-323");
holdersCovers.add("SCH-BGLI");
holdersCovers.add("SCH-GNLI");
holdersCovers.add("DC-207");
holdersCovers.add("DC-208");
holdersCovers.add("DC-206");
holdersCovers.add("DC-216");
holdersCovers.add("DC-200");
holdersCovers.add("DC-201");
holdersCovers.add("DC-215");
holdersCovers.add("DC-218");
holdersCovers.add("DC-217");
holdersCovers.add("DC-219");
holdersCovers.add("DC-211");
holdersCovers.add("DC-213");
holdersCovers.add("DC-214");
holdersCovers.add("DC-212");
holdersCovers.add("DC-203");
holdersCovers.add("DC-202");
holdersCovers.add("DC-210");
holdersCovers.add("CH-309");
holdersCovers.add("CH-338");
holdersCovers.add("CH-339");
holdersCovers.add("CH-340");
holdersCovers.add("CH-341");
holdersCovers.add("CH-342");
holdersCovers.add("CH-310");
holdersCovers.add("CH-307");
holdersCovers.add("CH-333");
holdersCovers.add("CH-334");
holdersCovers.add("CH-336"); 
holdersCovers.add("CH-335"); 
holdersCovers.add("CH-337"); 
holdersCovers.add("CH-328"); 
holdersCovers.add("CH-329"); 
holdersCovers.add("CH-306");
holdersCovers.add("CH-331"); 
holdersCovers.add("CH-332"); 
holdersCovers.add("CH-330"); 
holdersCovers.add("CH-305"); 
holdersCovers.add("AS-200"); 
holdersCovers.add("AS-201"); 

try {
	StringBuilder docids = new StringBuilder("");
	for(String sku : holdersCovers) {
		docids.append((UtilValidate.isNotEmpty(docids.toString())) ? "," + sku : sku);
	}

	params.put("docids", docids.toString());
	SearchBuilder builder = new SearchBuilder(delegator, params, WebSiteWorker.getWebSiteId(request));
	builder.executeSearch();

	SearchResult result = builder.getSearchResult();
	context.put("holdersCovers", result.getHits());
} catch(Exception e) {
	EnvUtil.reportError(e);
}

List<String> foilSeals = new ArrayList<>();
foilSeals.add("CERT-70WBLU");
foilSeals.add("CERT-70WBLK");
foilSeals.add("CERT-70WRED");
foilSeals.add("CERT-70WGLD");
foilSeals.add("CERTIF-BLA");
foilSeals.add("CERTIF-PAR");
foilSeals.add("CERT-29AWA");
foilSeals.add("CERT-29BLA");
foilSeals.add("CERT-29PAR");
foilSeals.add("CERT-29APP");
foilSeals.add("FA-200");
foilSeals.add("FS-305");
foilSeals.add("FS-307");
foilSeals.add("FS-306");
foilSeals.add("FS-309");
foilSeals.add("FS-308");
foilSeals.add("FS-301");
foilSeals.add("FS-302");
foilSeals.add("FS-304");
foilSeals.add("FS-303");
foilSeals.add("FS-300");
foilSeals.add("CERT-302");

try {
	StringBuilder docids = new StringBuilder("");
	for(String sku : foilSeals) {
		docids.append((UtilValidate.isNotEmpty(docids.toString())) ? "," + sku : sku);
	}

	params.put("docids", docids.toString());
	SearchBuilder builder = new SearchBuilder(delegator, params, WebSiteWorker.getWebSiteId(request));
	builder.executeSearch();

	SearchResult result = builder.getSearchResult();
	context.put("foilSeals", result.getHits());
} catch(Exception e) {
	EnvUtil.reportError(e);
}

List<String> reportCovers = new ArrayList<>();
reportCovers.add("RCW-7509");
reportCovers.add("RCW-192");
reportCovers.add("RC-7501");
reportCovers.add("RC-190");
reportCovers.add("RCFT-0922");
reportCovers.add("RC-0901");
reportCovers.add("RC81211-CDBL");
reportCovers.add("RC81211-LB");
reportCovers.add("RC81211-BK");
reportCovers.add("RC81211-BASST");
reportCovers.add("RC81211-DASST");
reportCovers.add("RC81211-CT");
reportCovers.add("RC81211-ECBK");
reportCovers.add("RC-400");
reportCovers.add("RC81211-SGBK");

try {
	StringBuilder docids = new StringBuilder("");
	for(String sku : reportCovers) {
		docids.append((UtilValidate.isNotEmpty(docids.toString())) ? "," + sku : sku);
	}

	params.put("docids", docids.toString());
	SearchBuilder builder = new SearchBuilder(delegator, params, WebSiteWorker.getWebSiteId(request));
	builder.executeSearch();

	SearchResult result = builder.getSearchResult();
	context.put("reportCovers", result.getHits());
} catch(Exception e) {
	EnvUtil.reportError(e);
}

List<String> binders = new ArrayList<>();
binders.add("EF-FD-07564");
binders.add("EF-155");
binders.add("EF-3512");
binders.add("F-1602");
binders.add("EF-3502");
binders.add("PB-1SRBLACK");
binders.add("PB-TUFFY");
binders.add("PB-12SRWHITE");
binders.add("PB-2SRBLACK");
binders.add("PB-2RREWHITE");
binders.add("PB-2RREBLUE");
binders.add("PB-3RREBLACK");
binders.add("PB-3SRWHITE");
binders.add("BP81211SL-ASST");
binders.add("PB-207");
binders.add("FA-205");

try {
	StringBuilder docids = new StringBuilder("");
	for(String sku : binders) {
		docids.append((UtilValidate.isNotEmpty(docids.toString())) ? "," + sku : sku);
	}

	params.put("docids", docids.toString());
	SearchBuilder builder = new SearchBuilder(delegator, params, WebSiteWorker.getWebSiteId(request));
	builder.executeSearch();

	SearchResult result = builder.getSearchResult();
	context.put("binders", result.getHits());
} catch(Exception e) {
	EnvUtil.reportError(e);
}

List<String> accessories = new ArrayList<>();
accessories.add("ACCO-SAPF");
accessories.add("2SAFPL");
accessories.add("DTF-3PRONG");
accessories.add("3HP12SH-BL");
accessories.add("3HP12SH-S");
accessories.add("3HP20SH-S");
accessories.add("3HP12SH-PU");
accessories.add("3HP12SH-PK");
accessories.add("2HP40SH-S");
accessories.add("2HP20SH-S");
accessories.add("1HP10SH-GY");
accessories.add("3HPELEC-GY");

try {
	StringBuilder docids = new StringBuilder("");
	for(String sku : accessories) {
		docids.append((UtilValidate.isNotEmpty(docids.toString())) ? "," + sku : sku);
	}

	params.put("docids", docids.toString());
	SearchBuilder builder = new SearchBuilder(delegator, params, WebSiteWorker.getWebSiteId(request));
	builder.executeSearch();

	SearchResult result = builder.getSearchResult();
	context.put("accessories", result.getHits());
} catch(Exception e) {
	EnvUtil.reportError(e);
}
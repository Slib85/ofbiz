import java.util.*;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.webapp.website.WebSiteWorker;
import com.bigname.search.elasticsearch.SearchBuilder;
import com.bigname.search.elasticsearch.SearchResult;
import com.envelopes.util.EnvUtil;

String module = "customSamplePack.groovy";

Map<String, Object> params = EnvUtil.getParameterMap(request);

List<String> presentationFolders = new ArrayList<>();
presentationFolders.add("SF-101");
presentationFolders.add("WP-0880");
presentationFolders.add("VS-105");
presentationFolders.add("3DF-0803");
presentationFolders.add("SF-0882");
presentationFolders.add("RP-0863");
presentationFolders.add("SF-0810");
presentationFolders.add("VS-104");
presentationFolders.add("VSRP-0830");

try {
	StringBuilder docids = new StringBuilder("");
	for(String sku : presentationFolders) {
		docids.append((UtilValidate.isNotEmpty(docids.toString())) ? "," + sku : sku);
	}

	params.put("docids", docids.toString());
	SearchBuilder builder = new SearchBuilder(delegator, params, WebSiteWorker.getWebSiteId(request));
	builder.executeSearch();

	SearchResult result = builder.getSearchResult();
	context.put("presentationFolders", result.getHits());
} catch(Exception e) {
	EnvUtil.reportError(e);
}

List<String> quickShip = new ArrayList<>();
quickShip.add("08-96-FOIL");
quickShip.add("08-96");

try {
	StringBuilder docids = new StringBuilder("");
	for(String sku : quickShip) {
		docids.append((UtilValidate.isNotEmpty(docids.toString())) ? "," + sku : sku);
	}

	params.put("docids", docids.toString());
	SearchBuilder builder = new SearchBuilder(delegator, params, WebSiteWorker.getWebSiteId(request));
	builder.executeSearch();

	SearchResult result = builder.getSearchResult();
	context.put("quickShip", result.getHits());
} catch(Exception e) {
	EnvUtil.reportError(e);
}

List<String> specialty = new ArrayList<>();
specialty.add("SF-0804");
specialty.add("STF-0816");
specialty.add("SPDAT-FD-06684");
specialty.add("SPDAT-FD-06521");
specialty.add("SPDAT-FD-04515");
specialty.add("SF-111");
specialty.add("SF-08-FLASH");
specialty.add("SFTF-0856");
specialty.add("SFTF-0855");
specialty.add("V1-0852");
specialty.add("VS-112");
specialty.add("FT-130");
specialty.add("FT-0822");
specialty.add("SF-102");
specialty.add("OR-144");
specialty.add("OR-145");
specialty.add("3DF-143");
specialty.add("SPDAT-FD-06521");
specialty.add("V1-0852");
specialty.add("TPF-3808");
specialty.add("TPF-3803");

try {
	StringBuilder docids = new StringBuilder("");
	for(String sku : specialty) {
		docids.append((UtilValidate.isNotEmpty(docids.toString())) ? "," + sku : sku);
	}

	params.put("docids", docids.toString());
	SearchBuilder builder = new SearchBuilder(delegator, params, WebSiteWorker.getWebSiteId(request));
	builder.executeSearch();

	SearchResult result = builder.getSearchResult();
	context.put("specialty", result.getHits());
} catch(Exception e) {
	EnvUtil.reportError(e);
}

List<String> legal = new ArrayList<>();
legal.add("LF-118");
legal.add("CL-1465");
legal.add("3DLF-168");

try {
	StringBuilder docids = new StringBuilder("");
	for(String sku : legal) {
		docids.append((UtilValidate.isNotEmpty(docids.toString())) ? "," + sku : sku);
	}

	params.put("docids", docids.toString());
	SearchBuilder builder = new SearchBuilder(delegator, params, WebSiteWorker.getWebSiteId(request));
	builder.executeSearch();

	SearchResult result = builder.getSearchResult();
	context.put("legal", result.getHits());
} catch(Exception e) {
	EnvUtil.reportError(e);
}

List<String> reinforced = new ArrayList<>();
reinforced.add("FTRE-2977");
reinforced.add("FTRE-2976");
reinforced.add("RE-2972");
reinforced.add("RE-2971-TOP");
reinforced.add("RE-2911");

try {
	StringBuilder docids = new StringBuilder("");
	for(String sku : reinforced) {
		docids.append((UtilValidate.isNotEmpty(docids.toString())) ? "," + sku : sku);
	}

	params.put("docids", docids.toString());
	SearchBuilder builder = new SearchBuilder(delegator, params, WebSiteWorker.getWebSiteId(request));
	builder.executeSearch();

	SearchResult result = builder.getSearchResult();
	context.put("reinforced", result.getHits());
} catch(Exception e) {
	EnvUtil.reportError(e);
}

List<String> smallFolders = new ArrayList<>();
smallFolders.add("CKH-2711");
smallFolders.add("CKH-2710");
smallFolders.add("CKH-2709");
smallFolders.add("CKH-2704");
smallFolders.add("MF-4804");
smallFolders.add("MF-4801");
smallFolders.add("MF-135");
smallFolders.add("HF-6851");
smallFolders.add("HF-6802");
smallFolders.add("HF-6801");
smallFolders.add("PHP-0303");
smallFolders.add("MFC-4865");

try {
	StringBuilder docids = new StringBuilder("");
	for(String sku : smallFolders) {
		docids.append((UtilValidate.isNotEmpty(docids.toString())) ? "," + sku : sku);
	}

	params.put("docids", docids.toString());
	SearchBuilder builder = new SearchBuilder(delegator, params, WebSiteWorker.getWebSiteId(request));
	builder.executeSearch();

	SearchResult result = builder.getSearchResult();
	context.put("smallFolders", result.getHits());
} catch(Exception e) {
	EnvUtil.reportError(e);
}

List<String> extraCapacity = new ArrayList<>();
extraCapacity.add("3ED-2872");
extraCapacity.add("3DFE-2886");
extraCapacity.add("3DF-149");
extraCapacity.add("3DF-141");
extraCapacity.add("3DF-139");
extraCapacity.add("3DF-113");
extraCapacity.add("3DF-0853");
extraCapacity.add("3DF-0812");
extraCapacity.add("3DF-0811");
extraCapacity.add("3DF-0803");
extraCapacity.add("3DF-0802");

try {
	StringBuilder docids = new StringBuilder("");
	for(String sku : extraCapacity) {
		docids.append((UtilValidate.isNotEmpty(docids.toString())) ? "," + sku : sku);
	}

	params.put("docids", docids.toString());
	SearchBuilder builder = new SearchBuilder(delegator, params, WebSiteWorker.getWebSiteId(request));
	builder.executeSearch();

	SearchResult result = builder.getSearchResult();
	context.put("extraCapacity", result.getHits());
} catch(Exception e) {
	EnvUtil.reportError(e);
}

List<String> certificateHolders = new ArrayList<>();
certificateHolders.add("CHEL-183");
certificateHolders.add("CHEL-185");
certificateHolders.add("CHEL-186");
certificateHolders.add("CHEL-189");
certificateHolders.add("CHCEL-0910");
certificateHolders.add("CHERL-0911");
certificateHolders.add("CHEP-183");
certificateHolders.add("CHEP-186");
certificateHolders.add("CHEP-188");
certificateHolders.add("CHCEP-0910");
certificateHolders.add("CHERP-0911");

try {
	StringBuilder docids = new StringBuilder("");
	for(String sku : certificateHolders) {
		docids.append((UtilValidate.isNotEmpty(docids.toString())) ? "," + sku : sku);
	}

	params.put("docids", docids.toString());
	SearchBuilder builder = new SearchBuilder(delegator, params, WebSiteWorker.getWebSiteId(request));
	builder.executeSearch();

	SearchResult result = builder.getSearchResult();
	context.put("certificateHolders", result.getHits());
} catch(Exception e) {
	EnvUtil.reportError(e);
}
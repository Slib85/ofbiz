import java.util.*;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.webapp.website.WebSiteWorker;
import com.bigname.search.elasticsearch.SearchBuilder;
import com.bigname.search.elasticsearch.SearchResult;
import com.envelopes.util.EnvUtil;

String module = "backToSchool.groovy";

Map<String, Object> params = EnvUtil.getParameterMap(request);

List<String> envelopes = new ArrayList<>();
envelopes.add("75746");
envelopes.add("4860-70W");
envelopes.add("4860-BULI");
envelopes.add("WS-3342");
envelopes.add("43703");
envelopes.add("75761");
envelopes.add("45161");
envelopes.add("WS-3342");
envelopes.add("43554");
envelopes.add("61549");
envelopes.add("1590");
envelopes.add("12310");
envelopes.add("11874");
envelopes.add("17905");
envelopes.add("4880-GB");
envelopes.add("1CO-ULIM");
envelopes.add("LUXLEVC-103");
envelopes.add("2X2CO-80N");
envelopes.add("WS-4618");
envelopes.add("WS-4642");
envelopes.add("SMD89521");
envelopes.add("SMD89523");
envelopes.add("SMD89522");
envelopes.add("SMD89527");
envelopes.add("SMD89540");
envelopes.add("SMD89543");
envelopes.add("SMD89544");
envelopes.add("SMD89542");
envelopes.add("SMD89547");
envelopes.add("PFX90016");

try {
	StringBuilder docids = new StringBuilder("");
	for(String sku : envelopes) {
		docids.append((UtilValidate.isNotEmpty(docids.toString())) ? "," + sku : sku);
	}

	params.put("docids", docids.toString());
	SearchBuilder builder = new SearchBuilder(delegator, params, WebSiteWorker.getWebSiteId(request));
	builder.executeSearch();

	SearchResult result = builder.getSearchResult();
	context.put("envelopes", result.getHits());
} catch(Exception e) {
	EnvUtil.reportError(e);
}

List<String> papers = new ArrayList<>();
papers.add("81211-P-78");
papers.add("1117-P-80W");
papers.add("81211-C-40");
papers.add("1117-C-NLI");
papers.add("1218-C-WLI");
papers.add("4040-120W");
papers.add("A7FW");
papers.add("LEVC933");
papers.add("17MFW");
papers.add("OXF 04736");
papers.add("OXF 61100");
papers.add("OXF 61200");
papers.add("PFX10009");
papers.add("PFX10009");
papers.add("AVE5388");
papers.add("FP1012X812CR-W");
papers.add("FP1012X812WR-W");
papers.add("FP3HCR-W");
papers.add("FP812X11CR-W");
papers.add("FP812X11COR-W");
papers.add("FP512X8127HP-W");
papers.add("FP812X512CR-W");
papers.add("FP8113HMP-20W");
papers.add("FP3HL-24W");
papers.add("FP3HMP-20W");
papers.add("FP3HMP-24W");
papers.add("FP3HRECY-20W");
papers.add("FP43H-20W");
papers.add("81211-P-23");
papers.add("81211-P-67");
papers.add("81211-C-13");
papers.add("81211-C-VIO");
papers.add("81214-P-GB ");
papers.add("81214-C-L07");
papers.add("1212-P-07");
papers.add("1212-C-W");
papers.add("1218-P-WLI");
papers.add("1319-P-80W");
papers.add("1319-C-B");

try {
	StringBuilder docids = new StringBuilder("");
	for(String sku : papers) {
		docids.append((UtilValidate.isNotEmpty(docids.toString())) ? "," + sku : sku);
	}

	params.put("docids", docids.toString());
	SearchBuilder builder = new SearchBuilder(delegator, params, WebSiteWorker.getWebSiteId(request));
	builder.executeSearch();

	SearchResult result = builder.getSearchResult();
	context.put("papers", result.getHits());
} catch(Exception e) {
	EnvUtil.reportError(e);
}

List<String> folders = new ArrayList<>();
folders.add("PF-DBLI");
folders.add("PF-130W");
folders.add("SF-101-SG12");
folders.add("LUX-PF-22");
folders.add("PF-M07");
folders.add("SF-101-BT80");
folders.add("SF-101-CMBLU12");
folders.add("PF-25PACKASST");
folders.add("LUX-PF-56");
folders.add("PF-GB");
folders.add("OR-144-SG12");
folders.add("SF-101-546-TANG");
folders.add("PF912ASST100PDQ-BRAD");
folders.add("PF-25PACKBURG");
folders.add("PLYF912CLEAR-BRAD");
folders.add("OR-145-CSG100");
folders.add("OR-144-DDBLK100");
folders.add("SF-102-DDBLU100");
folders.add("PLYF3HPCPBRGREEN");
folders.add("PLYF3HPCPCLEAR");

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

List<String> notebooks = new ArrayList<>();
notebooks.add("NP3X5NR-ASST");
notebooks.add("NP3X5NR-W");
notebooks.add("MB3X5NR-ASST");
notebooks.add("NP8X11QR-W");
notebooks.add("NP3X5-BASST");
notebooks.add("NP3X5-DASST");
notebooks.add("NP5X8-W");
notebooks.add("NP5X8AP-W");
notebooks.add("NP5X8DS-ASST");
notebooks.add("NP5X8MR-IV");
notebooks.add("NP5X8NRR-W");
notebooks.add("NP5X8P-ASST");
notebooks.add("NP5X8NR-ASST");
notebooks.add("NP6X9GR-ASST");
notebooks.add("NP6X9NR-ASST");
notebooks.add("NB7X5CR-BL");
notebooks.add("NB8X1012CR-ASST");
notebooks.add("NB8X1012WR-ASST");
notebooks.add("PLN812X634-B");
notebooks.add("PLN812X634-GN");
notebooks.add("LP8X11CR-W");
notebooks.add("NP8X112HP-Y");
notebooks.add("NP8X113HP-W");
notebooks.add("NP8X113HP-Y");
notebooks.add("NP8X11LR-BL");
notebooks.add("NP8X11LR-W");
notebooks.add("NP8X11WR-W");
notebooks.add("NP8X11WR-Y");
notebooks.add("NP8X11WRR-W");
notebooks.add("NB9X6CR-BL");
notebooks.add("PLN9X7-GN");
notebooks.add("CB9X7WR-ASST");
notebooks.add("CB9X7WR-B");
notebooks.add("NB11X9CR-BL");

try {
	StringBuilder docids = new StringBuilder("");
	for(String sku : notebooks) {
		docids.append((UtilValidate.isNotEmpty(docids.toString())) ? "," + sku : sku);
	}

	params.put("docids", docids.toString());
	SearchBuilder builder = new SearchBuilder(delegator, params, WebSiteWorker.getWebSiteId(request));
	builder.executeSearch();

	SearchResult result = builder.getSearchResult();
	context.put("notebooks", result.getHits());
} catch(Exception e) {
	EnvUtil.reportError(e);
}

List<String> holePunchers = new ArrayList<>();
holePunchers.add("1HP10SH-GY");
holePunchers.add("3HP12SH-BL");
holePunchers.add("3HP12SH-PU");
holePunchers.add("3HP12SH-S");
holePunchers.add("3HP12SH-PK");
holePunchers.add("3HP20SH-S");
holePunchers.add("2HP20SH-S");
holePunchers.add("2HP40SH-S");
holePunchers.add("3HPELEC-GY");

try {
	StringBuilder docids = new StringBuilder("");
	for(String sku : holePunchers) {
		docids.append((UtilValidate.isNotEmpty(docids.toString())) ? "," + sku : sku);
	}

	params.put("docids", docids.toString());
	SearchBuilder builder = new SearchBuilder(delegator, params, WebSiteWorker.getWebSiteId(request));
	builder.executeSearch();

	SearchResult result = builder.getSearchResult();
	context.put("holePunchers", result.getHits());
} catch(Exception e) {
	EnvUtil.reportError(e);
}

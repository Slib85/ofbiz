import java.util.*;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.webapp.website.WebSiteWorker;
import com.bigname.search.elasticsearch.SearchBuilder;
import com.bigname.search.elasticsearch.SearchResult;
import com.envelopes.util.EnvUtil;

String module = "graduationShop.groovy";

Map<String, Object> params = EnvUtil.getParameterMap(request);

List<String> certificateHolders = new ArrayList<>();
certificateHolders.add("CHEL-183-501-C");
certificateHolders.add("CHEL-185-501-C");
certificateHolders.add("CHEL-185");
certificateHolders.add("CHEL-183");
certificateHolders.add("CHEL-186");
certificateHolders.add("CHERP-0911");
certificateHolders.add("CHFP-0916");
certificateHolders.add("CHFL-0916");
certificateHolders.add("CHEL-185-DDBLU100");
certificateHolders.add("CHEL-185-DDBLK100");
certificateHolders.add("CHEL-185-DB100");
certificateHolders.add("CHEL-185-DDP100");
certificateHolders.add("CH91212-22");
certificateHolders.add("CHEL-185-DDBLU100-F");
certificateHolders.add("CH-SF-300");
certificateHolders.add("CHEL185-DDBLU100-FLORALSF");
certificateHolders.add("CHEL-185-DDBLK100-FLORALGF");
certificateHolders.add("SCH-BULI");
certificateHolders.add("SCH-BLI");
certificateHolders.add("CH-319");
certificateHolders.add("CH-311");
certificateHolders.add("CH-321");
certificateHolders.add("CH-334");
certificateHolders.add("CH-342");
certificateHolders.add("CH-306");
certificateHolders.add("CH-369");
certificateHolders.add("CH-370");
certificateHolders.add("CH-365");
certificateHolders.add("CH-366");
certificateHolders.add("CH-367");
certificateHolders.add("CH-368");
certificateHolders.add("CH-364");
certificateHolders.add("CH-376");
certificateHolders.add("CH-377");
certificateHolders.add("CH-378");
certificateHolders.add("CH-379");
certificateHolders.add("CH-380");
certificateHolders.add("CH-355");
certificateHolders.add("CH-356");
certificateHolders.add("CH-362");
certificateHolders.add("CH-363");
certificateHolders.add("CH-371");
certificateHolders.add("CH-373");
certificateHolders.add("CH-374");
certificateHolders.add("CH-358");
certificateHolders.add("CH-359");
certificateHolders.add("CH-360");
certificateHolders.add("CH-361");
certificateHolders.add("CH-372");
certificateHolders.add("CH-357");
certificateHolders.add("CH-353");
certificateHolders.add("CH-351");
certificateHolders.add("CH-352");
certificateHolders.add("CERTIF-APP");
certificateHolders.add("CERTIF-BLA");


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

List<String> paddedDiplomaCovers = new ArrayList<>();
paddedDiplomaCovers.add("PDCL-8x10");
paddedDiplomaCovers.add("PDCL-8.5x14");
paddedDiplomaCovers.add("PDCL-8.5x11");
paddedDiplomaCovers.add("PDCP-8.5x14");
paddedDiplomaCovers.add("PDCL-85x11-NB");
paddedDiplomaCovers.add("PDCL-85x11-DB");
paddedDiplomaCovers.add("DC-202");
paddedDiplomaCovers.add("DC-210");
paddedDiplomaCovers.add("DC-200");
paddedDiplomaCovers.add("DC-216");
paddedDiplomaCovers.add("AS-200");
paddedDiplomaCovers.add("AS-202");
paddedDiplomaCovers.add("AS-201");
paddedDiplomaCovers.add("FS-304");
paddedDiplomaCovers.add("FS-2020");
paddedDiplomaCovers.add("FS-300");
paddedDiplomaCovers.add("FS-301");
paddedDiplomaCovers.add("FS-303");
paddedDiplomaCovers.add("FS-306");
paddedDiplomaCovers.add("FS-307");
paddedDiplomaCovers.add("FS-305");
paddedDiplomaCovers.add("FS-308");
paddedDiplomaCovers.add("FS-309");
paddedDiplomaCovers.add("FA-200");

try {
	StringBuilder docids = new StringBuilder("");
	for(String sku : paddedDiplomaCovers) {
		docids.append((UtilValidate.isNotEmpty(docids.toString())) ? "," + sku : sku);
	}

	params.put("docids", docids.toString());
	SearchBuilder builder = new SearchBuilder(delegator, params, WebSiteWorker.getWebSiteId(request));
	builder.executeSearch();

	SearchResult result = builder.getSearchResult();
	context.put("paddedDiplomaCovers", result.getHits());
} catch(Exception e) {
	EnvUtil.reportError(e);
}

List<String> presentationFolders = new ArrayList<>();
presentationFolders.add("912-501-C");
presentationFolders.add("08-96-501-C");
presentationFolders.add("08-96-FOIL-501-C");
presentationFolders.add("SF-101");
presentationFolders.add("SF-101-SG12");
presentationFolders.add("PF-DBLI");
presentationFolders.add("SF-101-DB100");
presentationFolders.add("SF-101-CSG100");
presentationFolders.add("SF-101-DDP100");
presentationFolders.add("PF-BLI");
presentationFolders.add("PF-WLI");
presentationFolders.add("SF-101-546-TANG");

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



List<String> paperCertificates = new ArrayList<>();
paperCertificates.add("CERT-29APP");
paperCertificates.add("CERT-29AWA");
paperCertificates.add("CERT-29PAR");
paperCertificates.add("CERT-29BLA");
paperCertificates.add("CERTIF-PAR");
paperCertificates.add("CERT-70WGLD");
paperCertificates.add("CERT-70WBLK");
paperCertificates.add("CERT-70WRED");
paperCertificates.add("CERT-70WBLU");
paperCertificates.add("CERT-302");

try {
	StringBuilder docids = new StringBuilder("");
	for(String sku : paperCertificates) {
		docids.append((UtilValidate.isNotEmpty(docids.toString())) ? "," + sku : sku);
	}

	params.put("docids", docids.toString());
	SearchBuilder builder = new SearchBuilder(delegator, params, WebSiteWorker.getWebSiteId(request));
	builder.executeSearch();

	SearchResult result = builder.getSearchResult();
	context.put("paperCertificates", result.getHits());
} catch(Exception e) {
	EnvUtil.reportError(e);
}





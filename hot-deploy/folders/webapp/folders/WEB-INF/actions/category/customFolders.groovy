import org.apache.ofbiz.base.util.UtilValidate;

import com.envelopes.util.*;
import com.envelopes.product.*;
import com.bigname.search.*;
import org.apache.ofbiz.webapp.website.WebSiteWorker;
import com.bigname.search.elasticsearch.SearchBuilder;
import com.bigname.search.elasticsearch.SearchResult;

String module = "customFolders.groovy";

Map<String, Object> params = EnvUtil.getParameterMap(request);

if (UtilValidate.isNotEmpty((String) params.get("af"))) {
    try {
        params.put("cnt", "2000");
        SearchBuilder builder = new SearchBuilder(delegator, params, WebSiteWorker.getWebSiteId(request));
        builder.executeSearch();

        SearchResult result = builder.getSearchResult();
        context.put("pages", result.getTotalPages());
        context.put("totalHits", result.getTotalHits());
        context.put("hits", result.getHits());
        context.put("aggregations", result.getAggregations());
        context.put("categoryMap", SearchHelper.createCategoryMap((ArrayList) result.getHits()));
    } catch(Exception e) {
        EnvUtil.reportError(e);
    }

    if (((String) params.get("af")).matches("(.*)st\\:quickshipfoilstampedfolders(.*)") && ((String) params.get("af")).matches("(.*)st\\:quickshipfourcolorfolders(.*)")) {
        context.put("includeContent", "quickShip")
        context.put("headerTitle", "Quick Ship Folders");
        context.put("headerImage", "category-quickShipHeader");
    } else {
        context.put("headerTitle", "Blank Products");
    }
}

Map<String, Object> styles = new HashMap<>();
Map<String, Object> subStyles = new LinkedHashMap<>();
List<String> skus = new ArrayList<>();

// TRI PANEL FOLDERS

skus.add("TPF-3851");
skus.add("TPF-3803");
subStyles.put("One Pocket Folder", skus);

skus = new ArrayList<>();

skus.add("TPF-3832");
skus.add("TPF-3808");
skus.add("TP-109");
skus.add("3DF-143");
subStyles.put("Three Pocket Folder", skus);

styles.put("Tri-Panel Folder", subStyles);

// SPECIALTY FOLDERS

subStyles = new LinkedHashMap<>();

skus = new ArrayList<>();

skus.add("3DE-2872");
skus.add("3DFE-2886");
skus.add("3DF-149");
skus.add("3DF-141");
skus.add("3DF-139");
skus.add("3DF-113");
skus.add("3DF-0853");
skus.add("3DF-0812");
skus.add("3DF-0811");
skus.add("3DF-0803");
skus.add("3DF-0802");
subStyles.put("Extra Capacity Folder", skus);

skus = new ArrayList<>();

skus.add("SF-0804");
skus.add("STF-0816");
skus.add("SPDAT-FD-06684");
skus.add("SPDAT-FD-06521");
skus.add("SPDAT-FD-04515");
skus.add("SF-111");
skus.add("SF-08-Flash");
subStyles.put("1 Pocket Folder", skus);

skus = new ArrayList<>();

skus.add("FTRE-2977");
skus.add("FTRE-2976");
skus.add("RE-2972");
skus.add("RE-2971-TOP");
skus.add("RE-2971");
skus.add("RE-2911");
subStyles.put("Reinforced Folder", skus);

skus = new ArrayList<>();

skus.add("SFTF-0856");
skus.add("SFTF-0855");
skus.add("V1-0852");
subStyles.put("Tuck Tab Folder", skus);

skus = new ArrayList<>();

// skus.add("08-96-OFF");
skus.add("08-96-FOIL");
skus.add("08-96");
subStyles.put("Quick Ship Folder", skus);

skus = new ArrayList<>();

skus.add("OR-144");
skus.add("OR-145");
subStyles.put("Front Cover Card Slit Folder", skus);

skus = new ArrayList<>();

skus.add("VS-112");
subStyles.put("Vertical Pocket Folder", skus);

skus = new ArrayList<>();

skus.add("FT-130");
skus.add("FT-0822");
subStyles.put("File Tab Folder", skus);

skus = new ArrayList<>();

skus.add("SF-102");
subStyles.put("Window Folder", skus);

styles.put("Specialty 9 x 12 Presentation Folder", subStyles);

// SMALL FOLDER

subStyles = new LinkedHashMap<>();

skus = new ArrayList<>();

skus.add("HF-6851");
skus.add("HF-6802");
skus.add("HF-6801");
skus.add("HF-6801-508-C");
subStyles.put("6x9 Folder", skus);

skus = new ArrayList<>();

skus.add("MF-4804");
skus.add("MF-4801");
skus.add("MF-135");
skus.add("MF-4804-501-C");
subStyles.put("4x9 Folder", skus);

skus = new ArrayList<>();

skus.add("CKH-2711");
skus.add("CKH-2710");
skus.add("CKH-2709");
skus.add("CKH-2704");
skus.add("CKH-2709-501-C");
skus.add("CKH-2711-501-C");
skus.add("CKH-2710-501-C");
subStyles.put("Card Holder", skus);

skus = new ArrayList<>();

skus.add("PHP-0303");
subStyles.put("Photo Holder", skus);

styles.put("Small Folders", subStyles);

// REPORT COVER

subStyles = new LinkedHashMap<>();
skus = new ArrayList<>();

skus.add("RCST-197");
skus.add("RCST-193");
skus.add("RCSTW-198");
skus.add("RC-194");
skus.add("RCSTW-191");
skus.add("RCFT-0923");
skus.add("RCW-0909");
skus.add("RCW-195");
skus.add("RC-0901");
skus.add("RCFT-0922");
subStyles.put("One Piece Report Cover", skus);

skus = new ArrayList<>();

skus.add("RCW-7509");
skus.add("RCW-192");
skus.add("RC-190");
skus.add("RC-7501");
skus.add("PRC-190");
skus.add("RCW-7549");
skus.add("RC-7504");
subStyles.put("Two Piece Report Cover", skus);

styles.put("Report Covers", subStyles);

// PORTFOLIO

subStyles = new LinkedHashMap<>();
skus = new ArrayList<>();

skus.add("EF-155");
skus.add("EF-3512");
skus.add("EF-FD-07564");
skus.add("EF-3502");
subStyles.put("9 x 12 Portfolio", skus);

skus = new ArrayList<>();

skus.add("EFL-3501");
skus.add("EFL-3511");
skus.add("EFL-165");
subStyles.put("Legal Size Portfolio", skus);

skus = new ArrayList<>();

skus.add("F-1602");
subStyles.put("Large Size Portfolio", skus);

styles.put("Portfolios", subStyles);

// PHOTO HOLDER

subStyles = new LinkedHashMap<>();
skus = new ArrayList<>();

skus.add("PHLS-0305");
subStyles.put("4 x 6 Photo Holder", skus);

skus = new ArrayList<>();

skus.add("PHLS-0308");
skus.add("PHP-0307");
subStyles.put("5 x 7 Photo Holder", skus);

skus = new ArrayList<>();

skus.add("PHP-0309");
skus.add("PHLS-0310");
subStyles.put("8 x 10 Photo Holder", skus);

styles.put("Photo Holders", subStyles);


// PADDED DIPLOMA COVERS

subStyles = new LinkedHashMap<>();
skus = new ArrayList<>();

skus.add("PDCL-8x10");
skus.add("PDCL-8.5x14");
skus.add("PDCL-8.5x11");
subStyles.put("Landscape Diploma Cover", skus);

skus = new ArrayList<>();

skus.add("PDCP-8.5x14");
subStyles.put("Portrait Diploma Cover", skus);

styles.put("Padded Diploma Covers", subStyles);

// BINDER

subStyles = new LinkedHashMap<>();
skus = new ArrayList<>();

skus.add("RB-125");
skus.add("RB-169");
subStyles.put("Poly Binder", skus);

skus = new ArrayList<>();

skus.add("PRB-8849");
skus.add("PRBRE-8875");
skus.add("PRB-8804");
skus.add("PRB-8807");
skus.add("PRB-8801-ECO");
skus.add("PRB-8801");
skus.add("PRB-8805");
skus.add("PRBRE-8871");
skus.add("PRB-9901");
skus.add("PRBRE-9971");
subStyles.put("Paper Binder", skus);

styles.put("Binders", subStyles);

// OVERSIZED FOLDER

subStyles = new LinkedHashMap<>();
skus = new ArrayList<>();

skus.add("OF-0824");
subStyles.put("Standard Oversized Folder", skus);

skus = new ArrayList<>();

skus.add("3DF-152");
subStyles.put("Reinforced Folder", skus);

styles.put("Oversized Folder", subStyles);

// LEGAL SIZE FOLDER

subStyles = new LinkedHashMap<>();
skus = new ArrayList<>();

skus.add("LF-118");
skus.add("LF-1401");
skus.add("LF-118-501-C");
subStyles.put("Standard Legal Size Folder", skus);

skus = new ArrayList<>();

skus.add("3DLE-2813");
skus.add("CL-1465");
skus.add("3DLF-168");
subStyles.put("Extra Capacity Legal Size Folder", skus);

skus = new ArrayList<>();

skus.add("LST-1416");
skus.add("LF-1404");
skus.add("LHV-1457");
subStyles.put("One Pocket Legal Size Folder", skus);

skus = new ArrayList<>();

skus.add("LFIF-1408");
skus.add("LF-1417");
skus.add("LFT-2983");
subStyles.put("Other Legal Size Folder", skus);

styles.put("Legal Size Folders", subStyles);

// REINFORCED FOLDER

subStyles = new LinkedHashMap<>();
skus = new ArrayList<>();

skus.add("RE-2971-TOP");
skus.add("RE-2971");
skus.add("RE-2911");
skus.add("RECF-2965");
skus.add("RE-2988");
skus.add("FTRE-2975");
skus.add("SF-179");
skus.add("RE-2972");
skus.add("FTRE-2976");
skus.add("3DE-2872");

subStyles.put("Standard Size Reinforced Folder", skus);

skus = new ArrayList<>();

skus.add("3DF-139");
subStyles.put("Large Size Reinforced Folder", skus);

skus = new ArrayList<>();

skus.add("3DLE-2813");
subStyles.put("Legal Size Reinforced Folder", skus);

skus = new ArrayList<>();

skus.add("FTRE-2977");
skus.add("RE-2974");
subStyles.put("One Pocket Reinforced Folder", skus);

styles.put("Reinforced Folders", subStyles);

// EXTRA CAPACITY FOLDER

subStyles = new LinkedHashMap<>();
skus = new ArrayList<>();

skus.add("3DF-0812");
skus.add("3DF-0853");
skus.add("3DF-141");
skus.add("3DF-149");
subStyles.put("Box Pocket Folder", skus);

skus = new ArrayList<>();

skus.add("3DF-139");
skus.add("3DFE-2886");
skus.add("3DE-2872");
subStyles.put("Expansion Pocket Folder", skus);

skus = new ArrayList<>();

skus.add("3DF-0803");
skus.add("3DF-0802");
skus.add("3DF-0811");
skus.add("3DF-113");
subStyles.put("Scored Spine Pocket Folder", skus);

styles.put("Extra Capacity Folders", subStyles);

// FILE TAB FOLDER

subStyles = new LinkedHashMap<>();
skus = new ArrayList<>();

skus.add("AFF-0924-001");
skus.add("AFF-0924-003");
skus.add("FJ-1603");
subStyles.put("9 x 12 File Folder", skus);

skus = new ArrayList<>();

skus.add("AFF-0925-001");
skus.add("AFF-0925-003");
skus.add("AFF-0925-002");
subStyles.put("Legal Size File Folder", skus);

skus = new ArrayList<>();

skus.add("FT-107");
skus.add("FT-0821");
skus.add("FTRE-2975");
skus.add("LFT-2983");
skus.add("FT-130");
skus.add("FT-0822");
skus.add("FT-0821");
subStyles.put("File Tab Folder with Pockets", skus);

styles.put("File Tab Folders", subStyles);


// FILE FOLDER

subStyles = new LinkedHashMap<>();
skus = new ArrayList<>();

skus.add("AFF-0924-002");
subStyles.put("Standard File Folder", skus);

styles.put("File Folder", subStyles);




List<String> newCustomSkus = new ArrayList<>();
newCustomSkus.add("CHEL-183-501-C");
newCustomSkus.add("CHEL-183-518-C");
newCustomSkus.add("CHEL-185-501-C");
newCustomSkus.add("HF-6801-508-C");
newCustomSkus.add("MF-4804-501-C");
newCustomSkus.add("CKH-2709-501-C");
newCustomSkus.add("CKH-2711-501-C");
newCustomSkus.add("CKH-2710-501-C");
newCustomSkus.add("LF-118-501-C");

try {
	StringBuilder docids = new StringBuilder("");
	for(String sku : newCustomSkus) {
		docids.append((UtilValidate.isNotEmpty(docids.toString())) ? "," + sku : sku);
	}

	params.put("docids", docids.toString());
	SearchBuilder builder = new SearchBuilder(delegator, params, WebSiteWorker.getWebSiteId(request));
	builder.executeSearch();

	SearchResult result = builder.getSearchResult();
	context.put("newCustomSkus", result.getHits());
} catch(Exception e) {
	EnvUtil.reportError(e);
}
// CERTIFICATE HOLDER

subStyles = new LinkedHashMap<>();
skus = new ArrayList<>();

skus.add("CHFP-0916");
skus.add("CHERP-0911");
subStyles.put("Portrait Certificate Holder", skus);

skus = new ArrayList<>();

skus.add("CHEL-186");
skus.add("CHEL-185");
skus.add("CHFL-0916");
skus.add("CHEL-183-518-C");
subStyles.put("Landscape Certificate Holder", skus);

styles.put("Certificate Holders", subStyles);

// STANDARD PRESENTATION FOLDER

subStyles = new LinkedHashMap<>();
skus = new ArrayList<>();

skus.add("SF-101");
subStyles.put("", skus);

styles.put("Standard Presentation Folder", subStyles);

context.put("styles", styles);


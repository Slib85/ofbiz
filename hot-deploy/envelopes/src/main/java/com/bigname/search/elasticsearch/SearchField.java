package com.bigname.search.elasticsearch;

/**
 * Created by shoabkhan on 5/6/17.
 */

import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.DelegatorFactory;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;

import java.math.BigDecimal;
import java.util.*;

/**
 * Description - Description of the Field
 * Field Name - Field in ElasticSearch
 * Filter ID - ID to be used in URL
 * Filter Field - Filter field to call when applying filtering
 * Boost - Apply weights to individual fields
 * Searchable - When searching for a query, these fields will be used to find the query
 * Review - Is this a review aggregation, necessary because review will be a static range
 * Price - Is this a price aggregation, necessary because price will be a static range
 * String - Standard aggregation
 */
public enum SearchField {
    //                  DESCRIPTION              FIELD NAME            FILTER ID      FILTER FIELD               BOOST   SEARCHABLE    REVIEW   PRICE    STRING    SEQUENCE
    AVAILABILITY		("Availability",         "availability",       null,          null,                      1,      false,        false,   false,   false,    -1),
    AVGRATING			("Average Rating",       "avgrating",          "ra",          "avgrating",               1,      false,        true,    false,   false,    -1),
    CUSTOMIZABLE		("Customizable",         "customizable",       "customizable","customizable",            1,      false,        false,   false,   true,      0),
    BASEPRICE			("Base Price",           "baseprice",          null,          null,                      1,      false,        false,   false,   false,    -1),
    BASEQUANTITY		("Base Quantity",        "basequantity",       null,          null,                      1,      false,        false,   false,   false,    -1),
    BRAND				("Brand",                "brand",              null,          null,                      1,      false,        false,   false,   false,    -1),
    COLLECTION			("Collection",           "collection",         "col",         "collection.keyword",      1,      true,         false,   false,   true,     -1),
    COLOR				("Color",                "color",              "cog2",        "color.keyword",           3,      true,         false,   false,   true,      5),
    COLORDESCRIPTION 	("Color Description",    "colordescription",   "cdesc",       null,                      0.5f,   true,         false,   false,   false,    -1),
    COLORGROUP			("Color Group",          "colorgroup",         "cog1",        "colorgroup.keyword",      3,      true,         false,   false,   true,      4),
    CONVERSIONRATE		("Conversion Rate",      "conversionrate",     "cr",          "conversionrate",          1,      false,        false,   false,   false,    -1),
    CREATEDSTAMP		("Created",              "createdstamp",       "cs",          "createdstamp",            1,      false,        false,   false,   false,    -1),
    DESCRIPTION 		("Description",          "description",        "desc",        null,                      0.5f,   true,         false,   false,   false,    -1),
    DEPTH				("Depth",                "depth",              "depth",       null,                      3,      true,         false,   false,   false,    -1),
    FSC					("FSC",                  "fsc",                "fsc",         "fsc",                     1,      false,        false,   false,   true,     -1),
    HEIGHT				("Height",               "height",             "height",      null,                      3,      true,         false,   false,   false,    -1),
    IMAGE				("Image",                "image",              null,          null,                      1,      false,        false,   false,   false,    -1),
    INK					("Ink",                  "ink",                "ink",         "ink",                     1,      false,        false,   false,   true,     -1),
    LASER				("Laser",                "laser",              "laser",       "laser",                   1,      false,        false,   false,   true,     -1),
    MANUALRANK			("Rank",                 "manualrank",         null,          null,                      1,      false,        false,   false,   false,    -1),
    MEASUREMENT			("Measurement",          "measurement",        "mm",          "measurement",             1,      false,        false,   false,   false,    -1),
    METRICSIZE			("Metric Size",          "metricsize",         "metricsize",  null,                      1,      true,         false,   false,   false,    -1),
    NAME				("Product Name",         "name",               "name",        null,                      3,      true,         false,   false,   false,    -1),
    NEW					("New",                  "new",                "new",         "new",                     1,      false,        false,   false,   true,     -1),
    NUMOFPOCKETS		("Number of Pockets",    "numofpockets",       "nop",         "numofpockets",            1,      false,        false,   false,   true,     -1),
    NUMOFREVIEWS		("Number of Reviews",    "numofreviews",       null,          null,                      1,      false,        false,   false,   false,    -1),
    ONSALE				("On Sale",              "onsale",             "sale",        "onsale.keyword",          1,      false,        false,   false,   true,     -1),
    ORIGINALPRICE		("Original Price",       "originalprice",      null,          null,                      1,      false,        false,   false,   false,    -1),
    PAPERGRADE			("Paper Grade",          "papergrade",         null,          null,                      1,      false,        false,   false,   false,    -1),
    PAPERTEXTURE		("Finish",               "papertexture",       "finish",      "papertexture.keyword",    1,      true,         false,   false,   true,     -1),
    PAPERWEIGHT			("Paper Weight",         "paperweight",        "pw",          "paperweight.keyword",     1,      true,         false,   false,   true,      6),
    PARENTID			("Parent ID",            "parentid",           "parentid",    null,                      1,      true,         false,   false,   false,    -1),
    PEELANDSEAL			("Peel & Seal",          "peelandseal",        null,          null,                      1,      false,        false,   false,   false,    -1),
    PERCENTSAVINGS		("Percent Savings",      "percentsavings",     null,          null,                      1,      false,        false,   false,   false,    -1),
    PRINTABLE			("Printable",            "printable",          "printable",   "printable",               1,      false,        false,   false,   true,     -1),
    POCKETSPEC			("Pocket Spec",          "pocketspec",         "ps",          "pocketspec.keyword",      1,      true,         false,   false,   true,     -1),
    POCKETTYPE			("Pocket Type",          "pockettype",         "pt",          "pockettype.keyword",      1,      true,         false,   false,   true,     -1),
    PRINTOPTION         ("Print Option",         "printoption",        "po",          "printoption",             1,      false,        false,   false,   true,     -1),
    PRODUCTID			("Product ID",           "productid",          "productid",   "productid.keyword",       1,      true,         false,   false,   false,    -1),
    PRODUCTSTYLE		("Style",                "productstyle",       "st",          "productstyle.keyword",    3,      true,         false,   false,   true,      2),
    PRODUCTTYPE			("Product Type",         "producttype",        "prodtype",    "producttype",             3,      false,        false,   false,   true,     -1),
    PRODUCTUSE			("Category",             "productuse",         "use",         "productuse.keyword",      1,      true,         false,   false,   true,      1),
    QUANTITYPURCHASED   ("Quantity Purchased",   "quantitypurchased",  "qp",          "quantitypurchased",       1,      false,        false,   false,   false,    -1),
    RATINGSATTRIBUTE	("Raters",               "ratingsattribute",   "ratt",        null,                      1,      true,         false,   false,   false,    -1),
    RECYCLED			("Recycled",             "recycled",           null,          null,                      1,      false,        false,   false,   false,    -1),
    RECYCLEDCONTENT		("Recycled Content",     "recycledcontent",    null,          null,                      1,      true,         false,   false,   false,    -1),
    REVENUE		        ("Revenue",              "revenue",            "rev",         "revenue",                 1,      false,        false,   false,   false,    -1),
    SALESRANK			("Sales Rank",           "salesrank",          "sr",          "salesrank",               1,      false,        false,   false,   false,    -1),
    SEALINGMETHOD		("Sealing Method",       "sealingmethod",      "sm",          "sealingmethod.keyword",   1,      true,         false,   false,   true,     -1),
    SFI					("SFI",                  "sfi",                "sfi",         "sfi",                     1,      false,        false,   false,   true,     -1),
//    SIMPLESIZE          ("Simple Size",          "simplesize",         "simsize",     null,                      3,      true,         false,   false,   false,    -1),
    SIZE				("Size",                 "size",               "si",          "size.keyword",            1,      true,         false,   false,   true,     -1),
    SIZECODE			("Size Code",            "sizecode",           "s",           "sizecode.keyword",        3,      true,         false,   false,   true,      3),
    SPINESIZE			("Spine Size",           "spinesize",          "ss",          "spinesize.keyword",       1,      true,         false,   false,   true,     -1),
    TABSIZE 			("Tab Size",             "tabsize",            "ts",          "tabsize.keyword",         1,      true,         false,   false,   true,     -1),
    TAGLINE				("Tagline",              "tagline",            null,          null,                      1,      false,        false,   false,   false,    -1),
    TOTALCOLORS			("Total Colors",         "totalcolors",        "tc",          "totalcolors",             1,      false,        false,   false,   false,    -1),
    TYPE				("Type",                 "type",               "type",        "type",                    1,      true,         false,   false,   true,     -1),
    URL					("URL",                  "url",                null,          null,                      1,      false,        false,   false,   false,    -1),
    VIEWS		        ("Views",                "views",              "v",           "views",                   1,      false,        false,   false,   false,    -1),
    WEBSITEID			("Website ID",           "websiteid",          "websiteid",   "websiteid.keyword",       1,      false,        false,   false,   true,     -1),
    WIDTH				("Width",                "width",              "width",       null,                      3,      true,         false,   false,   false,    -1),
    UNKNOWN             (null,                   null,                 null,          null,                      1,      false,        false,   false,   false,    -1);

    private String description; //description of the field
    private String fieldName; //name of field in search index
    private String filterId; //id to be used when creating a search url
    private String filterField; //field to filter against when applying filters
    private float boostFactor; //boost factor to give weight to field
    private boolean isSearchable; //should this field be used when doing a query search
    private boolean isReviewAggregation; //is this a review filter
    private boolean isPriceAggregation; //is this a price filter
    private boolean isStringAggregation; //is this a string filter
    private int sequence; //sequence of filter, negative means do not show

    public static Map<String, Map<String, Object>> webSiteData = new HashMap<>();

    static {
        webSiteData.put("ae", new HashMap<>());
        webSiteData.put("envelopes", new HashMap<>());
        webSiteData.put("folders", new HashMap<>());
        webSiteData.put("bags", new HashMap<>());
        buildData();
    }

    SearchField(String description, String fieldName, String filterId, String filterField, float boostFactor, boolean isSearchable, boolean isReviewAggregation, boolean isPriceAggregation, boolean isStringAggregation, int sequence) {
        this.description = description;
        this.fieldName = fieldName;
        this.filterId = filterId;
        this.filterField = filterField;
        this.boostFactor = boostFactor;
        this.isSearchable = isSearchable;
        this.isReviewAggregation = isReviewAggregation;
        this.isPriceAggregation = isPriceAggregation;
        this.isStringAggregation = isStringAggregation;
        this.sequence = sequence;
    }

    public String getDescription(String webSiteId) {
        GenericValue field = ((Map<String, GenericValue>) webSiteData.get(webSiteId).get("fieldData")).get(this.fieldName);
        if(field != null && field.getString("description") != null) {
            return field.getString("description");
        }

        return this.description;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public String getFilterId() {
        return this.filterId;
    }

    public String getFilterField() {
        return this.filterField;
    }

    public float getBoostFactor(String webSiteId) {
        GenericValue field = ((Map<String, GenericValue>) webSiteData.get(webSiteId).get("fieldData")).get(this.fieldName);
        if(field != null && field.getBigDecimal("boostFactor") != null) {
            return field.getBigDecimal("boostFactor").floatValue();
        }

        return this.boostFactor;
    }

    public boolean getIsSearchable(String webSiteId) {
        GenericValue field = ((Map<String, GenericValue>) webSiteData.get(webSiteId).get("fieldData")).get(this.fieldName);
        if(field != null && field.getString("searchable") != null) {
            return "Y".equals(field.getString("searchable"));
        }

        return this.isSearchable;
    }

    public boolean getIsReviewAggregation() {
        return this.isReviewAggregation;
    }

    public boolean getIsPriceAggregation() {
        return this.isPriceAggregation;
    }

    public boolean getIsStringAggregation() {
        return this.isStringAggregation;
    }

    public int getSequence(String webSiteId) {
        GenericValue field = ((Map<String, GenericValue>) webSiteData.get(webSiteId).get("fieldData")).get(this.fieldName);
        if(field != null && field.getLong("sequenceNum") != null) {
            return field.getLong("sequenceNum").intValue();
        }

        return this.sequence;
    }

    public static SearchField getSearchField(String value) {
        try {
            for (SearchField sf : SearchField.values()) {
                if (sf.fieldName.equalsIgnoreCase(value)) {
                    return sf;
                }
            }
        } catch (Exception e) {
            return UNKNOWN;
        }

        return UNKNOWN;
    }

    public static Map<String, String> getFiltersNameMap(String webSiteId) {
        return (Map<String, String>) webSiteData.get(webSiteId).get("filterNameMap");
    }

    public static Map<String, String> getFiltersDescMap(String webSiteId) {
        return (Map<String, String>) webSiteData.get(webSiteId).get("filterDescriptionMap");
    }

    /**
     * Return a list of all the facet options given a type
     * @param delegator
     * @param filterTypeId
     * @return
     * @throws GenericEntityException
     */
    public static List<GenericValue> getFilterValues(Delegator delegator, String filterTypeId) throws GenericEntityException {
        if(UtilValidate.isNotEmpty(filterTypeId)) {
            return EntityQuery.use(delegator).from("SearchFacet").where("facetTypeId", filterTypeId).orderBy("facetName ASC").queryList();
        }

        return EntityQuery.use(delegator).from("SearchFacet").orderBy(UtilMisc.toList("facetTypeId ASC", "facetName ASC")).queryList();
    }

    /**
     * Get all the field values from database
     * @param delegator
     * @return
     * @throws GenericEntityException
     */
    public static List<GenericValue> getFields(Delegator delegator, String webSiteId) throws GenericEntityException {
        return EntityQuery.use(delegator).from("SearchField").where("webSiteId", webSiteId).orderBy(UtilMisc.toList("sequenceNum ASC")).cache(false).queryList();
    }

    /**
     * Get the description of the filter given its filter id
     * @param filterId
     * @return
     */
    public static String getFilterDescription(String filterId, String webSiteId) {
        return ((Map<String, String>) SearchField.webSiteData.get(webSiteId).get("filterDescriptionMap")).get(filterId);
    }

    /**
     * Get the description of the filter given its filter id
     * @return
     */
    public static Map<Integer, String> getFilterableMap(String webSiteId) {
        return (Map<Integer, String>) SearchField.webSiteData.get(webSiteId).get("selectableFilterMap");
    }

    /**
     * Get the field from database
     * @param fieldName
     * @return
     */
    public static GenericValue getField(String fieldName, String webSiteId) {
        Delegator delegator = DelegatorFactory.getDelegator("default");
        GenericValue field = null;
        try {
            field = EntityQuery.use(delegator).from("SearchField").where("fieldName", fieldName, "webSiteId", webSiteId).cache(false).queryOne();
            if (field == null) {
                field = makeField(delegator, fieldName, webSiteId);
            }
        } catch(Exception e) {
            throw new RuntimeException("Error loading enum from database.", e);
        }

        return field;
    }

    /**
     * Create database value for field and its defaults
     * @param delegator
     * @param fieldName
     * @return
     * @throws Exception
     */
    private static GenericValue makeField(Delegator delegator, String fieldName, String webSiteId) throws Exception {
        GenericValue field = delegator.makeValue("SearchField");
        field.set("fieldName", fieldName);
        field.set("webSiteId", webSiteId);
        field.set("changeByUserLoginId", "system");

        SearchField sf = SearchField.getSearchField(fieldName);
        if(sf.getFieldName() != null) {
            field.set("fieldId", sf.getFilterId());
            field.set("description", sf.getDescription(webSiteId));
            field.set("sequenceNum", Long.valueOf(sf.getSequence(webSiteId)));
            field.set("boostFactor", new BigDecimal(sf.getBoostFactor(webSiteId)));
            field.set("searchable", sf.getIsSearchable(webSiteId) ? "Y" : "N");
        }

        delegator.create(field);
        return field;
    }

    /**
     * Build static data
     */
    private static void buildData() {
        SearchField.webSiteData.forEach((k,v)->{
            SearchField.webSiteData.get(k).put("fieldData", new HashMap<>());
            SearchField.webSiteData.get(k).put("filterFieldMap", new HashMap<>());
            SearchField.webSiteData.get(k).put("filterNameMap", new HashMap<>());
            SearchField.webSiteData.get(k).put("filterDescriptionMap", new HashMap<>());
            SearchField.webSiteData.get(k).put("aggregationsList", new ArrayList<>());
            SearchField.webSiteData.get(k).put("selectableFilterMap", new TreeMap<>());
            SearchField.webSiteData.get(k).put("searchableFilterMap", new HashMap<>());

            for (SearchField searchField : SearchField.values()) {
                Map<String, GenericValue> fieldData = (Map<String, GenericValue>) SearchField.webSiteData.get(k).get("fieldData");
                Map<String, String> filterFieldMap = (Map<String, String>) SearchField.webSiteData.get(k).get("filterFieldMap");
                Map<String, String> filterNameMap = (Map<String, String>) SearchField.webSiteData.get(k).get("filterNameMap");
                Map<String, String> filterDescriptionMap = (Map<String, String>) SearchField.webSiteData.get(k).get("filterDescriptionMap");
                List<SearchField> aggregationsList = (List<SearchField>) SearchField.webSiteData.get(k).get("aggregationsList");
                Map<Integer, String> selectableFilterMap = (TreeMap<Integer, String>) SearchField.webSiteData.get(k).get("selectableFilterMap");
                Map<String, Float> searchableFilterMap = (Map<String, Float>) SearchField.webSiteData.get(k).get("searchableFilterMap");

                if(searchField.getFieldName() != null) {
                    /*
                     * Build a static map of all the filters and their generic values
                     * This also holds override values for enum constants that need to be updatable via the UI
                     */
                    fieldData.put(searchField.getFieldName(), getField(searchField.getFieldName(), k));
                }

                if(searchField.getFilterId() != null) {
                    /*
                     * Build a static map of all the filter ids so they can be retrieved
                     * without going through all the SearchField enums
                     */
                    filterFieldMap.put(searchField.getFilterId(), searchField.getFilterField());

                    /*
                     * Build a static map of all the filter field names so they can be retrieved
                     * without going through all the SearchField enums
                     */
                    filterNameMap.put(searchField.getFilterId(), searchField.getFieldName());

                    /*
                     * Build a static map of all the filter descriptions so they can be retrieved
                     * without going through all the SearchField enums
                     */
                    filterDescriptionMap.put(searchField.getFilterId(), searchField.getDescription(k));
                }

                /*
                 * Build a static list of all the aggregations so they can be retrieved
                 * without going through all the SearchField enums
                 */
                if(searchField.getFilterField() != null && (searchField.getIsStringAggregation() || searchField.getIsReviewAggregation())) {
                    aggregationsList.add(searchField);
                }

                /*
                 * Build a static list of all the user filterable aggregations
                 */
                if(searchField.getFilterId() != null && searchField.getFilterField() != null && searchField.getSequence(k) >= 0) {
                    selectableFilterMap.put(searchField.getSequence(k), searchField.getFilterId());
                }

                /*
                 * Build a static list of all the searchable filters and their boost
                 */
                if(searchField.getFilterId() != null && searchField.getIsSearchable(k)) {
                    searchableFilterMap.put(searchField.getFieldName(), searchField.getBoostFactor(k));
                }
            }
        });
    }

    /**
     * Clear cache and rebuild static data
     */
    public static void clearCache() {
        buildData();
    }
}
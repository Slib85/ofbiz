/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.product.importer;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.Trim;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;

/*
    * Sets up the processors used for the data import.
    * NotNull() means the column data cannot be empty
    * Optional() means the column data can be empty
    * @return the cell processors
*/
public enum ProductEnum {
    WEBSITE                  ("WebSite", false, "", null, null, new Optional(new Trim())),
    CODE                     ("Product", false, "productId", null, null, new UniqueHashCode(new Trim())),
    PARENT_CODE              ("Product", false, "parentProductId", null, null, new NotNull(new Trim())),
    MARKETPLACE_CODE         ("Product", false, "marketplaceProductId", null, null, new Optional(new Trim())),
    PRODUCT_TYPE             ("Product", false, "productTypeId", null, null, new NotNull(new Trim())),
    CATEGORY                 ("Product", false, "productCategoryId", null, null, new NotNull(new Trim())),
    STYLE                    ("Product", false, "primaryProductCategoryId", null, null, new NotNull(new Trim())),
    PRODUCT_NAME             ("Product", false, "productName", null, null, new NotNull(new Trim())),
    EACH_WEIGHT              ("Product", false, "productWeight", null, null, new NotNull(new Trim())),
    DESCRIPTION              ("Product", false, "longDescription", null, null, new Optional(new Trim())),
    COLOR_DESCRIPTION        ("Product", false, "colorDescription", null, null, new Optional(new Trim())),
    TAGLINE                  ("Product", false, "tagLine", null, null, new Optional(new Trim())),
    RUSH_PRODUCTION          ("Product", false, "hasRushProduction", null, null, new Optional(new Trim())),
    PRINTABLE                ("Product", false, "isPrintable", null, null, new Optional(new Trim())),
    CARTON_QUANTITY          ("Product", false, "cartonQty", null, null, new Optional(new Trim())),
    BOX_QUANTITY             ("Product", false, "boxQty", null, null, new Optional(new Trim())),
    PRODUCTION_PLAIN         ("Product", false, "leadTimePlain", null, null, new Optional(new Trim())),
    PRODUCTION_PRINTED       ("Product", false, "leadTimeStandardPrinted", null, null, new Optional(new Trim())),
    PRODUCTION_PRINTED_RUSH  ("Product", false, "leadTimeRushPrinted", null, null, new Optional(new Trim())),
    SALES_DISCONTINATION_DATE  ("Product", false, "salesDiscontinuationDate", null, null, new Optional(new Trim())),
    HAS_WHITE_INK            ("Product", false, "hasWhiteInk", null, null, new Optional(new Trim())),
    HAS_SAMPLE               ("Product", false, "hasSample", null, null, new Optional(new Trim())),
    HAS_FOLD                 ("Product", false, "hasFold", null, null, new Optional(new Trim())),
    HAS_CUT                  ("Product", false, "hasCut", null, null, new Optional(new Trim())),
    HAS_VARIABLE_DATA        ("Product", false, "hasVariableData", null, null, new Optional(new Trim())),
    HAS_CUSTOM_QTY           ("Product", false, "hasCustomQty", null, null, new Optional(new Trim())),
    BIN_LOCATION             ("Product", false, "binLocation", null, null, new Optional(new Trim())),
    PRICE                    ("ProductPrice", false, "price", null, null, new Optional(new Trim())),
    NETSUITE_CATEGORY        ("ProductFeature", false, "NETSUITE_CATEGORY", "STANDARD", null, new NotNull(new Trim())),
    AVAILABILITY             ("ProductFeature", false, "AVAILABILITY", "STANDARD", null, new Optional(new Trim())),
    BACKSLITS                ("ProductFeature", false, "BACKSLITS", "STANDARD", null, new Optional(new Trim())),
    BOTTOM_MARGIN            ("ProductFeature", false, "BOTTOM_MARGIN", "STANDARD", null, new Optional(new Trim())),
    RIGHT_MARGIN             ("ProductFeature", false, "RIGHT_MARGIN", "STANDARD", null, new Optional(new Trim())),
    LEFT_MARGIN              ("ProductFeature", false, "LEFT_MARGIN", "STANDARD", null, new Optional(new Trim())),
    TOP_MARGIN               ("ProductFeature", false, "TOP_MARGIN", "STANDARD", null, new Optional(new Trim())),
    COLLECTION               ("ProductFeature", false, "COLLECTION", "STANDARD", null, new NotNull(new Trim())),
    COLOR                    ("ProductFeature", false, "COLOR", "STANDARD", null, new NotNull(new Trim())),
    COATING                  ("ProductFeature", false, "COATING", "STANDARD", null, new Optional(new Trim())),
    COLOR_GROUP              ("ProductFeature", false, "COLOR_GROUP", "STANDARD", null, new NotNull(new Trim())),
    COMPARE_TO_BRAND         ("ProductFeature", false, "COMPARE_TO_BRAND", "STANDARD", null, new Optional(new Trim())),
    CORNER_RADIUS            ("ProductFeature", false, "CORNER_RADIUS", "STANDARD", null, new Optional(new Trim())),
    HORIZONTAL_SPACING       ("ProductFeature", false, "HORIZONTAL_SPACING", "STANDARD", null,new Optional(new Trim())),
    INKJET                   ("ProductFeature", false, "INKJET", "STANDARD", null, new Optional(new Trim())),
    LABELS_PER_SHEET         ("ProductFeature", false, "LABELS_PER_SHEET", "STANDARD", null,new Optional(new Trim())),
    LASER                    ("ProductFeature", false, "LASER", "STANDARD", null, new Optional(new Trim())),
    METRIC_SIZE              ("ProductFeature", false, "METRIC_SIZE", "STANDARD", null, new Optional(new Trim())),
    PAPER_TEXTURE            ("ProductFeature", false, "PAPER_TEXTURE", "STANDARD", null, new Optional(new Trim())),
    PAPER_WEIGHT             ("ProductFeature", false, "PAPER_WEIGHT", "STANDARD", null, new Optional(new Trim())),
    RECYCLED                 ("ProductFeature", false, "RECYCLED", "STANDARD", null, new Optional(new Trim())),
    RECYCLED_CONTENT         ("ProductFeature", false, "RECYCLED_CONTENT", "STANDARD", null, new Optional(new Trim())),
    RECYCLED_PERCENT         ("ProductFeature", false, "RECYCLED_PERCENT", "STANDARD", null, new Optional(new Trim())),
    SEALING_METHOD           ("ProductFeature", false, "SEALING_METHOD", "STANDARD", null, new Optional(new Trim())),
    SHAPE                    ("ProductFeature", false, "SHAPE", "STANDARD", null, new Optional(new Trim())),
    SIZE                     ("ProductFeature", false, "SIZE", "STANDARD", null, new NotNull(new Trim())),
    SIZE_CODE                ("ProductFeature", false, "SIZE_CODE", "STANDARD", null, new NotNull(new Trim())),
    VERTICAL_SPACING         ("ProductFeature", false, "VERTICAL_SPACING", "STANDARD", null, new Optional(new Trim())),
    WINDOW_POSITION          ("ProductFeature", false, "WINDOW_POSITION", "STANDARD", null, new Optional(new Trim())),
    WINDOW_SIZE              ("ProductFeature", false, "WINDOW_SIZE", "STANDARD", null, new Optional(new Trim())),
    ORIENTATION              ("ProductFeature", false, "ORIENTATION", "STANDARD", null, new Optional(new Trim())),
    NUMBER_OF_PANELS         ("ProductFeature", false, "NUMBER_OF_PANELS", "STANDARD", null, new Optional(new Trim())),
    CORNERS                  ("ProductFeature", false, "CORNERS", "STANDARD", null, new Optional(new Trim())),
    POCKET_HEIGHT            ("ProductFeature", false, "POCKET_HEIGHT", "STANDARD", null, new Optional(new Trim())),
    POCKET_PLACEMENT         ("ProductFeature", false, "POCKET_PLACEMENT", "STANDARD", null, new Optional(new Trim())),
    POCKET_GLUE_LOCATION     ("ProductFeature", false, "POCKET_GLUE_LOCATION", "STANDARD", null, new Optional(new Trim())),
    REINFORCED_EDGE          ("ProductFeature", false, "REINFORCED_EDGE", "STANDARD", null, new Optional(new Trim())),
    BACKBONE                 ("ProductFeature", false, "BACKBONE", "STANDARD", null, new Optional(new Trim())),
    ACCEPTABLE_INSERT_SIZES  ("ProductFeature", false, "ACCEPTABLE_INSERT_SIZES", "STANDARD", null, new Optional(new Trim())),
    MATERIAL                 ("ProductFeature", false, "MATERIAL", "STANDARD", null, new Optional(new Trim())),
    CARD_SLOTS               ("ProductFeature", false, "CARD_SLOTS", "STANDARD", null, new Optional(new Trim())),
    CARD_SLOT_PLACEMENT      ("ProductFeature", false, "CARD_SLOT_PLACEMENT", "STANDARD", null, new Optional(new Trim())),
    RIGHT_POCKET             ("ProductFeature", false, "RIGHT_POCKET", "STANDARD", null, new Optional(new Trim())),
    LEFT_POCKET              ("ProductFeature", false, "LEFT_POCKET", "STANDARD", null, new Optional(new Trim())),
    CENTER_POCKET            ("ProductFeature", false, "CENTER_POCKET", "STANDARD", null, new Optional(new Trim())),
    FRONT_POCKET             ("ProductFeature", false, "FRONT_POCKET", "STANDARD", null, new Optional(new Trim())),
    IMAGE_SIZE               ("ProductFeature", false, "IMAGE_SIZE", "STANDARD", null, new Optional(new Trim())),
    IMPRINT_METHOD           ("ProductFeature", true,  "IMPRINT_METHOD", "SELECTABLE", null, new Optional(new Trim())),
    POCKET_STYLE             ("ProductFeature", true,  "POCKET_STYLE", "SELECTABLE", null, new Optional(new Trim())),
    CARD_SLITS               ("ProductFeature", true,  "CARD_SLITS", null, "POCKET_STYLE", new Optional(new Trim())),
    INDIVIDUAL_AVAILABILITY  ("ProductFeature", true,  "AVAILABILITY", null, "POCKET_STYLE", new Optional(new Trim())),
    POCKET_SPEC              ("ProductFeature", true,  "POCKET_SPEC", null, "POCKET_STYLE", new Optional(new Trim())),
    NUMBER_OF_POCKETS        ("ProductFeature", true,  "NUMBER_OF_POCKETS", null, "POCKET_STYLE", new Optional(new Trim())),
    DIMENSION_CLOSED         ("ProductFeature", true,  "DIMENSION_CLOSED", null, "POCKET_STYLE", new Optional(new Trim())),
    DIMENSION_OPEN           ("ProductFeature", true,  "DIMENSION_OPEN", null, "POCKET_STYLE", new Optional(new Trim())),
    VENDOR                   ("Vendor", true,  "", null, null, new Optional(new Trim())),
    VENDOR_SKU               ("VendorProduct", true,  "", null, null, new Optional(new Trim())),
    UNKNOWN                  ("ProductFeature", false, "", "STANDARD", null, new Optional(new Trim()));

    private String type;
    private boolean isMultiField;
    private String field;
    private String fieldAppl;
    private String parent;
    private CellProcessor processor;

    ProductEnum(String type, boolean isMultiField, String field, String fieldAppl, String parent, CellProcessor processor) {
        this.type = type;
        this.isMultiField = isMultiField;
        this.field = field;
        this.fieldAppl = fieldAppl;
        this.parent = parent;
        this.processor = processor;
    }

    public String getType() {
        return type;
    }

    public boolean isMultiField() {
        return isMultiField;
    }

    public String getField() {
        return this.field;
    }

    public String getFieldAppl() {
        return this.fieldAppl;
    }

    public String getParent() {
        return this.parent;
    }

    public CellProcessor getProcessor() {
        return this.processor;
    }

    public static ProductEnum getProductEnum(String value) {
        try {
            return valueOf(value);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    public static CellProcessor[] getProcessor(String[] headers) {
        CellProcessor[] processors = new CellProcessor[headers.length];
        for(int i = 0; i < headers.length; i++) {
            ProductEnum ProductEnum = getProductEnum(headers[i]);

            //this class stores the cellprocessor in compile time, hack below for UniqueHashCode
            processors[i] = ProductEnum == CODE ? new UniqueHashCode(new Trim()) : ProductEnum.getProcessor();
        }

        return processors;
    }
}
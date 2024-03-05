package com.bigname.integration.click2mail;

import java.lang.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.DelegatorFactory;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.jdbc.SQLProcessor;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilMisc;
import com.bigname.pricingengine.util.EngineUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.sql.Date;

import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import org.json.JSONArray;
import org.json.JSONObject;

public class Click2MailHelper {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    private static final Map<String, Object> productOptions = new LinkedHashMap<>();

    static {

        Map<String, String> layoutOptions = new LinkedHashMap<>();
        Map<String, String> productionTimeOptions = new LinkedHashMap<>();
        Map<String, String> envelopeOptions = new LinkedHashMap<>();
        Map<String, String> printColorOptions = new LinkedHashMap<>();
        Map<String, String> paperColorOptions = new LinkedHashMap<>();
        Map<String, String> printOptions = new LinkedHashMap<>();
        Map<String, String> mailClassOptions = new LinkedHashMap<>();

        Map<String, Object> letter = new LinkedHashMap<>();
        letter.put("DOCUMENT_CLASS", "Letter 8.5 x 11");

        layoutOptions.put("ADDRESS_ON_SEPARATE_PAGE", "Address on Separate Page");
        layoutOptions.put("ADDRESS_ON_FIRST_PAGE", "Address on First Page");
        layoutOptions.put("PICTURE_AND_ADDRESS_ON_FIRST_PAGE", "Picture and Address First Page");
        layoutOptions.put("ADDRESS_BACK_PAGE", "Address Back Page");
        letter.put("LAYOUT_OPTIONS", layoutOptions);

        productionTimeOptions.put("NEXT_DAY", "Next Day");
        letter.put("PRODUCTION_TIME_OPTIONS", productionTimeOptions);

        envelopeOptions.put("#10_DOUBLE_WINDOW", "#10 Double Window");
        envelopeOptions.put("BEST_FIT", "Best Fit");
        envelopeOptions.put("#10_OPEN_WINDOW_ENVELOPE", "#10 Open Window Envelope");
        envelopeOptions.put("6_X_9.5_DOUBLE_WINDOW", "6 x 9.5 Double Window");
        envelopeOptions.put("6_X_9.5_OPEN_WINDOW", "6 x 9.5 Open Window");
        envelopeOptions.put("FLAT_ENVELOPE", "Flat Envelope");
        letter.put("ENVELOPE_OPTIONS", envelopeOptions);

        printColorOptions.put("FULL_COLOR", "Full Color");
        printColorOptions.put("BLACK_AND_WHITE", "Black and White");
        letter.put("PRINT_COLOR_OPTIONS", printColorOptions);

        paperColorOptions.put("WHITE_24#", "White 24#");
        paperColorOptions.put("OFF-WHITE_28#", "Off-White 28#");
        paperColorOptions.put("CANARY_24#", "Canary 24#");
        paperColorOptions.put("WHITE_28#", "White 28#");
        paperColorOptions.put("WHITE_28#_MATTE", "White 28# Matte");
        letter.put("PAPER_COLOR_OPTIONS", paperColorOptions);

        printOptions.put("PRINTING_ONE_SIDE", "Printing One Side");
        printOptions.put("PRINTING_BOTH_SIDES", "Printing Both Sides");
        letter.put("PRINT_OPTIONS", printOptions);

        mailClassOptions.put("FIRST_CLASS", "First Class");
        mailClassOptions.put("STANDARD", "Standard");
        mailClassOptions.put("FIRST_CLASS_LIVE_STAMP", "First Class Live Stamp");
        mailClassOptions.put("FIRST_CLASS_SPECIALTY_STAMP", "First Class Specialty Stamp");
        mailClassOptions.put("FIRST_CLASS_NO_MOVE_UPDATE", "First Class No Move Update");
        letter.put("MAIL_CLASS_OPTIONS", mailClassOptions);
        //productOptions.put("1590-GB", letter);
        productOptions.put("C2M-L85X11", letter);


        layoutOptions = new LinkedHashMap<>();
        productionTimeOptions = new LinkedHashMap<>();
        envelopeOptions = new LinkedHashMap<>();
        printColorOptions = new LinkedHashMap<>();
        paperColorOptions = new LinkedHashMap<>();
        printOptions = new LinkedHashMap<>();
        mailClassOptions = new LinkedHashMap<>();

        Map<String, Object> flatNoteCard = new LinkedHashMap<>();
        flatNoteCard.put("DOCUMENT_CLASS", "Notecard 4.25 x 5.5");

        layoutOptions.put("NOTECARD_SINGLE_SIDED", "Notecard -Single Sided");
        flatNoteCard.put("LAYOUT_OPTIONS", layoutOptions);

        productionTimeOptions.put("NEXT_DAY", "Next Day");
        flatNoteCard.put("PRODUCTION_TIME_OPTIONS", productionTimeOptions);

        envelopeOptions.put("A2_WHITE_OPEN_WINDOW", "A2 White Open Window");
        flatNoteCard.put("ENVELOPE_OPTIONS", envelopeOptions);

        printColorOptions.put("FULL_COLOR", "Full Color");
        printColorOptions.put("BLACK_AND_WHITE", "Black and White");
        flatNoteCard.put("PRINT_COLOR_OPTIONS", printColorOptions);

        paperColorOptions.put("120#_WHITE_UNCOATED", "120# White Uncoated");
        flatNoteCard.put("PAPER_COLOR_OPTIONS", paperColorOptions);

        printOptions.put("PRINTING_BOTH_SIDES", "Printing Both Sides");
        flatNoteCard.put("PRINT_OPTIONS", printOptions);

        mailClassOptions.put("FIRST_CLASS", "First Class");
        mailClassOptions.put("FIRST_CLASS_LIVE_STAMP", "First Class Live Stamp");
        mailClassOptions.put("FIRST_CLASS_SPECIALTY_STAMP", "First Class Specialty Stamp");
        flatNoteCard.put("MAIL_CLASS_OPTIONS", mailClassOptions);
        //productOptions.put("1590BK", flatNoteCard);
        productOptions.put("C2M-N425X55", flatNoteCard);

        layoutOptions = new LinkedHashMap<>();
        productionTimeOptions = new LinkedHashMap<>();
        envelopeOptions = new LinkedHashMap<>();
        printColorOptions = new LinkedHashMap<>();
        paperColorOptions = new LinkedHashMap<>();
        printOptions = new LinkedHashMap<>();
        mailClassOptions = new LinkedHashMap<>();

        Map<String, Object> foldedNoteCard = new LinkedHashMap<>();
        foldedNoteCard.put("DOCUMENT_CLASS", "Note Card - Folded 4.25 x 5.5");

        layoutOptions.put("NOTECARD_-_FOLDED", "Notecard - Folded");
        foldedNoteCard.put("LAYOUT_OPTIONS", layoutOptions);

        productionTimeOptions.put("NEXT_DAY", "Next Day");
        foldedNoteCard.put("PRODUCTION_TIME_OPTIONS", productionTimeOptions);

        envelopeOptions.put("A2_WHITE_OPEN_WINDOW", "A2 White Open Window");
        foldedNoteCard.put("ENVELOPE_OPTIONS", envelopeOptions);

        printColorOptions.put("FULL_COLOR", "Full Color");
        printColorOptions.put("BLACK_AND_WHITE", "Black and White");
        foldedNoteCard.put("PRINT_COLOR_OPTIONS", printColorOptions);

        paperColorOptions.put("120#_WHITE_UNCOATED", "120# White Uncoated");
        foldedNoteCard.put("PAPER_COLOR_OPTIONS", paperColorOptions);

        printOptions.put("PRINTING_BOTH_SIDES", "Printing Both Sides");
        foldedNoteCard.put("PRINT_OPTIONS", printOptions);

        mailClassOptions.put("FIRST_CLASS", "First Class");
        mailClassOptions.put("FIRST_CLASS_LIVE_STAMP", "First Class Live Stamp");
        mailClassOptions.put("FIRST_CLASS_SPECIALTY_STAMP", "First Class Specialty Stamp");
        foldedNoteCard.put("MAIL_CLASS_OPTIONS", mailClassOptions);
        //productOptions.put("1590-WLI", foldedNoteCard);
        productOptions.put("C2M-FN425X55", foldedNoteCard);
    }

    public static Map<String, Object> getProductOptions(String productId){
        return (Map<String, Object>) productOptions.get(productId);
    }

    public static Map<String, Object> getOptions(String productId){
        //String productId = "C2M-L85X11";
        /*String productId = "C2M-N425X55";*/
        Map<String, Object> PRODUCT_OPTIONS = new LinkedHashMap<>();

        Map<String, Object> LAYOUT_OPTIONS = new LinkedHashMap<>();
        Map<String, Object> LAYOUT_OPTION = new LinkedHashMap<>();

        List<Map<String, Object>> PRODUCTION_TIME_OPTIONS = new ArrayList<>();
        Map<String, Object> PRODUCTION_TIME = new LinkedHashMap<>();

        List<Map<String, Object>> ENVELOPE_OPTIONS = new ArrayList<>();
        Map<String, Object> ENVELOPE = new LinkedHashMap<>();

        List<Map<String, Object>> PRINT_COLOR_OPTIONS = new ArrayList<>();
        Map<String, Object> PRINT_COLOR = new LinkedHashMap<>();

        List<Map<String, Object>> PAPER_COLOR_OPTIONS = new ArrayList<>();
        Map<String, Object> PAPER_COLOR = new LinkedHashMap<>();

        List<Map<String, Object>> PRINT_OPTIONS = new ArrayList<>();
        Map<String, Object> PRINT_OPTION = new LinkedHashMap<>();

        List<Map<String, Object>> MAIL_CLASS_OPTIONS = new ArrayList<>();
        Map<String, Object> MAIL_CLASS = new LinkedHashMap<>();

        switch (productId) {
            case "C2M-L85X11":

                /**   ================== Letter & Envelope ========================**/

                /** LAYOUT_OPTION 1 **/
                LAYOUT_OPTION.put("INDEX", 0);

                PRODUCTION_TIME.put("INDEX", 0);
                PRODUCTION_TIME_OPTIONS.add(PRODUCTION_TIME);
                PRODUCTION_TIME = new LinkedHashMap<>();
                LAYOUT_OPTION.put("PRODUCTION_TIME_OPTIONS", PRODUCTION_TIME_OPTIONS);
                PRODUCTION_TIME_OPTIONS = new ArrayList<>();

                /** envelope includes print colors (print color include paper type)**/
                /** envelope includes print options (print option include number of pages)**/
                /** envelope includes mail class options**/

                /** ENVELOPE 1**/
                ENVELOPE.put("OPTION", "Best Fit");

                /** PRINT_COLOR 1**/
                PRINT_COLOR.put("OPTION", "Full Color");

                PAPER_COLOR.put("OPTION", "White 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                /** PRINT_COLOR 2**/
                PRINT_COLOR.put("OPTION", "Black and White");

                PAPER_COLOR.put("OPTION", "White 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "Off-White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "Canary 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_COLOR_OPTIONS", PRINT_COLOR_OPTIONS);
                PRINT_COLOR_OPTIONS = new ArrayList<>();

                /** PRINT_OPTION 1**/
                PRINT_OPTION.put("OPTION", "Printing One Side");
                PRINT_OPTION.put("MAX_PAGES", "47");
                PRINT_OPTIONS.add(PRINT_OPTION);
                PRINT_OPTION = new LinkedHashMap<>();

                /** PRINT_OPTION 2**/
                PRINT_OPTION.put("OPTION", "Printing Both Sides");
                PRINT_OPTION.put("MAX_PAGES", "94");
                PRINT_OPTIONS.add(PRINT_OPTION);
                PRINT_OPTION = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_OPTIONS", PRINT_OPTIONS);
                PRINT_OPTIONS = new ArrayList<>();

                /** MAIL_CLASS 1**/
                MAIL_CLASS.put("OPTION", "First Class");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 2**/
                MAIL_CLASS.put("OPTION", "Standard");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 3**/
                MAIL_CLASS.put("OPTION", "First Class Live Stamp");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 4**/
                MAIL_CLASS.put("OPTION", "First Class Specialty Stamp");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 5**/
                MAIL_CLASS.put("OPTION", "First Class No Move Update");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                ENVELOPE.put("MAIL_CLASS_OPTIONS", MAIL_CLASS_OPTIONS);
                MAIL_CLASS_OPTIONS = new ArrayList<>();

                ENVELOPE_OPTIONS.add(ENVELOPE);
                ENVELOPE = new LinkedHashMap<>();

                /** ENVELOPE 2**/
                ENVELOPE.put("OPTION", "#10 Double Window");

                /** PRINT_COLOR 1**/
                PRINT_COLOR.put("OPTION", "Full Color");

                PAPER_COLOR.put("OPTION", "White 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                /** PRINT_COLOR 2**/
                PRINT_COLOR.put("OPTION", "Black and White");

                PAPER_COLOR.put("OPTION", "White 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "Off-White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "Canary 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_COLOR_OPTIONS", PRINT_COLOR_OPTIONS);
                PRINT_COLOR_OPTIONS = new ArrayList<>();


                /** PRINT_OPTION 1**/
                PRINT_OPTION.put("OPTION", "Printing One Side");
                PRINT_OPTION.put("MAX_PAGES", "5");
                PRINT_OPTIONS.add(PRINT_OPTION);
                PRINT_OPTION = new LinkedHashMap<>();

                /** PRINT_OPTION 2**/
                PRINT_OPTION.put("OPTION", "Printing Both Sides");
                PRINT_OPTION.put("MAX_PAGES", "10");
                PRINT_OPTIONS.add(PRINT_OPTION);
                PRINT_OPTION = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_OPTIONS", PRINT_OPTIONS);
                PRINT_OPTIONS = new ArrayList<>();

                /** MAIL_CLASS 1**/
                MAIL_CLASS.put("OPTION", "First Class");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 2**/
                MAIL_CLASS.put("OPTION", "Standard");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 3**/
                MAIL_CLASS.put("OPTION", "First Class Live Stamp");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 4**/
                MAIL_CLASS.put("OPTION", "First Class Specialty Stamp");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 5**/
                MAIL_CLASS.put("OPTION", "First Class No Move Update");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                ENVELOPE.put("MAIL_CLASS_OPTIONS", MAIL_CLASS_OPTIONS);
                MAIL_CLASS_OPTIONS = new ArrayList<>();

                ENVELOPE_OPTIONS.add(ENVELOPE);
                ENVELOPE = new LinkedHashMap<>();


                /** ENVELOPE 3**/
                ENVELOPE.put("OPTION", "6 x 9.5 Double Window");

                /** PRINT_COLOR 1**/
                PRINT_COLOR.put("OPTION", "Full Color");

                PAPER_COLOR.put("OPTION", "White 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                /** PRINT_COLOR 2**/
                PRINT_COLOR.put("OPTION", "Black and White");

                PAPER_COLOR.put("OPTION", "White 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "Off-White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "Canary 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_COLOR_OPTIONS", PRINT_COLOR_OPTIONS);
                PRINT_COLOR_OPTIONS = new ArrayList<>();


                /** PRINT_OPTION 1**/
                PRINT_OPTION.put("OPTION", "Printing One Side");
                PRINT_OPTION.put("MAX_PAGES", "6");
                PRINT_OPTIONS.add(PRINT_OPTION);
                PRINT_OPTION = new LinkedHashMap<>();

                /** PRINT_OPTION 2**/
                PRINT_OPTION.put("OPTION", "Printing Both Sides");
                PRINT_OPTION.put("MAX_PAGES", "12");
                PRINT_OPTIONS.add(PRINT_OPTION);
                PRINT_OPTION = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_OPTIONS", PRINT_OPTIONS);
                PRINT_OPTIONS = new ArrayList<>();

                /** MAIL_CLASS 1**/
                MAIL_CLASS.put("OPTION", "First Class");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 2**/
                MAIL_CLASS.put("OPTION", "Standard");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 3**/
                MAIL_CLASS.put("OPTION", "First Class Live Stamp");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 4**/
                MAIL_CLASS.put("OPTION", "First Class Specialty Stamp");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 5**/
                MAIL_CLASS.put("OPTION", "First Class No Move Update");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                ENVELOPE.put("MAIL_CLASS_OPTIONS", MAIL_CLASS_OPTIONS);
                MAIL_CLASS_OPTIONS = new ArrayList<>();

                ENVELOPE_OPTIONS.add(ENVELOPE);
                ENVELOPE = new LinkedHashMap<>();


                /** ENVELOPE 4**/
                ENVELOPE.put("OPTION", "Flat Envelope");

                /** PRINT_COLOR 1**/
                PRINT_COLOR.put("OPTION", "Full Color");

                PAPER_COLOR.put("OPTION", "White 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                /** PRINT_COLOR 2**/
                PRINT_COLOR.put("OPTION", "Black and White");

                PAPER_COLOR.put("OPTION", "White 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "Off-White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "Canary 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_COLOR_OPTIONS", PRINT_COLOR_OPTIONS);
                PRINT_COLOR_OPTIONS = new ArrayList<>();


                /** PRINT_OPTION 1**/
                PRINT_OPTION.put("OPTION", "Printing One Side");
                PRINT_OPTION.put("MAX_PAGES", "47");
                PRINT_OPTIONS.add(PRINT_OPTION);
                PRINT_OPTION = new LinkedHashMap<>();

                /** PRINT_OPTION 2**/
                PRINT_OPTION.put("OPTION", "Printing Both Sides");
                PRINT_OPTION.put("MAX_PAGES", "94");
                PRINT_OPTIONS.add(PRINT_OPTION);
                PRINT_OPTION = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_OPTIONS", PRINT_OPTIONS);
                PRINT_OPTIONS = new ArrayList<>();

                /** MAIL_CLASS 1**/
                MAIL_CLASS.put("OPTION", "First Class");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 2**/
                MAIL_CLASS.put("OPTION", "Standard");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 3**/
                MAIL_CLASS.put("OPTION", "First Class No Move Update");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                ENVELOPE.put("MAIL_CLASS_OPTIONS", MAIL_CLASS_OPTIONS);
                MAIL_CLASS_OPTIONS = new ArrayList<>();

                ENVELOPE_OPTIONS.add(ENVELOPE);
                ENVELOPE = new LinkedHashMap<>();

                LAYOUT_OPTION.put("ENVELOPE_OPTIONS", ENVELOPE_OPTIONS);
                ENVELOPE_OPTIONS = new ArrayList<>();
                LAYOUT_OPTIONS.put("Address on Separate Page", LAYOUT_OPTION);
                LAYOUT_OPTION = new LinkedHashMap<>();

                /** LAYOUT_OPTION 2**/
                LAYOUT_OPTION.put("INDEX", 1);

                PRODUCTION_TIME.put("OPTION", "Next Day");
                PRODUCTION_TIME_OPTIONS.add(PRODUCTION_TIME);
                PRODUCTION_TIME = new LinkedHashMap<>();
                LAYOUT_OPTION.put("PRODUCTION_TIME_OPTIONS", PRODUCTION_TIME_OPTIONS);
                PRODUCTION_TIME_OPTIONS = new ArrayList<>();

                /** envelope includes print colors (print color include paper type)**/
                /** envelope includes print options (print option include number of pages)**/
                /** envelope includes mail class options**/

                /** ENVELOPE 1**/
                ENVELOPE.put("OPTION", "Best Fit");

                /** PRINT_COLOR 1**/
                PRINT_COLOR.put("OPTION", "Full Color");

                PAPER_COLOR.put("OPTION", "White 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                /** PRINT_COLOR 2**/
                PRINT_COLOR.put("OPTION", "Black and White");

                PAPER_COLOR.put("OPTION", "White 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "Off-White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "Canary 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_COLOR_OPTIONS", PRINT_COLOR_OPTIONS);
                PRINT_COLOR_OPTIONS = new ArrayList<>();


                /** PRINT_OPTION 1**/
                PRINT_OPTION.put("OPTION", "Printing One Side");
                PRINT_OPTION.put("MAX_PAGES", "48");
                PRINT_OPTIONS.add(PRINT_OPTION);
                PRINT_OPTION = new LinkedHashMap<>();

                /** PRINT_OPTION 2**/
                PRINT_OPTION.put("OPTION", "Printing Both Sides");
                PRINT_OPTION.put("MAX_PAGES", "96");
                PRINT_OPTIONS.add(PRINT_OPTION);
                PRINT_OPTION = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_OPTIONS", PRINT_OPTIONS);
                PRINT_OPTIONS = new ArrayList<>();

                /** MAIL_CLASS 1**/
                MAIL_CLASS.put("OPTION", "First Class");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 2**/
                MAIL_CLASS.put("OPTION", "Standard");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 3**/
                MAIL_CLASS.put("OPTION", "First Class Live Stamp");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 4**/
                MAIL_CLASS.put("OPTION", "First Class Specialty Stamp");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                ENVELOPE.put("MAIL_CLASS_OPTIONS", MAIL_CLASS_OPTIONS);
                MAIL_CLASS_OPTIONS = new ArrayList<>();

                ENVELOPE_OPTIONS.add(ENVELOPE);
                ENVELOPE = new LinkedHashMap<>();


                /** ENVELOPE 2**/
                ENVELOPE.put("OPTION", "#10 Double Window");

                /** PRINT_COLOR 1**/
                PRINT_COLOR.put("OPTION", "Full Color");

                PAPER_COLOR.put("OPTION", "White 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                /** PRINT_COLOR 2**/
                PRINT_COLOR.put("OPTION", "Black and White");

                PAPER_COLOR.put("OPTION", "White 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "Off-White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "Canary 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_COLOR_OPTIONS", PRINT_COLOR_OPTIONS);
                PRINT_COLOR_OPTIONS = new ArrayList<>();


                /** PRINT_OPTION 1**/
                PRINT_OPTION.put("OPTION", "Printing One Side");
                PRINT_OPTION.put("MAX_PAGES", "6");
                PRINT_OPTIONS.add(PRINT_OPTION);
                PRINT_OPTION = new LinkedHashMap<>();

                /** PRINT_OPTION 2**/
                PRINT_OPTION.put("OPTION", "Printing Both Sides");
                PRINT_OPTION.put("MAX_PAGES", "12");
                PRINT_OPTIONS.add(PRINT_OPTION);
                PRINT_OPTION = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_OPTIONS", PRINT_OPTIONS);
                PRINT_OPTIONS = new ArrayList<>();

                /** MAIL_CLASS 1**/
                MAIL_CLASS.put("OPTION", "First Class");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 2**/
                MAIL_CLASS.put("OPTION", "Standard");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 3**/
                MAIL_CLASS.put("OPTION", "First Class Live Stamp");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 4**/
                MAIL_CLASS.put("OPTION", "First Class Specialty Stamp");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                ENVELOPE.put("MAIL_CLASS_OPTIONS", MAIL_CLASS_OPTIONS);
                MAIL_CLASS_OPTIONS = new ArrayList<>();

                ENVELOPE_OPTIONS.add(ENVELOPE);
                ENVELOPE = new LinkedHashMap<>();


                /** ENVELOPE 3**/
                ENVELOPE.put("OPTION", "6 x 9.5 Double Window");

                /** PRINT_COLOR 1**/
                PRINT_COLOR.put("OPTION", "Full Color");

                PAPER_COLOR.put("OPTION", "White 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                /** PRINT_COLOR 2**/
                PRINT_COLOR.put("OPTION", "Black and White");

                PAPER_COLOR.put("OPTION", "White 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "Off-White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "Canary 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_COLOR_OPTIONS", PRINT_COLOR_OPTIONS);
                PRINT_COLOR_OPTIONS = new ArrayList<>();


                /** PRINT_OPTION 1**/
                PRINT_OPTION.put("OPTION", "Printing One Side");
                PRINT_OPTION.put("MAX_PAGES", "7");
                PRINT_OPTIONS.add(PRINT_OPTION);
                PRINT_OPTION = new LinkedHashMap<>();

                /** PRINT_OPTION 2**/
                PRINT_OPTION.put("OPTION", "Printing Both Sides");
                PRINT_OPTION.put("MAX_PAGES", "14");
                PRINT_OPTIONS.add(PRINT_OPTION);
                PRINT_OPTION = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_OPTIONS", PRINT_OPTIONS);
                PRINT_OPTIONS = new ArrayList<>();

                /** MAIL_CLASS 1**/
                MAIL_CLASS.put("OPTION", "First Class");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 2**/
                MAIL_CLASS.put("OPTION", "Standard");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 3**/
                MAIL_CLASS.put("OPTION", "First Class Live Stamp");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 4**/
                MAIL_CLASS.put("OPTION", "First Class Specialty Stamp");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                ENVELOPE.put("MAIL_CLASS_OPTIONS", MAIL_CLASS_OPTIONS);
                MAIL_CLASS_OPTIONS = new ArrayList<>();

                ENVELOPE_OPTIONS.add(ENVELOPE);
                ENVELOPE = new LinkedHashMap<>();


                /** ENVELOPE 4**/
                ENVELOPE.put("OPTION", "Flat Envelope");

                /** PRINT_COLOR 1**/
                PRINT_COLOR.put("OPTION", "Full Color");

                PAPER_COLOR.put("OPTION", "White 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                /** PRINT_COLOR 2**/
                PRINT_COLOR.put("OPTION", "Black and White");

                PAPER_COLOR.put("OPTION", "White 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "Off-White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "Canary 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_COLOR_OPTIONS", PRINT_COLOR_OPTIONS);
                PRINT_COLOR_OPTIONS = new ArrayList<>();


                /** PRINT_OPTION 1**/
                PRINT_OPTION.put("OPTION", "Printing One Side");
                PRINT_OPTION.put("MAX_PAGES", "48");
                PRINT_OPTIONS.add(PRINT_OPTION);
                PRINT_OPTION = new LinkedHashMap<>();

                /** PRINT_OPTION 2**/
                PRINT_OPTION.put("OPTION", "Printing Both Sides");
                PRINT_OPTION.put("MAX_PAGES", "96");
                PRINT_OPTIONS.add(PRINT_OPTION);
                PRINT_OPTION = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_OPTIONS", PRINT_OPTIONS);
                PRINT_OPTIONS = new ArrayList<>();

                /** MAIL_CLASS 1**/
                MAIL_CLASS.put("OPTION", "First Class");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 2**/
                MAIL_CLASS.put("OPTION", "Standard");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                ENVELOPE.put("MAIL_CLASS_OPTIONS", MAIL_CLASS_OPTIONS);
                MAIL_CLASS_OPTIONS = new ArrayList<>();

                ENVELOPE_OPTIONS.add(ENVELOPE);
                ENVELOPE = new LinkedHashMap<>();

                LAYOUT_OPTION.put("ENVELOPE_OPTIONS", ENVELOPE_OPTIONS);
                ENVELOPE_OPTIONS = new ArrayList<>();
                LAYOUT_OPTIONS.put("Address on First Page", LAYOUT_OPTION);
                LAYOUT_OPTION = new LinkedHashMap<>();


                /** LAYOUT_OPTION 3**/
                LAYOUT_OPTION.put("INDEX", 2);

                PRODUCTION_TIME.put("OPTION", "Next Day");
                PRODUCTION_TIME_OPTIONS.add(PRODUCTION_TIME);
                PRODUCTION_TIME = new LinkedHashMap<>();
                LAYOUT_OPTION.put("PRODUCTION_TIME_OPTIONS", PRODUCTION_TIME_OPTIONS);
                PRODUCTION_TIME_OPTIONS = new ArrayList<>();

                /** envelope includes print colors (print color include paper type)**/
                /** envelope includes print options (print option include number of pages)**/
                /** envelope includes mail class options**/

                /** ENVELOPE 1**/
                ENVELOPE.put("OPTION", "Best Fit");

                /** PRINT_COLOR 1**/
                PRINT_COLOR.put("OPTION", "Full Color");

                PAPER_COLOR.put("OPTION", "White 28# Matte");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_COLOR_OPTIONS", PRINT_COLOR_OPTIONS);
                PRINT_COLOR_OPTIONS = new ArrayList<>();


                /** PRINT_OPTION 1**/
                PRINT_OPTION.put("OPTION", "Printing One Side");
                PRINT_OPTION.put("MAX_PAGES", "7");
                PRINT_OPTIONS.add(PRINT_OPTION);
                PRINT_OPTION = new LinkedHashMap<>();

                /** PRINT_OPTION 2**/
                PRINT_OPTION.put("OPTION", "Printing Both Sides");
                PRINT_OPTION.put("MAX_PAGES", "14");
                PRINT_OPTIONS.add(PRINT_OPTION);
                PRINT_OPTION = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_OPTIONS", PRINT_OPTIONS);
                PRINT_OPTIONS = new ArrayList<>();

                /** MAIL_CLASS 1**/
                MAIL_CLASS.put("OPTION", "First Class");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 2**/
                MAIL_CLASS.put("OPTION", "Standard");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 3**/
                MAIL_CLASS.put("OPTION", "First Class Live Stamp");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 4**/
                MAIL_CLASS.put("OPTION", "First Class Specialty Stamp");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                ENVELOPE.put("MAIL_CLASS_OPTIONS", MAIL_CLASS_OPTIONS);
                MAIL_CLASS_OPTIONS = new ArrayList<>();

                ENVELOPE_OPTIONS.add(ENVELOPE);
                ENVELOPE = new LinkedHashMap<>();


                /** ENVELOPE 2**/
                ENVELOPE.put("OPTION", "#10 Open Window Envelope");

                /** PRINT_COLOR 1**/
                PRINT_COLOR.put("OPTION", "Full Color");

                PAPER_COLOR.put("OPTION", "White 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28# Matte");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                /** PRINT_COLOR 2**/
                PRINT_COLOR.put("OPTION", "Black and White");

                PAPER_COLOR.put("OPTION", "White 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "Off-White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "Canary 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_COLOR_OPTIONS", PRINT_COLOR_OPTIONS);
                PRINT_COLOR_OPTIONS = new ArrayList<>();


                /** PRINT_OPTION 1**/
                PRINT_OPTION.put("OPTION", "Printing One Side");
                PRINT_OPTION.put("MAX_PAGES", "6");
                PRINT_OPTIONS.add(PRINT_OPTION);
                PRINT_OPTION = new LinkedHashMap<>();

                /** PRINT_OPTION 2**/
                PRINT_OPTION.put("OPTION", "Printing Both Sides");
                PRINT_OPTION.put("MAX_PAGES", "12");
                PRINT_OPTIONS.add(PRINT_OPTION);
                PRINT_OPTION = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_OPTIONS", PRINT_OPTIONS);
                PRINT_OPTIONS = new ArrayList<>();

                /** MAIL_CLASS 1**/
                MAIL_CLASS.put("OPTION", "First Class");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 2**/
                MAIL_CLASS.put("OPTION", "Standard");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 3**/
                MAIL_CLASS.put("OPTION", "First Class Live Stamp");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 4**/
                MAIL_CLASS.put("OPTION", "First Class Specialty Stamp");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                ENVELOPE.put("MAIL_CLASS_OPTIONS", MAIL_CLASS_OPTIONS);
                MAIL_CLASS_OPTIONS = new ArrayList<>();

                ENVELOPE_OPTIONS.add(ENVELOPE);
                ENVELOPE = new LinkedHashMap<>();


                /** ENVELOPE 3**/
                ENVELOPE.put("OPTION", "6 x 9.5 Open Window");

                /** PRINT_COLOR 1**/
                PRINT_COLOR.put("OPTION", "Full Color");

                PAPER_COLOR.put("OPTION", "White 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28# Matte");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                /** PRINT_COLOR 2**/
                PRINT_COLOR.put("OPTION", "Black and White");

                PAPER_COLOR.put("OPTION", "White 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "Off-White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "Canary 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_COLOR_OPTIONS", PRINT_COLOR_OPTIONS);
                PRINT_COLOR_OPTIONS = new ArrayList<>();


                /** PRINT_OPTION 1**/
                PRINT_OPTION.put("OPTION", "Printing One Side");
                PRINT_OPTION.put("MAX_PAGES", "7");
                PRINT_OPTIONS.add(PRINT_OPTION);
                PRINT_OPTION = new LinkedHashMap<>();

                /** PRINT_OPTION 2**/
                PRINT_OPTION.put("OPTION", "Printing Both Sides");
                PRINT_OPTION.put("MAX_PAGES", "14");
                PRINT_OPTIONS.add(PRINT_OPTION);
                PRINT_OPTION = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_OPTIONS", PRINT_OPTIONS);
                PRINT_OPTIONS = new ArrayList<>();

                /** MAIL_CLASS 1**/
                MAIL_CLASS.put("OPTION", "First Class");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 2**/
                MAIL_CLASS.put("OPTION", "Standard");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 3**/
                MAIL_CLASS.put("OPTION", "First Class Live Stamp");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 4**/
                MAIL_CLASS.put("OPTION", "First Class Specialty Stamp");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                ENVELOPE.put("MAIL_CLASS_OPTIONS", MAIL_CLASS_OPTIONS);
                MAIL_CLASS_OPTIONS = new ArrayList<>();

                ENVELOPE_OPTIONS.add(ENVELOPE);
                ENVELOPE = new LinkedHashMap<>();

                LAYOUT_OPTION.put("ENVELOPE_OPTIONS", ENVELOPE_OPTIONS);
                ENVELOPE_OPTIONS = new ArrayList<>();
                LAYOUT_OPTIONS.put("Picture and Address on First Page", LAYOUT_OPTION);
                LAYOUT_OPTION = new LinkedHashMap<>();


                /** LAYOUT_OPTION 4**/
                LAYOUT_OPTION.put("INDEX", 3);

                PRODUCTION_TIME.put("OPTION", "Next Day");
                PRODUCTION_TIME_OPTIONS.add(PRODUCTION_TIME);
                PRODUCTION_TIME = new LinkedHashMap<>();
                LAYOUT_OPTION.put("PRODUCTION_TIME_OPTIONS", PRODUCTION_TIME_OPTIONS);
                PRODUCTION_TIME_OPTIONS = new ArrayList<>();

                /** envelope includes print colors (print color include paper type)**/
                /** envelope includes print options (print option include number of pages)**/
                /** envelope includes mail class options**/

                /** ENVELOPE 1**/
                ENVELOPE.put("OPTION", "Best Fit");

                /** PRINT_COLOR 1**/
                PRINT_COLOR.put("OPTION", "Full Color");

                PAPER_COLOR.put("OPTION", "White 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                /** PRINT_COLOR 2**/
                PRINT_COLOR.put("OPTION", "Black and White");

                PAPER_COLOR.put("OPTION", "White 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "Off-White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "Canary 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_COLOR_OPTIONS", PRINT_COLOR_OPTIONS);
                PRINT_COLOR_OPTIONS = new ArrayList<>();

                /** PRINT_OPTION 1**/
                PRINT_OPTION.put("OPTION", "Printing Both Sides");
                PRINT_OPTION.put("MAX_PAGES", "95");
                PRINT_OPTIONS.add(PRINT_OPTION);
                PRINT_OPTION = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_OPTIONS", PRINT_OPTIONS);
                PRINT_OPTIONS = new ArrayList<>();

                /** MAIL_CLASS 1**/
                MAIL_CLASS.put("OPTION", "First Class");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 2**/
                MAIL_CLASS.put("OPTION", "Standard");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 3**/
                MAIL_CLASS.put("OPTION", "First Class Live Stamp");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 4**/
                MAIL_CLASS.put("OPTION", "First Class Specialty Stamp");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                ENVELOPE.put("MAIL_CLASS_OPTIONS", MAIL_CLASS_OPTIONS);
                MAIL_CLASS_OPTIONS = new ArrayList<>();

                ENVELOPE_OPTIONS.add(ENVELOPE);
                ENVELOPE = new LinkedHashMap<>();


                /** ENVELOPE 2**/
                ENVELOPE.put("OPTION", "#10 Double Window");

                /** PRINT_COLOR 1**/
                PRINT_COLOR.put("OPTION", "Full Color");

                PAPER_COLOR.put("OPTION", "White 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                /** PRINT_COLOR 2**/
                PRINT_COLOR.put("OPTION", "Black and White");

                PAPER_COLOR.put("OPTION", "White 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "Off-White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "Canary 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_COLOR_OPTIONS", PRINT_COLOR_OPTIONS);
                PRINT_COLOR_OPTIONS = new ArrayList<>();


                /** PRINT_OPTION 1**/
                PRINT_OPTION.put("OPTION", "Printing Both Sides");
                PRINT_OPTION.put("MAX_PAGES", "11");
                PRINT_OPTIONS.add(PRINT_OPTION);
                PRINT_OPTION = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_OPTIONS", PRINT_OPTIONS);
                PRINT_OPTIONS = new ArrayList<>();

                /** MAIL_CLASS 1**/
                MAIL_CLASS.put("OPTION", "First Class");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 2**/
                MAIL_CLASS.put("OPTION", "Standard");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 3**/
                MAIL_CLASS.put("OPTION", "First Class Live Stamp");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 4**/
                MAIL_CLASS.put("OPTION", "First Class Specialty Stamp");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                ENVELOPE.put("MAIL_CLASS_OPTIONS", MAIL_CLASS_OPTIONS);
                MAIL_CLASS_OPTIONS = new ArrayList<>();

                ENVELOPE_OPTIONS.add(ENVELOPE);
                ENVELOPE = new LinkedHashMap<>();


                /** ENVELOPE 3**/
                ENVELOPE.put("OPTION", "6 x 9.5 Double Window");

                /** PRINT_COLOR 1**/
                PRINT_COLOR.put("OPTION", "Full Color");

                PAPER_COLOR.put("OPTION", "White 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                /** PRINT_COLOR 2**/
                PRINT_COLOR.put("OPTION", "Black and White");

                PAPER_COLOR.put("OPTION", "White 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "Off-White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "Canary 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_COLOR_OPTIONS", PRINT_COLOR_OPTIONS);
                PRINT_COLOR_OPTIONS = new ArrayList<>();


                /** PRINT_OPTION 1**/
                PRINT_OPTION.put("OPTION", "Printing Both Sides");
                PRINT_OPTION.put("MAX_PAGES", "13");
                PRINT_OPTIONS.add(PRINT_OPTION);
                PRINT_OPTION = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_OPTIONS", PRINT_OPTIONS);
                PRINT_OPTIONS = new ArrayList<>();

                /** MAIL_CLASS 1**/
                MAIL_CLASS.put("OPTION", "First Class");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 2**/
                MAIL_CLASS.put("OPTION", "Standard");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 3**/
                MAIL_CLASS.put("OPTION", "First Class Live Stamp");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 4**/
                MAIL_CLASS.put("OPTION", "First Class Specialty Stamp");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                ENVELOPE.put("MAIL_CLASS_OPTIONS", MAIL_CLASS_OPTIONS);
                MAIL_CLASS_OPTIONS = new ArrayList<>();

                ENVELOPE_OPTIONS.add(ENVELOPE);
                ENVELOPE = new LinkedHashMap<>();


                /** ENVELOPE 4**/
                ENVELOPE.put("OPTION", "Flat Envelope");

                /** PRINT_COLOR 1**/
                PRINT_COLOR.put("OPTION", "Full Color");

                PAPER_COLOR.put("OPTION", "White 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                /** PRINT_COLOR 2**/
                PRINT_COLOR.put("OPTION", "Black and White");

                PAPER_COLOR.put("OPTION", "White 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "Off-White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "Canary 24#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PAPER_COLOR.put("OPTION", "White 28#");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_COLOR_OPTIONS", PRINT_COLOR_OPTIONS);
                PRINT_COLOR_OPTIONS = new ArrayList<>();


                /** PRINT_OPTION 1**/
                PRINT_OPTION.put("OPTION", "Printing Both Sides");
                PRINT_OPTION.put("MAX_PAGES", "95");
                PRINT_OPTIONS.add(PRINT_OPTION);
                PRINT_OPTION = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_OPTIONS", PRINT_OPTIONS);
                PRINT_OPTIONS = new ArrayList<>();

                /** MAIL_CLASS 1**/
                MAIL_CLASS.put("OPTION", "First Class");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 2**/
                MAIL_CLASS.put("OPTION", "Standard");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                ENVELOPE.put("MAIL_CLASS_OPTIONS", MAIL_CLASS_OPTIONS);
                MAIL_CLASS_OPTIONS = new ArrayList<>();

                ENVELOPE_OPTIONS.add(ENVELOPE);
                ENVELOPE = new LinkedHashMap<>();

                LAYOUT_OPTION.put("ENVELOPE_OPTIONS", ENVELOPE_OPTIONS);
                ENVELOPE_OPTIONS = new ArrayList<>();
                LAYOUT_OPTIONS.put("Address Back Page", LAYOUT_OPTION);
                LAYOUT_OPTION = new LinkedHashMap<>();

                PRODUCT_OPTIONS.put("LAYOUT_OPTIONS", LAYOUT_OPTIONS);
                LAYOUT_OPTIONS = new LinkedHashMap<>();

                break;

            case "C2M-N425X55":

                /**   ================== Flat Notecard========================**/
                /** LAYOUT_OPTION 1 **/
                LAYOUT_OPTION.put("INDEX", 0);

                PRODUCTION_TIME.put("OPTION", "Next Day");
                PRODUCTION_TIME_OPTIONS.add(PRODUCTION_TIME);
                PRODUCTION_TIME = new LinkedHashMap<>();
                LAYOUT_OPTION.put("PRODUCTION_TIME_OPTIONS", PRODUCTION_TIME_OPTIONS);
                PRODUCTION_TIME_OPTIONS = new ArrayList<>();

                /** envelope includes print colors (print color include paper type)**/
                /** envelope includes print options (print option include number of pages)**/
                /** envelope includes mail class options**/

                /** ENVELOPE 1**/
                ENVELOPE.put("OPTION", "A2 White Open Window");

                /** PRINT_COLOR 1**/
                PRINT_COLOR.put("OPTION", "Full Color");

                PAPER_COLOR.put("OPTION", "120# White Uncoated");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                /** PRINT_COLOR 2**/
                PRINT_COLOR.put("OPTION", "Black and White");

                PAPER_COLOR.put("OPTION", "120# White Uncoated");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_COLOR_OPTIONS", PRINT_COLOR_OPTIONS);
                PRINT_COLOR_OPTIONS = new ArrayList<>();

                /** PRINT_OPTION 1**/
                PRINT_OPTION.put("OPTION", "Printing Both Sides");
                PRINT_OPTION.put("MAX_PAGES", "1");
                PRINT_OPTIONS.add(PRINT_OPTION);
                PRINT_OPTION = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_OPTIONS", PRINT_OPTIONS);
                PRINT_OPTIONS = new ArrayList<>();

                /** MAIL_CLASS 1**/
                MAIL_CLASS.put("OPTION", "First Class");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 2**/
                MAIL_CLASS.put("OPTION", "First Class Live Stamp");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 3**/
                MAIL_CLASS.put("OPTION", "First Class Specialty Stamp");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                ENVELOPE.put("MAIL_CLASS_OPTIONS", MAIL_CLASS_OPTIONS);
                MAIL_CLASS_OPTIONS = new ArrayList<>();

                ENVELOPE_OPTIONS.add(ENVELOPE);
                ENVELOPE = new LinkedHashMap<>();

                LAYOUT_OPTION.put("ENVELOPE_OPTIONS", ENVELOPE_OPTIONS);
                ENVELOPE_OPTIONS = new ArrayList<>();
                LAYOUT_OPTIONS.put("Notecard -Single Sided", LAYOUT_OPTION);
                LAYOUT_OPTION = new LinkedHashMap<>();

                PRODUCT_OPTIONS.put("LAYOUT_OPTIONS", LAYOUT_OPTIONS);
                LAYOUT_OPTIONS = new LinkedHashMap<>();

                break;

            case "C2M-FN425X55":

                /**   ================== Folded Notecard ========================**/
                /** LAYOUT_OPTION 1 **/
                LAYOUT_OPTION.put("INDEX", 0);

                PRODUCTION_TIME.put("OPTION", "Next Day");
                PRODUCTION_TIME_OPTIONS.add(PRODUCTION_TIME);
                PRODUCTION_TIME = new LinkedHashMap<>();
                LAYOUT_OPTION.put("PRODUCTION_TIME_OPTIONS", PRODUCTION_TIME_OPTIONS);
                PRODUCTION_TIME_OPTIONS = new ArrayList<>();

                /** envelope includes print colors (print color include paper type)**/
                /** envelope includes print options (print option include number of pages)**/
                /** envelope includes mail class options**/

                /** ENVELOPE 1**/
                ENVELOPE.put("OPTION", "A2 White Open Window");

                /** PRINT_COLOR 1**/
                PRINT_COLOR.put("OPTION", "Full Color");

                PAPER_COLOR.put("OPTION", "120# White Uncoated");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                /** PRINT_COLOR 2**/
                PRINT_COLOR.put("OPTION", "Black and White");

                PAPER_COLOR.put("OPTION", "120# White Uncoated");
                PAPER_COLOR_OPTIONS.add(PAPER_COLOR);
                PAPER_COLOR = new LinkedHashMap<>();

                PRINT_COLOR.put("PAPER_COLOR_OPTIONS", PAPER_COLOR_OPTIONS);
                PAPER_COLOR_OPTIONS = new ArrayList<>();
                PRINT_COLOR_OPTIONS.add(PRINT_COLOR);
                PRINT_COLOR = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_COLOR_OPTIONS", PRINT_COLOR_OPTIONS);
                PRINT_COLOR_OPTIONS = new ArrayList<>();

                /** PRINT_OPTION 1**/
                PRINT_OPTION.put("OPTION", "Printing Both Sides");
                PRINT_OPTION.put("MAX_PAGES", "1");
                PRINT_OPTIONS.add(PRINT_OPTION);
                PRINT_OPTION = new LinkedHashMap<>();

                ENVELOPE.put("PRINT_OPTIONS", PRINT_OPTIONS);
                PRINT_OPTIONS = new ArrayList<>();

                /** MAIL_CLASS 1**/
                MAIL_CLASS.put("OPTION", "First Class");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 2**/
                MAIL_CLASS.put("OPTION", "First Class Live Stamp");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                /** MAIL_CLASS 3**/
                MAIL_CLASS.put("OPTION", "First Class Specialty Stamp");
                MAIL_CLASS_OPTIONS.add(MAIL_CLASS);
                MAIL_CLASS = new LinkedHashMap<>();

                ENVELOPE.put("MAIL_CLASS_OPTIONS", MAIL_CLASS_OPTIONS);
                MAIL_CLASS_OPTIONS = new ArrayList<>();

                ENVELOPE_OPTIONS.add(ENVELOPE);
                ENVELOPE = new LinkedHashMap<>();

                LAYOUT_OPTION.put("ENVELOPE_OPTIONS", ENVELOPE_OPTIONS);
                ENVELOPE_OPTIONS = new ArrayList<>();
                LAYOUT_OPTIONS.put("Notecard - Folded", LAYOUT_OPTION);
                LAYOUT_OPTION = new LinkedHashMap<>();

                PRODUCT_OPTIONS.put("LAYOUT_OPTIONS", LAYOUT_OPTIONS);
                LAYOUT_OPTIONS = new LinkedHashMap<>();

                break;
        }
        //return EnvUtil.doResponse(request, response, PRODUCT_OPTIONS, null, EnvConstantsUtil.RESPONSE_PLAIN);
        return PRODUCT_OPTIONS;
    }

    public static Map<String, Object> parseProductOptions(Map<String, Object> product) {
        Map<String, Object> finalLayoutMap = new HashMap<>();
        List<Map<String, String>> options = (List<Map<String, String>>) product.get("option");

        /** Initializing all option values**/
        List<Map<String, Object>> layouts = new ArrayList<>();
        Map<String, Object> layout = new LinkedHashMap<>();

        /** Initializing envelope as map, since mailclass is dependentent on envelope value**/
        Set<String> envelopesSet = new LinkedHashSet<>();
        Set<Map<String, Object>> envelopes = new LinkedHashSet<>();
        Map<String, Object> envelope = new LinkedHashMap<>();

        /** Initializing print options**/
        Set<String> printOptions = new LinkedHashSet<>();

        /** Initializing printColor as map, since paperType is dependentent on printColor value**/
        Set<String> printColorsSet = new LinkedHashSet<>();
        Set<Map<String, Object>> printColors = new LinkedHashSet<>();
        Map<String, Object> printColor = new LinkedHashMap<>();

        /** Initializing previous options**/
        String previouslayout = "";
        String previousPrintColor = "";
        String previousPaperType = "";
        String previousMailClass = "";
        int i = 0;
        for (Map<String, String> option : options) {
            ++i;
            /** Initializing current options**/
            String currentLayout = option.get("layout");
            String currentEnvelopeValue = option.get("envelope");
            String currentPrintOptionValue = option.get("printOption");
            String currentPrintColorValue = option.get("printColor");
            String currentPaperTypeValue = option.get("paperType");
            String currentMailClassValue = option.get("mailClass");

            /** checking current layout is differnt from previous layout or whether it reaches loop size **/
            if (!previouslayout.isEmpty() && !previouslayout.equals(currentLayout) || (i == options.size())) {
                //System.out.println("i :" + i + " & size :" + options.size() + " layout :" + currentLayout + " previouslayout :" + previouslayout);

                /** for every layout change or when the for loop ends , add the options to layout map and re-initialize product options,**/
                layout.put("layout", previouslayout);
                envelopes.add(envelope);
                printColors.add(printColor);
                layout.put("envelopes", envelopes);
                layout.put("printOptions", printOptions);
                layout.put("printColors", printColors);
                layouts.add(layout);

                layout = new LinkedHashMap<>();
                envelope = new LinkedHashMap<>();
                envelopes  = new LinkedHashSet<>();
                envelopesSet  = new LinkedHashSet<>();
                printOptions = new LinkedHashSet<>();
                printColor = new LinkedHashMap<>();
                printColors = new LinkedHashSet<>();
                printColorsSet = new LinkedHashSet<>();
            }

            /** checking envelopesSet contains current envelope.if not, initialize a new envelope map and set the mailClass**/
            if (!envelopesSet.contains(currentEnvelopeValue)) {
                Map<String, Object> envelopeMap = new HashMap<>();
                Set<String> mailClassesSet = new LinkedHashSet<>();
                envelopeMap.put("envelope", currentEnvelopeValue);
                envelopeMap.put("mailClasses", mailClassesSet);
                envelope.put(currentEnvelopeValue, envelopeMap);
                envelopesSet.add(currentEnvelopeValue);
            }

            /** setting current mailClass for the corresponding envelope value**/
            if (envelope.containsKey(currentEnvelopeValue)) {
                Map<String, Object> envelopeMap = (Map<String, Object>) envelope.get(currentEnvelopeValue);
                Set<String> currentMailClasses = (Set<String>) envelopeMap.get("mailClasses");
                currentMailClasses.add(currentMailClassValue);
            }

            if (!printOptions.contains(currentPrintOptionValue)){
                printOptions.add(currentPrintOptionValue);
            }

            /** checking printColorsSet contains current printColor.if not, initialize a new printColor map and set the paperTypes**/
            if (!printColorsSet.contains(currentPrintColorValue)) {
                Map<String, Object> printColorMap = new HashMap<>();
                Set<String> paperTypesSet = new LinkedHashSet<>();
                printColorMap.put("printColor", currentPrintColorValue);
                printColorMap.put("paperTypes", paperTypesSet);
                printColor.put(currentPrintColorValue, printColorMap);
                printColorsSet.add(currentPrintColorValue);
            }

            /** setting currentPaperType for the corresponding printColor value**/
            if (printColor.containsKey(currentPrintColorValue)) {
                Map<String, Object> printColorMap = (Map<String, Object>) printColor.get(currentPrintColorValue);
                Set<String> currentPaperTypes = (Set<String>) printColorMap.get("paperTypes");
                currentPaperTypes.add(currentPaperTypeValue);
            }

            previouslayout = currentLayout;
            previousPrintColor = currentPrintColorValue;
            previousPaperType = currentPaperTypeValue;
            previousMailClass = currentMailClassValue;
        }

        finalLayoutMap.put("layouts", layouts);
        System.out.println("finalLayoutMap: " + finalLayoutMap);
        return finalLayoutMap;
    }

    /**
     when save & exit is clicked without creating job on click2mail(without job Id) ,createJob is triggered & after successful
     creation of job, saveJob is triggered. If job was already created saveJob is directly called.
     **/
    public static void saveJob(HttpServletRequest request, String partyId, Delegator delegator) throws Exception {
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        String jobId = UtilValidate.isNotEmpty(request.getAttribute("jobId")) ? (String) request.getAttribute("jobId") : (String) context.get("jobId");
        String data = (String) context.get("jobData");
        String productId = (String) context.get("productId");
        String addressListId = (String) context.get("addressId");
        String dataGroupId = (String) context.get("dataGroupId") ;
        dataGroupId = (dataGroupId == null || dataGroupId == "") ? null : dataGroupId;
        if(UtilValidate.isNotEmpty(data)) {
            data = Click2MailHelper.trimAddressArray(data);
        }
        if (UtilValidate.isNotEmpty(jobId) && UtilValidate.isNotEmpty(data)) {
            GenericValue savedJob = EntityQuery.use(delegator).from("DirectMailingJob").where(UtilMisc.toMap("jobId", jobId, "partyId", partyId)).queryOne();
            if (UtilValidate.isEmpty(savedJob)) {
                savedJob = delegator.makeValue("DirectMailingJob", "jobId", jobId);
                savedJob.put("partyId", partyId);
                savedJob.put("fromDate", UtilDateTime.nowTimestamp());
                savedJob.put("productId", productId);
            }
            savedJob.put("addressListId", addressListId);
            savedJob.put("dataGroupId", dataGroupId);
            savedJob.put("data", data);
            delegator.createOrStore(savedJob);
        } else {
            throw new Exception("Job Id and/or Job Data is empty");
        }

    }

    /**
     Function for updating DirectMailingJob with addressListId. This function is called after successfully creating addressList on Click2Mail.
     **/
    public static void updateAddressListId(String jobId, String addressListId, String partyId) throws Exception {
        if (UtilValidate.isNotEmpty(partyId)) {
                Delegator delegator = DelegatorFactory.getDelegator("default");
                GenericValue savedJob = EntityQuery.use(delegator).from("DirectMailingJob").where(UtilMisc.toMap("jobId", jobId)).queryOne();
                if(UtilValidate.isNotEmpty(savedJob)) {
                    savedJob.put("addressListId", addressListId);
                    delegator.createOrStore(savedJob);
                }
            }
    }

    /**
     Function for saving DirectMailingDocument. This function is called after uploading document to Click2Mail.
     **/
    public static void saveDirectMailingDocument(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
        String partyId = userLogin != null ? userLogin.getString("partyId") : "";
        if(UtilValidate.isNotEmpty(partyId)) {
            GenericValue  directMailingContent = delegator.makeValue("DirectMailingContent", "directMailingContentId", delegator.getNextSeqId("DirectMailingContent"));
            //directMailingContent = EntityQuery.use(delegator).from("DirectMailingContent").where(UtilMisc.toMap("jobId", context.get("jobId"))).queryOne();
            directMailingContent.put("jobId", context.get("jobId"));
            directMailingContent.put("contentPurposeEnumId", "DMC_DOCUMENT");
            directMailingContent.put("contentPath", context.get("contentPath"));
            directMailingContent.put("contentName", context.get("contentName"));
            directMailingContent.put("productId", context.get("productId"));
            directMailingContent.put("documentClass", context.get("documentClass"));
            directMailingContent.put("documentId", context.get("documentId"));
            directMailingContent.put("partyId", partyId);
            directMailingContent.put("fromDate", UtilDateTime.nowTimestamp());

            delegator.createOrStore(directMailingContent);
        }
    }

    public static List<Map<String, Object>> getDirectMailingDocuments(HttpServletRequest request, HttpServletResponse response) throws GenericEntityException {
        List<Map<String, Object>> documents = new ArrayList<>();
        GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
        String partyId = userLogin != null ? userLogin.getString("partyId") : "";
        if(UtilValidate.isEmpty(partyId)) {
            return new ArrayList<>();
        }
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Delegator delegator = (Delegator) request.getAttribute("delegator");

        List<EntityCondition> conditionList = new ArrayList<>();
        conditionList.add(EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId));
//        conditionList.add(EntityCondition.makeCondition("inactive", EntityOperator.EQUALS, null));

        List<GenericValue> documentGVs =  EntityQuery.use(delegator).select("directMailingContentId", "contentPath", "contentName", "lastUpdatedStamp").from("DirectMailingContent").where(conditionList).orderBy("createdStamp").queryList();
        for (GenericValue documentGV : documentGVs) {
            try {
                Map<String, Object> documentAttributes = new HashMap<>();
                documentAttributes.put("directMailingContentId", documentGV.getString("directMailingContentId"));
                documentAttributes.put("contentPath", documentGV.getString("contentPath"));
                documentAttributes.put("contentName", documentGV.getString("contentName"));
                documentAttributes.put("lastUpdatedStamp", sdf.format(documentGV.get("lastUpdatedStamp")));
                documents.add(documentAttributes);
            } catch (Exception e) {
                System.out.println("error");
            }
        }

        return documents;
    }

    /**
     Function for gettting DirectMailingDocuments based on productId and partyId.
     **/
    public static List<Map<String, Object>> getSavedDocuments(HttpServletRequest request) throws GenericEntityException {
        List<Map<String, Object>> documents = new ArrayList<>();
        String partyId = getPartyId(request);
        if(UtilValidate.isEmpty(partyId)) {
            return new ArrayList<>();
        }
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Delegator delegator = (Delegator) request.getAttribute("delegator");

        List<EntityCondition> conditionList = new ArrayList<>();
        conditionList.add(EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId));
//        conditionList.add(EntityCondition.makeCondition("inactive", EntityOperator.EQUALS, null));
        conditionList.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, context.get("productId")));
        List<GenericValue> documentGVs = EntityQuery.use(delegator).select("directMailingContentId", "contentPath", "contentName", "lastUpdatedStamp","documentId").from("DirectMailingContent").where(conditionList).orderBy("lastUpdatedStamp DESC").queryList();
        for (GenericValue documentGV : documentGVs) {
            Map<String, Object> documentAttributes = new HashMap<>();
            documentAttributes.put("directMailingContentId", documentGV.getString("directMailingContentId"));
            documentAttributes.put("documentId", documentGV.getString("documentId"));
            documentAttributes.put("contentPath", documentGV.getString("contentPath"));
            documentAttributes.put("contentName", documentGV.getString("contentName"));
            documentAttributes.put("lastUpdatedStamp", sdf.format(documentGV.get("lastUpdatedStamp")));
            documentAttributes.put("documentClass", documentGV.get("documentClass"));
            documents.add(documentAttributes);
       }
       return documents;
    }

    public static String deactivateDocument(HttpServletRequest request, HttpServletResponse response) throws GenericEntityException {
        Map<String, Object> jsonResponse = new HashMap<>();
        boolean success = false;
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        String directMailingContentId = (String) context.get("directMailingContentId");

        Delegator delegator = (Delegator) request.getAttribute("delegator");

        GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
        String partyId = userLogin != null ? userLogin.getString("partyId") : "";

        List<GenericValue> documents = delegator.findByAnd("DirectMailingContent", UtilMisc.toMap("directMailingContentId", directMailingContentId), null, false);
        if (UtilValidate.isNotEmpty(documents) && documents.size() > 0) {
            GenericValue document = documents.get(0);
            if (UtilValidate.isNotEmpty(document) && document.getString("directMailingContentId").equals(directMailingContentId)) {
//                document.put("inactive", "Y");
                document.store();
                success = true;
            }
        }
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    /**
    Funjction for reading the click2_mail_saved_job jobs -- created by melbin currently not used
     **/
    public static List<Map<String, Object>> getSavedJobs(HttpServletRequest request, HttpServletResponse response) throws GenericEntityException {
        List<Map<String, Object>> jobs = new ArrayList<>();
        GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
        String partyId = userLogin != null ? userLogin.getString("partyId") : "";
        if(UtilValidate.isEmpty(partyId)) {
            return new ArrayList<>();
        }

        List<EntityCondition> conditionList = new ArrayList<>();
        conditionList.add(EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId));
        conditionList.add(EntityCondition.makeCondition("jobId", EntityOperator.NOT_EQUAL, null));
//        conditionList.add(EntityCondition.makeCondition("inactive", EntityOperator.EQUALS, null));


        Delegator delegator = (Delegator) request.getAttribute("delegator");
        List<GenericValue> storedJobs = EntityQuery.use(delegator).select("partyId", "jobId", "productId", "data", "lastUpdatedStamp").from("DirectMailingJob").where(conditionList).orderBy("createdStamp").queryList();
//        List<GenericValue> storedJobs = EntityQuery.use(delegator).select("partyId", "jobId", "productId", "data", "fromDate", "thruDate").from("Click2MailSavedJob").orderBy("createdStamp").queryList();
        for(GenericValue values : storedJobs) {
            Map<String, Object> data = new HashMap<>();
            String editUrl = "/envelopes/control/directMailingProduct?productId="+values.get("productId") + "&jobId=" + values.get("jobId");
            data.put("partyId", values.get("partyId"));
            data.put("productId", values.get("productId"));
            data.put("jobId", values.get("jobId"));
            data.put("data", values.get("data"));
            data.put("lastUpdatedStamp", sdf.format(values.get("lastUpdatedStamp")));
            //data.put("fromDate", values.get("fromDate"));
            //data.put("thruDate", values.get("thruDate"));
            data.put("editUrl", editUrl);
            jobs.add(data);
        }
        return jobs;
    }

    public static String deactivateJob(HttpServletRequest request, HttpServletResponse response) throws GenericEntityException {
        Map<String, Object> jsonResponse = new HashMap<>();
        boolean success = false;
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        String jobId = (String) context.get("jobId");

        Delegator delegator = (Delegator) request.getAttribute("delegator");

        List<GenericValue> jobs = delegator.findByAnd("DirectMailingJob", UtilMisc.toMap("jobId", jobId), null, false);
        if (UtilValidate.isNotEmpty(jobs) && jobs.size() > 0) {
            GenericValue job = jobs.get(0);
            if (UtilValidate.isNotEmpty(job) && job.getString("jobId").equals(jobId)) {
//                job.put("inactive", "Y");
                job.store();
                success = true;
            }
        }
        jsonResponse.put("success", success);
        return EnvUtil.doResponse(request, response, jsonResponse, null, EnvConstantsUtil.RESPONSE_PLAIN);
    }

    /*public static Map<String, Object> getSavedJobs(HttpServletRequest request, String jobId, String partyId) throws GenericEntityException {
        Map<String, Object> designs = null;

        List<EntityCondition> conditionList = new ArrayList<>();
        if(UtilValidate.isNotEmpty(partyId)) {
            conditionList.add(EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId));
        }else {
            return null;
        }
        if(UtilValidate.isNotEmpty(jobId)) {
            conditionList.add(EntityCondition.makeCondition("jobId", EntityOperator.EQUALS, jobId));
        }else {
            return null;
        }
        *//*if(dataParam != null) {
            conditionList.add(EntityCondition.makeCondition("data", EntityOperator.EQUALS, dataParam));
        }*//*

        Delegator delegator = (Delegator) request.getAttribute("delegator");
        List<GenericValue> storedJobs = EntityQuery.use(delegator).select("partyId", "jobId", "productId", "data", "fromDate", "thruDate").from("Click2MailSavedJob").where(conditionList).orderBy("createdStamp").queryList();
//        List<GenericValue> storedJobs = EntityQuery.use(delegator).select("partyId", "jobId", "productId", "data", "fromDate", "thruDate").from("Click2MailSavedJob").orderBy("createdStamp").queryList();
        for(GenericValue values : storedJobs) {
            // varaible data group id, address list id
            designs = new HashMap<>();
            String editUrl = "/envelopes/control/directMailingProduct?productId="+values.get("productId") + "&jobId=" + values.get("jobId");
            designs.put("partyId", values.get("partyId"));
            designs.put("productId", values.get("productId"));
            designs.put("jobId", values.get("jobId"));
            designs.put("data", values.get("data"));
            designs.put("fromDate", values.get("fromDate"));
            designs.put("thruDate", values.get("thruDate"));
            designs.put("editUrl", editUrl);
        }
        return designs;
    }*/

    /**
     Funjction for reading details of a saved job
     **/
    public static Map<String, Object> getJobDetails(HttpServletRequest request) throws GenericEntityException {
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        Map<String, Object> jobDetails = new HashMap<>();
        String partyId = getPartyId(request);
        String jobId = (String) context.get("jobId");

        if(UtilValidate.isEmpty(partyId) || UtilValidate.isEmpty(jobId)) {
            return new HashMap<>();
        }

        Delegator delegator = (Delegator) request.getAttribute("delegator");

        List<GenericValue> jobs = delegator.findByAnd("DirectMailingJob", UtilMisc.toMap("jobId", jobId, "partyId", partyId), null, false);
        if (UtilValidate.isNotEmpty(jobs)) {
            GenericValue job = jobs.get(0);
                jobDetails.put("jobId", job.getString("jobId"));
                String editUrl = "/envelopes/control/directMailingProduct?productId="+job.get("productId") + "&jobId=" + job.get("jobId");
                jobDetails.put("partyId", job.get("partyId"));
                jobDetails.put("productId", job.get("productId"));
                jobDetails.put("jobId", job.get("jobId"));
                jobDetails.put("data", job.get("data"));
                jobDetails.put("lastUpdatedStamp", sdf.format(job.get("lastUpdatedStamp")));
                jobDetails.put("editUrl", editUrl);
                jobDetails.put("addressListId", job.get("addressListId"));
//                jobMap.put("dataGroupId", job.get("dataGroupId"));
                if(UtilValidate.isNotEmpty(job.get("dataGroupId"))) { 
                    Map<String, Object> addressMap = getAddressListByDataGroupId(delegator, job.get("dataGroupId").toString());
                    addressMap.put("dataGroupId", job.get("dataGroupId"));
                    if(UtilValidate.isNotEmpty(addressMap)) {
                        jobDetails.put("addressingData",  addressMap);
                    } else {
                        jobDetails.put("addressingData", null);
                    }
                }else {
                    jobDetails.put("addressingData", null);
                }


            }
        return jobDetails;
    }

    public static Map<String, Object> getAddressListByDataGroupId(Delegator delegator, String dataGroupId) throws GenericEntityException {
        Map<String, Object> addressGroupMap = new HashMap();
        List<String> datas = new ArrayList<>();
        Map<String, Object> datasNew = new HashedMap();
        List<GenericValue> variableDatas = delegator.findByAnd("VariableData", UtilMisc.toMap("variableDataGroupId", dataGroupId), null, false);
        if (UtilValidate.isNotEmpty(variableDatas) && variableDatas.size() > 0) {
            for (GenericValue variableData : variableDatas){
                datas.add(variableData.getString("data"));
            }
        }
        addressGroupMap.put("data", datas);
        return addressGroupMap;
    }

    public static List<Map<String, String>> getAddresses(HttpServletRequest request, String dataGroupId) throws Exception {
        return getAddresses((Delegator) request.getAttribute("delegator"), dataGroupId);
    }

    /**
    The function for retrieving the stored addressList
     **/
    public static List<Map<String, String>> getAddresses(Delegator delegator, String dataGroupId) throws Exception {
        List<String> headers = null;
        List<Map<String, String>> resultAddress = null;
        List<EntityCondition> conditionList = new ArrayList<>();
        if(UtilValidate.isNotEmpty(dataGroupId)) {
            conditionList.add(EntityCondition.makeCondition("variableDataGroupId", EntityOperator.EQUALS, dataGroupId));
        }
        GenericValue savedAddressGroupInfo = EntityQuery.use(delegator).select("variableDataGroupId", "attributes").from("VariableDataGroup").where(conditionList).queryOne();
        if(UtilValidate.isNotEmpty(savedAddressGroupInfo)) {
            String attibutes = (String) savedAddressGroupInfo.get("attributes");
            headers = Click2MailHelper.convertStringToList(attibutes);
            List<GenericValue> addressDatas = EntityQuery.use(delegator).select("data").from("VariableData").where(conditionList).queryList();
            if(UtilValidate.isNotEmpty(addressDatas)) {
                resultAddress = new ArrayList<>();
                for(GenericValue addressValues : addressDatas) {
                    String address = (String) addressValues.get("data");
                    List<String> addressAsList = Click2MailHelper.convertStringToList(address);
                    if(UtilValidate.isNotEmpty(addressAsList) && (addressAsList.size() == headers.size())) {
                        Iterator iterHeader = headers.iterator();
                        Iterator iterrAddress = addressAsList.iterator();
                        Map<String, String> addressValue = new HashMap<>();
                        while (iterHeader.hasNext()) {
                            String header = (String)iterHeader.next();
                            header = header.replace("\"", "");
                            switch (header) {
                                case "Name Line 1": {
                                    addressValue.put("firstName", ((String)iterrAddress.next()).trim());
                                    break;
                                }
                                case "organization": {
                                    addressValue.put("organization", ((String)iterrAddress.next()).trim());
                                    break;
                                }
                                case "Name Line 2": {
                                    addressValue.put("lastName", ((String)iterrAddress.next()).trim());
                                    break;
                                }
                                case "Address Line 1": {
                                    addressValue.put("address1", ((String)iterrAddress.next()).trim());
                                    break;
                                }
                                case "Address Line 2": {
                                    addressValue.put("address2", ((String)iterrAddress.next()).trim());
                                    break;
                                }
                                case "Address Line 3": {
                                    addressValue.put("address3", ((String)iterrAddress.next()).trim());
                                    break;
                                }
                                case "City": {
                                    addressValue.put("city", ((String)iterrAddress.next()).trim());
                                    break;
                                }
                                case "State": {
                                    addressValue.put("state", ((String)iterrAddress.next()).trim());
                                    break;
                                }
                                case "Zip": {
                                    addressValue.put("zip", ((String)iterrAddress.next()).trim());
                                    break;
                                }
                                case "Country": {
                                    addressValue.put("country", ((String)iterrAddress.next()).trim());
                                    break;
                                }
                            }
                        }
                        resultAddress.add(addressValue);
                    }
                }
            }
        }
        return resultAddress;
    }

    public static List<String> convertStringToList(String data) {
        List<String> dataArray = null;
        if(UtilValidate.isNotEmpty(data)) {
            String[] subData = data.replace("[", "").replace("]", "").split(",");
            dataArray = Arrays.asList(subData);
            List<String> dataArrayTrim = new ArrayList<>();
            if (UtilValidate.isNotEmpty(dataArray)) {
                for(String valueToTrim : dataArray) {
                    if(UtilValidate.isNotEmpty(valueToTrim)){
                        dataArrayTrim.add(valueToTrim.replace("\"", "").trim());
                    }
                }
                dataArray = dataArrayTrim;
            }
        }
        return dataArray;
    }

    public static String trimAddressArray(String data) {
        String dataResult = data;
        System.err.println("data = " + data);
        try {
            HashMap<String, Object> result = new ObjectMapper().readValue(data, HashMap.class);
            if(UtilValidate.isNotEmpty(result)) {
               System.err.println("addressingData = " + result.get("addressingData"));
               result.remove("addressingData");
               System.err.println("addressingData = " + result.get("addressingData"));
                for (Map.Entry<String, Object> entry :result.entrySet()) {
                    if(entry.getValue() instanceof String) {
                        result.put(entry.getKey(), ((String)entry.getValue()).trim());
                    }else {
                        result.put(entry.getKey(), entry.getValue());
                    }

                }
               dataResult = new JSONObject(result).toString().trim();
//               dataResult = result.toString();
               System.err.println("final data = " + dataResult);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
          return dataResult;
    }

    public static String getPartyId(HttpServletRequest request) {
        GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
        return userLogin != null ? userLogin.getString("partyId") : "";
    }

    public static void saveJobOrder(Map<String, Object> context, Delegator delegator) throws Exception {
        String jobId = (String) context.get("jobId");
        if (UtilValidate.isNotEmpty(jobId)) {
            GenericValue savedJobOrder = EntityQuery.use(delegator).from("DirectMailingJobOrder").where(UtilMisc.toMap("jobId", jobId)).queryOne();
            if (UtilValidate.isEmpty(savedJobOrder)) {
                savedJobOrder = delegator.makeValue("DirectMailingJobOrder", "jobId", jobId);
            }
            savedJobOrder.put("lastUpdatedDate", UtilDateTime.nowTimestamp());
            savedJobOrder.put("submittedDate", context.get("submittedDate") != null ? UtilDateTime.getTimestamp(sdf.parse((String)context.get("submittedDate")).getTime()) : null);
            savedJobOrder.put("processedFlag", context.get("processedFlag"));
            delegator.createOrStore(savedJobOrder);
        } else {
            throw new Exception("Job Id is empty");
        }
    }

    /**
    Function for checking whether the addressList is updated. This function is called before creating addressList on Click2Mail.
     **/
    public static boolean isAddressListUpdated(String click2MailDateTime, String dataGroupId, Delegator delegator) throws Exception {
        boolean isAddressUpdated = false;
        Timestamp addressModifiedTime = Click2MailHelper.getLastAddressModifiedTime(delegator, dataGroupId);
        if (!click2MailDateTime.equals("0") && UtilValidate.isNotEmpty(addressModifiedTime)) {
            Date click2MailDate = new Date((Long.parseLong(click2MailDateTime)));
            Timestamp click2MailAddressModifiedTime = new Timestamp(click2MailDate.getTime());
            if (addressModifiedTime.after(click2MailAddressModifiedTime)) {
                isAddressUpdated = true;
            }
        }
        return isAddressUpdated;
    }

    /**
    Function for getting last address updated date time
     **/
    public static Timestamp getLastAddressModifiedTime(Delegator delegator, String dataGroupId) throws Exception {
        Timestamp lastUpdatedDate = null;
        SQLProcessor sqlProcessor = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));
        String sqlCommand1 = "SELECT max(LAST_UPDATED_STAMP) AS LAST_UPDATED_STAMP FROM variable_data where VARIABLE_DATA_GROUP_ID = '" + dataGroupId +"'";
        try {
            ResultSet rs = sqlProcessor.executeQuery(sqlCommand1);
            if (rs != null) {
                while (rs.next()) {
                    lastUpdatedDate = rs.getTimestamp("LAST_UPDATED_STAMP");
                }
            }
        }finally {
            if(sqlProcessor != null) {
                sqlProcessor.close();
            }
        }
        return lastUpdatedDate;
    }

    /*
    Fucntion for getting the modified date of job
     */
    /*public static Timestamp getJobLatestUpdateTime(Delegator delegator, String jobId) throws Exception {
        Timestamp lastUpdatedDate = null;
        SQLProcessor sqlProcessor = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));
        String sqlCommand1 = "SELECT LAST_UPDATED_STAMP FROM ofbiz2017.direct_mailing_job where JOB_ID = '" + jobId +"'";
        try {
            ResultSet rs = sqlProcessor.executeQuery(sqlCommand1);
            if (rs != null) {
                while (rs.next()) {
                    lastUpdatedDate = rs.getTimestamp("LAST_UPDATED_STAMP");
                }
            }
        }finally {
            if(sqlProcessor != null) {
                sqlProcessor.close();
            }
        }
        return lastUpdatedDate;
    }*/
}




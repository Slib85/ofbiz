package com.bigname.search.elasticsearch.util;

import com.bigname.search.elasticsearch.SearchField;
import org.apache.commons.lang.math.Fraction;
import org.apache.ofbiz.base.util.UtilValidate;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchUtil {
    public static final String module = SearchUtil.class.getName();

    private static final Pattern NUMBER_PATTERN = Pattern.compile("[\\d\\s\\/\\.-]*");

    /**
     * Take map of conditions and removes any values that are not related to search filters
     * @param context
     * @return TreeMap
     */
    public static Map<String, Object> cleanConditions(Map<String, Object> context, String webSiteId) {
        Map<String, Object> newContext = new TreeMap<>();
        for (SearchField aggregation : (List<SearchField>) SearchField.webSiteData.get(webSiteId).get("aggregationsList")) {
            if (context.containsKey(aggregation.getFilterId()) && UtilValidate.isNotEmpty(context.get(aggregation.getFilterId()))) {
                if(context.get(aggregation.getFilterId()) instanceof List) {
                    List<String> filter = (List<String>) context.get(aggregation.getFilterId());
                    filter.sort(String.CASE_INSENSITIVE_ORDER);
                    newContext.put(aggregation.getFilterId(), filter);
                } else {
                    newContext.put(aggregation.getFilterId(), context.get(aggregation.getFilterId()));
                }
            }
        }

        return newContext;
    }

    /**
     * Clean query to convert case and remove special chars
     * @param value
     * @return
     */
    public static String cleanQuery(String value) {
        if(isNullOrWhiteSpace(value)) {
            return null;
        }

        value = value.trim().replaceAll(" +", " ");
        value = value.toLowerCase();

        return value;
    }

    /**
     * Check if string is empty
     * @param value
     * @return
     */
    public static boolean isNullOrWhiteSpace(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Split the phrase, remove un-needed terms as well
     * @param phrase
     * @return
     */
    public static String[] splitTerms(String phrase) {
        phrase = phrase.replace("&","");
        phrase = SearchUtil.cleanQuery(phrase);
        return phrase.split(" ");
    }

    /**
     * Return float value from number that is decimal or fraction
     * @param number
     * @return
     */
    public static Float convertToFloat(String number) {
        number = number.trim().replaceAll("(\\s|-)+", " ").replaceAll("(\\s\\/\\s?)|(\\s?\\/\\s)", "/");
        Matcher matcher = NUMBER_PATTERN.matcher(number);
        if(matcher.matches()) {
            return Fraction.getFraction(number).floatValue();
        } else {
            return 0f;
        }
    }

    /**
     * Take a string value and convert it to a facet id value
     * Certain facet values need to keep underscore
     * @param value
     * @param keepUnderscore
     * @return
     */
    public static String cleanFacet(String value, boolean keepUnderscore) {
        if(isNullOrWhiteSpace(value)) {
            return null;
        }

        value = value.trim();
        value = value.replaceAll(keepUnderscore ? "[^a-zA-Z0-9_]" : "[^a-zA-Z0-9]", "");
        value = value.toLowerCase();

        return value;
    }

    /**
     * Get the upper bound for filtering using ratings
     * We only do half increments on 3 and above
     * @param db
     * @return
     */
    public static Double getUpperBoundForRatingsFilter(Double db) {
        if(db.intValue() <= 2) {
            return db.intValue() + 1.0;
        } else if (Double.valueOf(db/.5).intValue() % 2 == 1) {
            return Math.ceil(db);
        } else {
            return db.intValue() + .5;
        }
    }
}
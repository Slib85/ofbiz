package com.bigname.search.elasticsearch;

/**
 * Created by shoab on 3/26/18.
 */

import org.apache.ofbiz.base.util.UtilValidate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class will be used to analyze search queries to add BIGNAME specific
 * rules.
 */
public class SearchRules {
    public static final String module = SearchRules.class.getName();

    private SearchTranslator translator = null;

    public SearchRules(SearchTranslator translator) {
        this.translator = translator;
        this.process();
    }

    /**
     * If the query is a single term with what appears to be a size (determined by numbers separated by an "x")
     * Then split the numbers by the x and add spacing to treat as separate terms
     */
    public void checkQueryForSize() {
        if(isSizeQuery()) {
            this.translator.isSingleTerm = false;
            this.translator.query = this.translator.query.toLowerCase().replaceAll("([\\d\\.]+?)(?:\\s+|)x(?:\\s+|)([\\d\\.]+)", " $1 x $2 ").replaceAll("(?:^\\s+|\\s+$)", "").replaceAll("\\s+", " ");
            this.translator.queryTerms = this.translator.query.split(" ");
        }
    }

    /**
     * If the query contains a hashtag followed by a number, separate the hashtag and number
     */
    public void checkQueryForHashtag() {
        if(isHashtagQuery()) {
            this.translator.isSingleTerm = false;
            this.translator.query = this.translator.query.toLowerCase().replaceAll("(#)(\\d+)", " $1 $2 ").replaceAll("(?:^\\s+|\\s+$)", "").replaceAll("\\s+", " ");
            this.translator.queryTerms = this.translator.query.split(" ");
        }
    }

    /**
     * Check if there is a size in the query
     * @return
     */
    private boolean isSizeQuery() {
        if (UtilValidate.isNotEmpty(this.translator.query)) {
            String regex = "([\\d\\.]+?(?:\\s+|)x(?:\\s+|)[\\d\\.]+)";
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(this.translator.query);
            if (matcher.find()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Check if there is a hash in the query
     * @return
     */
    private boolean isHashtagQuery() {
        if (UtilValidate.isNotEmpty(this.translator.query)) {
            String regex = "(#\\d+)";
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(this.translator.query);
            if (matcher.find()) {
                return true;
            }
        }

        return false;
    }

    //process the query changes
    public void process() {
        this.checkQueryForSize();
        this.checkQueryForHashtag();
    }
}

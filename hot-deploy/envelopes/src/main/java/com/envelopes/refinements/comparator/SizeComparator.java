package com.envelopes.refinements.comparator;

import com.envelopes.refinements.model.Product;
import com.envelopes.util.EnvUtil;
import org.apache.commons.lang.math.Fraction;
import org.apache.ofbiz.base.util.Debug;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Manu on 9/18/2014.
 */
public class SizeComparator implements Comparator<Product> {
    public static final String module = SizeComparator.class.getName();
    private static final int MAX_SIZE_TOKENS = 3;
    private static final Pattern NUMBER_PATTERN = Pattern.compile("[\\d\\s\\/\\.-]*");
    private boolean ascendingOrder = true;

    public SizeComparator(boolean... ascending) {
        this.ascendingOrder = !(ascending != null && ascending.length == 1) || ascending[0];
    }

    @Override
    public int compare(Product o1, Product o2) {
        String size1 = o1.getProductSize();
        if(size1 == null) {
            size1 = "";
        }

        String size2 = o2.getProductSize();
        if(size2 == null) {
            size2 = "";
        }

        String[] size1TokensString = size1.split("(?i)x");

        String[] size2TokensString = size2.split("(?i)x");

        Float[] size1Tokens = new Float[MAX_SIZE_TOKENS];
        Float[] size2Tokens = new Float[MAX_SIZE_TOKENS];

        for(int i = 0; i < MAX_SIZE_TOKENS; i ++) {
            if(i < size1TokensString.length) {
                size1Tokens[i] = convertToFloat(size1TokensString[i]);
            } else {
                size1Tokens[i] = 0f;
            }

            if(i < size2TokensString.length) {
                size2Tokens[i] = convertToFloat(size2TokensString[i]);
            } else {
                size2Tokens[i] = 0f;
            }

            if(size1Tokens[i].compareTo(size2Tokens[i]) != 0) {
                return ascendingOrder ? size1Tokens[i].compareTo(size2Tokens[i]): size1Tokens[i].compareTo(size2Tokens[i])  * -1 ;

            }
        }
        return 0;
    }

    public static Float convertToFloat(String number) {

        try {
            number = number.trim().replaceAll("(\\s|-)+", " ").replaceAll("(\\s\\/\\s?)|(\\s?\\/\\s)", "/");
            Matcher matcher = NUMBER_PATTERN.matcher(number);
            if(matcher.matches() && !"".equals(number)) {
                return Fraction.getFraction(number).floatValue();
            } else {
                return 0f;
            }
        } catch (Exception e) {
            Debug.logError(e, module);
            EnvUtil.reportError(e);
        }
        return 0f;
    }

}

package com.envelopes.refinements.comparator;

import com.envelopes.refinements.model.Product;
import org.apache.commons.lang.math.NumberUtils;

import java.util.Comparator;

/**
 * Created by Manu on 9/18/2014.
 */
public class PopularityComparator implements Comparator<Product> {
    private boolean ascendingOrder = true;

    public PopularityComparator(boolean... ascending) {
        this.ascendingOrder = !(ascending != null && ascending.length == 1) || ascending[0];
    }

    @Override
    public int compare(Product o1, Product o2) {
        Double rank1 = o1.getProductRank();
        if(rank1 == null) {
            rank1 = 999999D;
        }

        Double rank2 = o2.getProductRank();
        if(rank2 == null) {
            rank2 = 999999D;
        }

        return ascendingOrder ? rank1.compareTo(rank2) : rank1.compareTo(rank2)  * -1;
    }
}

package com.envelopes.refinements.comparator;

import com.envelopes.refinements.model.Product;
import org.apache.commons.lang.math.NumberUtils;

import java.util.Comparator;

/**
 * Created by Manu on 9/18/2014.
 */
public class PriceComparator implements Comparator<Product> {

    private boolean ascendingOrder = true;

    public PriceComparator(boolean... ascending) {
        this.ascendingOrder = !(ascending != null && ascending.length == 1) || ascending[0];
    }

    @Override
    public int compare(Product o1, Product o2) {
        Double price1 = NumberUtils.toDouble(o1.getProductPrice(), 999999D);
        Double price2 = NumberUtils.toDouble(o2.getProductPrice(), 999999D);

        return ascendingOrder ? price1.compareTo(price2) : price1.compareTo(price2) * -1;
    }

}

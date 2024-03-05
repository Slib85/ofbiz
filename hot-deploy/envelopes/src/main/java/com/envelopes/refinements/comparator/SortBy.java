package com.envelopes.refinements.comparator;

import com.envelopes.refinements.model.Product;

import java.util.Comparator;

/**
 * Created by Manu on 9/18/2014.
 */
public enum SortBy {
    SIZE_SMALL {
        public Comparator<Product> getComparator() {
            return new SizeComparator();
        }
    },

    SIZE_LARGE {
        public Comparator<Product> getComparator() {
            return new SizeComparator(false);
        }
    },

    PRICE_LOW {
        public Comparator<Product> getComparator() {
            return new PriceComparator();
        }
    },

    PRICE_HIGH {
        public Comparator<Product> getComparator() {
            return new PriceComparator(false);
        }
    },

    MOST_POPULAR {
        public Comparator<Product> getComparator() {
            return new PopularityComparator();
        }
    },

    DEFAULT_SORT {
        public Comparator<Product> getComparator() {
            return new SizeComparator();
        }
    };

    private boolean defaultSort = false;


    public Comparator<Product> getComparator() {
        return new SizeComparator();
    }

    public static SortBy getSortBy(String name) {

        try {
            return valueOf(name);
        } catch (Exception e) {
            return DEFAULT_SORT;
        }
    }

}

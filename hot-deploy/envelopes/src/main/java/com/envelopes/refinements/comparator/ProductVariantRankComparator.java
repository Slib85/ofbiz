package com.envelopes.refinements.comparator;

import com.envelopes.refinements.model.ProductVariant;

import java.util.Comparator;

/**
 * Created by Manu on 10/19/2015.
 */
public class ProductVariantRankComparator implements Comparator<ProductVariant> {
	@Override
	public int compare(ProductVariant variant1, ProductVariant variant2) {
		double rank1 = variant1.getProductVariantSalesRank();
		double rank2 = variant2.getProductVariantSalesRank();
		return (int) (rank2 - rank1);
	}
}

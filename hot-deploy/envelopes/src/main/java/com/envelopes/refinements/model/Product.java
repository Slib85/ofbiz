package com.envelopes.refinements.model;

import com.envelopes.common.HashMapSupport;
import com.envelopes.product.ProductHelper;
import org.apache.ofbiz.base.util.UtilValidate;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Manu on 8/5/2014.
 */
public class Product extends HashMapSupport {

    private String productId = "";
    private String productName = "";
    private String productSize = "";
    private String productPrice = "";
    private String productImageURL = "";
    private Double productRank = 999999D;
	private Double productSalesRank = 0D;
	private Double productSalesPercentage = 0D;
    private String productRating = "0";
    private String productURL = "";
    private String unfriendlyProductURL = "";
	private String productPrintable = "";
	private String productIsNew = "";
	private String productOnClearance = "";
    private boolean suppressSize = false;
	private String productOnSale = "";
	private Double productBaseQuantity = 0D;

//    public static final Pattern cardSizePattern = Pattern.compile("\\s?\\(\\s?\\d+((\\s|-)\\d+\\/\\d+|.\\d+)?(\\sx\\s)(\\d+)((\\s|-)\\d+\\/\\d+|.\\d+)?\\s?\\)");
//    public static final Pattern cardSizePattern = Pattern.compile("\\s?\\(\\s?\\d+((\\s|-)\\d+\\/\\d+|.\\d+)?(\\sx\\s)(\\d+)((\\s|-)\\d+\\/\\d+|.\\d+)?\\s?(\\s?(?i)Closed)?\\)");
//    public static final Pattern cardSizePattern = Pattern.compile("\\s?\\(\\s*\\d+((\\s|-)*\\d+\\/\\d+|.\\d+)?(\\s*(?i)x\\s*)(\\d+)((\\s|-)*\\d+\\/\\d+|.\\d+)?(\\s*(?i)Closed)?\\s*\\)");
    private ProductVariant defaultProductVariant = null;
    private List<ProductVariant> productVariants = new ArrayList<>();

    public Product() {
        put("productVariants", productVariants);
        put("defaultProductVariant", defaultProductVariant);
    }

    public String getProductId() {
        return this.productId;
    }

    public String getProductName() {
        return this.productName;
    }

    public String getProductSize() {
        return this.productSize;
    }

    public String getProductPrice() {
        return this.productPrice;
    }

    public String getProductImageURL() {
        return this.productImageURL;
    }

    public String getProductURL() {
        return this.productURL;
    }

	public String getUnfriendlyProductURL() {
		return unfriendlyProductURL;
	}

	public Double getProductRank() {
        return productRank;
    }

    public String getProductRating() {
        return productRating;
    }

	public Double getProductSalesRank() {
		return productSalesRank;
	}

	public String getProductOnSale() {
		return productOnSale;
	}

    public Double getProductBaseQuantity() {
        return productBaseQuantity;
    }

	public ProductVariant getDefaultProductVariant() {
        if(isValid() && this.defaultProductVariant == null) {
            this.defaultProductVariant = productVariants.get(0);
        }
        return this.defaultProductVariant;
    }

    public void setDefaultProductVariant(ProductVariant defaultProductVariant) {
        this.defaultProductVariant = defaultProductVariant;
        this.productId = this.defaultProductVariant.getProductId();
        this.productName = ProductHelper.formatName(this.defaultProductVariant.getProductVariantName());
        this.productSize = this.defaultProductVariant.getProductVariantSize();
        if(UtilValidate.isNotEmpty(this.productSize) && this.productName.contains(this.productSize)) {
            this.suppressSize = true;
        }
        this.productPrice = this.defaultProductVariant.getProductVariantPrice();
        this.productImageURL = this.defaultProductVariant.getProductVariantImageURL().replaceAll("/small/", "/medium/");
		this.productPrintable = this.defaultProductVariant.getProductVariantPrintable();
		this.productIsNew = this.defaultProductVariant.getProductVariantIsNew();
		this.productOnClearance = this.defaultProductVariant.getProductVariantOnClearance();
        this.productURL = this.defaultProductVariant.getProductVariantURL();
        this.unfriendlyProductURL = this.defaultProductVariant.getUnfriendlyProductVariantURL();
        this.productRank = this.defaultProductVariant.getProductVariantRank();
        this.productRating = this.defaultProductVariant.getProductVariantRating();
		this.productSalesRank = this.defaultProductVariant.getProductVariantSalesRank();
		this.productOnSale = this.defaultProductVariant.getProductVariantIsOnSale();
		this.productSalesPercentage = this.defaultProductVariant.getProductVariantSalesPercentage();
        this.productBaseQuantity = this.defaultProductVariant.getProductVariantBaseQuantity();
    }

    public void addProductVariant(ProductVariant productVariant) {
        if(productVariant != null && !productVariant.isEmpty()) {
            this.productVariants.add(productVariant);
        }
    }

    public boolean isSuppressSize() {
        return suppressSize;
    }

    public void setSuppressSize(boolean suppressSize) {
        this.suppressSize = suppressSize;
    }

    public List<ProductVariant> getProductVariants() {
        return this.productVariants;
    }

    public int getProductVariantCount() {
        return this.productVariants.size();
    }

    public boolean isValid() {
        return this.productVariants.size() > 0;
    }

	public String getProductPrintable() {
		return productPrintable;
	}

	public String getProductIsNew() {
		return productIsNew;
	}

	public String getProductOnClearance() {
		return productOnClearance;
	}
}

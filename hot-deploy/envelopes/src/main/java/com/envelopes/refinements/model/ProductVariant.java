package com.envelopes.refinements.model;

import com.envelopes.common.HashMapSupport;
import com.envelopes.product.ProductHelper;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ofbiz.base.util.UtilValidate;

import java.util.Map;

/**
 * Created by Manu on 8/5/2014.
 */
public class ProductVariant extends HashMapSupport {

    private String productVariantName = "";
    private String productVariantSize = "";
    private String productVariantPrice = "";
    private String productVariantImageURL = "";
    private String productVariantURL = "";
    private String unfriendlyProductVariantURL = "";
    private String productVariantColorGroup = "";
    private String productVariantColor = "";
    private Double productVariantRank = 999999D;
	private Double productVariantSalesRank = 0D;
	private Double productVariantSalesPercentage = 0D;
	private String productVariantId = "";
	private String productId = "";
	private String productVariantRating = "0";
	private String itemUrlPrefix = "";
	private String itemUrlParameters = "";
	private String productVariantPrintable = "";
	private String productVariantIsNew = "";
	private String productVariantOnClearance = "";
	private String productVariantIsOnSale = "";
	private Double productVariantBaseQuantity = 0D;
    private String sliItemURLPrefix = "", unfriendlySliItemURLPrefix = "";


    public ProductVariant(Map sliItem, String itemUrlPrefix, String itemUrlParameters) {
        super(sliItem);
        this.itemUrlPrefix = itemUrlPrefix;
		this.itemUrlParameters = itemUrlParameters;
        loadAttributes();
    }

    protected void loadAttributes() {
		sliItemURLPrefix = ((String)get(SLIResponse.Attribute.ITEM_URL.getSliAttribute())).startsWith("https") ? "https://www.envelopes.com" : "http://www.envelopes.com";
		unfriendlySliItemURLPrefix = ((String)get(SLIResponse.Attribute.UNFRIENDLY_ITEM_URL.getSliAttribute())).startsWith("https") ? "https://www.actionenvelope.com" : "http://www.actionenvelope.com";
        productVariantName = ProductHelper.formatName((String) get(SLIResponse.Attribute.TITLE.getSliAttribute()));
        productVariantSize = (String)get(SLIResponse.Attribute.SIZE.getSliAttribute());
        productVariantPrice = (String)get(SLIResponse.Attribute.PRICE.getSliAttribute());
        productVariantImageURL = ((String)get(SLIResponse.Attribute.IMAGE_URL.getSliAttribute())).replaceAll("/small/", "/medium/");
        productVariantURL = ((String)get(SLIResponse.Attribute.ITEM_URL.getSliAttribute())).replace(sliItemURLPrefix, UtilValidate.isEmpty(itemUrlPrefix) ? sliItemURLPrefix : itemUrlPrefix);

        unfriendlyProductVariantURL = UtilValidate.isEmpty(get(SLIResponse.Attribute.UNFRIENDLY_ITEM_URL.getSliAttribute())) ? productVariantURL : ((String)get(SLIResponse.Attribute.UNFRIENDLY_ITEM_URL.getSliAttribute())).replace(unfriendlySliItemURLPrefix, UtilValidate.isEmpty(itemUrlPrefix) ? unfriendlySliItemURLPrefix : itemUrlPrefix);
		if(UtilValidate.isNotEmpty(itemUrlParameters)) {
			if (productVariantURL.contains("?")) {
				productVariantURL += itemUrlParameters;
			} else {
				productVariantURL += "?" + itemUrlParameters;
			}
			if (unfriendlyProductVariantURL.contains("?")) {
				unfriendlyProductVariantURL += itemUrlParameters;
			} else {
				unfriendlyProductVariantURL += "?" + itemUrlParameters;
			}
		}
		productVariantPrintable = (String)get(SLIResponse.Attribute.PRINTABLE.getSliAttribute());
		if(UtilValidate.isEmpty(productVariantPrintable)) {
			productVariantPrintable = "N";
		}
		productVariantIsNew = (String)get(SLIResponse.Attribute.NEW.getSliAttribute());
		if(UtilValidate.isEmpty(productVariantIsNew)) {
			productVariantIsNew = "N";
		}
		productVariantOnClearance = (String)get(SLIResponse.Attribute.CLEARANCE.getSliAttribute());
		if(UtilValidate.isEmpty(productVariantOnClearance)) {
			productVariantOnClearance = "N";
		}

		productVariantColorGroup = (String)get(SLIResponse.Attribute.COLOR_GROUP.getSliAttribute());
        productVariantColor = (String)get(SLIResponse.Attribute.COLOR.getSliAttribute());
        productVariantRank = (Double)get(SLIResponse.Attribute.RANK.getSliAttribute());
        productVariantRating = (String)get(SLIResponse.Attribute.RATING.getSliAttribute());
        if(productVariantRating == null || productVariantRating.trim().equals("")) {
            productVariantRating = "0";
        } else {
            Double ratings = NumberUtils.toDouble(productVariantRating.contains(".") ? productVariantRating.substring(0, productVariantRating.indexOf(".") + 1) : productVariantRating , 0D);
            productVariantRating = ratings.toString().replace(".", "_");
        }
		productVariantSalesRank = NumberUtils.toDouble((String)get(SLIResponse.Attribute.SALES_RANK.getSliAttribute()));
		productVariantBaseQuantity = NumberUtils.toDouble((String)get(SLIResponse.Attribute.QUANTITY.getSliAttribute()));
		productVariantSalesPercentage = NumberUtils.toDouble((String)get(SLIResponse.Attribute.PERCENTAGE_SAVINGS.getSliAttribute()));
		productVariantIsOnSale = (String)get(SLIResponse.Attribute.ON_SALE.getSliAttribute());
		if(UtilValidate.isEmpty(productVariantIsOnSale)) {
			productVariantIsOnSale = "N";
		}
        productVariantId = (String)get(SLIResponse.Attribute.PRODUCT_ID.getSliAttribute());
        productId = (String)get(SLIResponse.Attribute.PARENT_ID.getSliAttribute());
    }

    public String getProductVariantName() {
        return productVariantName;
    }

    public String getProductVariantSize() {
        return productVariantSize;
    }

    public String getProductVariantPrice() {
        return productVariantPrice;
    }

    public String getProductVariantImageURL() {
        return productVariantImageURL;
    }

    public String getProductVariantURL() {
        return productVariantURL;
    }

	public String getUnfriendlyProductVariantURL() {
		return unfriendlyProductVariantURL;
	}

	public String getProductVariantColorGroup() {
        return productVariantColorGroup;
    }

    public String getProductVariantColor() {
        return productVariantColor;
    }

    public Double getProductVariantRank() {
        return productVariantRank;
    }

    public String getProductVariantRating() {
        return productVariantRating;
    }

    public String getProductVariantId() {
        return productVariantId;
    }

    public String getProductId() {
        return productId;
    }

	public String getProductVariantPrintable() {
		return productVariantPrintable;
	}

	public String getProductVariantIsNew() {
		return productVariantIsNew;
	}

	public String getProductVariantOnClearance() {
		return productVariantOnClearance;
	}

	public Double getProductVariantSalesRank() {
		return productVariantSalesRank;
	}

	public String getProductVariantIsOnSale() {
		return productVariantIsOnSale;
	}

	public Double getProductVariantSalesPercentage() {
		return productVariantSalesPercentage;
	}

	public Double getProductVariantBaseQuantity() {
		return productVariantBaseQuantity;
	}
}

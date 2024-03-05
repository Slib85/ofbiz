/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.product.importer;

import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductValidation {
    public static final String module = ProductValidation.class.getName();

    private List<String> errors = new ArrayList<>();
    private List<String> duplicates = new ArrayList<>();
    private boolean hasErrors = false;

    public ProductValidation(List<Map<String, Object>> products) {
        int i = 1;
        for (Map<String, Object> product : products) {
            if(!hasValidPricing(product)) {
                this.hasErrors = true;
                this.errors.add("Product on Line " + i + " has issues with pricing.");
            }

            i++;
        }
    }

    public boolean isValid() {
        return !this.hasErrors;
    }

    public List<String> getErrors() {
        return this.errors;
    }

    private boolean hasValidPricing(Map<String, Object> product) {
        boolean isValid = true;
        //check for duplicate prices
        List<String> qtyColList = new ArrayList<>();

        String priceCtx = (String) product.get(ProductEnum.PRICE.name());
        if(UtilValidate.isNotEmpty(priceCtx) && !priceCtx.equalsIgnoreCase("PRICING_ENGINE")) {
            String[] eachPrice = priceCtx.split(";");
            for (int i = 0; i < eachPrice.length; i++) {
                String[] qtyPrice = eachPrice[i].split(",");
                if (qtyPrice.length == 3) {
                    if (qtyColList.contains(qtyPrice[0].trim() + "_" + qtyPrice[1].trim())) {
                        this.errors.add(product.get(ProductEnum.CODE.name()) + ", has duplicate prices.");
                        isValid = false;
                    } else {
                        qtyColList.add(qtyPrice[0].trim() + "_" + qtyPrice[1].trim());
                    }
                } else {
                    this.errors.add(product.get(ProductEnum.CODE.name()) + ", has a missing value for price.");
                    isValid = false;
                }

                if (qtyPrice.length == 3 && (Long.valueOf(qtyPrice[0].trim()) < 0 || Long.valueOf(qtyPrice[1].trim()) < 0 || (new BigDecimal(qtyPrice[2].trim())).compareTo(BigDecimal.ZERO) <= 0)) {
                    this.errors.add(product.get(ProductEnum.CODE.name()) + ", has a incorrect value for price.");
                    isValid = false;
                }
            }
        }

        return isValid;
    }
}
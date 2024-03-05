package com.envelopes.product;

import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.model.DynamicViewEntity;
import org.apache.ofbiz.entity.model.ModelKeyMap;
import org.apache.ofbiz.entity.util.EntityQuery;

import java.util.*;

/**
* Created by Manu on 4/4/2017.
* Line broked else by Mikey on 4/5/2017.
* Hated by Shoab one second later.
*/
public class FolderProductHelper {
    public static List<GenericValue> getProductMaterials(Delegator delegator, String productId, Boolean basicAndPremiumFlag) throws GenericEntityException {
        List<EntityCondition> conditions = new ArrayList<>();
        conditions.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId));
        if(basicAndPremiumFlag != null) {
            if(basicAndPremiumFlag) {
                conditions.add(EntityCondition.makeCondition("type", EntityOperator.IN, UtilMisc.toList("basic", "premium")));
            } else {
                conditions.add(EntityCondition.makeCondition("type", EntityOperator.NOT_IN, UtilMisc.toList("basic", "premium")));
            }
        }
        DynamicViewEntity dve = new DynamicViewEntity();
        dve.addMemberEntity("PM", "ProductMaterial");
        dve.addAlias("PM", "materialId");
        dve.addAlias("PM", "productId");
        dve.addMemberEntity("M", "Material");
        dve.addAliasAll("M", null, null);
        dve.addViewLink("PM", "M", false, ModelKeyMap.makeKeyMapList("materialId", "materialId"));

        if (basicAndPremiumFlag) {
            return EntityQuery.use(delegator).select("materialId", "type", "description", "color", "coating", "price", "img", "colorHexCode").from(dve).where(conditions).orderBy("sequenceNumber").queryList();
        } else {
            return EntityQuery.use(delegator).select("materialId", "type", "description", "color", "coating", "price", "img", "colorHexCode").from(dve).where(conditions).orderBy("type").queryList();
        }
    }

    public static List<GenericValue> getProductMaterials(Delegator delegator, String materialType, String productId) throws GenericEntityException {
        if (UtilValidate.isNotEmpty(materialType)) {
            return EntityQuery.use(delegator).from("Material").where("type", materialType).cache().queryList();
        } else {
            return EntityQuery.use(delegator).from("MaterialAndProductMaterial").where("productId", productId).cache().queryList();
        }
    }

    public static List<GenericValue> getPaperStocks(Delegator delegator) throws GenericEntityException {
        List<EntityCondition> conditions = new ArrayList<>();
        conditions.add(EntityCondition.makeCondition("type", EntityOperator.IN, UtilMisc.toList("basic", "premium")));
        return EntityQuery.use(delegator).from("Material").where(conditions).orderBy("price").cache().queryList();
    }

    public static List<GenericValue> getProductSlitInfo(Delegator delegator, String productId) throws GenericEntityException {
        return EntityQuery.use(delegator).from("ProductSlit").where("style", productId).orderBy(UtilMisc.toList("pocket DESC", "type DESC")).cache().orderBy("pocket").queryList();
    }

    public static List<GenericValue> getFolderCategoryList(Delegator delegator) throws GenericEntityException {
        return EntityQuery.use(delegator).select("productCategoryId", "categoryName").from("ProductCategory").where("primaryParentCategoryId", "FOLDERS").cache().queryList();
    }

    public static List<GenericValue> getFolderStyleList(Delegator delegator, String categoryId) throws GenericEntityException {
        if (UtilValidate.isEmpty(categoryId)) {
            categoryId = "all";
        }

        if (categoryId.toLowerCase().equals("all")) {
            return EntityQuery.use(delegator).select("productId", "productName").from("Product").where("parentProductId", "FOLDER_STYLE").cache().queryList();
        }
        else {
            return EntityQuery.use(delegator).select("productId", "productName").from("Product").where("parentProductId", "FOLDER_STYLE", "primaryProductCategoryId", categoryId.toUpperCase()).cache().queryList();
        }
    }

    public static List<GenericValue> getProductFoils(Delegator delegator, Boolean hasMetallic) throws GenericEntityException {
        return EntityQuery.use(delegator).from("FoilGuide").where("type", (hasMetallic ? "Metallic" : "Non-Metallic")).cache().queryList();
    }
}
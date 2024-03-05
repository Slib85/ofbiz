package com.bigname.marketplace;

import com.bigname.core.restful.client.util.DateFormatter;
import com.bigname.marketplace.mirakl.BignameShippingMethod;
import com.bigname.marketplace.mirakl.MiraklUtil;
import com.bigname.marketplace.mirakl.client.domain.*;
import com.bigname.marketplace.mirakl.client.request.CreateOrderRequest;
import com.envelopes.order.OrderHelper;
import com.envelopes.party.PartyHelper;
import com.envelopes.product.ProductHelper;
import com.envelopes.product.importer.ProductImporter;
import com.envelopes.util.EnvUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntity;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.model.DynamicViewEntity;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.order.order.OrderReadHelper;
import org.supercsv.cellprocessor.*;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListReader;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.quote.AlwaysQuoteMode;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.envelopes.party.PartyHelper.*;

/**
 * Created by Manu on 2/6/2017.
 */
public class MarketplaceHelper {
    public static final String module = MarketplaceHelper.class.getName();


    public static void syncMarketplaceSellers(Delegator delegator, MiraklShops miraklShops) throws GenericEntityException {

        List<GenericValue> sellersList = getAllSellers(delegator);
        Map<String, GenericValue> sellerMap = new HashMap<>();
        sellersList.forEach(s -> sellerMap.put(s.getString(MiraklShop.ID), s));

        Map<String, MiraklShop> shopsMap = new HashMap<>();
        miraklShops.getShops().forEach(s -> shopsMap.put(s.getId(), s));

        // Create / Modify Mirakl Shops to Bigname
        for (MiraklShop shop : miraklShops.getShops()) {
            GenericValue seller;
            String sellerId;
            //Update
            if (sellerMap.containsKey(sellerId = shop.getId())) {
                seller = sellerMap.get(sellerId);
                //Shop modified in Mirakl after last update
                if (shop.hasUpdatedAfter(seller.getTimestamp(MiraklShop.LAST_UPDATED_DATE))) {
                    seller.putAll(shop.getAsMap(false));
//                    seller.remove("fromDate");
//                    seller.remove("thruDate");
                }
                //Shop not modified in Mirakl after last update
                else {
                    continue;
                }
            }
            //Create
            else {
                seller = delegator.makeValue("MarketplaceSeller");
                seller.putAll(shop.getAsMap(true));
            }

            delegator.createOrStore(seller);

        }

        //Deactivate Shops that are removed in Mirakl - Not needed, since no delete store option available in Mirakl
        /*for(GenericValue seller : sellersList) {
            if(!shopsMap.containsKey(seller.getString(MiraklShop.ID))) {
                seller.put("thruDate", UtilDateTime.nowTimestamp());
                delegator.createOrStore(seller);
            }
        }*/
    }

    public static void syncMarketplaceHierarchies(Delegator delegator, MiraklHierarchies miraklHierarchies) throws GenericEntityException {
        List<GenericValue> hierarchiesList = getAllHierarchies(delegator);
        Map<String, GenericValue> hierarchyMap = new HashMap<>();
        hierarchiesList.forEach(h -> hierarchyMap.put(h.getString(MiraklHierarchy.ID), h));

        Map<String, MiraklHierarchy> miraklHierarchyMap = new HashMap<>();
        miraklHierarchies.getHierarchies().forEach(mh -> miraklHierarchyMap.put(mh.getCode(), mh));

        for (MiraklHierarchy miraklHierarchy : miraklHierarchies.getHierarchies()) {
            GenericValue hierarchy;
            String hierarchyId;

            if (hierarchyMap.containsKey(hierarchyId = miraklHierarchy.getCode())) {
                hierarchy = hierarchyMap.get(hierarchyId);
                if (!miraklHierarchy.equals(hierarchy)) {
                    hierarchy.putAll(miraklHierarchy.getAsMap(false));
                } else {
                    continue;
                }
            } else {
                hierarchy = delegator.makeValue("MarketplaceHierarchy");
                hierarchy.putAll(miraklHierarchy.getAsMap(true));
            }

            delegator.createOrStore(hierarchy);
        }

        //Remove Hierarchies that are removed in Mirakl
        //TODO - BUG - delete child hierarchies first
        for (GenericValue hierarchy : hierarchiesList) {
            if (!miraklHierarchyMap.containsKey(hierarchy.getString(MiraklHierarchy.ID))) {
                delegator.removeValue(hierarchy);
            }
        }
    }

    public static void syncMarketplaceCategories(Delegator delegator, MiraklCategories miraklCategories) throws GenericEntityException {
        List<GenericValue> categoriesList = getAllCategories(delegator);

        Map<String, Long> levelMap = getCategoryLevelMap(delegator);

        Map<String, GenericValue> categoryMap = new HashMap<>();
        categoriesList.forEach(c -> categoryMap.put(c.getString(MiraklCategory.ID), c));

        Map<String, MiraklCategory> miraklCategoryMap = new HashMap<>();
        miraklCategories.getCategories().forEach(mc -> miraklCategoryMap.put(mc.getCode(), mc));

        for(int i = 1; i <=3; i ++) { //Sync Catalog Categories, Root Categories and then Sub Categories to satisfy foreign key relationship

            for (MiraklCategory miraklCategory : miraklCategories.getCategories()) {
                if(!levelMap.containsKey(miraklCategory.getCode()) || levelMap.get(miraklCategory.getCode()) != i) {
                    //Sync Catalog Categories, Root Categories and then Sub Categories to satisfy foreign key relationship
                    continue;
                }
                GenericValue category;
                String categoryId;

                if (categoryMap.containsKey(categoryId = miraklCategory.getCode())) {
                    category = categoryMap.get(categoryId);
                    //if (!miraklCategory.equals(category)) {
                    category.putAll(miraklCategory.getAsMap(false));
                    //} else {
                    //   continue;
                    //}
                } else {
                    category = delegator.makeValue("MarketplaceCategory");
                    category.putAll(miraklCategory.getAsMap(true));
                }
                category.put(MiraklCategory.LEVEL, levelMap.containsKey((String) category.get(MiraklCategory.ID)) ? levelMap.get((String) category.get(MiraklCategory.ID)) : 1);

                delegator.createOrStore(category);
            }
        }

        //Remove Categories that are removed in Mirakl
        //TODO - BUG - delete child categories first
        for (GenericValue category : categoriesList) {
            if (!miraklCategoryMap.containsKey(category.getString(MiraklCategory.ID))) {
                delegator.removeValue(category);
            }
        }
    }

    public static void syncMarketplaceProductAttributes(Delegator delegator, MiraklProductAttributes miraklProductAttributes) throws GenericEntityException {
        List<GenericValue> attributesList = getAllProductAttributes(delegator);
        Map<String, GenericValue> attributeMap = new HashMap<>();
        attributesList.forEach(a -> attributeMap.put(a.getString(MiraklProductAttribute.ID), a));

        Map<String, MiraklProductAttribute> miraklAttributeMap = new HashMap<>();
        miraklProductAttributes.getAttributes().forEach(ma -> miraklAttributeMap.put(ma.getCode(), ma));

        for (MiraklProductAttribute miraklProductAttribute : miraklProductAttributes.getAttributes()) {
            GenericValue attribute;
            String attributeId;

            if (attributeMap.containsKey(attributeId = miraklProductAttribute.getCode())) {
                attribute = attributeMap.get(attributeId);
                if (!miraklProductAttribute.equals(attribute)) {
                    attribute.putAll(miraklProductAttribute.getAsMap(false));
                } else {
                    continue;
                }
            } else {
                attribute = delegator.makeValue("MarketplaceProductAttribute");
                attribute.putAll(miraklProductAttribute.getAsMap(true));
            }

            delegator.createOrStore(attribute);
        }

        //Remove Attributes that are removed in Mirakl
        for (GenericValue attribute : attributesList) {
            if (!miraklAttributeMap.containsKey(attribute.getString(MiraklProductAttribute.ID))) {
                delegator.removeValue(attribute);
            }
        }
    }

    public static List<GenericValue> getAllHierarchies(Delegator delegator) throws GenericEntityException {
        return delegator.findAll("MarketplaceHierarchy", false);
    }

    public static List<GenericValue> getAllCategories(Delegator delegator) throws GenericEntityException {
        return delegator.findAll("MarketplaceCategory", false);
    }

    public static List<GenericValue> getAllSellers(Delegator delegator) throws GenericEntityException {
        return delegator.findAll("MarketplaceSeller", false);
    }

    public static List<GenericValue> getCatalogCategories(Delegator delegator) throws GenericEntityException {
        return delegator.findByAnd("ProductCategory", UtilMisc.toMap("productCategoryId", "ENVELOPES"), UtilMisc.toList("productCategoryId"), true);
    }

    public static List<GenericValue> getRootCategories(Delegator delegator) throws GenericEntityException {
        return delegator.findByAnd("ProductCategory", UtilMisc.toMap("primaryParentCategoryId", "ENVELOPES"), UtilMisc.toList("productCategoryId"), true);
    }

    public static List<GenericValue> getSubCategories(Delegator delegator, List<GenericValue> rootCategories) throws GenericEntityException {
        List<EntityCondition> conditionList = new ArrayList<>();

        List<String> parentCategoryIds = new ArrayList<>();

        rootCategories.forEach(c -> parentCategoryIds.add(c.getString("productCategoryId")));

        List<GenericValue> subCategories = delegator.findList("ProductCategory", EntityCondition.makeCondition("primaryParentCategoryId", EntityOperator.IN, parentCategoryIds), null, UtilMisc.toList("primaryParentCategoryId", "productCategoryId"), null, true);

        return subCategories;
    }

    public static List<Map<String, Object>> getHierarchy(Delegator delegator) throws GenericEntityException {

        List<GenericValue> catalogCategories = getCatalogCategories(delegator);

        List<GenericValue> rootCategories = getRootCategories(delegator);

        List<GenericValue> subCategories = getSubCategories(delegator, rootCategories);

        List<Map<String, Object>> hierarchy = new ArrayList<>();

        catalogCategories.forEach(cc -> {
            Map<String, Object> ccMap = new HashMap<>(cc);
            ccMap.put("level", 1);
            ccMap.put("primaryParentCategoryId", "");
            hierarchy.add(ccMap);
            rootCategories.stream().filter(rc -> rc.getString("primaryParentCategoryId").equals(cc.getString("productCategoryId"))).collect(Collectors.toList()).forEach(rc -> {
                Map<String, Object> rcMap = new HashMap<>(rc);
                rcMap.put("level", 2);
                hierarchy.add(rcMap);
                subCategories.stream().filter(sc -> sc.getString("primaryParentCategoryId").equals(rc.getString("productCategoryId"))).collect(Collectors.toList()).forEach(c -> {
                    Map<String, Object> cMap = new HashMap<>(c);
                    cMap.put("level", 3);
                    hierarchy.add(cMap);
                });
            });

        });

        return hierarchy;
    }

    public static List<Map<String, Object>> getCategories(Delegator delegator) throws GenericEntityException {

        List<GenericValue> catalogCategories = getCatalogCategories(delegator);

        List<GenericValue> rootCategories = getRootCategories(delegator);

        List<GenericValue> subCategories = getSubCategories(delegator, rootCategories);

        List<Map<String, Object>> hierarchy = new ArrayList<>();

        catalogCategories.forEach(cc -> {
            Map<String, Object> ccMap = new HashMap<>(cc);
            ccMap.put("level", 1);
            ccMap.put("primaryParentCategoryId", "");
            hierarchy.add(ccMap);
            rootCategories.stream().filter(rc -> rc.getString("primaryParentCategoryId").equals(cc.getString("productCategoryId"))).collect(Collectors.toList()).forEach(rc -> {
                Map<String, Object> rcMap = new HashMap<>(rc);
                rcMap.put("level", 2);
                hierarchy.add(rcMap);
                subCategories.stream().filter(sc -> sc.getString("primaryParentCategoryId").equals(rc.getString("productCategoryId"))).collect(Collectors.toList()).forEach(c -> {
                    Map<String, Object> cMap = new HashMap<>(c);
                    cMap.put("level", 3);
                    hierarchy.add(cMap);
                });
            });

        });

        return hierarchy;
    }

    public static Map<String, Long> getCategoryLevelMap(Delegator delegator) throws GenericEntityException {
        Map<String, Long> levelMap = new HashMap<>();

        List<GenericValue> catalogCategories = getCatalogCategories(delegator);

        List<GenericValue> rootCategories = getRootCategories(delegator);

        List<GenericValue> subCategories = getSubCategories(delegator, rootCategories);

        List<Map<String, Object>> hierarchy = new ArrayList<>();

        catalogCategories.forEach(cc -> {
            levelMap.put(cc.getString("productCategoryId"), 1L);
            rootCategories.stream().filter(rc -> rc.getString("primaryParentCategoryId").equals(cc.getString("productCategoryId"))).collect(Collectors.toList()).forEach(rc -> {
                levelMap.put(rc.getString("productCategoryId"), 2L);
                subCategories.stream().filter(sc -> sc.getString("primaryParentCategoryId").equals(rc.getString("productCategoryId"))).collect(Collectors.toList()).forEach(c -> {
                    levelMap.put(c.getString("productCategoryId"), 3L);
                });
            });

        });
        return levelMap;
    }

    public static List<GenericValue> getMiraklHierarchy(Delegator delegator, long level) throws GenericEntityException {
        return delegator.findByAnd("MarketplaceHierarchy", UtilMisc.toMap("level", level), UtilMisc.toList("categoryId"), false);
    }

    public static List<GenericValue> getMiraklCategories(Delegator delegator, long level) throws GenericEntityException {
        return delegator.findByAnd("MarketplaceCategory", UtilMisc.toMap("level", level), UtilMisc.toList("categoryId"), false);
    }

    public static List<Map<String, Object>> getMiraklHierarchies(Delegator delegator) throws GenericEntityException {

        List<GenericValue> catalogCategories = getMiraklHierarchy(delegator, 1);

        List<GenericValue> rootCategories = getMiraklHierarchy(delegator, 2);

        List<GenericValue> subCategories = getMiraklHierarchy(delegator, 3);

        List<Map<String, Object>> hierarchy = new ArrayList<>();

        catalogCategories.forEach(cc -> {
            Map<String, Object> ccMap = new HashMap<>(cc);
            ccMap.put("level", 1);
            hierarchy.add(ccMap);
            rootCategories.stream().filter(rc -> rc.getString("parentCategoryId").equals(cc.getString("categoryId"))).collect(Collectors.toList()).forEach(rc -> {
                Map<String, Object> rcMap = new HashMap<>(rc);
                rcMap.put("level", 2);
                hierarchy.add(rcMap);
                subCategories.stream().filter(sc -> sc.getString("parentCategoryId").equals(rc.getString("categoryId"))).collect(Collectors.toList()).forEach(c -> {
                    Map<String, Object> cMap = new HashMap<>(c);
                    cMap.put("level", 3);
                    hierarchy.add(cMap);
                });
            });

        });

        return hierarchy;
    }

    public static List<Map<String, Object>> getMiraklCategories(Delegator delegator) throws GenericEntityException {

        List<GenericValue> catalogCategories = getMiraklCategories(delegator, 1);

        List<GenericValue> rootCategories = getMiraklCategories(delegator, 2);

        List<GenericValue> subCategories = getMiraklCategories(delegator, 3);

        List<Map<String, Object>> hierarchy = new ArrayList<>();

        catalogCategories.forEach(cc -> {
            Map<String, Object> ccMap = new HashMap<>(cc);
            ccMap.put("level", 1);
            hierarchy.add(ccMap);
            rootCategories.stream().filter(rc -> rc.getString("parentCategoryId").equals(cc.getString("categoryId"))).collect(Collectors.toList()).forEach(rc -> {
                Map<String, Object> rcMap = new HashMap<>(rc);
                rcMap.put("level", 2);
                hierarchy.add(rcMap);
                subCategories.stream().filter(sc -> sc.getString("parentCategoryId").equals(rc.getString("categoryId"))).collect(Collectors.toList()).forEach(c -> {
                    Map<String, Object> cMap = new HashMap<>(c);
                    cMap.put("level", 3);
                    hierarchy.add(cMap);
                });
            });

        });

        return hierarchy;
    }

    private static CellProcessor[] hierarchyProcessors = new CellProcessor[]{
            new NotNull(),
            new NotNull(),
            new org.supercsv.cellprocessor.Optional(),
            new NotNull()
    };

    private static CellProcessor[] categoryProcessors = new CellProcessor[]{
            new NotNull(),
            new NotNull(),
            new org.supercsv.cellprocessor.Optional(),
            new NotNull(),
            new NotNull(),
            new NotNull()
    };

    private static CellProcessor[] productProcessors = new CellProcessor[] {
            new NotNull(),
            new NotNull(),
            new NotNull(),
            new NotNull(),
            new NotNull()
    };

    public static void addOrUpdateRequiredProductAttribute(Delegator delegator, Map<String, Object> productAttribute) throws GenericEntityException {
        GenericValue attribute = delegator.makeValue("MarketplaceRequiredProductAttribute");
        attribute.putAll(productAttribute);
        delegator.createOrStore(attribute);
    }

    public static List<GenericValue> getRequiredProductAttributes(Delegator delegator) throws GenericEntityException {
        return delegator.findAll("MarketplaceRequiredProductAttribute", false);
    }

    public static List<GenericValue> getAllProductAttributes(Delegator delegator) throws GenericEntityException {
        return delegator.findAll("MarketplaceProductAttribute", false);
    }


    private static String[] hierarchyHeader = new String[]{"hierarchy-code", "hierarchy-label", "hierarchy-parent-code", "update-delete"};
    private static String[] categoryHeader = new String[]{"category-code", "category-label", "parent-code", "logistic-class", "update-delete", "category-description"};
    private static String[] productHeader = new String[] {"product-description","product-sku","product-title","category-code","authorized-shop-ids","update-delete"};
//    private static String[] offerHeader = new String[] {"sku","product-id","product-id-type","description","internal-description","price","quantity","min-quantity-alert","state","discount-price","discount-start-date","discount-end-date","discount-ranges","allow-quote-request","update-delete"};
    private static String[] offerHeader = new String[] {"sku","product-id","product-id-type","description","internal-description","price","quantity","min-quantity-alert","state","allow-quote-request","update-delete"};
    private static String[] productAttributesHeader = new String[]{"code", "label", "hierarchy-code", "description", "example", "required", "type", "type-parameter", "variant", "default-value", "transformations", "validations", "update-delete"};

    public static File createHierarchyFeed(Delegator delegator, String[] excludedCategories) throws Exception {

        Set<String> excludedCategoryIds = new HashSet<>(Arrays.asList(excludedCategories));
        File hierarchyFeed = new File("C:/tmp/hierarchy-feed" + System.currentTimeMillis() + ".csv");
        ICsvListWriter listWriter = null;
        try {

            CsvPreference preference = new CsvPreference.Builder('"', ';', "\n")
                    .useQuoteMode(new AlwaysQuoteMode()).build();

            listWriter = new CsvListWriter(new FileWriter(hierarchyFeed), preference);

            final ICsvListWriter $listWriter = listWriter;

            List<Map<String, Object>> categories = getHierarchy(delegator);

            Map<String, Object> catchAllCategory = new HashMap<>();
            catchAllCategory.put("productCategoryId", "OTHERS");
            catchAllCategory.put("primaryParentCategoryId", "");
            catchAllCategory.put("categoryName", "Others");

            categories.add(catchAllCategory);

            listWriter.writeHeader(hierarchyHeader);

            categories.forEach(c -> {
                String categoryName = (String)c.get("categoryName");
                categoryName = categoryName.replaceAll("[^a-zA-Z0-9\\s&_-]", "");
                if(!c.get("categoryName").equals(categoryName)) {
                    System.out.println(c.get("categoryName") + " -- " + categoryName);
                }
                if (excludedCategoryIds.contains(c.get("productCategoryId")) || excludedCategoryIds.contains(c.get("primaryParentCategoryId"))) {
                    if (excludedCategoryIds.contains(c.get("primaryParentCategoryId"))) {
                        excludedCategoryIds.add((String) c.get("productCategoryId"));
                    }
                    try {
                        $listWriter.write(Arrays.asList(c.get("productCategoryId"), categoryName, c.get("primaryParentCategoryId"), "delete"), hierarchyProcessors);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        $listWriter.write(Arrays.asList(c.get("productCategoryId"), categoryName, c.get("primaryParentCategoryId"), "update"), hierarchyProcessors);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        } finally {
            if (listWriter != null) {
                listWriter.close();
            }
        }

        return hierarchyFeed;
    }

    public static File createCategoryFeed(Delegator delegator, String[] excludedCategories) throws Exception {

        Set<String> excludedCategoryIds = new HashSet<>(Arrays.asList(excludedCategories));
        File categoryFeed = new File("C:/tmp/category-feed" + System.currentTimeMillis() + ".csv");
        ICsvListWriter listWriter = null;
        try {

            CsvPreference preference = new CsvPreference.Builder('"', ';', "\n")
                    .useQuoteMode(new AlwaysQuoteMode()).build();

            listWriter = new CsvListWriter(new FileWriter(categoryFeed), preference);

            final ICsvListWriter $listWriter = listWriter;

            List<Map<String, Object>> categories = getCategories(delegator);

            listWriter.writeHeader(categoryHeader);

            categories.forEach(c -> {
                String categoryName = (String)c.get("categoryName");
                categoryName = categoryName.replaceAll("[^a-zA-Z0-9\\s&_-]", "");
                if(!c.get("categoryName").equals(categoryName)) {
                    System.out.println(c.get("categoryName") + " -- " + categoryName);
                }
                if (excludedCategoryIds.contains(c.get("productCategoryId")) || excludedCategoryIds.contains(c.get("primaryParentCategoryId"))) {
                    if (excludedCategoryIds.contains(c.get("primaryParentCategoryId"))) {
                        excludedCategoryIds.add((String) c.get("productCategoryId"));
                    }
                    try {
                        $listWriter.write(Arrays.asList(c.get("productCategoryId"), categoryName, c.get("primaryParentCategoryId"), "DEFAULT", "delete", categoryName), categoryProcessors);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        $listWriter.write(Arrays.asList(c.get("productCategoryId"), categoryName, c.get("primaryParentCategoryId"), "DEFAULT", "update", categoryName), categoryProcessors);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        } finally {
            if (listWriter != null) {
                listWriter.close();
            }
        }

        return categoryFeed;
    }

    public static File createProductAttributesFeed(Delegator delegator) throws Exception {


        File attributesFeed = new File("C:/tmp/product-attributes-feed" + System.currentTimeMillis() + ".csv");
        ICsvListWriter listWriter = null;
        try {

            CsvPreference preference = new CsvPreference.Builder('"', ';', "\n")
                    .useQuoteMode(new AlwaysQuoteMode()).build();

            listWriter = new CsvListWriter(new FileWriter(attributesFeed), preference);

            final ICsvListWriter $listWriter = listWriter;

            List<GenericValue> requiredAttributes = getRequiredProductAttributes(delegator);
            Map<String, GenericValue> requiredAttributeMap = new HashMap<>();
            requiredAttributes.forEach(ra -> requiredAttributeMap.put(ra.getString("attributeId"), ra));

            List<GenericValue> marketplaceAttributes = getAllProductAttributes(delegator);

            listWriter.writeHeader(productAttributesHeader);

            requiredAttributes.forEach(a -> {
                try {
                    $listWriter.write(Arrays.asList(a.get("attributeId"), a.get("attributeLabel"), "", "", "", a.getBoolean("required"), a.get("type"), "", false, "", "", "", "update"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            marketplaceAttributes.forEach(ma -> {
                if (!requiredAttributeMap.containsKey(ma.get("attributeId"))) {
                    try {
                        $listWriter.write(Arrays.asList(ma.get("attributeId"), ma.get("attributeLabel"), "", "", "", ma.getBoolean("required"), ma.get("type"), "", false, "", "", "", "delete"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } finally {
            if (listWriter != null) {
                listWriter.close();
            }
        }

        return attributesFeed;
    }

    public static File createProductFeed(Delegator delegator, String ids) throws Exception {
        List<String> productIds = Arrays.asList(ids.split(","));
        List<GenericValue> products = EntityQuery.use(delegator).from("MarketplaceProduct").where(EntityCondition.makeCondition("marketplaceProductId", EntityOperator.IN, productIds)).queryList();

        File productFeed = new File("C:/tmp/product-feed" + System.currentTimeMillis() + ".csv");
        ICsvListWriter listWriter = null;
        try {

            CsvPreference preference = new CsvPreference.Builder('"', ';', "\n")
                    .useQuoteMode(new AlwaysQuoteMode()).build();

            listWriter = new CsvListWriter(new FileWriter(productFeed), preference);

            final ICsvListWriter $listWriter = listWriter;

            listWriter.writeHeader(productHeader);

            products.forEach(p -> {
                try {
                    GenericValue bignameProduct = ProductHelper.getProduct(delegator, p.getString("bignameProductId"));
                    $listWriter.write(Arrays.asList(bignameProduct.get("longDescription"), p.get("marketplaceProductId"), bignameProduct.get("productName"),p.get("marketplaceCategoryId"),p.get("marketplaceSellerId"),"update"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } finally {
            if (listWriter != null) {
                listWriter.close();
            }
        }

        return productFeed;
    }

    public static Map<String, File> createOfferFeeds(Delegator delegator, String ids) throws Exception {

        Map<String, File> offerFeeds = new HashMap<>();

        List<String> productIds = Arrays.asList(ids.split(","));
        List<GenericValue> products = EntityQuery.use(delegator).from("MarketplaceProduct").where(EntityCondition.makeCondition("marketplaceProductId", EntityOperator.IN, productIds)).queryList();

        Map<String, List<GenericValue>> storeProductMap = new HashMap<>();
        products.forEach(p -> {
            String sellerId = p.getString("marketplaceSellerId");
            if(storeProductMap.containsKey(sellerId)) {
                storeProductMap.get(sellerId).add(p);
            } else {
                List<GenericValue> productList = new ArrayList<>();
                productList.add(p);
                storeProductMap.put(sellerId, productList);
            }
        });

        for (Map.Entry<String, List<GenericValue>> entry : storeProductMap.entrySet()) {
            offerFeeds.put(entry.getKey(), createOfferFeed(delegator, entry.getValue()));
        }

        return offerFeeds;
    }

    public static File createOfferFeed(Delegator delegator, List<GenericValue> products) throws Exception {

        File offerFeed = new File("C:/tmp/offer-feed" + System.currentTimeMillis() + ".csv");
        ICsvListWriter listWriter = null;
        try {

            CsvPreference preference = new CsvPreference.Builder('"', ';', "\n")
                    .useQuoteMode(new AlwaysQuoteMode()).build();

            listWriter = new CsvListWriter(new FileWriter(offerFeed), preference);

            final ICsvListWriter $listWriter = listWriter;

            listWriter.writeHeader(offerHeader);

            products.forEach(p -> {
                try {
                    $listWriter.write(Arrays.asList("OFP-" + p.get("marketplaceProductId"), p.get("marketplaceProductId"), "SKU", "", "", p.get("unitPrice"), "100000", "100","11",/*"", "", "", p.get("volumePrice"),*/ "true", "update"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } finally {
            if (listWriter != null) {
                listWriter.close();
            }
        }

        return offerFeed;
    }

    private static String hostName = "blacwood.com";
    private static String userName = "bigname@blacwood.com";
    private static String password = "envelopes5300!";
    private static String remoteFileLocation = "bigname/mirakl/import/product/";
    private static String remoteSuccessLocation = "processed/success/";
    private static String remoteErrorLocation = "processed/error/";
    public static final SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public static List<Map<String, Object>> getNewSellerUploads() throws Exception {
        List<Map<String, Object>> fileDetails = new ArrayList<>();
        FTPClient ftpClient = new FTPClient();
        try {

            ftpClient.connect(hostName, 21);
            ftpClient.login(userName, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            FTPFile[] files = ftpClient.listFiles(remoteFileLocation, ftpFile -> ftpFile.getName().endsWith(".csv"));
            for(FTPFile file : files) {
                Map<String, Object> detailMap = new HashMap<>();
                detailMap.put("fileName", file.getName());
                detailMap.put("uploadTime", file.getTimestamp().getTime());
                fileDetails.add(detailMap);
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return fileDetails;
    }

    public static boolean moveSellerUpload(String fileName, boolean errorFlag) throws Exception {
        FTPClient ftpClient = new FTPClient();
        boolean result = false;
        try {

            ftpClient.connect(hostName, 21);
            ftpClient.login(userName, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            FTPFile[] files = ftpClient.listFiles(remoteFileLocation, ftpFile -> ftpFile.getName().endsWith(".csv"));
            for(FTPFile file : files) {
                if(file.getName().equals(fileName)) {
                    result = ftpClient.rename(remoteFileLocation + file.getName(), remoteFileLocation + (errorFlag ? remoteErrorLocation : remoteSuccessLocation) + file.getName());
                }
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static List<GenericValue> getAllSellerUploads(Delegator delegator) throws GenericEntityException {
        return delegator.findAll("MarketplaceSellerUpload", false);
    }

    public static void checkNewSellerUploads(Delegator delegator) throws GenericEntityException {

        try {
            List<Map<String, Object>> uploads = getNewSellerUploads();

            for (Map<String, Object> upload : uploads) {
                String[] tokens = ((String)upload.get("fileName")).split("-");
                String storeId = tokens[0];
                String uploadId = tokens[1];
                String fileName = tokens[2];
                if(checkSeller(delegator, storeId, true)) {
                    createSellerUpload(delegator, uploadId, storeId, fileName, (Date)upload.get("uploadTime"));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public static boolean checkSeller(Delegator delegator, String sellerId, boolean syncFlag) throws GenericEntityException {
        GenericEntity seller = delegator.findOne("MarketplaceSeller", UtilMisc.toMap("marketplaceSellerId", sellerId), false);
        if(syncFlag && seller == null) {
            syncMarketplaceSellers(delegator, MiraklUtil.getMiraklShops());
            return checkSeller(delegator, sellerId, false);
        } else {
            return seller != null;
        }
    }

    public static void createSellerUpload(Delegator delegator, String uploadId, String sellerId, String fileName, Date uploadTime) throws GenericEntityException {
        if(!checkSellerUpload(delegator, uploadId)) {
            GenericValue upload = delegator.makeValue("MarketplaceSellerUpload");
            upload.put("uploadId", uploadId);
            upload.put("sellerId", sellerId);
            upload.put("fileName", fileName);
            upload.put("uploadTime", UtilDateTime.toTimestamp(uploadTime));
            upload.put("status", "Receiving");
            delegator.create(upload);
        }
        getSellerUploadedProducts(delegator, uploadId, sellerId);

        try {
            moveSellerUpload(sellerId + "-" + uploadId + "-" + fileName, false);
        } catch (Exception e) {
            throw new GenericEntityException(e);
        }
    }

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static CellProcessor[] getProcessors(int size) {
        final CellProcessor[] processors = new CellProcessor[size];
        for (int i = 0; i < processors.length; i ++) {
            processors[i] = new org.supercsv.cellprocessor.Optional();
        }
        return processors;
    }

    public static List<String> getUploadedData(String uploadId) throws Exception {
        List<String> data = new ArrayList<>();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet request = new HttpGet("https://envelopes-dev.mirakl.net/api/products/imports/" + uploadId + "/transformed_file");
        request.addHeader("Authorization", "5e5905d3-8a11-4b6b-bce9-c37f7a0111d5");

        CloseableHttpResponse response = httpclient.execute(request);

        ICsvListReader listReader = new CsvListReader(new InputStreamReader(response.getEntity().getContent()), new CsvPreference.Builder('"', ';', "\n").build());

        String[] header = listReader.getHeader(true);
        data.add(objectMapper.writeValueAsString(header));

        CellProcessor[] processors = getProcessors(header.length);

        while(listReader.read() != null) {
            List<Object> list = listReader.executeProcessors(processors);
            data.add(objectMapper.writeValueAsString(list));
        }
        httpclient.close();
        return data;
    }

    private static List<String> getOffersSince(Date lastRequestDate) throws Exception {
        List<String> data = new ArrayList<>();
        CloseableHttpClient httpclient = HttpClients.createDefault();



        HttpGet request = new HttpGet(new URIBuilder().setScheme("https").setHost("envelopes-dev.mirakl.net").setPath("/api/offers/export").setParameter("last_request_date", DateFormatter.formatDate(lastRequestDate)).build());

        request.addHeader("Authorization", "5e5905d3-8a11-4b6b-bce9-c37f7a0111d5");

        CloseableHttpResponse response = httpclient.execute(request);

        ICsvListReader listReader = new CsvListReader(new InputStreamReader(response.getEntity().getContent()), new CsvPreference.Builder('"', ';', "\n").build());

        String[] header = listReader.getHeader(true);
        data.add(objectMapper.writeValueAsString(header));

        CellProcessor[] processors = getProcessors(header.length);

        while(listReader.read() != null) {
            List<Object> list = listReader.executeProcessors(processors);
            data.add(objectMapper.writeValueAsString(list));
        }
        httpclient.close();
        return data;
    }

    public static void getSellerUploadedProducts(Delegator delegator, String uploadId, String sellerId) throws GenericEntityException {
        try {
            List<String> data = getUploadedData(uploadId);
            ProductImporter productImporter = new ProductImporter(delegator, null);
            String headerColumns = "";
            boolean headerRow = true;
            long idx = 0;
            for(String rowData : data) {
                if(headerRow) {
                    GenericValue upload = delegator.findOne("MarketplaceSellerUpload", UtilMisc.toMap("uploadId", uploadId), false);
                    headerColumns = rowData;
                    upload.put("headerColumns", headerColumns);
                    upload.put("numOfItems", (long)data.size() - 1);
                    upload.store();
                    headerRow = false;
                } else {
                    boolean status = false;
                    String errorMessage = "";

                    try {
                        status = createOrUpdateMarketplaceProduct(delegator, headerColumns, rowData, sellerId, productImporter);
                    } catch (Exception e) {
                        EnvUtil.reportError(e);
                        errorMessage = "Error occurred while importing product, " + rowData + " due to : " + e.getMessage();
                        Debug.logError(e, errorMessage, module);
                    }

                    GenericValue uploadProduct = delegator.makeValue("MarketplaceSellerUploadedProduct");
                    uploadProduct.put("uploadId", uploadId);
                    uploadProduct.put("sellerId", sellerId);
                    uploadProduct.put("sequenceNum", idx);
                    uploadProduct.put("attributeValues", rowData);
                    uploadProduct.put("status", status ? "Imported" : "Failed");
                    uploadProduct.put("errorMessage", errorMessage);
                    delegator.createOrStore(uploadProduct);
                    idx ++;
                }
            }
            GenericValue upload = delegator.findOne("MarketplaceSellerUpload", UtilMisc.toMap("uploadId", uploadId), false);
            upload.put("status", "Received");
            upload.store();
        } catch (Exception e) {
            throw new GenericEntityException(e);
        }
    }

    public static void getActiveOffers(Delegator delegator, Date lastRequestDate) throws GenericEntityException {
        try {
            List<String> data = getOffersSince(lastRequestDate);
            String headerColumns = "";
            boolean headerRow = true;
            for(String rowData : data) {
                if(headerRow) {
                    headerColumns = rowData;
                    headerRow = false;
                } else {
                    createOrUpdateOffer(delegator, headerColumns, rowData);
                }
            }
        } catch (Exception e) {
            throw new GenericEntityException(e);
        }
    }

    private static void createOrUpdateOffer(Delegator delegator, String keys, String values) throws GenericEntityException {
        try {
            Map<String, Object> offerAttributes = convertToMap(keys, values);
            GenericValue offer = EntityQuery.use(delegator).from("MarketplaceOffer").where("marketplaceOfferId", offerAttributes.get("offer-id")).queryOne();
            if(offer == null) {
                offer = delegator.makeValue("MarketplaceOffer", UtilMisc.toMap("offerId", delegator.getNextSeqId("MarketplaceOffer"), "marketplaceOfferId", offerAttributes.get("offer-id")));
                offer.put("marketplaceProductId", offerAttributes.get("product-sku"));
            }
            offer.put("unitPrice", new BigDecimal((String)offerAttributes.get("price")));
            offer.put("volumePricing", offerAttributes.get("discount-ranges"));
            delegator.createOrStore(offer);
        } catch (Exception e) {
            throw new GenericEntityException(e);
        }
    }



    public static boolean createOrUpdateMarketplaceProduct(Delegator delegator, String keys, String values, String sellerId, ProductImporter productImporter) throws Exception {
        Map<String, Object> productAttributes = convertToMap(keys, values);
        String sellerProductId = (String)productAttributes.get("IDENTIFIER");
        String oldSellerProductId = (String)productAttributes.get("OLD_IDENTIFIER");
        String rushProduction;
        if(UtilValidate.isNotEmpty(rushProduction = (String)productAttributes.get("RUSH_PRODUCTION")) && (rushProduction.equalsIgnoreCase("Y") || rushProduction.equalsIgnoreCase("YES") || rushProduction.equalsIgnoreCase("true") || rushProduction.equals("1"))) {
            rushProduction = "Y";
        } else {
            rushProduction = "N";
        }
        productAttributes.put("RUSH_PRODUCTION", rushProduction);
        GenericValue product = EntityQuery.use(delegator).from("MarketplaceProduct").where("sellerProductId", UtilValidate.isNotEmpty(oldSellerProductId) ? oldSellerProductId : sellerProductId).queryOne();
        String marketPlaceProductId = "";
        if(product == null) {
            marketPlaceProductId = delegator.getNextSeqId("MarketplaceProduct");
            product = delegator.makeValue("MarketplaceProduct", UtilMisc.toMap("marketplaceProductId", marketPlaceProductId, "status", "NEW"));
        } else {
            //Product updates are not allowed in Phase 1
            throw new Exception("An error occurred while importing vendor product to Bigname, product already exists");
//            marketPlaceProductId = product.getString("marketplaceProductId");
//            product.put("status", "MODIFIED");
        }
        if(productAttributes.containsKey("CATEGORY")) {
            String hierarchyCode = (String)productAttributes.get("CATEGORY");
            if(hierarchyCode.contains("|")) {
                hierarchyCode = hierarchyCode.substring(hierarchyCode.lastIndexOf("|") + 1);
            }
            product.put("marketplaceHierarchyId", hierarchyCode);
            if(!hierarchyCode.equals("OTHERS")) {
                product.put("marketplaceCategoryId", hierarchyCode);
            }
        }
        product.put("marketplaceSellerId", sellerId);
        product.put("sellerProductId", sellerProductId);
        product.put("bignameProductId", productAttributes.get("CODE"));
        product.put("unitPrice", productAttributes.get("UNIT_PRICE"));
        product.put("volumePrice", productAttributes.get("VOLUME_PRICE"));


        productAttributes.put("MARKETPLACE_CODE", marketPlaceProductId);
        productAttributes.put("PRODUCT_NAME", productAttributes.get("TITLE"));


        productImporter.insertProduct(productAttributes);
        if(!productImporter.isSuccess()) {
            throw new Exception("An error occurred while importing vendor product to Bigname");
        }
        product.set("imported", "Y");
        product.set("status", "IMPORTED");
        delegator.createOrStore(product);
        return true;
    }

    public static void holdProducts(Delegator delegator, String ids) throws GenericEntityException {
        List<String> productIds = Arrays.asList(ids.split(","));
        List<GenericValue> products = EntityQuery.use(delegator).from("MarketplaceProduct").where(EntityCondition.makeCondition("marketplaceProductId", EntityOperator.IN, productIds)).queryList();
        for(GenericValue product : products) {
            product.set("status", "HOLD");
            product.store();
        }
    }

    public static void importedProducts(Delegator delegator, String ids) throws GenericEntityException {
        List<String> productIds = Arrays.asList(ids.split(","));
        List<GenericValue> products = EntityQuery.use(delegator).from("MarketplaceProduct").where(EntityCondition.makeCondition("marketplaceProductId", EntityOperator.IN, productIds)).queryList();
        for(GenericValue product : products) {
            product.set("imported", "Y");
            product.set("status", "IMPORTED");
            product.store();
        }
    }

    public static void publishedProducts(Delegator delegator, String ids) throws GenericEntityException {
        List<String> productIds = Arrays.asList(ids.split(","));
        List<GenericValue> products = EntityQuery.use(delegator).from("MarketplaceProduct").where(EntityCondition.makeCondition("marketplaceProductId", EntityOperator.IN, productIds)).queryList();
        for(GenericValue product : products) {
            product.set("published", "Y");
            product.set("status", "PUBLISHED");
            product.store();
        }
    }



    public static List<GenericValue> getSellerProducts(Delegator delegator, List<String> statuses) throws GenericEntityException {
        if(statuses.size() > 0) {
            return EntityQuery.use(delegator).from("MarketplaceProduct").where(EntityCondition.makeCondition("status", EntityOperator.IN, statuses)).orderBy("marketplaceProductId").queryList();
        } else {
            return EntityQuery.use(delegator).from("MarketplaceProduct").orderBy("status", "marketplaceProductId").queryList();
        }
    }

    public static List<Map<String, Object>> getImportedSellerProducts(Delegator delegator) throws GenericEntityException {
        List<GenericValue> products =  EntityQuery.use(delegator).from("MarketplaceProduct").where(EntityCondition.makeCondition("imported", EntityOperator.EQUALS, "Y")).orderBy("marketplaceProductId").queryList();
        List<Map<String, Object>> importedSellerProducts = new ArrayList<>();
        products.forEach(p -> {
            Map<String, Object> productMap = new HashMap(p);
            try {
                GenericValue bignameProduct = ProductHelper.getProduct(delegator, p.getString("bignameProductId"));
                if(bignameProduct != null) {
                    productMap.putAll(bignameProduct);
                    productMap.put("sizeCode", ProductHelper.getSizeCode(delegator, p.getString("bignameProductId")));
                    productMap.put("size", ProductHelper.getSize(delegator, p.getString("bignameProductId")));
                    importedSellerProducts.add(productMap);
                }
            } catch (Exception e) {
                Debug.logError(e, "Error: " + e.getMessage(), module);
            }
        });

        return importedSellerProducts;
    }

    public static List<Map<String, Object>> getPublishedSellerProducts(Delegator delegator) throws GenericEntityException {
        List<GenericValue> products =  EntityQuery.use(delegator).from("MarketplaceProduct").where(EntityCondition.makeCondition("published", EntityOperator.EQUALS, "Y")).orderBy("marketplaceProductId").queryList();
        List<Map<String, Object>> importedSellerProducts = new ArrayList<>();
        products.forEach(p -> {
            Map<String, Object> productMap = new HashMap(p);
            try {
                GenericValue bignameProduct = ProductHelper.getProduct(delegator, p.getString("bignameProductId"));
                if(bignameProduct != null) {
                    productMap.putAll(bignameProduct);
                    productMap.put("sizeCode", ProductHelper.getSizeCode(delegator, p.getString("bignameProductId")));
                    productMap.put("size", ProductHelper.getSize(delegator, p.getString("bignameProductId")));
                    importedSellerProducts.add(productMap);
                }
            } catch (Exception e) {
                Debug.logError(e, "Error: " + e.getMessage(), module);
            }
        });

        return importedSellerProducts;
    }


    public static Map<String, Object> convertToMap(String keys, String values) throws Exception {
        String[] keyArray = objectMapper.readValue(keys, String[].class);

        String[] valueArray = objectMapper.readValue(values, String[].class);
        Map<String, Object> map = new HashMap<>();
        for(int i = 0; i < keyArray.length; i ++) {
            map.put(keyArray[i], valueArray[i]);
        }
        return map;
    }



    public static String convertToArray(String values) {
        StringTokenizer tokenizer = new StringTokenizer(values, "\";\"");
        List<String> valList = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            valList.add(tokenizer.nextToken());
        }
        return valList.toString();
    }

    public static boolean checkSellerUpload(Delegator delegator, String uploadId) throws GenericEntityException {
        return  delegator.findOne("MarketplaceSellerUpload", UtilMisc.toMap("uploadId", uploadId), false) != null;
    }

    public static final String EMPTY_STRING = "";

    public static boolean createMarketplaceOrder(Delegator delegator, String bignameOrderId) throws GenericEntityException {




        OrderReadHelper orderReadHelper = new OrderReadHelper(delegator, bignameOrderId);

        List<GenericValue> marketplaceOrderItems = EntityQuery.use(delegator).from("OrderItemAndProduct").where(EntityCondition.makeCondition(EntityCondition.makeCondition("orderId", EntityOperator.EQUALS, bignameOrderId), EntityOperator.AND, EntityCondition.makeCondition("marketplaceProductId", EntityOperator.NOT_EQUAL, null))).queryList();

        if(UtilValidate.isEmpty(marketplaceOrderItems)) {
            return false;
        }

        GenericValue shippingAddress = OrderHelper.getShippingAddress(orderReadHelper, delegator, bignameOrderId);
        GenericValue billingAddress = OrderHelper.getBillingAddress(orderReadHelper, delegator, bignameOrderId);
        String shipmentMethodTypeId = orderReadHelper.getOrderItemShipGroup("00001").getString("shipmentMethodTypeId");


        GenericValue marketplaceOrder = EntityQuery.use(delegator).from("MarketplaceOrderHeader").where(EntityCondition.makeCondition("bignameOrderId", EntityOperator.EQUALS, bignameOrderId)).queryOne();

        if(UtilValidate.isNotEmpty(marketplaceOrder)) {
            throw new MarketplaceOrderCreationException("Marketplace order with orderId : " + bignameOrderId + " already exists");
        }

        marketplaceOrder = delegator.makeValue("MarketplaceOrderHeader", UtilMisc.toMap("bignameOrderId", bignameOrderId));

        String firstName = EMPTY_STRING;

        String lastName = EMPTY_STRING;

        if(billingAddress.containsKey(TO_NAME)) {
            String[] names = new String[0];
            String toName;
            if(UtilValidate.isNotEmpty(toName = (String)billingAddress.get(TO_NAME))) {
                names = splitAtFirstSpace(toName);
            }
            if(names.length == 2) {
                firstName = names[0];
                lastName = names[1];
            } else if(names.length == 1) {
                firstName = names[0];
            }
        }

        GenericValue billingPhoneNum = OrderHelper.getTelecomNumber(orderReadHelper, delegator, bignameOrderId, "PHONE_BILLING");
        String phoneNumber = UtilValidate.isNotEmpty(billingPhoneNum) && billingPhoneNum.containsKey("contactNumber") ? billingPhoneNum.getString("contactNumber") : "";
        marketplaceOrder.put("customerFirstName", firstName);
        marketplaceOrder.put("customerLastName", lastName);
        marketplaceOrder.put("customerEmail", orderReadHelper.getOrderEmailString());
        marketplaceOrder.put("billingFirstName", firstName);
        marketplaceOrder.put("billingLastName", lastName);
        marketplaceOrder.put("billingCompany", billingAddress.get(COMPANY_NAME));
        marketplaceOrder.put("billingStreet1", billingAddress.get(ADDRESS1));
        marketplaceOrder.put("billingStreet2", billingAddress.get(ADDRESS2));
        marketplaceOrder.put("billingCity", billingAddress.get(CITY));
        marketplaceOrder.put("billingState", billingAddress.get(STATE_PROVINCE));
        marketplaceOrder.put("billingZip", billingAddress.get(POSTAL_CODE));
        marketplaceOrder.put("billingCountry", billingAddress.get(COUNTRY));
        marketplaceOrder.put("billingPhone1", phoneNumber);

        firstName = EMPTY_STRING;

        lastName = EMPTY_STRING;

        if(shippingAddress.containsKey(TO_NAME)) {
            String[] names = new String[0];
            String toName;
            if(UtilValidate.isNotEmpty(toName = (String)shippingAddress.get(TO_NAME))) {
                names = splitAtFirstSpace(toName);
            }
            if(names.length == 2) {
                firstName = names[0];
                lastName = names[1];
            } else if(names.length == 1) {
                firstName = names[0];
            }
        }

        marketplaceOrder.put("shippingFirstName", firstName);
        marketplaceOrder.put("shippingLastName", lastName);
        marketplaceOrder.put("shippingCompany", shippingAddress.get(COMPANY_NAME));
        marketplaceOrder.put("shippingStreet1", shippingAddress.get(ADDRESS1));
        marketplaceOrder.put("shippingStreet2", shippingAddress.get(ADDRESS2));
        marketplaceOrder.put("shippingCity", shippingAddress.get(CITY));
        marketplaceOrder.put("shippingState", shippingAddress.get(STATE_PROVINCE));
        marketplaceOrder.put("shippingZip", shippingAddress.get(POSTAL_CODE));
        marketplaceOrder.put("shippingCountry", shippingAddress.get(COUNTRY));
        marketplaceOrder.put("shippingPhone1", phoneNumber);
        final String shippingMethod =  BignameShippingMethod.getMiraklShippingMethod(shipmentMethodTypeId);
        String shippingZone = UtilValidate.isEmpty(shippingAddress.get(COUNTRY)) || shippingAddress.getString(COUNTRY).equalsIgnoreCase("USA") ? "US" : "INTRNL";
        marketplaceOrder.put("shippingMethod", shippingMethod);
        marketplaceOrder.put("shippingZone", shippingZone);

        List<String> marketplaceProductIds = new ArrayList<>();
        marketplaceOrderItems.forEach(miraklOrderItem -> marketplaceProductIds.add(miraklOrderItem.getString("marketplaceProductId")));

        List<MiraklProductOffers> productOffersList = MiraklUtil.getProductsOffers(marketplaceProductIds.toArray(new String[0])).getProducts();
        if(UtilValidate.isEmpty(productOffersList)) {
            throw new MarketplaceOrderCreationException("Unable to get active offers for the items in the order with Id: " + bignameOrderId);
        }
        Map<String, List<MiraklOffer>> productOffersMap = new HashMap<>();
        productOffersList.forEach(offer -> productOffersMap.put(offer.getProductId(), offer.getOffers()));
        List<MiraklCreateOrderOffer> orderOffers = new ArrayList<>();
        marketplaceOrderItems.forEach(miraklOrderItem -> {
            String marketplaceProductId = miraklOrderItem.getString("marketplaceProductId");
            List<MiraklOffer> productOffers = productOffersMap.get(marketplaceProductId);
            if(UtilValidate.isEmpty(productOffers)) {
                throw new MarketplaceOrderCreationException("Unable to get active offer for marketplace product with Id: " + marketplaceProductId);
            }

            MiraklOffer offer = productOffers.get(0);

            GenericValue marketplaceOrderItem = delegator.makeValue("MarketplaceOrderDetail", UtilMisc.toMap("bignameOrderId", bignameOrderId, "orderItemSeqNum", Long.parseLong(miraklOrderItem.getString("orderItemSeqId"))));
            marketplaceOrderItem.put("offerId", offer.getId().toString());
            marketplaceOrderItem.put("marketplaceProductId", marketplaceProductId);
            marketplaceOrderItem.put("bignameProductId", miraklOrderItem.getString("productId"));
            marketplaceOrderItem.put("unitPrice", offer.getPrice());
            marketplaceOrderItem.put("quantity", miraklOrderItem.getBigDecimal("quantity").longValue());
            marketplaceOrderItem.put("itemTotal", offer.getPrice().multiply(new BigDecimal(miraklOrderItem.getBigDecimal("quantity").toString())));
            marketplaceOrderItem.put("shippingTypeCode", shippingMethod);
            marketplaceOrderItem.put("shippingPrice", BigDecimal.ZERO);
            marketplaceOrderItem.put("status", "P");
            MiraklCreateOrderOffer orderOffer = new MiraklCreateOrderOffer();
            orderOffer.setId(marketplaceOrderItem.getString("offerId"));
            orderOffer.setPriceUnit(marketplaceOrderItem.getBigDecimal("unitPrice"));
            orderOffer.setQuantity(marketplaceOrderItem.getLong("quantity").intValue());
            orderOffer.setPrice(marketplaceOrderItem.getBigDecimal("itemTotal"));
            orderOffer.setOrderLineId(bignameOrderId + "-" + marketplaceOrderItem.getLong("orderItemSeqNum"));
            orderOffer.setShippingPrice(marketplaceOrderItem.getBigDecimal("shippingPrice"));
            orderOffer.setShippingTypeCode(marketplaceOrderItem.getString("shippingTypeCode"));
            orderOffers.add(orderOffer);
            try {
                marketplaceOrderItem.create();
            } catch (GenericEntityException e) {
                throw new RuntimeException(e);
            }
        });
        marketplaceOrder.create();

        MiraklCreateOrder createOrder = new MiraklCreateOrder();
        createOrder.setCommercialId(bignameOrderId);
//        createOrder.setChannelCode("WEBSITE");
        createOrder.setScored(false);

        MiraklOrderCustomer orderCustomer = new MiraklOrderCustomer();
        orderCustomer.setId(orderReadHelper.getBillToParty().getString("partyId"));//TODO null check
        orderCustomer.setFirstname(marketplaceOrder.getString("customerFirstName"));
        orderCustomer.setLastname(marketplaceOrder.getString("customerLastName"));
        orderCustomer.setEmail(marketplaceOrder.getString("customerEmail"));

        MiraklCustomerBillingAddress customerBillingAddress = new MiraklCustomerBillingAddress();
        customerBillingAddress.setFirstname(marketplaceOrder.getString("billingFirstName"));
        customerBillingAddress.setLastname(marketplaceOrder.getString("billingLastName"));
        customerBillingAddress.setCompany(UtilValidate.isNotEmpty(marketplaceOrder.get("billingCompany")) ? marketplaceOrder.getString("billingCompany") : "");
        customerBillingAddress.setStreet1(marketplaceOrder.getString("billingStreet1"));
        customerBillingAddress.setStreet2(UtilValidate.isNotEmpty(marketplaceOrder.get("billingStreet2")) ? marketplaceOrder.getString("billingStreet2") : "");
        customerBillingAddress.setCity(marketplaceOrder.getString("billingCity"));
        customerBillingAddress.setState(marketplaceOrder.getString("billingState"));
        customerBillingAddress.setZipCode(marketplaceOrder.getString("billingZip"));
        customerBillingAddress.setCountry(marketplaceOrder.getString("billingCountry"));

        orderCustomer.setBillingAddress(customerBillingAddress);

        MiraklCustomerShippingAddress customerShippingAddress = new MiraklCustomerShippingAddress();
        customerShippingAddress.setFirstname(marketplaceOrder.getString("shippingFirstName"));
        customerShippingAddress.setLastname(marketplaceOrder.getString("shippingLastName"));
        customerShippingAddress.setCompany(UtilValidate.isNotEmpty(marketplaceOrder.get("shippingCompany")) ? marketplaceOrder.getString("shippingCompany") : "");
        customerShippingAddress.setStreet1(marketplaceOrder.getString("shippingStreet1"));
        customerShippingAddress.setStreet2(UtilValidate.isNotEmpty(marketplaceOrder.get("shippingStreet2")) ? marketplaceOrder.getString("shippingStreet2") : "");
        customerShippingAddress.setCity(marketplaceOrder.getString("shippingCity"));
        customerShippingAddress.setState(marketplaceOrder.getString("shippingState"));
        customerShippingAddress.setZipCode(marketplaceOrder.getString("shippingZip"));
        customerShippingAddress.setCountry(marketplaceOrder.getString("shippingCountry"));

        orderCustomer.setShippingAddress(customerShippingAddress);

        createOrder.setCustomer(orderCustomer);

        createOrder.setShippingZoneCode(shippingZone);

        createOrder.setOffers(orderOffers);

        MiraklCreatedOrders orders = MiraklUtil.createOrder(new CreateOrderRequest(createOrder));
        System.out.println(orders.getTotalCount());

        return false;
    }

}
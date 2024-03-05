import java.lang.*;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityConditionList;
import org.apache.ofbiz.entity.util.EntityFindOptions;
import org.apache.ofbiz.entity.util.EntityListIterator;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.model.DynamicViewEntity;
import org.apache.ofbiz.entity.model.ModelKeyMap
import org.apache.ofbiz.entity.Delegator;
import java.sql.Timestamp;

Timestamp nowTime = UtilDateTime.nowTimestamp();
Delegator delegator = request.getAttribute("delegator");

List<Map<String, Object>> couponCodes = new ArrayList<>();

dynamicViewEntity = new DynamicViewEntity();
efo = new EntityFindOptions();

dynamicViewEntity.addMemberEntity("PP", "ProductPromo");
dynamicViewEntity.addMemberEntity("PPC", "ProductPromoCond");
dynamicViewEntity.addMemberEntity("PPA", "ProductPromoAction");
dynamicViewEntity.addMemberEntity("PC", "ProductPromoCode");
dynamicViewEntity.addMemberEntity("PSP", "ProductStorePromoAppl");

dynamicViewEntity.addAlias("PP", "productPromoId", "productPromoId", null, null, null, null);
dynamicViewEntity.addAlias("PP", "lastUpdatedStamp", "lastUpdatedStamp", null, null, null, null);
dynamicViewEntity.addAlias("PP", "promoName", "promoName", null, null, null, null);
dynamicViewEntity.addAlias("PP", "promoText", "promoText", null, null, null, null);
dynamicViewEntity.addAlias("PP", "requireCode", "requireCode", null, null, null, null);
dynamicViewEntity.addAlias("PP", "showOnSite", "showOnSite", null, null, null, null);
dynamicViewEntity.addAlias("PC", "productPromoCodeId", "productPromoCodeId", null, null, null, null);
dynamicViewEntity.addAlias("PSP", "productStoreId", "productStoreId", null, null, null, null);
dynamicViewEntity.addAlias("PSP", "fromDate", "fromDate", null, null, null, null);
dynamicViewEntity.addAlias("PSP", "thruDate", "thruDate", null, null, null, null);
dynamicViewEntity.addAlias("PSP", "webSiteId", "webSiteId", null, null, null, null);
dynamicViewEntity.addAlias("PPC", "inputParamEnumId", "inputParamEnumId", null, null, null, null);
dynamicViewEntity.addAlias("PPC", "operatorEnumId", "operatorEnumId", null, null, null, null);
dynamicViewEntity.addAlias("PPC", "condValue", "condValue", null, null, null, null);
dynamicViewEntity.addAlias("PPA", "productPromoActionEnumId", "productPromoActionEnumId", null, null, null, null);
dynamicViewEntity.addAlias("PPA", "amount", "amount", null, null, null, null);

dynamicViewEntity.addViewLink("PP", "PPC", Boolean.FALSE, ModelKeyMap.makeKeyMapList("productPromoId"));
dynamicViewEntity.addViewLink("PP", "PPA", Boolean.FALSE, ModelKeyMap.makeKeyMapList("productPromoId"));
dynamicViewEntity.addViewLink("PP", "PSP", Boolean.FALSE, ModelKeyMap.makeKeyMapList("productPromoId"));
dynamicViewEntity.addViewLink("PP", "PC", Boolean.FALSE, ModelKeyMap.makeKeyMapList("productPromoId"));

fieldsToSelect = UtilMisc.toList("productPromoId", "promoName", "promoText", "requireCode", "productPromoCodeId", "productStoreId");
fieldsToSelect.add("fromDate");
fieldsToSelect.add("thruDate");
fieldsToSelect.add("showOnSite");
fieldsToSelect.add("lastUpdatedStamp");
fieldsToSelect.add("inputParamEnumId");
fieldsToSelect.add("operatorEnumId");
fieldsToSelect.add("condValue");
fieldsToSelect.add("productPromoActionEnumId");
fieldsToSelect.add("amount");
fieldsToSelect.add("webSiteId");

List orderByList = UtilMisc.toList("lastUpdatedStamp DESC");

EntityCondition mainCond = null;
List andExprs = new ArrayList();

andExprs.add(EntityCondition.makeCondition("requireCode", EntityOperator.EQUALS, "Y"));
andExprs.add(EntityCondition.makeCondition("showOnSite", EntityOperator.EQUALS, "Y"));
andExprs.add(EntityCondition.makeCondition("productStoreId", EntityOperator.EQUALS, "10000"));
andExprs.add(EntityCondition.makeCondition("promoText", EntityOperator.NOT_EQUAL, null));
andExprs.add(EntityCondition.makeCondition(EntityCondition.makeCondition("fromDate", EntityOperator.EQUALS, null), EntityOperator.OR, EntityCondition.makeCondition("fromDate", EntityOperator.LESS_THAN, nowTime)));
andExprs.add(EntityCondition.makeCondition(EntityCondition.makeCondition("thruDate", EntityOperator.EQUALS, null), EntityOperator.OR, EntityCondition.makeCondition("thruDate", EntityOperator.GREATER_THAN, nowTime)));
andExprs.add(EntityCondition.makeCondition(EntityCondition.makeCondition("webSiteId", EntityOperator.EQUALS, null), EntityOperator.OR, EntityCondition.makeCondition("webSiteId", EntityOperator.LIKE, "%" + webSiteId + "%")));
if(andExprs.size() > 0) {
    mainCond = new EntityConditionList(andExprs, EntityOperator.AND);
}

try {
    EntityListIterator eli = delegator.findListIteratorByCondition(dynamicViewEntity, mainCond, null, fieldsToSelect, orderByList, efo);
    GenericValue couponData = null;
    while (((couponData = (GenericValue) eli.next()) != null)) {
        Map<String, Object> couponInfo = new HashMap<>();
        couponInfo.put("productPromoId", couponData.getString("productPromoId"));
        couponInfo.put("promoName", couponData.getString("promoName"));
        couponInfo.put("promoText", couponData.getString("promoText"));
        couponInfo.put("productPromoCodeId", couponData.getString("productPromoCodeId"));
        couponInfo.put("inputParamEnumId", couponData.getString("inputParamEnumId"));
        couponInfo.put("operatorEnumId", couponData.getString("operatorEnumId"));
        couponInfo.put("condValue", couponData.getString("condValue"));
        couponInfo.put("productPromoActionEnumId", couponData.getString("productPromoActionEnumId"));
        couponInfo.put("amount", couponData.getBigDecimal("amount"));
        if(couponData.getTimestamp("thruDate") != null && nowTime.compareTo(couponData.getTimestamp("thruDate")) > 0) {
            couponInfo.put("expired", "Y");
        }
        couponCodes.add(couponInfo);
    }
    if(eli != null) {
        eli.close();
    }
} catch (GenericEntityException e) {
    Debug.logError(e, "Error retrieving promocodes from couponList.groovy");
}

context.put("couponCodes", couponCodes);
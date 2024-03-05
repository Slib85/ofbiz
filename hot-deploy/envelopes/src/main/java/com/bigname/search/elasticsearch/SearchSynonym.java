package com.bigname.search.elasticsearch;

import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.util.EntityQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by shoab on 6/14/17.
 */
public class SearchSynonym {
    public static final String module = SearchSynonym.class.getName();

    private Delegator delegator = null;
    private String id = null;
    private String[] fromSynonyms = null;
    private String[] toSynonyms = null;
    private String synonymTypeId = null;
    private String webSiteId = null;
    private boolean enabled = false;
    private String error = null;

    /**
     * Create SearchSynonym
     * @param delegator
     * @param id
     * @param fromSynonyms
     * @param toSynonyms
     * @param synonymTypeId
     * @param webSiteId
     * @param enabled
     */
    public SearchSynonym(Delegator delegator, String id, String[] fromSynonyms, String[] toSynonyms, String synonymTypeId, String webSiteId, String enabled) {
        this.delegator = delegator;
        this.id = UtilValidate.isNotEmpty(id) ? id : null;
        this.fromSynonyms = fromSynonyms;
        this.toSynonyms = toSynonyms;
        this.synonymTypeId = UtilValidate.isNotEmpty(synonymTypeId) ? synonymTypeId : null;
        this.webSiteId = UtilValidate.isNotEmpty(webSiteId) ? webSiteId : null;
        this.enabled = "Y".equalsIgnoreCase(enabled);
    }

    /**
     * Validate the entry
     * @return
     * @throws GenericEntityException
     */
    public boolean isValid() throws GenericEntityException {
        boolean isValid = false;
        String existingId = getExistingId();
        if(this.id == null && existingId != null) {
            this.error = "Synonym conflict with existing rule in ID: " + existingId;
        } else if(this.synonymTypeId != null && this.synonymTypeId.equalsIgnoreCase("ONE_WAY") && UtilValidate.isEmpty(this.toSynonyms)) {
            this.error = "One Way synonym selected, but no TO Synonym provided.";
        } else {
            isValid = true;
        }

        return isValid;
    }

    /**
     * Check if a synonym or set of synonyms already exist in the database
     * If so, return the ID of the first matched found entry
     * @return
     * @throws GenericEntityException
     */
    private String getExistingId() throws GenericEntityException {
        String id = null;

        /*
         * Check if the any synonym in the fromSynonyms list already exist in the database, if so return the ID
         */
        id = getId(this.fromSynonyms, "fromSynonyms");

        /*
         * If there are toSynonyms and a ONE_WAY type, check to see if any value in the toSynonyms exist in the fromSynonyms database
         */
        if(UtilValidate.isNotEmpty(this.toSynonyms) && this.synonymTypeId.equalsIgnoreCase("ONE_WAY")) {
            id = getId(this.toSynonyms, "fromSynonyms");
        }

        return id;
    }

    /**
     * Database check for existing data
     * @param synonymsList
     * @param fieldToCheck
     * @return
     * @throws GenericEntityException
     */
    private String getId(String[] synonymsList, String fieldToCheck) throws GenericEntityException {
        for (String synonym : synonymsList) {
            synonym = synonym.trim();

            List<EntityCondition> conditionListOr = new ArrayList();
            conditionListOr.add(EntityCondition.makeCondition(fieldToCheck, EntityOperator.EQUALS, synonym));
            conditionListOr.add(EntityCondition.makeCondition(fieldToCheck, EntityOperator.LIKE, synonym + ",%"));
            conditionListOr.add(EntityCondition.makeCondition(fieldToCheck, EntityOperator.LIKE, "%," + synonym));
            conditionListOr.add(EntityCondition.makeCondition(fieldToCheck, EntityOperator.LIKE, "%," + synonym + ",%"));

            List<EntityCondition> conditionListAnd = new ArrayList();
            conditionListAnd.add(EntityCondition.makeCondition("webSiteId", EntityOperator.EQUALS, this.webSiteId));
            conditionListAnd.add(EntityCondition.makeCondition(conditionListOr, EntityOperator.OR));

            List<GenericValue> foundList = EntityQuery.use(this.delegator).from("SearchSynonym").where(EntityCondition.makeCondition(conditionListAnd, EntityOperator.AND)).queryList();
            if (UtilValidate.isNotEmpty(foundList)) {
                for (GenericValue found : foundList) {
                    if (doesSynonymExist(found.getString(fieldToCheck), synonym)) {
                        return found.getString("id");
                    }
                }
            }
        }

        return null;
    }

    /**
     * Compare a given synonym to a list and check if it exists in it
     * @param synonyms
     * @param synonymToFind
     * @return
     */
    private boolean doesSynonymExist(String synonyms, String synonymToFind) {
        String[] synonymList = synonyms.split("\\s*,\\s*");
        return Arrays.stream(synonymList).anyMatch(synonymToFind::equals);
    }

    /**
     * Save the synonym to the database
     * @return
     * @throws GenericEntityException
     */
    public GenericValue saveSynonym() throws GenericEntityException {
        GenericValue synonym = null;
        if(this.id == null) {
            this.id = this.delegator.getNextSeqId("SearchSynonym");
            synonym = this.delegator.makeValue("SearchSynonym", UtilMisc.toMap("id", this.id));
        } else {
            synonym = EntityQuery.use(this.delegator).from("SearchSynonym").where("id", this.id).queryFirst();
        }

        synonym.set("fromSynonyms", String.join(",", this.fromSynonyms));
        if(UtilValidate.isNotEmpty(this.toSynonyms) && this.synonymTypeId.equalsIgnoreCase("ONE_WAY")) {
            synonym.set("toSynonyms", String.join(",", this.toSynonyms));
        } else if(this.synonymTypeId.equalsIgnoreCase("TWO_WAY")) {
            synonym.set("toSynonyms", null);
        }
        synonym.set("synonymTypeId", this.synonymTypeId);
        synonym.set("webSiteId", this.webSiteId);
        synonym.set("enabled", this.enabled ? "Y" : "N");
        delegator.createOrStore(synonym);

        return synonym;
    }

    /**
     * Return any error messages
     * @return
     */
    public String getError() {
        return this.error;
    }
}

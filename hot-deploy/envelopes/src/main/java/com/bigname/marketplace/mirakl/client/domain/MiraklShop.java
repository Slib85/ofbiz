package com.bigname.marketplace.mirakl.client.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ofbiz.entity.GenericValue;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Manu on 2/1/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MiraklShop {

    public static final String ID = "marketplaceSellerId";
    public static final String NAME = "sellerName";
    public static final String DATE_CREATED = "miraklCreatedDate";
    public static final String LAST_UPDATED_DATE = "miraklLastUpdatedDate";

    @JsonIgnore
    private Map<String, Object> shop;

    @JsonProperty("shop_id")
    private String id;

    private Timestamp dateCreated;

    @JsonProperty("shop_name")
    private String name;

    private Timestamp lastUpdatedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public boolean hasUpdatedAfter(Timestamp lastUpdatedTime) {
        return lastUpdatedTime != null && getLastUpdatedDate().getTime() > lastUpdatedTime.getTime();
    }

    public Map<String, Object> getAsMap(boolean withId) {
        if(shop == null) {
            shop = new HashMap<>();
            if(withId) {
                shop.put(ID, getId());
            }
            shop.put(NAME, getName());
            shop.put(DATE_CREATED, getDateCreated());
            shop.put(LAST_UPDATED_DATE, getLastUpdatedDate());
        }
        return shop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MiraklShop that = (MiraklShop) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}

package com.bigname.marketplace.mirakl.client.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Manu on 2/24/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MiraklOrder {
    @JsonProperty("order_id")
    private String id;
    private String commercialId;

    private Date createdDate;
    private Date lastUpdatedDate;
    private Date acceptanceDecisionDate;

    private BigDecimal price;
    private BigDecimal totalPrice;
    private BigDecimal totalCommission;

    private String shopId;
    private String shopName;

    private boolean canEvaluate;
    private boolean canCancel;

    private Integer evaluationGrade;

    private MiraklOrderCustomer customer;

    private List<MiraklOrderLine> orderLines = new ArrayList<MiraklOrderLine>();

    private String imprintNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommercialId() {
        return commercialId;
    }

    public void setCommercialId(String commercialId) {
        this.commercialId = commercialId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Date getAcceptanceDecisionDate() {
        return acceptanceDecisionDate;
    }

    public void setAcceptanceDecisionDate(Date acceptanceDecisionDate) {
        this.acceptanceDecisionDate = acceptanceDecisionDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(BigDecimal totalCommission) {
        this.totalCommission = totalCommission;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public boolean isCanEvaluate() {
        return canEvaluate;
    }

    public void setCanEvaluate(boolean canEvaluate) {
        this.canEvaluate = canEvaluate;
    }

    public boolean isCanCancel() {
        return canCancel;
    }

    public void setCanCancel(boolean canCancel) {
        this.canCancel = canCancel;
    }

    public Integer getEvaluationGrade() {
        return evaluationGrade;
    }

    public void setEvaluationGrade(Integer evaluationGrade) {
        this.evaluationGrade = evaluationGrade;
    }

    public MiraklOrderCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(MiraklOrderCustomer customer) {
        this.customer = customer;
    }

    public List<MiraklOrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<MiraklOrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public String getImprintNumber() {
        return imprintNumber;
    }

    public void setImprintNumber(String imprintNumber) {
        this.imprintNumber = imprintNumber;
    }
}

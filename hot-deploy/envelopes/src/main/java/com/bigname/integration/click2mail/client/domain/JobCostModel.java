package com.bigname.integration.click2mail.client.domain;

public class JobCostModel {
    private String id;
    private String status;
    private String description;
    private String cost;
    private String statusUrl;
    private StandardCost standardCost;
    private NonstandardCost nonstandardCost;
    private InternationalCost internationalCost;
    private ProductionCost productionCost;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getStatusUrl() {
        return statusUrl;
    }

    public void setStatusUrl(String statusUrl) {
        this.statusUrl = statusUrl;
    }

    public StandardCost getStandardCost() {
        return standardCost;
    }

    public void setStandardCost(StandardCost standardCost) {
        this.standardCost = standardCost;
    }

    public NonstandardCost getNonstandardCost() {
        return nonstandardCost;
    }

    public void setNonstandardCost(NonstandardCost nonstandardCost) {
        this.nonstandardCost = nonstandardCost;
    }

    public InternationalCost getInternationalCost() {
        return internationalCost;
    }

    public void setInternationalCost(InternationalCost internationalCost) {
        this.internationalCost = internationalCost;
    }

    public ProductionCost getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(ProductionCost productionCost) {
        this.productionCost = productionCost;
    }
}

class StandardCost {
    private String postageName;
    private String postageUnitCost;
    private String quantity;
    private String handlingFee;
    private String subtotal;

    public String getPostageName() {
        return postageName;
    }

    public void setPostageName(String postageName) {
        this.postageName = postageName;
    }

    public String getPostageUnitCost() {
        return postageUnitCost;
    }

    public void setPostageUnitCost(String postageUnitCost) {
        this.postageUnitCost = postageUnitCost;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getHandlingFee() {
        return handlingFee;
    }

    public void setHandlingFee(String handlingFee) {
        this.handlingFee = handlingFee;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }
}
class NonstandardCost {
    private String postageName;
    private String postageUnitCost;
    private String quantity;
    private String handlingFee;
    private String subtotal;

    public String getPostageName() {
        return postageName;
    }

    public void setPostageName(String postageName) {
        this.postageName = postageName;
    }

    public String getPostageUnitCost() {
        return postageUnitCost;
    }

    public void setPostageUnitCost(String postageUnitCost) {
        this.postageUnitCost = postageUnitCost;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getHandlingFee() {
        return handlingFee;
    }

    public void setHandlingFee(String handlingFee) {
        this.handlingFee = handlingFee;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }
}

class InternationalCost {
    private String postageName;
    private String postageUnitCost;
    private String quantity;
    private String handlingFee;
    private String subtotal;

    public String getPostageName() {
        return postageName;
    }

    public void setPostageName(String postageName) {
        this.postageName = postageName;
    }

    public String getPostageUnitCost() {
        return postageUnitCost;
    }

    public void setPostageUnitCost(String postageUnitCost) {
        this.postageUnitCost = postageUnitCost;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getHandlingFee() {
        return handlingFee;
    }

    public void setHandlingFee(String handlingFee) {
        this.handlingFee = handlingFee;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }
}
class ProductionCost {
    private String postageName;
    private String postageUnitCost;
    private String quantity;
    private String handlingFee;
    private String subtotal;

    public String getPostageName() {
        return postageName;
    }

    public void setPostageName(String postageName) {
        this.postageName = postageName;
    }

    public String getPostageUnitCost() {
        return postageUnitCost;
    }

    public void setPostageUnitCost(String postageUnitCost) {
        this.postageUnitCost = postageUnitCost;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getHandlingFee() {
        return handlingFee;
    }

    public void setHandlingFee(String handlingFee) {
        this.handlingFee = handlingFee;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }
}
package com.bigname.integration.click2mail.client.domain;

import java.util.List;

/**
 * Created by Meenu on 16-04-2018.
 */
public class CostEstimate {
    private ProductionCostEstimate productionCost;
    private String description;
    private String status;
    private NonstandardCostEstimate nonstandardCost;
    private InternationalCostEstimate internationalCost;
    private StandardCostEstimate standardCost;

    public ProductionCostEstimate getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(ProductionCostEstimate productionCost) {
        this.productionCost = productionCost;
    }

    public NonstandardCostEstimate getNonstandardCost() {
        return nonstandardCost;
    }

    public void setNonstandardCost(NonstandardCostEstimate nonstandardCost) {
        this.nonstandardCost = nonstandardCost;
    }

    public InternationalCostEstimate getInternationalCost() {
        return internationalCost;
    }

    public void setInternationalCost(InternationalCostEstimate internationalCost) {
        this.internationalCost = internationalCost;
    }

    public StandardCostEstimate getStandardCost() {
        return standardCost;
    }

    public void setStandardCost(StandardCostEstimate standardCost) {
        this.standardCost = standardCost;
    }




    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
    class ProductionCostEstimate {
        private String quantity;
        private String discountSubtotal;
        private String subtotal;
        private String productionUnitCost;

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getDiscountSubtotal() {
            return discountSubtotal;
        }

        public void setDiscountSubtotal(String discountSubtotal) {
            this.discountSubtotal = discountSubtotal;
        }

        public String getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(String subtotal) {
            this.subtotal = subtotal;
        }

        public String getProductionUnitCost() {
            return productionUnitCost;
        }

        public void setProductionUnitCost(String productionUnitCost) {
            this.productionUnitCost = productionUnitCost;
        }
    }

    class NonstandardCostEstimate {
        private String quantity;
        private String subtotal;
        private String postageUnitCost;
        private String postageName;

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(String subtotal) {
            this.subtotal = subtotal;
        }

        public String getPostageUnitCost() {
            return postageUnitCost;
        }

        public void setPostageUnitCost(String postageUnitCost) {
            this.postageUnitCost = postageUnitCost;
        }

        public String getPostageName() {
            return postageName;
        }

        public void setPostageName(String postageName) {
            this.postageName = postageName;
        }


    }

    class InternationalCostEstimate {
        private String quantity;
        private String subtotal;
        private String postageUnitCost;
        private String postageName;

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(String subtotal) {
            this.subtotal = subtotal;
        }

        public String getPostageUnitCost() {
            return postageUnitCost;
        }

        public void setPostageUnitCost(String postageUnitCost) {
            this.postageUnitCost = postageUnitCost;
        }

        public String getPostageName() {
            return postageName;
        }

        public void setPostageName(String postageName) {
            this.postageName = postageName;
        }
    }

    class StandardCostEstimate {
        private String quantity;
        private String subtotal;
        private String postageUnitCost;
        private String postageName;

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(String subtotal) {
            this.subtotal = subtotal;
        }

        public String getPostageUnitCost() {
            return postageUnitCost;
        }

        public void setPostageUnitCost(String postageUnitCost) {
            this.postageUnitCost = postageUnitCost;
        }

        public String getPostageName() {
            return postageName;
        }

        public void setPostageName(String postageName) {
            this.postageName = postageName;
        }
    }


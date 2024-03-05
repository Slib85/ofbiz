package com.envelopes.printing;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Manu on 9/2/2016.
 */
public class JobItemDetails implements Serializable {
    private String orderId;
    private String itemSeqNum;
    private String itemId;
    private String internalJobId;
    private int qty;
    private Date itemDueDate;
    private boolean rushItem;
    private boolean variableDataItem;
    private boolean scene7Item;
    private double itemWidth;

    public JobItemDetails(String orderId, String itemSeqNum, String itemId, String internalJobId, int qty, Date itemDueDate, boolean rushItem, boolean scene7Item, boolean variableDataItem, double itemWidth) {
        this.orderId = orderId;
        this.itemSeqNum = itemSeqNum;
        this.itemId = itemId;
        this.internalJobId = internalJobId;
        this.qty = qty;
        this.itemDueDate = itemDueDate;
        this.rushItem = rushItem;
        this.scene7Item = scene7Item;
        this.variableDataItem = variableDataItem;
        this.itemWidth = itemWidth;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getItemSeqNum() {
        return itemSeqNum;
    }

    public String getItemId() {
        return itemId;
    }

    public String getInternalJobId() {
        return internalJobId;
    }

    public int getQty() {
        return qty;
    }

    public Date getItemDueDate() {
        return itemDueDate;
    }

    public boolean isRushItem() {
        return rushItem;
    }

    public boolean isVariableDataItem() {
        return variableDataItem;
    }

    public boolean isScene7Item() {
        return scene7Item;
    }

    public double getItemWidth() {
        return itemWidth;
    }
}

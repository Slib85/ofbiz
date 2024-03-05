package com.bigname.marketplace.mirakl.client.domain;

import java.util.List;

/**
 * Created by Manu on 3/27/2017.
 */
public class MiraklCreatedOrders {
    private List<MiraklOrder> orders;

    private long totalCount;

    public List<MiraklOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<MiraklOrder> orders) {
        this.orders = orders;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MiraklCreatedOrders that = (MiraklCreatedOrders) o;

        if (totalCount != that.totalCount) {
            return false;
        }
        return orders != null ? orders.equals(that.orders) : that.orders == null;

    }

    @Override
    public int hashCode() {
        int result = orders != null ? orders.hashCode() : 0;
        result = 31 * result + (int) (totalCount ^ (totalCount >>> 32));
        return result;
    }
}

package com.bigname.marketplace.mirakl.client.request;

import com.bigname.core.restful.client.request.AbstractApiRequest;
import com.bigname.core.restful.client.request.ApiEndpoint;
import com.bigname.marketplace.mirakl.client.domain.MiraklCreateOrder;

/**
 * Created by Manu on 3/27/2017.
 */
public class CreateOrderRequest extends AbstractApiRequest {

    private MiraklCreateOrder createOrder;

    public CreateOrderRequest(MiraklCreateOrder createOrder) {
        setCreateOrder(createOrder);
    }

    public MiraklCreateOrder getCreateOrder() {
        return createOrder;
    }

    public void setCreateOrder(MiraklCreateOrder createOrder) {
        this.createOrder = createOrder;
    }

    @Override
    public ApiEndpoint getEndpoint() {
        return MiraklEndpoint.OR01;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CreateOrderRequest that = (CreateOrderRequest) o;

        return createOrder != null ? createOrder.equals(that.createOrder) : that.createOrder == null;

    }

    @Override
    public int hashCode() {
        return createOrder != null ? createOrder.hashCode() : 0;
    }
}

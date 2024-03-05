package com.envelopes.cxml;

import com.bigname.cxml.*;
import com.bigname.cxml.cxml.ItemDetail;
import com.bigname.cxml.cxml.OrderRequest;
import com.bigname.cxml.cxml.OrderRequestHeader;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.service.LocalDispatcher;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

public class NavitorCXMLOrderRequest extends BaseCXMLOrderRequest {
    public NavitorCXMLOrderRequest(Delegator delegator, LocalDispatcher dispatcher, Map<String, Object> context, List<GenericValue> orderItems, GenericValue vendor) throws Exception {
        super(delegator, dispatcher, context, orderItems, vendor);
    }

    public NavitorCXMLOrderRequest(Delegator delegator, LocalDispatcher dispatcher, Map<String, Object> context, GenericValue vendor) throws Exception {
        super(delegator, dispatcher, context, vendor);
    }

    protected BaseCXMLOrderRequest createOrderRequestHeaderNode(OrderRequest orderRequest) {
        OrderRequestHeader orderRequestHeader = new OrderRequestHeader();
        orderRequestHeader.setOrderDate(EnvConstantsUtil.UTC.format(this.orh.getOrderHeader().getTimestamp("entryDate")));
        orderRequestHeader.setOrderID((this.orderPerLine) ? this.orh.getOrderId() + "_" + this.context.get("orderItemSeqId") : this.orh.getOrderId());
        orderRequestHeader.setType("new");
        orderRequestHeader.setOrderType("regular");
        orderRequestHeader.setOrderVersion("1");

        this.createTotalNode(orderRequestHeader)
                .createShipToNode(orderRequestHeader)
                .createBillToNode(orderRequestHeader)
                .createExtrinsicNode(orderRequestHeader, "ShippingAcknowledgementAddress", rootURL + "/envelopes/control/cxmlNavitorShipNotice")
                .createExtrinsicNode(orderRequestHeader, "CustomerPO", (String)context.get("purchaseOrderId"));
        orderRequest.setOrderRequestHeader(orderRequestHeader);
        return this;
    }

    protected BaseCXMLOrderRequest createColorExtrinsicNodes(ItemDetail itemDetail, Map<String, Object> lineItemData) {
        this.createExtrinsicNode(itemDetail, "Side 1 Inks", ((Integer) lineItemData.get("colorsFront")).toString());
        this.createExtrinsicNode(itemDetail, "Side 2 Inks", ((Integer) lineItemData.get("colorsBack")).toString());

        List<String> inkColorsFront = (List<String>)lineItemData.get("inkColorsFront");
        for(int i = 1; i <= inkColorsFront.size(); i ++) {
            String color = inkColorsFront.get(i - 1);
            if(NumberUtils.isNumber(color)) {
                color = "PMS " + color;
            }
            this.createExtrinsicNode(itemDetail, "Side 1 Ink " + i, color);
        }

        List<String> inkColorsBack = (List<String>)lineItemData.get("inkColorsBack");
        for(int i = 1; i <= inkColorsBack.size(); i ++) {
            String color = inkColorsBack.get(i - 1);
            if(NumberUtils.isNumber(color)) {
                color = "PMS " + color;
            }
            this.createExtrinsicNode(itemDetail, "Side 2 Ink " + i, color);
        }
        return this;
    }


}

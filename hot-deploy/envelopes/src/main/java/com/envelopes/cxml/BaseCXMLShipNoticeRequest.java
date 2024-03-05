package com.envelopes.cxml;

import com.bigname.cxml.fulfill.CXML;
import com.envelopes.order.OrderHelper;
import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.order.order.OrderReadHelper;
import org.apache.ofbiz.service.LocalDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BaseCXMLShipNoticeRequest {

    protected HttpServletRequest request;
    protected OrderReadHelper orh = null;
    protected Delegator delegator;
    protected LocalDispatcher dispatcher;
    protected String orderId = "";
    protected String orderItemSeqId = "";
    protected String trackingNumber;
    protected boolean success = false;
    public static final String module = CXMLShipNoticeRequest.class.getName();

    public BaseCXMLShipNoticeRequest(HttpServletRequest request) {
        this.request = request;
        this.dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        this.delegator = (Delegator) request.getAttribute("delegator");
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderItemSeqId() {
        return orderItemSeqId;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public BaseCXMLShipNoticeRequest updateTracking() {
        this.success = false;
        try {
            CXML cxml = toCXML();
            this.trackingNumber = cxml.getRequest().getShipNoticeRequest().getShipControl().get(0).getShipmentIdentifier().get(0).getContent();
            String[] orderItemID = cxml.getRequest().getShipNoticeRequest().getShipNoticePortion().get(0).getOrderReference().getOrderID().split("_");
            if (orderItemID.length == 2 && UtilValidate.isNotEmpty(orderItemID[0]) && UtilValidate.isNotEmpty(orderItemID[1]) && UtilValidate.isNotEmpty(this.trackingNumber)) {
                this.orderId = orderItemID[0];
                this.orderItemSeqId = orderItemID[1];
                this.orh = new OrderReadHelper(this.delegator, this.orderId);

                if(orh.getOrderHeader() != null) {
                    GenericValue orderItem = orh.getOrderItem(this.orderItemSeqId);
                    Map<String, Map<String, String>> orderAndTracking = new HashMap<>();
                    orderAndTracking.put(this.orderId, UtilMisc.<String, String>toMap(this.orderItemSeqId + "|" + orderItem.getString("productId"), this.trackingNumber));
                    Map<String, List<String>> updatedTracking = OrderHelper.updateOrderTracking(delegator, orderAndTracking);

                    //change item statuses to shipped
                    Iterator updatedTrackingIter = updatedTracking.entrySet().iterator();
                    while(updatedTrackingIter.hasNext()) {
                        Map.Entry pairs = (Map.Entry) updatedTrackingIter.next();
                        String orderId = (String) pairs.getKey();
                        List<String> orderItemSeqIds = (List<String>) pairs.getValue();

                        for(String orderItemSeqId : orderItemSeqIds) {
                            OrderHelper.processItemShipStatusChange(delegator, dispatcher, orderId, orderItemSeqId, true, null);
                        }
                    }

                    this.success = true;
                }
            }
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying to store tracking data", module);
        }
        return this;
    }

    public boolean isSuccess() {
        return true;
    }

    protected CXML toCXML() {
        BufferedReader bufferedReader = null;

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CXML.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (CXML)((JAXBElement)jaxbUnmarshaller.unmarshal(this.request.getInputStream())).getValue();

        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying to parse Ship Notice Request cXML", module);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception ex) {
                    EnvUtil.reportError(ex);
                    Debug.logError(ex, "Error trying to parse Ship Notice Request cXML", module);
                }
            }
        }
        return null;
    }

    protected String getXML() {
        BufferedReader bufferedReader = null;
        try {
            StringBuilder stringBuilder = new StringBuilder();
            bufferedReader = new BufferedReader(new InputStreamReader(this.request.getInputStream()));
            char[] charBuffer = new char[128];
            int bytesRead = -1;
            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying to read cxml data", module);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception ex) {
                    EnvUtil.reportError(ex);
                    Debug.logError(ex, "Error trying to read cxml data", module);
                }
            }
        }
        return null;
    }
}

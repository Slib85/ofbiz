package com.envelopes.printing;

import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.UtilValidate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Manu on 9/9/2016.
 */
public class JobIdentifier {

    private String orderId;
    private String orderItemSeqId;
    private Side side;
    private String jobId;

    protected enum Key{orderId, orderItemSeqId, side, jobId}

    protected JobIdentifier(String jobId) {
        String[] idTokens = jobId.split("-");
        this.orderId = idTokens[0];
        this.orderItemSeqId = idTokens[1];
        this.side = Side.valueOf(idTokens[2]);
    }

    public JobIdentifier(HttpServletRequest request) throws InvalidRequestException {
        Map<String, Object> context = EnvUtil.getParameterMap(request);
        if(UtilValidate.isEmpty(orderId = (String)context.get(Key.orderId.name()))) {
            throw new InvalidRequestException("Order Id is empty");
        }

        if(UtilValidate.isEmpty(orderItemSeqId = (String)context.get(Key.orderItemSeqId.name()))) {
            throw new InvalidRequestException("Item Sequence Number is empty");
        }

        if(UtilValidate.isEmpty(context.get(Key.side.name()))) {
            throw new InvalidRequestException("Side is empty");
        } else if((side = Side.getSide((String)context.get(Key.side.name()))) == Side.UNKNOWN) {
            throw new InvalidRequestException("Side value in Invalid");
        }
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderItemSeqId() {
        return orderItemSeqId;
    }

    public Side getSide() {
        return side;
    }

    public String getJobId() {
        if(UtilValidate.isEmpty(jobId)) {
            jobId = orderId + "-" + orderItemSeqId + "-" + side;
        }
        return jobId;
    }
}

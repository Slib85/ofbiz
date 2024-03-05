/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.cxml;

import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilValidate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class CXMLStatusResponse {
    public static final String module = CXMLStatusResponse.class.getName();

    protected Map<String, Object> requestObj = null;
    protected HashMap<String, Object> responseObj = null;
    protected Map<String, Object> context = null;
    protected Document responseXML = null;
    protected Document document = null;

    protected String orderId = null;
    protected String orderItemSeqId = null;

    /**
     * CXMLStatusResponse constructor for creating a response
     */
    public CXMLStatusResponse(HttpServletRequest request)  {
        this.context = EnvUtil.getParameterMap(request);
    }

    /**
     * CXMLStatusResponse constructor for getting an existing response
     */
    public CXMLStatusResponse(String response)  {
        if(UtilValidate.isNotEmpty(response)) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            try {
                builder = factory.newDocumentBuilder();
                this.document = builder.parse(new InputSource(new StringReader(response)));
                this.document.getDocumentElement().normalize();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setData() {
        this.orderId = (String) this.context.get("orderId");
        this.orderItemSeqId = (UtilValidate.isNotEmpty((String) this.context.get("orderItemSeqId"))) ? (String) this.context.get("orderItemSeqId") : (String) this.context.get("lineItem");
    }

    public boolean isSuccess() {
        Node responseNode = this.document.getElementsByTagName("Response").item(0);
        if(responseNode.getNodeType() == Node.ELEMENT_NODE) {
            Element responseElement = (Element) responseNode;
            Node statusNode = responseElement.getElementsByTagName("Status").item(0);
            if(statusNode.getNodeType() == Node.ELEMENT_NODE) {
                Element statusElement = (Element) statusNode;
                if(UtilValidate.isNotEmpty(statusElement.getAttribute("text")) && "OK".equalsIgnoreCase(statusElement.getAttribute("text"))) {
                    return true;
                }
            }
        }

        return false;
    }

    public void createResponseDoc(boolean success) throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        this.responseXML = docBuilder.newDocument();

        this.responseXML.appendChild(createResponse(success));
    }

    public Element createResponse(boolean success) throws Exception {
        Element rootElement = createElement(this.responseXML, "cXML");
        rootElement.setAttribute("payloadID", this.orderId + "_" + this.orderItemSeqId + "-STATUS-RESPONSE-" + UtilDateTime.nowAsString());
        rootElement.setAttribute("timestamp", EnvConstantsUtil.UTC.format(UtilDateTime.nowTimestamp()));
        rootElement.setAttribute("version", "1.2.029");
        rootElement.setAttribute("xml:lang", "en-US");

        Element response = createElement(this.responseXML, "Response");
        Element status = createElement(this.responseXML, "Status");
        if(success) {
            status.setAttribute("code", "200");
            status.setAttribute("text", "OK");
        } else {
            status.setAttribute("code", "400");
            status.setAttribute("text", "BAD");
        }

        response.appendChild(status);
        rootElement.appendChild(response);

        return rootElement;
    }

    /** Set the orderId
	 * @param orderId
	 */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * Set the orderItemSeqId
     * @param orderItemSeqId
     */
    public void setOrderItemSeqId(String orderItemSeqId) {
        this.orderItemSeqId = orderItemSeqId;
    }


    private Element createElement(Document xml, String str) {
        return xml.createElement(str);
    }

    public String getXMLResponseString() throws TransformerException {
        return CXMLHelper.getXMLString(this.responseXML);
    }
}

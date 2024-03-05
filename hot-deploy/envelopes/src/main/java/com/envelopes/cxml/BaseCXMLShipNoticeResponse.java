package com.envelopes.cxml;

import com.bigname.cxml.fulfill.CXML;
import com.bigname.cxml.fulfill.Response;
import com.bigname.cxml.fulfill.Status;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.UtilDateTime;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.StringWriter;

public class BaseCXMLShipNoticeResponse {

    protected String orderId;
    protected  String orderItemSeqId;
    protected CXML cxml;
    protected boolean success;

    public BaseCXMLShipNoticeResponse(String orderId, String orderItemSeqId, boolean success) {
        this.orderId = orderId;
        this.orderItemSeqId = orderItemSeqId;
        this.success = success;
    }

    protected void init() {
        createRootNode().createResponseNode();
    }

    protected BaseCXMLShipNoticeResponse createRootNode() {
        cxml = new CXML();
        cxml.setLang("en-US");
        cxml.setVersion("1.2.029");
        cxml.setTimestamp(EnvConstantsUtil.UTC.format(UtilDateTime.nowTimestamp()) );
        cxml.setPayloadID(this.orderId + "_" + this.orderItemSeqId + "-" + UtilDateTime.nowAsString());
        return this;
    }

    protected BaseCXMLShipNoticeResponse createResponseNode() {
        Response response = new Response();
        Status status = new Status();
        status.setCode(success ? "200" : "400");
        status.setText(success ? "OK" : "BAD");
        response.setStatus(status);
        return this;

    }

    public String toString() {
        try
        {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(com.bigname.cxml.cxml.CXML.class);

            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty("jaxb.encoding", "ISO-8859-1");
//            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

            // output pretty printed
            try {
                jaxbMarshaller.setProperty("com.sun.xml.bind.xmlHeaders", "<!DOCTYPE cXML SYSTEM  \"http://xml.cxml.org/schemas/cXML/1.2.029/cXML.dtd\">\n");
            } catch (PropertyException ignore) {
                // Ignore
            }

            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            //Print XML String to Console
            StringWriter sw = new StringWriter();

            //Write XML to StringWriter
            jaxbMarshaller.marshal(cxml, sw);

            //Verify XML Content
            return sw.toString().replaceAll("<cxml", "<cXML").replaceAll("</cxml", "</cXML"); //TODO - fix the issue with the lowercase root node

        } catch (JAXBException e) {
            EnvUtil.reportError(e);
        }
        return "";
    }

}

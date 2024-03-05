package com.envelopes.cxml;

import com.bigname.cxml.fulfill.CXML;
import com.bigname.cxml.fulfill.Response;
import com.bigname.cxml.fulfill.Status;
import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.Debug;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.StringReader;

public class BaseCXMLOrderResponse {

    protected String responseXML;
    public static final String module = CXMLShipNoticeRequest.class.getName();

    public BaseCXMLOrderResponse(String responseXML) {
        this.responseXML = responseXML;
    }

    protected CXML toCXML() {
        BufferedReader bufferedReader = null;

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CXML.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (CXML) jaxbUnmarshaller.unmarshal(new StringReader(this.responseXML));

        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying to parse Order Response cXML", module);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception ex) {
                    EnvUtil.reportError(ex);
                    Debug.logError(ex, "Error trying to parse Order Response cXML", module);
                }
            }
        }
        return null;
    }

    public boolean isSuccess() {
        try {
            CXML cxml = toCXML();
            Response response = cxml != null ? cxml.getResponse() : null;
            Status status = response != null ? response.getStatus() : null;
            return status != null && "200".equals(status.getCode()) && ("OK".equalsIgnoreCase(status.getText()) || status.getText().contains("Order Already Exists"));
        } catch (Exception e) {
            return false;
        }
    }
}

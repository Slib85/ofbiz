/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.dialogtech;

import com.envelopes.http.HTTPHelper;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class CallDetailReport {
    public static final String module = CallDetailReport.class.getName();

    private Map<String, String> data = new HashMap<>();
    private String phoneNumber = null;
    private String response = null;
    private Document xml = null;
    private String gclid = null;
    private String source = null;

    public CallDetailReport(Delegator delegator, String phoneNumber, Timestamp startDate, Timestamp endDate) {
        this.phoneNumber = phoneNumber;

        this.data.put("action", DialogTechHelper.CALL_REPORT_ACTION);
        this.data.put("format", "xml");
        this.data.put("gclid", "1");
        this.data.put("ani", "1");
        this.data.put("phone_label", "1");
        this.data.put("start_date", EnvConstantsUtil.yyyyMMdd.format(startDate));
        this.data.put("end_date", EnvConstantsUtil.yyyyMMdd.format(endDate));
    }

    public void process() throws Exception {
        sendRequest();
        processResponse();
    }

    public void sendRequest() {
        try {
            this.response = HTTPHelper.getURL(DialogTechHelper.buildRequestURL(data), "GET", null, null, null, null, false, EnvConstantsUtil.RESPONSE_JSON, EnvConstantsUtil.RESPONSE_XML);
        } catch(Exception e) {
            EnvUtil.reportError(e);
        }
    }

    public void processResponse() throws Exception {
        if(UtilValidate.isNotEmpty(this.response)) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(this.response));
            this.xml = builder.parse(is);

            NodeList nodes = this.xml.getElementsByTagName("record");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);

                NodeList name = element.getElementsByTagName("ani");
                Element line = (Element) name.item(0);
                if(getCharacterDataFromElement(line).equalsIgnoreCase(this.phoneNumber)) {
                    NodeList title = element.getElementsByTagName("gclid");
                    line = (Element) title.item(0);
                    this.gclid = getCharacterDataFromElement(line);

                    NodeList source = element.getElementsByTagName("phone_label");
                    line = (Element) source.item(0);
                    this.source = getCharacterDataFromElement(line);
                    break;
                }
            }
        }
    }

    public String getGCLID() {
        return this.gclid;
    }

    public String getSource() {
        return this.source;
    }

    private static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return (UtilValidate.isNotEmpty(cd.getData())) ? cd.getData() : null;
        }
        return null;
    }
}

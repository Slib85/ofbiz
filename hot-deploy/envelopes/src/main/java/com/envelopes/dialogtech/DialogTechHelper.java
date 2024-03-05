/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.dialogtech;

import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilProperties;

import java.util.Map;

public class DialogTechHelper {
    public static final String module = DialogTechHelper.class.getName();

    protected static String ACCESS_KEY;
    protected static String SECRET;
    protected static final String ENDPOINT = "https://secure.dialogtech.com/ibp_api.php";
    protected static final String CALL_REPORT_ACTION = "report.call_detail";

    static {
        try {
            ACCESS_KEY = UtilProperties.getPropertyValue("envelopes", "dialogtech.key");
            SECRET = UtilProperties.getPropertyValue("envelopes", "dialogtech.secret");
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError("Error, could not open: " + "envelopes.properties", module);
        }
    }

    protected static String buildRequestURL(Map<String, String> data) {
        StringBuilder str = new StringBuilder(ENDPOINT + "?");
        str.append("access_key=").append(ACCESS_KEY).append("&secret_access_key=").append(SECRET);

        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            str.append("&").append(key).append("=").append(value);
        }

        return str.toString();
    }
}
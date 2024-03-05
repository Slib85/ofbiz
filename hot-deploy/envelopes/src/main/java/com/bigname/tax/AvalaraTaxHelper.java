/*******************************************************************************
 * BigName Commerce LLC
 *******************************************************************************/
package com.bigname.tax;

import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilProperties;

import java.util.Map;

public class AvalaraTaxHelper {
    public static final String module = AvalaraTaxHelper.class.getName();

    protected static boolean SANDBOX;
    protected static String ACCOUNT_ID;
    protected static String LICENSE_KEY;
    protected static String APP_NAME = "bigname";
    protected static String APP_VERSION = "1.0";
    protected static String MACHINE_NAME = "jvm";

    // endpoint names
    public static final String RESOLVE_ADDRESS = "ResolveAddress";

    static {
        try {
            SANDBOX     = UtilProperties.getPropertyAsBoolean("envelopes", "avalara.sandbox", true);
            ACCOUNT_ID  = UtilProperties.getPropertyValue("envelopes", (SANDBOX) ? "avalara.sandbox.accountid" : "avalara.production.accountid");
            LICENSE_KEY = UtilProperties.getPropertyValue("envelopes", (SANDBOX) ? "avalara.sandbox.licensekey" : "avalara.production.licensekey");
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError("Error, could not open envelopes.properties.", module);
        }
    }
}

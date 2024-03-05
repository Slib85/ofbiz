import java.lang.*;
import java.util.*;
import java.sql.Timestamp;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.entity.GenericValue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.bigname.navigation.NavigationHelper;
import com.envelopes.util.*;

String module = "navigationEditor.groovy";

context.megaMenuData = NavigationHelper.getMegaMenuData(delegator, 'envelopes');

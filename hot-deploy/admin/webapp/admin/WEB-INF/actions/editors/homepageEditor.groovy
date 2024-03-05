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

import com.envelopes.homepage.HomepageHelper;
import com.envelopes.product.ProductReviewEvents;
import com.envelopes.util.*;

String module = "homepageEditor.groovy";

context.timedHomepageImages = HomepageHelper.getTimedHomepageImages(request, response);

import java.math.BigDecimal;
import java.util.*;
import java.sql.Timestamp;

import org.apache.ofbiz.base.util.Debug;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.envelopes.product.*;
import com.envelopes.util.*;

String module = "recycled-envelopes.groovy";

request.setAttribute("saveResponse", true);

List<String> recycledProductPrimaryParentCategoryIds = null;
List<String> recycledProductProductCategoryIds = null;

/*
	Currently set to have false data since the database doesn't have the correct data as is.
	After Applying real data, can insert the correct ID's and remove this comment
*/

recycledProductPrimaryParentCategoryIds = Arrays.asList("BoatRental");
recycledProductProductCategoryIds = Arrays.asList("GB_MEDIA");
ProductEvents.getRecycledProdCatList(request, response, recycledProductPrimaryParentCategoryIds, recycledProductProductCategoryIds);
context.put("oneHundredPercentRecycled", request.getAttribute("savedResponse"));

recycledProductPrimaryParentCategoryIds = Arrays.asList("GOOGLE_BASE");
recycledProductProductCategoryIds = Arrays.asList("GB_MATURE", "GB_HME_N_GRDN");
ProductEvents.getRecycledProdCatList(request, response, recycledProductPrimaryParentCategoryIds, recycledProductProductCategoryIds);
context.put("thirtyPercentRecycled", request.getAttribute("savedResponse"));

recycledProductPrimaryParentCategoryIds = Arrays.asList("BRIGHTS");
recycledProductProductCategoryIds = Arrays.asList("GB_ANIMALS", "GB_HME_N_GRDN");
ProductEvents.getRecycledProdCatList(request, response, recycledProductPrimaryParentCategoryIds, recycledProductProductCategoryIds);
context.put("twentyPercentRecycled", request.getAttribute("savedResponse"));

recycledProductPrimaryParentCategoryIds = Arrays.asList("BLACKS");
recycledProductProductCategoryIds = Arrays.asList("GB_OFFC_SPPLS", "GB_SOFTWARE");
ProductEvents.getRecycledProdCatList(request, response, recycledProductPrimaryParentCategoryIds, recycledProductProductCategoryIds);
context.put("tenPercentRecycled", request.getAttribute("savedResponse"));

recycledProductPrimaryParentCategoryIds = Arrays.asList("BLACKS");
recycledProductProductCategoryIds = Arrays.asList("GB_OFFC_SPPLS", "GB_HME_N_GRDN");
ProductEvents.getRecycledProdCatList(request, response, recycledProductPrimaryParentCategoryIds, recycledProductProductCategoryIds);
context.put("cottonRecycled", request.getAttribute("savedResponse"));

import com.envelopes.product.ProductEvents;
import com.envelopes.product.ProductHelper;
import com.bigname.quote.calculator.CalculatorHelper

import java.lang.*;
import java.util.*;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.GenericValue;

import com.envelopes.cart.CartHelper;
import com.envelopes.product.*;
import com.envelopes.util.*;

import com.google.gson.Gson;
  
String module = "product.groovy";

Map<String, Object> params = EnvUtil.getParameterMap(request);
String categoryId = (String) params.get("category_id");

try {
	String productId = (UtilValidate.isNotEmpty((String) params.get("product_id"))) ? (String) params.get("product_id") : null;

	if(productId != null) {
		context.FEATURES_TO_SHOW = ProductEvents.FEATURES_TO_SHOW;
		Product product = new Product(delegator, dispatcher, productId, request);
		if(product.isValid()) {
			if(product.isVirtual()) {
				product.getFirstActiveChild();
			}

			context.product = product;
			context.crossSells = CartHelper.getCrossSellItems(request);
			if(UtilValidate.isNotEmpty(product.getProduct().getString("primaryProductCategoryId"))) {
				categoryId = product.getProduct().getString("primaryProductCategoryId");
			}

			List breadcrumbs = new ArrayList();
			GenericValue category = delegator.findOne("ProductCategory", UtilMisc.toMap("productCategoryId", categoryId), true);
			breadcrumbs.add(UtilMisc.toMap("name", category != null ? category.description : "", "link", "category/~category_id=" + (category != null ? category.productCategoryId : "")));

			if(UtilValidate.isNotEmpty(category) && UtilValidate.isNotEmpty(category.get("primaryParentCategoryId"))) {
				category = delegator.findOne("ProductCategory", UtilMisc.toMap("productCategoryId", category.getString("primaryParentCategoryId")), true);
				breadcrumbs.add(0, UtilMisc.toMap("name", category != null ? category.description : "", "link", "category/~category_id=" + (category != null ? category.productCategoryId : "")));
			}

			breadcrumbs.add(UtilMisc.toMap("name", product.getName(), "link", ""));

			context.breadcrumbs = breadcrumbs;
			context.pocketStyles = ProductHelper.getProductFeaturesByType(delegator, product.getProduct(), "POCKET_STYLE");
			context.pocketStylesJS = new Gson().toJson(context.get("pocketStyles"));
			request.getSession().setAttribute("productId", product.getId());
			request.getSession().setAttribute("reviews", product.getReviews());

			context.printedDiscount = CalculatorHelper.discountAmount;
		}

		// This portion is to set up for quotes with the old data.  Remove this when new data is entered for Folder Styles, such as SF-101
		Map<String, Object> foldersProduct = FoldersProduct.getProductData(productId);

		context.quoteFormMaterialList = FolderProductHelper.getProductMaterials(delegator, null, productId);

		if (UtilValidate.isNotEmpty(foldersProduct)) {
			context.basicAndPremiumMaterialList = FolderProductHelper.getProductMaterials(delegator, productId, Boolean.TRUE);
			context.nonBasicAndPremiumMaterialList = FolderProductHelper.getProductMaterials(delegator, productId, Boolean.FALSE);
			context.productSlitInfoList = FolderProductHelper.getProductSlitInfo(delegator, productId);
		}
	}

	if (UtilValidate.isNotEmpty(request.getParameter("productAttributes"))) {
		context.productAttributes = new Gson().fromJson(request.getParameter("productAttributes"), HashMap.class);
	}
} catch (Exception e) {
	EnvUtil.reportError(e);
	Debug.logError("Error loading product: " + params.get("product_id"), module);
}
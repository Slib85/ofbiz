import java.util.*;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.GenericValue;

import org.apache.ofbiz.order.shoppingcart.*;
import com.envelopes.cart.CartEvents;

import com.envelopes.product.ProductHelper;
import com.envelopes.cart.CartHelper;
import com.envelopes.scene7.Scene7Helper;
import com.envelopes.util.EnvConstantsUtil;

String module ="cross-sell.groovy";
session = request.getSession();

if(UtilValidate.isNotEmpty(request.getParameter("lastVisitedURL"))) {
	session.setAttribute("lastVisitedURL", request.getParameter("lastVisitedURL"));
	context.lastVisitedURL = request.getParameter("lastVisitedURL");
}

ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
ShoppingCartItem lastAddedItem = null;
context.cartStateData = CartEvents.getCartCookieData(cart, request);
List<ShoppingCartItem> cartItems = cart.items();
for(ShoppingCartItem sci : cartItems) {
	if(!sci.getIsPromo()) {
		lastAddedItem = sci;
		break;
	}
}

if(lastAddedItem != null) {
	Map<String, Object> artworkAttributes = lastAddedItem.getAttribute("envArtworkAttributes");
	Map<String, Object> quoteAttributes = lastAddedItem.getAttribute("envQuoteAttributes");
	Map<String, Object> priceCalcAttributes = lastAddedItem.getAttribute("envPriceCalcAttributes");
	context.product = lastAddedItem.getProduct();
	context.matchingProducts = ProductHelper.getMatchingProducts(delegator, lastAddedItem.getProduct());
	context.artworkAttributes = artworkAttributes;
	context.quoteAttributes = quoteAttributes;
	context.priceCalcAttributes = priceCalcAttributes;
	context.unitPrice = lastAddedItem.getBasePrice();
	String quantity = lastAddedItem.getQuantity();
	String nextQuantity = null;
	BigDecimal nextPrice = null;
	BigDecimal nextPriceDifference = null;

	Map<String, Object> quantityAttributes = lastAddedItem.getAttribute("envQuantityAttributes");
	for(String keyQty : quantityAttributes.keySet()) {
		if(Double.valueOf(keyQty) > Double.valueOf(quantity) && nextQuantity == null) {
			nextQuantity = keyQty;
			nextPrice = quantityAttributes.get(keyQty);
		} else if(Double.valueOf(keyQty) > Double.valueOf(quantity) && Double.valueOf(keyQty) < Double.valueOf(nextQuantity)) {
			nextQuantity = keyQty;
			nextPrice = quantityAttributes.get(keyQty);
		}
	}

	if(nextPrice != null) {
		nextPriceDifference = nextPrice.subtract(lastAddedItem.getItemSubTotal().setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING)).setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING)
	}

	context.quantity = quantity;
	context.nextQuantity = nextQuantity;
	context.nextPriceDifference = nextPriceDifference;
	context.isItemPrinted = cart.isItemPrinted(lastAddedItem);

	if(UtilValidate.isNotEmpty(artworkAttributes.get("scene7ParentId")) && UtilValidate.isNotEmpty(artworkAttributes.get("scene7DesignId")) && UtilValidate.isNotEmpty(artworkAttributes.get("designId"))) {
		GenericValue template = delegator.findOne("Scene7Template", UtilMisc.toMap("scene7TemplateId", (String) artworkAttributes.get("designId")), true);
		context.isDesign = true;
		context.designId = (String) artworkAttributes.get("designId");
		context.template = template;
		context.scene7DesignId = artworkAttributes.get("scene7DesignId");
		context.getMatchingDesigns = Scene7Helper.getMatchingDesigns(delegator, (String) artworkAttributes.get("designId"));
	}
} else {

}
import com.envelopes.cart.CartEvents;
import com.envelopes.promo.PromoHelper;
import com.envelopes.shipping.ShippingEvents
import org.apache.ofbiz.order.shoppingcart.ShoppingCartEvents;

String module = "shipping-options.groovy";

context.shippingOptions = ShippingEvents.getShippingRates(request);
context.getShippingDiscount = PromoHelper.getShippingDiscount(ShoppingCartEvents.getCartObject(request), true);
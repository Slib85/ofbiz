import org.apache.ofbiz.order.shoppingcart.ShoppingCart;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartEvents;
import com.envelopes.util.*;

String module = "payByPayPalButton.groovy";
import org.apache.ofbiz.order.shoppingcart.ShoppingCartEvents;
ShoppingCart cart = ShoppingCartEvents.getCartObject(request);

context.grandTotal = cart.getDisplayGrandTotal().setScale(EnvConstantsUtil.ENV_SCALE, EnvConstantsUtil.ENV_ROUNDING);
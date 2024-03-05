import com.envelopes.cart.CartHelper
import org.apache.ofbiz.order.shoppingcart.ShoppingCart
import org.apache.ofbiz.order.shoppingcart.WebShoppingCart

import javax.servlet.http.HttpSession
import java.util.*;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.GenericValue;
import com.envelopes.cart.CartEvents;
import com.envelopes.order.CheckoutEvents;

String module ="createOrder.groovy";

context.currentYear = Calendar.getInstance().get(Calendar.YEAR);

//reset cart for PPS
ShoppingCart cart = new WebShoppingCart(request, null, null);
HttpSession session = request.getSession();
session.setAttribute("shoppingCart", cart);
CartHelper.setCartDefaults(cart, request, null, null, null);
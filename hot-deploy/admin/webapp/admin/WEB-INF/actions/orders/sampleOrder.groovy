import com.envelopes.cart.CartHelper
import org.apache.ofbiz.order.shoppingcart.ShoppingCart
import org.apache.ofbiz.order.shoppingcart.WebShoppingCart

import javax.servlet.http.HttpSession
import java.util.*;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.GenericValue;
import com.envelopes.cart.CartEvents;
import com.envelopes.order.CheckoutEvents;
import com.envelopes.product.*;

String module ="sampleOrder.groovy";

context.currentYear = Calendar.getInstance().get(Calendar.YEAR);

//reset cart for PPS
ShoppingCart cart = new WebShoppingCart(request, null, null);
HttpSession session = request.getSession();
session.setAttribute("shoppingCart", cart);
CartHelper.setCartDefaults(cart, request, null, null, null);

context.folderCategoryList = FolderProductHelper.getFolderCategoryList(delegator);
context.folderStyleList = FolderProductHelper.getFolderStyleList(delegator, "all");
context.metallicFoilList = FolderProductHelper.getProductFoils(delegator, Boolean.TRUE);
context.nonMetallicFoilList = FolderProductHelper.getProductFoils(delegator, Boolean.FALSE);

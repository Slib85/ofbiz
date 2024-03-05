import java.util.*;
import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntity;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityConditionList;
import org.apache.ofbiz.entity.condition.EntityExpr;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.model.DynamicViewEntity;
import org.apache.ofbiz.entity.model.ModelKeyMap;
import org.apache.ofbiz.entity.transaction.GenericTransactionException;
import org.apache.ofbiz.entity.transaction.TransactionUtil;
import org.apache.ofbiz.entity.util.EntityFindOptions;
import org.apache.ofbiz.entity.util.EntityListIterator;
import org.apache.ofbiz.entity.util.EntityTypeUtil;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.order.order.OrderReadHelper;
import org.apache.ofbiz.order.shoppingcart.ShoppingCart;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartEvents;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartItem;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

import com.envelopes.order.OrderHelper;

String module ="fileTransferCenter.groovy";

List<EntityCondition> conditionList = new ArrayList<>();
conditionList.add(EntityCondition.makeCondition("statusId", EntityOperator.IN, UtilMisc.toList("ART_PROOF_REJECTED", "ART_NEED_NEW_ARTWORK", "ART_UPLOADED_LATER", "ART_NOT_RECEIVED")));
conditionList.add(EntityCondition.makeCondition("orderId", EntityOperator.EQUALS, request.getParameter("orderId")));
List<GenericValue> orderItems = delegator.findList("OrderItem", EntityCondition.makeCondition(conditionList), null, null, null, false);

context.orderItems = orderItems;
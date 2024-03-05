/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.product;

import java.lang.*;
import java.math.BigDecimal;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.jdbc.SQLProcessor;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.LocalDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.envelopes.util.*;

public class ProductReviewEvents {
	public static final String module = ProductReviewEvents.class.getName();


	/*
	 * Approve a review
	 */
	public static String approveReview(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher)request.getAttribute("dispatcher");
		Delegator delegator = (Delegator)request.getAttribute("delegator");

		Map<String, Object> context = EnvUtil.getParameterMap(request);

		String productReviewId = (String) context.remove("id");

		Debug.logError("context: " + context, module);
		if(UtilValidate.isNotEmpty(productReviewId)) {
			try {
				GenericValue review = EntityQuery.use(delegator).from("ProductReview").where("productReviewId", productReviewId).queryOne();
				Iterator contextIter = context.entrySet().iterator();
				while(contextIter.hasNext()) {
					Map.Entry pairs = (Map.Entry) contextIter.next();
					if(((String) pairs.getKey()).equals("productRating")) {
						review.set((String) pairs.getKey(), new BigDecimal((String) pairs.getValue()));
					} else {
						review.set((String) pairs.getKey(), (String) pairs.getValue());
					}
				}
				review.store();
			} catch(GenericEntityException e) {
				EnvUtil.reportError(e);
				Debug.logError("Error while trying to retrieve json data. " + e + " : " + e.getMessage(), module);
			}
		}

		return "success";
	}

	public static List<Map> getTopReviewList(HttpServletRequest request, HttpServletResponse response, ArrayList<String> productIdList) {
		LocalDispatcher dispatcher = (LocalDispatcher)request.getAttribute("dispatcher");
		Delegator delegator = (Delegator)request.getAttribute("delegator");
		BigDecimal productRating = new BigDecimal(5);
		List<Map> reviewList = new ArrayList<Map>();

		try {
			String salesChannelEnumId = EnvUtil.getSalesChannelEnumId((String) request.getSession().getAttribute("webSiteId"));
			String result = null;
			SQLProcessor du = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));
			String sqlCommand = null;
			try {
				String productIds = "";

				if (productIdList != null) {
					for (String productId : productIdList) {
						productIds += (productIds.isEmpty() ? "" : "', '") + productId;
					}
				}

				sqlCommand = "" +
					"SELECT p.product_id, p.primary_product_category_id, p.product_name, cw.color_description, REPLACE(pr.product_review, '\\r', '') AS product_review, pr.created_stamp, pr.nick_name " +
					"FROM product_review pr " +
					"INNER JOIN color_warehouse cw ON pr.product_id = cw.variant_product_id " +
					"INNER JOIN product p ON pr.product_id = p.product_id " +
					"WHERE pr.product_rating = " + productRating + " " +
						"AND pr.status_id = 'PRR_APPROVED'" +
						"AND CHAR_LENGTH(pr.product_review) > 60 " +
						(!productIds.isEmpty() ? "AND pr.product_id IN ('" + productIds + "') " : " ") +
						(UtilValidate.isNotEmpty(salesChannelEnumId) ? "AND pr.sales_channel_enum_id = '" + salesChannelEnumId + "' " : " ") +
					"ORDER BY pr.created_stamp DESC " +
					"LIMIT 10";

				ResultSet rs = null;
				rs = du.executeQuery(sqlCommand);

				if (rs != null) {
					while (rs.next()) {
						Map<String, Object> reviewMap = new HashMap<String, Object>();
						reviewMap.put("productId", rs.getString(1));
						reviewMap.put("primaryProductCategoryId", rs.getString(2));
						reviewMap.put("productName", rs.getString(3));
						reviewMap.put("colorDescription", rs.getString(4));
						reviewMap.put("productReview", rs.getString(5));
						reviewMap.put("createdStamp", rs.getTimestamp(6));
						reviewMap.put("nickName", rs.getString(7));
						reviewList.add(reviewMap);
					}
				}
			} catch (Exception e) {
				Debug.logError(e.getMessage(), module);
			} finally {
				if(du != null) {
					du.close();
				}
			}
		} catch (GenericEntityException e) {
			Debug.logError(e.getMessage(), module);
		}

		return reviewList;
	}
}
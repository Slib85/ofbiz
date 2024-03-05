package com.envelopes.label;

import com.bigname.quote.calculator.CalculatorHelper;
import com.envelopes.http.HTTPHelper;
import com.envelopes.netsuite.NetsuiteHelper;
import com.envelopes.product.ProductHelper;
import com.envelopes.util.EnvConstantsUtil;
import com.google.gson.Gson;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang.*;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.apache.xpath.SourceTree;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericDataSourceException;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.jdbc.SQLProcessor;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Manu on 5/12/2016.
 */
public class LabelPrintHelper {

	public static final String LABEL_FILE_LOCATION = EnvConstantsUtil.OFBIZ_HOME + EnvConstantsUtil.UPLOAD_DIR + "/" + EnvConstantsUtil.PRODUCT_LABEL_DIR + "/";
	public static final String PACK_LABEL_FILE_LOCATION = EnvConstantsUtil.OFBIZ_HOME + EnvConstantsUtil.UPLOAD_DIR + "/" + EnvConstantsUtil.PRODUCT_LABEL_DIR + "/packLabels/";
	public static final String MINI_LABEL_FILE_LOCATION = EnvConstantsUtil.OFBIZ_HOME + EnvConstantsUtil.UPLOAD_DIR + "/" + EnvConstantsUtil.PRODUCT_LABEL_DIR + "/miniLabels/";
	public static final String FOLDER_SAMPLE_LABEL_FILE_LOCATION = EnvConstantsUtil.OFBIZ_HOME + EnvConstantsUtil.UPLOAD_DIR + "/" + EnvConstantsUtil.PRODUCT_LABEL_DIR + "/folderSampleLabels/";
	public static final String ASSETS_LOCATION = LABEL_FILE_LOCATION + "resources/";

	private static final String[] webTokens = {"b1a6fcad-20e3-4dc4-9347-d266fa012bee"};

	protected static Font helvetica32Black = FontFactory.getFont(FontFactory.HELVETICA, 32, Font.NORMAL, BaseColor.BLACK);
	protected static Font helvetica24Black = FontFactory.getFont(FontFactory.HELVETICA, 24, Font.NORMAL, BaseColor.BLACK);
	protected static Font helvetica18Black = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.NORMAL, BaseColor.BLACK);
	protected static Font helvetica16Black = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.NORMAL, BaseColor.BLACK);
	protected static Font helvetica14Black = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.NORMAL, BaseColor.BLACK);
	protected static Font helvetica12Bold = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
	protected static Font helvetica12Black = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
	protected static Font helvetica10Black = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
	protected static Font helvetica10Bold = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
	protected static Font helvetica8Black = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
	protected static Font helvetica8Bold  = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
	protected static Font helvetica9Bold  = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);

//	protected static int[] packQtyArray = {1000, 950, 900, 850, 800, 750, 700, 650, 600, 550, 500, 450, 400, 350, 300, 250, 200, 150, 100, 50, 25, 10, 5 };
	protected static int[] packQtyArray = { 50 };

	public static String checkProductId(Delegator delegator, String productIdWithQty) throws GenericEntityException {
		java.util.List<EntityCondition> conditionList = new ArrayList<>();
		conditionList.add(EntityCondition.makeCondition(UtilMisc.toList(
						EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productIdWithQty),
						EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productIdWithQty.lastIndexOf("-") > -1 ? productIdWithQty.substring(0, productIdWithQty.lastIndexOf("-")) : productIdWithQty)
				), EntityOperator.OR)
		);
		EntityCondition condition = EntityCondition.makeCondition(conditionList, EntityOperator.AND);
		java.util.List<GenericValue> productList = delegator.findList("Product", condition, null, null, null, true);
		if(UtilValidate.isNotEmpty(productList)) {
			if(productList.size() == 1) {
				return productList.get(0).getString("productId");
			} else {
				return productList.get(0).getString("productId").equals(productIdWithQty) ? productList.get(0).getString("productId") : productList.get(1).getString("productId");
			}
		}
		return "";
	}

	public static Object[] checkOrderOrProductId(Delegator delegator, String orderIdOrProductIdWithQty, boolean miniLabel) throws Exception {
		String productId = checkProductId(delegator, orderIdOrProductIdWithQty);
		String orderId;
		if(UtilValidate.isEmpty(productId)) {
			java.util.List<EntityCondition> conditionList = new ArrayList<>();
			conditionList.add(EntityCondition.makeCondition(UtilMisc.toList(
							EntityCondition.makeCondition("orderId", EntityOperator.EQUALS, orderIdOrProductIdWithQty)
					), EntityOperator.OR)
			);
			EntityCondition condition = EntityCondition.makeCondition(conditionList, EntityOperator.AND);
			java.util.List<GenericValue> ordersList = delegator.findList("OrderHeader", condition, null, null, null, true);
			if (UtilValidate.isNotEmpty(ordersList)) {
				orderId = ordersList.get(0).getString("orderId");
				return new Object[]{orderId, "order"};
			} else if(miniLabel) {
				Map<String, Object> channelOrderData = NetsuiteHelper.findChannelOrder(orderIdOrProductIdWithQty.trim());
				if(!channelOrderData.isEmpty()) {
					return new Object[] {channelOrderData, "channelOrder"};
				}
			} else if(orderIdOrProductIdWithQty.startsWith("WO")){
				String workOrderId = orderIdOrProductIdWithQty.lastIndexOf("-") > -1 ? orderIdOrProductIdWithQty.substring(0, orderIdOrProductIdWithQty.lastIndexOf("-")) : orderIdOrProductIdWithQty;
				productId = NetsuiteHelper.getWorkOrderItem(workOrderId.trim());
				if(UtilValidate.isNotEmpty(productId)) {
					return new Object[] {productId, "workOrder"};
				}
			}
		} else {
			return new Object[]{productId, "product"};
		}
		return new Object[0];
	}

	public static int getPackQty(String productId, String productIdWithQty) {
		if(productId.trim().equalsIgnoreCase(productIdWithQty.trim())) {
			return 0;
		} else {
			String packQty = productIdWithQty.replace(productId, "").replaceAll("-", "");
			return NumberUtils.toInt(packQty, -1);
		}
	}

	/**
	 * Method used to fetch the product attributes needed to generate the Label for the given productId
	 */
	protected static Map<String, String> getLabelAttributesForProduct(Delegator delegator, String productId) throws GenericEntityException, SQLException {
		Map<String, String> attributes = new HashMap<>();
		SQLProcessor sqlProcessor = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));

		String sqlCommand = "SELECT " +
				"    product_id, " +
				"    product_name, " +
				"    small_image_url AS header_image, " +
				"    bin_location, " +
				"    box_qty, " +
				"    carton_qty " +
				"FROM " +
				"    product " +
				"WHERE " +
				"    product_id = '" + productId + "'";

		ResultSet rs = null;
		try {
			rs = sqlProcessor.executeQuery(sqlCommand);
			if (rs != null) {
				if (rs.next()) {
					String value = null;
					attributes.put("PRODUCT_SKU", 	 UtilValidate.isNotEmpty(value = rs.getString("product_id")) 	? value : "");
					attributes.put("PRODUCT_NAME", 	 UtilValidate.isNotEmpty(value = rs.getString("product_name")) 	? value : "");
					attributes.put("HEADER_IMAGE",   UtilValidate.isNotEmpty(value = rs.getString("header_image"))  ? value : "envelope_header.jpg");
					attributes.put("BIN_LOCATION", 	 UtilValidate.isNotEmpty(value = rs.getString("bin_location")) 	? value : "");
					attributes.put("BOX_QTY", 	 	 UtilValidate.isNotEmpty(value = rs.getString("box_qty")) 		? value.replaceAll(" PER BOX", "") : "");
					attributes.put("CARTON_QTY", 	 UtilValidate.isNotEmpty(value = rs.getString("carton_qty")) 	? value : "");
				}
				attributes.put("PRODUCT_IMAGE", productId);
			}


		sqlCommand = "SELECT " +
				"    pf.product_feature_type_id, description " +
				"FROM " +
				"    product_feature_appl pfa " +
				"        INNER JOIN " +
				"    product_feature pf ON pfa.product_feature_id = pf.product_feature_id " +
				"WHERE " +
				"    product_feature_type_id IN ('COLOR' , 'SIZE', 'PAPER_WEIGHT', 'SEALING_METHOD') " +
				"        AND product_id = '" + productId + "'";

			rs = sqlProcessor.executeQuery(sqlCommand);
			if (rs != null) {
				while (rs.next()) {
					String value = null;
					if(rs.getString("product_feature_type_id").equalsIgnoreCase("COLOR")) {
						attributes.put("PRODUCT_COLOR",  UtilValidate.isNotEmpty(value = rs.getString("description")) ? value : "");
					} else if(rs.getString("product_feature_type_id").equalsIgnoreCase("SIZE")) {
						attributes.put("PRODUCT_SIZE",  UtilValidate.isNotEmpty(value = rs.getString("description")) ? value : "");
					} else if(rs.getString("product_feature_type_id").equalsIgnoreCase("PAPER_WEIGHT")) {
						attributes.put("PRODUCT_WEIGHT",  UtilValidate.isNotEmpty(value = rs.getString("description")) ? value : "");
					} else if(rs.getString("product_feature_type_id").equalsIgnoreCase("SEALING_METHOD")) {
						attributes.put("PEEL_AND_PRESS", UtilValidate.isNotEmpty(value = rs.getString("description"))&& value.toLowerCase().contains("peel") && value.toLowerCase().contains("press") ? "Y" : "N");
					}
				}
			}

			sqlCommand = "SELECT " +
					"    name," +
					"    image_name," +
					"    size," +
					"    color_name," +
					"    weight," +
					"    box_qty," +
					"    carton_qty," +
					"    peel_and_press," +
					"    header_name," +
					"    bin," +
					"    name_font_size," +
					"    size_font_size," +
					"    color_font_size," +
					"    weight_font_size " +
					"FROM " +
					"    product_label " +
					"WHERE " +
					"    product_id = '" + productId + "'";
			rs = sqlProcessor.executeQuery(sqlCommand);
			if(rs != null) {
				while(rs.next()) {
					String value = null;
					if(UtilValidate.isNotEmpty(value = rs.getString("name"))) {
						attributes.put("PRODUCT_NAME", 	 UtilValidate.isNotEmpty(value = rs.getString("name")) ? value : "");
					}
					if(UtilValidate.isNotEmpty(value = rs.getString("image_name"))) {
						attributes.put("PRODUCT_IMAGE", 	 UtilValidate.isNotEmpty(value = rs.getString("image_name")) ? value : "");
					}
					if(UtilValidate.isNotEmpty(value = rs.getString("size"))) {
						attributes.put("PRODUCT_SIZE", 	 UtilValidate.isNotEmpty(value = rs.getString("size")) && !value.equals("EMPTY") ? value : "");
					}
					if(UtilValidate.isNotEmpty(value = rs.getString("color_name"))) {
						attributes.put("PRODUCT_COLOR", 	 UtilValidate.isNotEmpty(value = rs.getString("color_name")) && !value.equals("EMPTY") ? value : "");
					}
					if(UtilValidate.isNotEmpty(value = rs.getString("weight"))) {
						attributes.put("PRODUCT_WEIGHT", 	 UtilValidate.isNotEmpty(value = rs.getString("weight")) && !value.equals("EMPTY") ? value : "");
					}
					if(UtilValidate.isNotEmpty(value = rs.getString("box_qty"))) {
						attributes.put("BOX_QTY", 	 UtilValidate.isNotEmpty(value = rs.getString("box_qty")) && !value.equals("EMPTY") ? value.replaceAll(" PER BOX", "") : "");
					}
					if(UtilValidate.isNotEmpty(value = rs.getString("carton_qty"))) {
						attributes.put("CARTON_QTY", 	 UtilValidate.isNotEmpty(value = rs.getString("carton_qty")) && !value.equals("EMPTY") ? value : "");
					}
					if(UtilValidate.isNotEmpty(value = rs.getString("peel_and_press"))) {
						attributes.put("PEEL_AND_PRESS", 	 UtilValidate.isNotEmpty(value = rs.getString("peel_and_press")) ? value : "N");
					}
					if(UtilValidate.isNotEmpty(value = rs.getString("header_name"))) {
						attributes.put("HEADER_IMAGE", 	 UtilValidate.isNotEmpty(value = rs.getString("header_name")) ? value : "");
					}
					if(UtilValidate.isNotEmpty(value = rs.getString("bin"))) {
						attributes.put("BIN_LOCATION", 	 UtilValidate.isNotEmpty(value = rs.getString("bin")) && !value.equals("EMPTY") ? value : "");
					}
					if(UtilValidate.isNotEmpty(value = rs.getString("name_font_size"))) {
						attributes.put("NAME_FONT_SIZE", 	 UtilValidate.isNotEmpty(value = rs.getString("name_font_size")) ? value : "0");
					}
					if(UtilValidate.isNotEmpty(value = rs.getString("size_font_size"))) {
						attributes.put("SIZE_FONT_SIZE", 	 UtilValidate.isNotEmpty(value = rs.getString("size_font_size")) ? value : "0");
					}
					if(UtilValidate.isNotEmpty(value = rs.getString("color_font_size"))) {
						attributes.put("COLOR_FONT_SIZE", 	 UtilValidate.isNotEmpty(value = rs.getString("color_font_size")) ? value : "0");
					}
					if(UtilValidate.isNotEmpty(value = rs.getString("weight_font_size"))) {
						attributes.put("WEIGHT_FONT_SIZE", 	 UtilValidate.isNotEmpty(value = rs.getString("weight_font_size")) ? value : "0");
					}
				}
			}
		} finally {
				sqlProcessor.close();
		}
		return attributes;
	}

	protected static Map<String, String> getOrderItemQtyMap(List orderItems) {
		Map<String, String> orderItemQtyMap = new LinkedHashMap<>();
		for(Object orderItemObj : orderItems) {
			Map orderItem = (Map) orderItemObj;
			String sku = (String) orderItem.get("sku");
			if(orderItemQtyMap.containsKey(sku)) {
				orderItemQtyMap.put(sku, Integer.toString(NumberUtils.toInt(orderItemQtyMap.get("qty")) + NumberUtils.toInt((String)orderItem.get("quantity"))));
			} else {
				orderItemQtyMap.put(sku, (String)orderItem.get("quantity"));
			}
		}
		return orderItemQtyMap;
	}

	protected static String getProductIdInClauseValues(Map<String, String> orderItemQtyMap) {
		StringBuilder builder = new StringBuilder();
		for (Map.Entry<String, String> orderItemEntry : orderItemQtyMap.entrySet()) {
			builder.append(builder.length() == 0 ? "'" : ",'").append(orderItemEntry.getKey()).append("'");
		}

		return builder.toString();
	}

	protected static List<Map<String, String>> getLabelAttributesForChannelOrder(Delegator delegator, Map orderData) throws GenericEntityException, SQLException {
		List<Map<String, String>> attributesList = new ArrayList<>();
		Map<String, String> orderItemQtyMap = getOrderItemQtyMap((List)orderData.get("items"));
		SQLProcessor sqlProcessor = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));

		String sqlCommand1 = "SELECT  " +
				"    product_id, " +
				"    product_name, " +
				"    small_image_url AS header_image, " +
				"    bin_location, " +
				"    box_qty, " +
				"    carton_qty " +
				"FROM " +
				"    product " +
				"WHERE " +
				"    product_id in (" + getProductIdInClauseValues(orderItemQtyMap) + ")";



		try {
			ResultSet rs1 = sqlProcessor.executeQuery(sqlCommand1);
			if (rs1 != null) {
				while (rs1.next()) {
					Map<String, String> attributes = new HashMap<>();
					String value = null;
					attributes.put("PRODUCT_SKU", 	 UtilValidate.isNotEmpty(value = rs1.getString("product_id")) 		? value : "");
					String productId = value;
					attributes.put("ORDER_ITEM_QTY", UtilValidate.isNotEmpty(value = orderItemQtyMap.get(productId))	? NumberUtils.toInt(value, 0) <=5 ? "SAMPLES" : value : "");
					attributes.put("PRODUCT_NAME", 	 UtilValidate.isNotEmpty(value = rs1.getString("product_name")) 	? value : "");
					attributes.put("HEADER_IMAGE",   UtilValidate.isNotEmpty(value = rs1.getString("header_image"))  	? value : "envelope_header.jpg");
					attributes.put("BIN_LOCATION", 	 UtilValidate.isNotEmpty(value = rs1.getString("bin_location")) 	? value : "");
					attributes.put("BOX_QTY", 	 	 UtilValidate.isNotEmpty(value = rs1.getString("box_qty")) 			? value.replaceAll(" PER BOX", "") : "");
					attributes.put("CARTON_QTY", 	 UtilValidate.isNotEmpty(value = rs1.getString("carton_qty")) 		? value : "");
					attributes.put("PRODUCT_IMAGE",  productId);

					String sqlCommand = "SELECT " +
							"    pf.product_feature_type_id, description " +
							"FROM " +
							"    product_feature_appl pfa " +
							"        INNER JOIN " +
							"    product_feature pf ON pfa.product_feature_id = pf.product_feature_id " +
							"WHERE " +
							"    product_feature_type_id IN ('COLOR' , 'SIZE', 'PAPER_WEIGHT', 'SEALING_METHOD') " +
							"        AND product_id = '" + productId + "'";

					ResultSet rs = sqlProcessor.executeQuery(sqlCommand);
					if (rs != null) {
						while (rs.next()) {
							value = null;
							if(rs.getString("product_feature_type_id").equalsIgnoreCase("COLOR")) {
								attributes.put("PRODUCT_COLOR",  UtilValidate.isNotEmpty(value = rs.getString("description")) ? value : "");
							} else if(rs.getString("product_feature_type_id").equalsIgnoreCase("SIZE")) {
								attributes.put("PRODUCT_SIZE",  UtilValidate.isNotEmpty(value = rs.getString("description")) ? value : "");
							} else if(rs.getString("product_feature_type_id").equalsIgnoreCase("PAPER_WEIGHT")) {
								attributes.put("PRODUCT_WEIGHT",  UtilValidate.isNotEmpty(value = rs.getString("description")) ? value : "");
							} else if(rs.getString("product_feature_type_id").equalsIgnoreCase("SEALING_METHOD")) {
								attributes.put("PEEL_AND_PRESS", UtilValidate.isNotEmpty(value = rs.getString("description"))&& value.toLowerCase().contains("peel") && value.toLowerCase().contains("press") ? "Y" : "N");
							}
						}
					}

					sqlCommand = "SELECT " +
							"    name," +
							"    image_name," +
							"    size," +
							"    color_name," +
							"    weight," +
							"    box_qty," +
							"    carton_qty," +
							"    peel_and_press," +
							"    header_name," +
							"    bin," +
							"    name_font_size," +
							"    size_font_size," +
							"    color_font_size," +
							"    weight_font_size " +
							"FROM " +
							"    product_label " +
							"WHERE " +
							"    product_id = '" + productId + "'";
					rs = sqlProcessor.executeQuery(sqlCommand);
					if(rs != null) {
						while(rs.next()) {
							value = null;
							if(UtilValidate.isNotEmpty(value = rs.getString("name"))) {
								attributes.put("PRODUCT_NAME", 	 UtilValidate.isNotEmpty(value = rs.getString("name")) ? value : "");
							}
							if(UtilValidate.isNotEmpty(value = rs.getString("image_name"))) {
								attributes.put("PRODUCT_IMAGE", 	 UtilValidate.isNotEmpty(value = rs.getString("image_name")) ? value : "");
							}
							if(UtilValidate.isNotEmpty(value = rs.getString("size"))) {
								attributes.put("PRODUCT_SIZE", 	 UtilValidate.isNotEmpty(value = rs.getString("size")) && !value.equals("EMPTY") ? value : "");
							}
							if(UtilValidate.isNotEmpty(value = rs.getString("color_name"))) {
								attributes.put("PRODUCT_COLOR", 	 UtilValidate.isNotEmpty(value = rs.getString("color_name")) && !value.equals("EMPTY") ? value : "");
							}
							if(UtilValidate.isNotEmpty(value = rs.getString("weight"))) {
								attributes.put("PRODUCT_WEIGHT", 	 UtilValidate.isNotEmpty(value = rs.getString("weight")) && !value.equals("EMPTY") ? value : "");
							}
							if(UtilValidate.isNotEmpty(value = rs.getString("box_qty"))) {
								attributes.put("BOX_QTY", 	 UtilValidate.isNotEmpty(value = rs.getString("box_qty")) && !value.equals("EMPTY") ? value.replaceAll(" PER BOX", "") : "");
							}
							if(UtilValidate.isNotEmpty(value = rs.getString("carton_qty"))) {
								attributes.put("CARTON_QTY", 	 UtilValidate.isNotEmpty(value = rs.getString("carton_qty")) && !value.equals("EMPTY") ? value : "");
							}
							if(UtilValidate.isNotEmpty(value = rs.getString("peel_and_press"))) {
								attributes.put("PEEL_AND_PRESS", 	 UtilValidate.isNotEmpty(value = rs.getString("peel_and_press")) ? value : "N");
							}
							if(UtilValidate.isNotEmpty(value = rs.getString("header_name"))) {
								attributes.put("HEADER_IMAGE", 	 UtilValidate.isNotEmpty(value = rs.getString("header_name")) ? value : "");
							}
							if(UtilValidate.isNotEmpty(value = rs.getString("bin"))) {
								attributes.put("BIN_LOCATION", 	 UtilValidate.isNotEmpty(value = rs.getString("bin")) && !value.equals("EMPTY") ? value : "");
							}
							if(UtilValidate.isNotEmpty(value = rs.getString("name_font_size"))) {
								attributes.put("NAME_FONT_SIZE", 	 UtilValidate.isNotEmpty(value = rs.getString("name_font_size")) ? value : "0");
							}
							if(UtilValidate.isNotEmpty(value = rs.getString("size_font_size"))) {
								attributes.put("SIZE_FONT_SIZE", 	 UtilValidate.isNotEmpty(value = rs.getString("size_font_size")) ? value : "0");
							}
							if(UtilValidate.isNotEmpty(value = rs.getString("color_font_size"))) {
								attributes.put("COLOR_FONT_SIZE", 	 UtilValidate.isNotEmpty(value = rs.getString("color_font_size")) ? value : "0");
							}
							if(UtilValidate.isNotEmpty(value = rs.getString("weight_font_size"))) {
								attributes.put("WEIGHT_FONT_SIZE", 	 UtilValidate.isNotEmpty(value = rs.getString("weight_font_size")) ? value : "0");
							}
						}
					}
					attributesList.add(attributes);
				}
			}
		} finally {
			sqlProcessor.close();
		}
		return attributesList;
	}

	protected static List<Map<String, String>> getLabelAttributesForOrder(Delegator delegator, String orderId) throws GenericEntityException, SQLException {
		List<Map<String, String>> attributesList = new ArrayList<>();
		SQLProcessor sqlProcessor = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));

		String sqlCommand1 = "SELECT  " +
				"    p.product_id, " +
				"    FLOOR(oi.quantity) as qty, " +
				"    p.product_name, " +
				"    p.small_image_url AS header_image, " +
				"    p.bin_location, " +
				"    p.box_qty, " +
				"    p.carton_qty " +
				"FROM " +
				"    product p " +
				"        INNER JOIN " +
				"    order_item oi ON oi.product_id = p.product_id " +
				"WHERE " +
				"    oi.order_id = '" + orderId + "'";



		try {
			ResultSet rs1 = sqlProcessor.executeQuery(sqlCommand1);
			if (rs1 != null) {
				while (rs1.next()) {
					Map<String, String> attributes = new HashMap<>();
					String value = null;
					attributes.put("PRODUCT_SKU", 	 UtilValidate.isNotEmpty(value = rs1.getString("product_id")) 		? value : "");
					String productId = value;
					attributes.put("ORDER_ITEM_QTY", UtilValidate.isNotEmpty(value = rs1.getString("qty")) 				? NumberUtils.toInt(value, 0) <=5 ? "SAMPLES" : value : "");
					attributes.put("PRODUCT_NAME", 	 UtilValidate.isNotEmpty(value = rs1.getString("product_name")) 	? value : "");
					attributes.put("HEADER_IMAGE",   UtilValidate.isNotEmpty(value = rs1.getString("header_image"))  	? value : "envelope_header.jpg");
					attributes.put("BIN_LOCATION", 	 UtilValidate.isNotEmpty(value = rs1.getString("bin_location")) 	? value : "");
					attributes.put("BOX_QTY", 	 	 UtilValidate.isNotEmpty(value = rs1.getString("box_qty")) 			? value.replaceAll(" PER BOX", "") : "");
					attributes.put("CARTON_QTY", 	 UtilValidate.isNotEmpty(value = rs1.getString("carton_qty")) 		? value : "");
					attributes.put("PRODUCT_IMAGE",  productId);

					String sqlCommand = "SELECT " +
							"    pf.product_feature_type_id, description " +
							"FROM " +
							"    product_feature_appl pfa " +
							"        INNER JOIN " +
							"    product_feature pf ON pfa.product_feature_id = pf.product_feature_id " +
							"WHERE " +
							"    product_feature_type_id IN ('COLOR' , 'SIZE', 'PAPER_WEIGHT', 'SEALING_METHOD') " +
							"        AND product_id = '" + productId + "'";

					ResultSet rs = sqlProcessor.executeQuery(sqlCommand);
					if (rs != null) {
						while (rs.next()) {
							value = null;
							if(rs.getString("product_feature_type_id").equalsIgnoreCase("COLOR")) {
								attributes.put("PRODUCT_COLOR",  UtilValidate.isNotEmpty(value = rs.getString("description")) ? value : "");
							} else if(rs.getString("product_feature_type_id").equalsIgnoreCase("SIZE")) {
								attributes.put("PRODUCT_SIZE",  UtilValidate.isNotEmpty(value = rs.getString("description")) ? value : "");
							} else if(rs.getString("product_feature_type_id").equalsIgnoreCase("PAPER_WEIGHT")) {
								attributes.put("PRODUCT_WEIGHT",  UtilValidate.isNotEmpty(value = rs.getString("description")) ? value : "");
							} else if(rs.getString("product_feature_type_id").equalsIgnoreCase("SEALING_METHOD")) {
								attributes.put("PEEL_AND_PRESS", UtilValidate.isNotEmpty(value = rs.getString("description"))&& value.toLowerCase().contains("peel") && value.toLowerCase().contains("press") ? "Y" : "N");
							}
						}
					}

					sqlCommand = "SELECT " +
							"    name," +
							"    image_name," +
							"    size," +
							"    color_name," +
							"    weight," +
							"    box_qty," +
							"    carton_qty," +
							"    peel_and_press," +
							"    header_name," +
							"    bin," +
							"    name_font_size," +
							"    size_font_size," +
							"    color_font_size," +
							"    weight_font_size " +
							"FROM " +
							"    product_label " +
							"WHERE " +
							"    product_id = '" + productId + "'";
					rs = sqlProcessor.executeQuery(sqlCommand);
					if(rs != null) {
						while(rs.next()) {
							value = null;
							if(UtilValidate.isNotEmpty(value = rs.getString("name"))) {
								attributes.put("PRODUCT_NAME", 	 UtilValidate.isNotEmpty(value = rs.getString("name")) ? value : "");
							}
							if(UtilValidate.isNotEmpty(value = rs.getString("image_name"))) {
								attributes.put("PRODUCT_IMAGE", 	 UtilValidate.isNotEmpty(value = rs.getString("image_name")) ? value : "");
							}
							if(UtilValidate.isNotEmpty(value = rs.getString("size"))) {
								attributes.put("PRODUCT_SIZE", 	 UtilValidate.isNotEmpty(value = rs.getString("size")) && !value.equals("EMPTY") ? value : "");
							}
							if(UtilValidate.isNotEmpty(value = rs.getString("color_name"))) {
								attributes.put("PRODUCT_COLOR", 	 UtilValidate.isNotEmpty(value = rs.getString("color_name")) && !value.equals("EMPTY") ? value : "");
							}
							if(UtilValidate.isNotEmpty(value = rs.getString("weight"))) {
								attributes.put("PRODUCT_WEIGHT", 	 UtilValidate.isNotEmpty(value = rs.getString("weight")) && !value.equals("EMPTY") ? value : "");
							}
							if(UtilValidate.isNotEmpty(value = rs.getString("box_qty"))) {
								attributes.put("BOX_QTY", 	 UtilValidate.isNotEmpty(value = rs.getString("box_qty")) && !value.equals("EMPTY") ? value.replaceAll(" PER BOX", "") : "");
							}
							if(UtilValidate.isNotEmpty(value = rs.getString("carton_qty"))) {
								attributes.put("CARTON_QTY", 	 UtilValidate.isNotEmpty(value = rs.getString("carton_qty")) && !value.equals("EMPTY") ? value : "");
							}
							if(UtilValidate.isNotEmpty(value = rs.getString("peel_and_press"))) {
								attributes.put("PEEL_AND_PRESS", 	 UtilValidate.isNotEmpty(value = rs.getString("peel_and_press")) ? value : "N");
							}
							if(UtilValidate.isNotEmpty(value = rs.getString("header_name"))) {
								attributes.put("HEADER_IMAGE", 	 UtilValidate.isNotEmpty(value = rs.getString("header_name")) ? value : "");
							}
							if(UtilValidate.isNotEmpty(value = rs.getString("bin"))) {
								attributes.put("BIN_LOCATION", 	 UtilValidate.isNotEmpty(value = rs.getString("bin")) && !value.equals("EMPTY") ? value : "");
							}
							if(UtilValidate.isNotEmpty(value = rs.getString("name_font_size"))) {
								attributes.put("NAME_FONT_SIZE", 	 UtilValidate.isNotEmpty(value = rs.getString("name_font_size")) ? value : "0");
							}
							if(UtilValidate.isNotEmpty(value = rs.getString("size_font_size"))) {
								attributes.put("SIZE_FONT_SIZE", 	 UtilValidate.isNotEmpty(value = rs.getString("size_font_size")) ? value : "0");
							}
							if(UtilValidate.isNotEmpty(value = rs.getString("color_font_size"))) {
								attributes.put("COLOR_FONT_SIZE", 	 UtilValidate.isNotEmpty(value = rs.getString("color_font_size")) ? value : "0");
							}
							if(UtilValidate.isNotEmpty(value = rs.getString("weight_font_size"))) {
								attributes.put("WEIGHT_FONT_SIZE", 	 UtilValidate.isNotEmpty(value = rs.getString("weight_font_size")) ? value : "0");
							}
						}
					}
					attributesList.add(attributes);
				}
			}
		} finally {
			sqlProcessor.close();
		}
		return attributesList;
	}

	public static boolean invalidateLabel(String productId) {
		boolean success = false;
		File pdfFile = new File(LABEL_FILE_LOCATION + productId + ".pdf");
		File pngFile = new File(LABEL_FILE_LOCATION + productId + ".png");
		if(pdfFile.exists()) {
			success = pdfFile.delete();
			if(pngFile.exists()) {
				success = pngFile.delete();
			}
			File packLabelFolder = new File(PACK_LABEL_FILE_LOCATION);
			FileFilter fileFilter = new WildcardFileFilter(productId + "-*.*");
			File[] packLabels = packLabelFolder.listFiles(fileFilter);
			for(int i = 0; i < packLabels.length; i ++) {
				success = packLabels[i].delete();
			}

			File miniLabelFolder = new File(MINI_LABEL_FILE_LOCATION);
			fileFilter = new WildcardFileFilter(productId + "-*.*");
			File[] miniLabels = miniLabelFolder.listFiles(fileFilter);
			for(int i = 0; i < miniLabels.length; i ++) {
				success = miniLabels[i].delete();
			}
		} else {
			success = true;
		}
		return success;
	}

	public static boolean invalidateJobOrderLabel(String jobNumber) {

		boolean success = true;

		File dir = new File(FOLDER_SAMPLE_LABEL_FILE_LOCATION);

		File[] files = dir.listFiles((dir1, name) -> name.startsWith(jobNumber + "_"));
		for (File file : files) {
			success =  success && file.delete();
		}
		return success;
	}

	public static boolean hasAccess(String webToken) {
		if(UtilValidate.isNotEmpty(webToken)) {
			for(int i = 0; i < webTokens.length; i ++) {
				if(webToken.trim().toLowerCase().equals(webTokens[i])) {
					return true;
				}
			}
		}
		return false;
	}

	public static List<Map<String, String>> getLabelData(Delegator delegator, String orderOrProductIdWithQty, boolean miniLabel, boolean... rebuildFlag) throws Exception {
		OrderLabel orderLabel = getOrderLabel(delegator, orderOrProductIdWithQty, miniLabel, rebuildFlag);
		List<Map<String, String>> labelsData = new ArrayList<>();
		if(orderLabel != null) {
			for (ProductLabel productLabel : orderLabel.getLabels()) {
				Map<String, String> labelData = new HashMap<>();
				labelData.put("productId", productLabel.getProductId());
				labelData.put("productIdWithQty", productLabel.getProductIdWithQty());
				labelData.put("labelQty", productLabel.getLabelQty());
				labelData.put("copies", Integer.toString(productLabel.getLabelCopies()));
				labelData.put("labelPath", productLabel.getRelativeLabelPath() + (rebuildFlag != null && rebuildFlag.length == 1 && rebuildFlag[0] ? "&ts=" + System.currentTimeMillis() : ""));
				labelData.put("labelPDFPath", productLabel.getRelativeLabelPDFPath() + (rebuildFlag != null && rebuildFlag.length == 1 && rebuildFlag[0] ? "&ts=" + System.currentTimeMillis() : ""));
				labelData.put("lastModified", Long.toString(productLabel.getLastModified()));
				labelsData.add(labelData);
			}
		}
		return labelsData;
	}

	public static List<Map<String, String>> getFolderSampleLabelData(Delegator delegator, LocalDispatcher dispatcher, String jobNumber, boolean... rebuildFlag) throws Exception {
		JobOrderLabel orderLabel = getFolderSampleLabel(delegator, dispatcher, jobNumber, rebuildFlag);
		List<Map<String, String>> labelsData = new ArrayList<>();
		if(orderLabel != null) {
			for (FolderSampleLabel sampleLabel : orderLabel.getLabels()) {
				Map<String, String> labelData = new HashMap<>();
				labelData.put("jobNumber", sampleLabel.getJobNumber());
				labelData.put("copies", Integer.toString(sampleLabel.getLabelCopies()));
				labelData.put("labelPath", sampleLabel.getRelativeLabelPath() + (rebuildFlag != null && rebuildFlag.length == 1 && rebuildFlag[0] ? "&ts=" + System.currentTimeMillis() : ""));
				labelData.put("labelPDFPath", sampleLabel.getRelativeLabelPDFPath() + (rebuildFlag != null && rebuildFlag.length == 1 && rebuildFlag[0] ? "&ts=" + System.currentTimeMillis() : ""));
				labelData.put("lastModified", Long.toString(sampleLabel.getLastModified()));
				labelsData.add(labelData);
			}
		}
		return labelsData;
	}

	protected static OrderLabel getOrderLabel(Delegator delegator, String orderOrProductIdWithQty, boolean miniLabel, boolean... rebuildFlag) throws Exception {

		/**
		 * Check the given id to see if it is a valid ORDER ID, WORK_ORDER_ID or PRODUCT ID. The LabelPrintHelper.checkOrderOrProductId() method returns a 2 element object array, where first element
		 * is the valid ORDER ID, CHANNEL_ORDER_DATA or PRODUCT ID and the second element will tell if its an ORDER_ID, CHANNEL_ORDER, WORK_ORDER or a PRODUCT_ID. eg: ["ENV12345678", "order_id"] or ["A7FFW-B", "product_id"]. If the
		 * given id is an invalid ID, then the LabelPrintHelper.checkOrderOrProductId() method will return an empty array.
		 */
		Object[] idData = LabelPrintHelper.checkOrderOrProductId(delegator, orderOrProductIdWithQty.trim(), miniLabel);

		/**If the passed in ID is invalid, exit method by returning the empty dataMap */
		if(idData.length == 0) {
			return null;
		}

		boolean isChannelOrder = idData[1].equals("channelOrder");
		boolean isWorkOrder = idData[1].equals("workOrder");
		String workOrderId = isWorkOrder ? orderOrProductIdWithQty.contains("-") ? orderOrProductIdWithQty.substring(0, orderOrProductIdWithQty.lastIndexOf("-")) : orderOrProductIdWithQty : "";

		/**The OrderLabel object to hold one or more product labels. Even if we are printing a single product label using the PRODUCT_ID, we still use the Order Label object to store it */
		OrderLabel orderLabel;

		/**The valid ORDER_ID or PRODUCT_ID */
		String id = isChannelOrder ? orderOrProductIdWithQty : (String)idData[0];

		/** Boolean flag to show if the given ID is a PRODUCT_ID or ORDER_ID. If the given ID is an ORDER_ID, then this flag will be false. Otherwise, this will be true */
		boolean isProduct = idData[1].equals("product") || isWorkOrder;

		/** Initialize the orderLabel object with the given ORDER_ID, if the given ID is an ORDER_ID. Otherwise, initialize the orderLabel object with empty ORDER_ID */
		orderLabel = new OrderLabel(isProduct ? "" : id, miniLabel);
//		data.put(isProduct ? "PRODUCT_ID" : "ORDER_ID", id);

		/** The list to hold the label attributes for each PRODUCT_ID, if the given ID is an ORDER_ID. Otherwise, this list will hold the label attributes for the given PRODUCT_ID */
		List<Map<String, String>> labelAttributesList = new ArrayList<>();

		/** If the given ID is PRODUCT_ID, get the label attributes for the product label corresponding to the given PRODUCT_ID and add the label attributes to the labelAttributesList */
		if(isProduct) {
			labelAttributesList.add(getLabelAttributesForProduct(delegator, id));
		}
		/** If the given ID is an ORDER_ID, the getAttributesForOrder() method will return the list of labelAttributes containing the labelAttributes for each product labels in the given ORDER_ID */
		else if(isChannelOrder) {
			labelAttributesList = getLabelAttributesForChannelOrder(delegator, (Map)idData[0]);
		} else {
			labelAttributesList = getLabelAttributesForOrder(delegator, id);
		}

		/** Loop through each productLabel's attributes in the labelAttributesList*/
		for(Map<String, String> labelAttributes : labelAttributesList) {

//			Map<String, String> labelData = new HashMap<>();
			String boxQty = labelAttributes.get("BOX_QTY");
			String cartonQty = labelAttributes.get("CARTON_QTY");
			String orderItemQuantity = !isProduct ? labelAttributes.get("ORDER_ITEM_QTY") : "";

			boolean packLabel = false;
			boolean cartonLabel = false;
			boolean samplesFlag = false;

			/** The PRODUCT_ID with the QTY */
			String productIdWithQty;

			/**	The PRODUCT_ID of the current productLabel */
			String productId = labelAttributes.get("PRODUCT_SKU");

			/** If the given ID is PRODUCT_ID , then give PRODUCT_ID will contain the QTY. If the given ID is WORK_ORDER_ID, then we need to add the qty to the productId received from Netsuite. The validated PRODUCT_ID will be in UPPERCASE, so convert the given PRODUCT_ID with QTY to UPPERCASE as well */
			if(isProduct) {
				if(!isWorkOrder) {
					productIdWithQty = orderOrProductIdWithQty.toUpperCase();
				} else {
					productIdWithQty = id;
					if(orderOrProductIdWithQty.contains("-")) {
						id += orderOrProductIdWithQty.substring(orderOrProductIdWithQty.lastIndexOf("-")).trim();
					}
				}
			}
			/** If the given ID is an ORDER_ID, then use the ORDER_ITEM_QTY as the LABEL_QTY*/
			else {
				productIdWithQty = productId + "-" + orderItemQuantity;
			}

			/**
			 * The QTY for the current productLabel. This is the quantity the user wants to generate the label for. If the QTY is equal to BOX_QTY, then a BOX_LABEL will be generated, if its equal to the
			 * CARTON_QTY, then a CARTON_LABEL will be generated. If its less than BOX_QTY, a PACK_LABEL will be generated. If its greater than BOX_QTY and less than CARTON_QTY, still a BOX_LABEL will be
			 * generated. If the given ID is an ORDER_ID, the productIdWithQty variable will always contain the QTY as a suffix to the PRODUCT_ID, separated by a hyphen (-). But if the given ID is a
			 * PRODUCT_ID, the productIdWithQty may not contain the QTY suffix. In that case the BOX_QTY or CARTON_QTY(if BOX_QTY is not available) will be used as the default QTY
			 **/
			String qty;

			/**
			 * The QTY used on the label as the SUFFIX of the SKU. This can be the BOX_QTY or CARTON_QTY or PACK_QTY
			 */
			String labelQty;

			/** The number of copies of labels need to be printed based. by default this is 1, but if the required QTY or ITEM_QTY is greater than BOX_QTY, this can be more than one*/
			int labelCopies = 1;

			/** Try to find the QTY, by replacing the PRODUCT_ID followed by hyphen from the productIdWithQty variable */
			if(isWorkOrder) {
				qty = orderOrProductIdWithQty.replace(workOrderId, "").replaceAll("-", "").trim();
			} else {
				qty = productIdWithQty.replace(productId, "").replaceAll("-", "").trim();
			}
			/** If QTY is empty or QTY is invalid and NOT SAMPLES, use BOX_QTY or CARTON_QTY(if BOX_QTY is empty) as the QTY */
			if (qty.isEmpty() || NumberUtils.toInt(qty, 0) <= 0) {
				if(!qty.equals("SAMPLES")) {
					if (!boxQty.isEmpty()) {
						qty = boxQty;
					} else if (!cartonQty.isEmpty()) {
						qty = cartonQty;
					}
				}
			}

			/** Set the QTY as the LABEL_QTY */
			labelQty = qty;

			/** For mini labels, we won't consider carton or box quantities, instead the matching packQty corresponding to the item qty will be found from the packQty array and update the number of copies, if possible. */
			if(miniLabel) {
				int qtyInt = NumberUtils.toInt(qty, 0);
				/** Do this qty match only when mini labels are first generated for a given ORDER ID, not during updating the qty of an already rendered label */
				if(!isProduct) {
					for (int i = 0; i < packQtyArray.length; i++) {
						if (qtyInt >= packQtyArray[i] && qtyInt % packQtyArray[i] == 0) {
							labelQty = Integer.toString(packQtyArray[i]);
							labelCopies = qtyInt / packQtyArray[i];
							packLabel = true;
							break;
						}
					}
				}

				/** If we can't find a matching pack qty from the packQty array, use the given qty as the pack qty and set the number of copies to 1*/
				if (!packLabel) {
					labelQty = qty;
					labelCopies = 1;
					packLabel = true;
				}
			} else {
				/** If QTY is not empty, find the LABEL_QTY */
				if (!qty.isEmpty()) {
					/** If the QTY is not empty and is equal to SAMPLES, set the samplesFlag to TRUE */
					if (qty.equalsIgnoreCase("SAMPLES")) {
						samplesFlag = true;
						packLabel = true;
					}
					/** If not empty and not SAMPLES, and QTY equals BOX_QTY, set the BOX_QTY as the LABEL_QTY */
					else if (qty.equals(boxQty)) {
						labelQty = boxQty;
					}
					/** If not empty, not SAMPLES and not BOX_QTY, and QTY equals CARTON_QTY, set the CARTON_QTY as the LABEL_QTY */
					else if (qty.equals(cartonQty)) {
						labelQty = cartonQty;
						cartonLabel = true;
						if (!boxQty.isEmpty()) {
							packLabel = true;
						}
					}
					/** If not empty, not SAMPLES, not BOX_QTY and not CARTON_QTY, find LABEL_QTY based on QTY and the PRODUCT's BOX_QTY and CARTON_QTY */
					else {
						//QTY
						int qtyInt = NumberUtils.toInt(qty, 0);

						//BOX_QTY
						int boxQtyInt = NumberUtils.toInt(boxQty, 0);

						//CARTON_QTY
						int cartonQtyInt = NumberUtils.toInt(cartonQty, 0);

						//BOX_QTY or CARTON_QTY(if BOX_QTY is 0)
						int boxOrCartonQty = boxQtyInt > 0 ? boxQtyInt : cartonQtyInt;

						/** If the is valid and is not equal to SAMPLES, not equal to BOX_QTY and not equal to CARTON_QTY */
						if (qtyInt > 0) {
							/** If the QTY is multiples of CARTON_QTY, set CARTON_QTY as the LABEL_QTY and set the number of copies */
							if (cartonQtyInt > 0 && qtyInt > cartonQtyInt && qtyInt % cartonQtyInt == 0) {
								labelQty = cartonQty;
								cartonLabel = true;
								labelCopies = qtyInt / cartonQtyInt;
								if (boxQtyInt > 0) {
									packLabel = true;
								}
							}
							/** If the QTY is not multiples of CARTON_QTY, but multiples of BOX QTY, set BOX_QTY as the LABEL_QTY and set the number of copies */
							else if (boxQtyInt > 0 && qtyInt > boxQtyInt && qtyInt % boxQtyInt == 0) {
								labelQty = boxQty;
								labelCopies = qtyInt / boxQtyInt;
							}
							/** If the QTY is not multiples of CARTON_QTY and not multiples of BOX QTY, set the given qty as the matching LABEL_QTY and set the copies to one */
							else {
								labelQty = qty;
								labelCopies = 1;
								packLabel = true;
							}
						}
					}
				}
			}

			ProductLabel productLabel = new ProductLabel(productId, miniLabel, workOrderId);

			productLabel.setPackLabel(!miniLabel && packLabel);

			productLabel.setSamplesFlag(samplesFlag);

			productLabel.setLabelQty(labelQty);

			productLabel.setDefaultLabel(cartonLabel || labelQty.equals(boxQty) || labelQty.isEmpty());

			productLabel.setLabelCopies(labelCopies);

			if(isWorkOrder) {
				labelAttributes.put("WORK_ORDER_ID", workOrderId);
			}

			productLabel.setAttributes(labelAttributes);

			if(rebuildFlag != null && rebuildFlag.length == 1 && rebuildFlag[0]) {
				invalidateLabel(productId);
			}

			if(!new File(productLabel.getLabelPDFPath()).exists()) {
				if(productLabel.isMiniLabel()) {
					createMiniLabel(productLabel);
				} else {
					createLabel(productLabel);
				}
				convertToImage(packLabel || miniLabel ? productLabel.isWorkOrder()? productLabel.getWorkOrderIdWithQty() : productLabel.getProductIdWithQty() : productLabel.isWorkOrder()? productLabel.getWorkOrderId() : productLabel.getProductId(), productLabel.isPackLabel(), productLabel.isMiniLabel());
			}
			productLabel.setLastModified(new File(productLabel.getLabelPDFPath()).lastModified());

			orderLabel.getLabels().add(productLabel);

		}

		return orderLabel;
	}

	public static Map<String, Object> getJobData(Delegator delegator, LocalDispatcher dispatcher, String jobNumber) throws Exception {
		Map<String, Object> labelDataFromBOS1 = getLabelDataFromBOS1(delegator, jobNumber);
		if((boolean)labelDataFromBOS1.get("valid")) {
			return labelDataFromBOS1;
		} else {
			Map<String, Object> labelDataFromBOS2 = getLabelDataFromBOS2(delegator, jobNumber);
			if((boolean)labelDataFromBOS2.get("valid")) {
				return labelDataFromBOS2;
			} else {
				Map<String, Object> labelDataFromNetSuite = getLabelDataFromNetSuite(delegator, dispatcher, jobNumber);
				if ((boolean) labelDataFromNetSuite.get("valid")) {
					return labelDataFromNetSuite;
				}
			}
		}
		return null;
	}

	private static Map<String, Object> getLabelDataFromBOS1(Delegator delegator, String jobNumber) throws Exception {

		Map<String, Object> dataObject = new HashMap<>();
		GenericValue labelsData = EntityQuery.use(delegator).from("SampleFolderLabel").where(UtilMisc.toMap("jobNumber", jobNumber)).queryOne();

		boolean valid = false;
		if(labelsData != null && UtilValidate.isNotEmpty(labelsData.get("labelsData"))) {

//			dataObject.put("jobData", new Gson().fromJson(labelsData.getString("jobData"), Map.class));
			dataObject.put("jobDataString", labelsData.getString("jobData"));

			dataObject.put("labelsData", new Gson().fromJson(labelsData.getString("labelsData"), Map.class));
			dataObject.put("labelsDataString", labelsData.getString("labelsData"));

			if(UtilValidate.isNotEmpty(labelsData.get("jobDataOverride"))) {
				dataObject.put("jobDataOverrideString", labelsData.getString("jobDataOverride"));
			}
			valid = true;
		}

		dataObject.put("source", "bos");
		dataObject.put("valid", valid);
		return dataObject;
	}

	private static Map<String, Object> getLabelDataFromBOS2(Delegator delegator, String jobNumber) throws Exception {

		Map<String, Object> dataObject = new HashMap<>();
		GenericValue labelsData = EntityQuery.use(delegator).from("SampleFolderLabel").where(UtilMisc.toMap("jobNumber", jobNumber)).queryOne();

		boolean valid = false;
		if(labelsData != null && UtilValidate.isNotEmpty(labelsData.get("jobDataOverride"))) {
			String jobDataOverrideString = labelsData.getString("jobDataOverride");
			Map<String, Object> jobDataOverride = new Gson().fromJson(jobDataOverrideString, Map.class);
			Map<String, Object> _labelsData = generateLabelsDataFromJobDetails(delegator, jobNumber, jobDataOverride);

//			dataObject.put("jobData", new Gson().fromJson(labelsData.getString("jobData"), Map.class));
			dataObject.put("jobDataString", labelsData.getString("jobData"));

			dataObject.put("labelsData", _labelsData);
			dataObject.put("labelsDataString", new Gson().toJson(_labelsData));

			dataObject.put("jobDataOverrideString", jobDataOverrideString);
			valid = true;
		}

		dataObject.put("source", "bos");
		dataObject.put("valid", valid);
		return dataObject;
	}


	private static Map<String, Object> getLabelDataFromNetSuite(Delegator delegator, LocalDispatcher dispatcher, String jobNumber) throws Exception {
		Map<String, Object> dataObject = new HashMap<>();

		Map<String, Object> jobDetails = NetsuiteHelper.findPO(delegator, dispatcher, UtilMisc.<String, Object>toMap("purchaseOrderId", jobNumber));
		if((boolean)jobDetails.get("success")) {
			dataObject.put("valid", true);
			dataObject.put("source", "ns");
			dataObject.put("jobData", jobDetails);
			dataObject.put("jobDataString", new Gson().toJson(jobDetails));
			dataObject.put("labelsData", generateLabelsDataFromJobDetails(delegator, jobNumber, jobDetails));
		} else {
			dataObject.put("valid", false);
		}
		return dataObject;
	}

	private static Map<String, Object> generateLabelsDataFromJobDetails(Delegator delegator, String jobNumber, Map<String, Object> jobDetails) {

		List<Map<String, Object>> labelsData = new ArrayList<>();

		List<Map<String, Object>> items = (List<Map<String, Object>>) jobDetails.get("items");
		if(items != null && !items.isEmpty()) {
			for (Map<String, Object> item : items) {
				if (item.containsKey("name")) {
					Map<String, Object> labelData = convertJobDataToLabelData(delegator, jobNumber, (String) item.get("name"));
					labelsData.add(labelData);
				} else {
					Map<String, Object> labelData = convertJobDataToLabelData(delegator, jobNumber, "");
					labelsData.add(labelData);
				}
			}
		}

		Map<String, Object> result = new HashMap<>();
		result.put("LABELS_DATA", labelsData);

		return result;
	}

	private static Map<String, Object> convertJobDataToLabelData(Delegator delegator, String jobNumber, String jobData) {
		Map<String, Object> labelData = new LinkedHashMap<>();
		labelData.put("JOB_NUMBER", jobNumber);
		labelData.putAll(getLabelAttributesFromJobData(delegator, jobData));
		return labelData;
	}

	public static boolean overrideJobData(Delegator delegator, Map<String, Object> context) throws GenericEntityException {
		String jobNumber = (String) context.get("jobNumber");
		String jobDataOriginal = (String) context.get("jobDataOriginal");
		String jobDataOverride = (String) context.get("jobDataOverride");

		GenericValue labelsData = EntityQuery.use(delegator).from("SampleFolderLabel").where(UtilMisc.toMap("jobNumber", jobNumber)).queryOne();
		if(labelsData == null) {
			labelsData = delegator.makeValue("SampleFolderLabel", "jobNumber", jobNumber, "jobData", jobDataOriginal);
		}
		labelsData.put("jobDataOverride", jobDataOverride);
		delegator.createOrStore(labelsData);

		return invalidateJobOrderLabel(jobNumber);
	}

	public static boolean overrideLabelData(Delegator delegator, LocalDispatcher dispatcher, Map<String, Object> context) throws Exception {
		String jobNumber = (String) context.get("jobNumber");
		int idx = Integer.parseInt((String) context.get("idx"));
		String style = (String) context.get("style");
		String name = (String) context.get("name");
		String stock = (String) context.get("stock");
		String printMethod1 = (String) context.get("printMethod1");
		String printMethod2 = (String) context.get("printMethod2");
		String printMethod3 = (String) context.get("printMethod3");
		String coatings = (String) context.get("coatings");

		Map<String, Object> jobData = getJobData(delegator, dispatcher, jobNumber);

		String jobDataOriginal = (String) jobData.get("jobDataString");

		Map<String, Object> labelsDataObject = (Map<String, Object>)jobData.get("labelsData");

		List<Map<String, Object>> labelsDataList = (List<Map<String, Object>>)labelsDataObject.get("LABELS_DATA");

		Map<String, Object> labelData = labelsDataList.get(idx);

		labelData.put("STYLE", style);
		labelData.put("NAME", name);
		labelData.put("STOCK", stock);
		labelData.put("PRINT_METHOD_1", printMethod1);
		labelData.put("PRINT_METHOD_2", printMethod2);
		labelData.put("PRINT_METHOD_3", printMethod3);
		labelData.put("COATINGS", coatings);
		labelData.put("valid", UtilValidate.isNotEmpty(style) && UtilValidate.isNotEmpty(stock) && UtilValidate.isNotEmpty(name) && (UtilValidate.isNotEmpty(printMethod1) || UtilValidate.isNotEmpty(printMethod2) || UtilValidate.isNotEmpty(printMethod3)));

		String labelsDataString = new Gson().toJson(labelsDataObject);
		GenericValue labelsData = EntityQuery.use(delegator).from("SampleFolderLabel").where(UtilMisc.toMap("jobNumber", jobNumber)).queryOne();
		if(labelsData == null) {
			labelsData = delegator.makeValue("SampleFolderLabel", "jobNumber", jobNumber, "jobData", jobDataOriginal);
		}
		labelsData.put("labelsData", labelsDataString);
		delegator.createOrStore(labelsData);
		return invalidateJobOrderLabel(jobNumber);
	}

	public static String getFolderName(Delegator delegator, String styleId) throws GenericEntityException {
		if(UtilValidate.isEmpty(styleId)) {
			return "";
		}
		String name = "";
		List<String> styleIds = tokenize(styleId, '-');

		List<GenericValue> styles = EntityQuery.use(delegator).from("QcStyle").where(EntityCondition.makeCondition("styleId", EntityOperator.IN, styleIds)).queryList();
		if(styles != null) {
			styles.sort((o1, o2) -> o1.getString("styleId").length() > o2.getString("style").length() ? 1 : -1);
			GenericValue style = styles.isEmpty() ? null : styles.get(0);
			if(style != null) {
				if(UtilValidate.isNotEmpty(style.get("styleShortName"))) {
					name = style.getString("styleShortName");
				} else {
					name = style.getString("styleName");
				}
			}
		}

		return name;
	}

	private static List<String> tokenize(String text, char delim, List<String>... tokens) {
		List<String> _tokens = tokens == null || tokens.length == 0 ? new ArrayList<>() : tokens[0];
		String _delim = Character.toString(delim);
		if(text != null) {
			text = text.trim();
			if(text.endsWith(_delim)) {
				text = text.substring(0, text.length() - 1);
			}
			if(!text.isEmpty()) {
				_tokens.add(text);
			}
			if(text.contains(_delim)) {
				tokenize(text.substring(0, text.lastIndexOf(_delim)), delim, _tokens);
			}
		}
		return _tokens;
	}

	protected static JobOrderLabel getFolderSampleLabel(Delegator delegator, LocalDispatcher dispatcher, String jobNumber, boolean... rebuildFlag) throws Exception {
		Map<String, Object> jobData = getJobData(delegator, dispatcher, jobNumber);
		JobOrderLabel orderLabel = null;
		if(UtilValidate.isNotEmpty(jobData)) {
			List<Map<String, Object>> labelsData = (List<Map<String, Object>>)((Map<String, Object>)jobData.get("labelsData")).get("LABELS_DATA");
			int idx = 0;
			for (Map<String, Object> labelData : labelsData) {
				if(labelData.containsKey("valid") && (boolean) labelData.get("valid")) {
					if(orderLabel == null) {
						orderLabel = new JobOrderLabel(jobNumber);
					}
					String labelName = jobNumber + "_" + idx + "_" + labelData.get("STYLE");
					FolderSampleLabel sampleLabel = new FolderSampleLabel(labelName.trim());
					Map<String, String> labelAttributes = new HashMap<>();
					labelData.forEach((key, value) -> {if (value instanceof String) labelAttributes.put(key, (String) value);});
					sampleLabel.setAttributes(labelAttributes);
					sampleLabel.setLabelCopies(1);
					if(rebuildFlag != null && rebuildFlag.length == 1 && rebuildFlag[0]) {
							invalidateJobOrderLabel(jobNumber);
					}
					if(!new File(sampleLabel.getLabelPDFPath()).exists()) {
						createFolderSampleLabel(sampleLabel);
						convertToImage(sampleLabel.getJobNumber());
					}
					sampleLabel.setLastModified(new File(sampleLabel.getLabelPDFPath()).lastModified());

					orderLabel.getLabels().add(sampleLabel);
					idx ++;
				}
			}

		}
		return orderLabel;
	}

	protected static final String stylePatternString = "(?i)STYLE (.*?)( |:)";
	protected static final String stockPatternString = "(?i)Paper stock: (.*)";
	protected static final String printMethodPatternString = "(?i)[Second |Third ]?Print Method Required: (.*)";
	protected static final String foilStampingPatternString = "(?i)Foil Stamping(.*)+-(.*)";
	protected static final String coatingPatternString = "(?i)Coating: (.*)";
	protected static final Pattern stylePattern  = Pattern.compile(stylePatternString);
	protected static final Pattern stockPattern  = Pattern.compile(stockPatternString);
	protected static final Pattern printMethodPattern = Pattern.compile(printMethodPatternString);
	protected static final Pattern foilStampPattern = Pattern.compile(foilStampingPatternString);
	protected static final Pattern coatingPattern = Pattern.compile(coatingPatternString);

	public static Map<String, Object> getLabelAttributesFromJobData(Delegator delegator, String text) {
		String style = "", stock = "", printMethod1a = "", printMethod1b = "", printMethod2 = "", printMethod3 = "", coating = "";
		while(text.contains("\n\n")) {
			text = text.replaceAll("\n\n", "\n");
		}
		StringTokenizer tokenizer = new StringTokenizer(text, "\n");
		while(tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if(style.isEmpty() && token.toLowerCase().contains("style")) {
				Matcher matcher = stylePattern.matcher(token);
				if(matcher.find()) {
					style = matcher.group(1).trim();
					continue;
				}
			}
			if(stock.isEmpty() && token.toLowerCase().contains("stock")) {
				Matcher matcher = stockPattern.matcher(token);
				if(matcher.find()) {
					stock = matcher.group(1).trim();
					continue;
				}
			}
			if(printMethod1a.isEmpty() && token.toLowerCase().contains("print method")) {
				Matcher matcher = printMethodPattern.matcher(token);
				if(matcher.find()) {
					String printMethod = matcher.group(1).trim();
					if(printMethod.toLowerCase().contains("offset")) {
						printMethod1a = printMethod.trim();
						continue;
					}
					if(printMethod.toLowerCase().contains("foil stamping")) {
						Matcher matcher1 =  foilStampPattern.matcher(printMethod);
						if(matcher1.find()) {
							printMethod2 = "Foil Stamping - " + matcher1.group(2).trim();
							continue;
						}
					}
				}
			}
			if(!printMethod1a.isEmpty() && printMethod1b.isEmpty() && !token.contains(":") && token.toLowerCase().contains("side 2")) {
				printMethod1b = token.trim();
				continue;
			}

			if(printMethod2.isEmpty() && token.toLowerCase().contains("print method")) {
				Matcher matcher = printMethodPattern.matcher(token);
				if(matcher.find()) {
					String printMethod = matcher.group(1).trim();
					if(printMethod.toLowerCase().contains("foil stamping")) {
						Matcher matcher1 =  foilStampPattern.matcher(printMethod);
						if(matcher1.find()) {
							printMethod2 = "Foil Stamping - " + matcher1.group(2).trim();
							continue;
						}
					}
				}
			}

			if(coating.isEmpty() && token.toLowerCase().contains("coating")) {
				Matcher matcher = coatingPattern.matcher(token);
				if(matcher.find()) {
					coating = matcher.group(1).trim();
					continue;
				}
			}

		}
		Map<String, Object> attributes = new LinkedHashMap<>();
		boolean valid = true;
		String printMethod1 = "";
		String name = "";
		if(UtilValidate.isEmpty(style)) {
			style = "";
			valid = false;
		} else {
			try {
				GenericValue styleGV = EntityQuery.use(delegator).from("QcStyle").where(UtilMisc.toMap("styleId", style)).queryOne();
				if(UtilValidate.isNotEmpty(styleGV)) {
					if(UtilValidate.isNotEmpty(styleGV.get("styleShortName"))) {
						name = styleGV.getString("styleShortName");
					} else {
						name = styleGV.getString("styleName");
					}
				}
			} catch (GenericEntityException ignore) {
				// Ignore
			}
		}

		if(UtilValidate.isEmpty(name)) {
			name = "";
			valid = false;
		}

		if(UtilValidate.isEmpty(stock)) {
			stock = "";
			valid = false;
		}

		if(UtilValidate.isNotEmpty(printMethod1a)) {
			printMethod1 = printMethod1a;
			if(UtilValidate.isNotEmpty(printMethod1b)) {
				printMethod1 = printMethod1 + "," + printMethod1b;
			}
		}

		if(UtilValidate.isEmpty(printMethod1)) {
			printMethod1 = "";
		}

		if(UtilValidate.isEmpty(printMethod2)) {
			printMethod2 = "";
		}

		if(UtilValidate.isEmpty(printMethod3)) {
			printMethod3 = "";
		}

		if(UtilValidate.isEmpty(printMethod1) && UtilValidate.isEmpty(printMethod2) && UtilValidate.isEmpty(printMethod3)) {
			valid = false;
		}


		attributes.put("STYLE", style);
		attributes.put("STOCK", stock);
		attributes.put("NAME", name);
		attributes.put("PRINT_METHOD_1", printMethod1a);
		attributes.put("PRINT_METHOD_1B", printMethod1b);
		attributes.put("PRINT_METHOD_2", printMethod2);
		attributes.put("PRINT_METHOD_3", printMethod3);
		attributes.put("COATINGS", coating);
		attributes.put("valid", valid);
		return attributes;
	}

	protected static void createMiniLabel(ProductLabel productLabel) throws Exception {
		Rectangle labelSize = new Rectangle(216f, 144f);
		Document document = new Document(labelSize, 0f, 0f, 0f, 0f);
		FileOutputStream fos = new FileOutputStream(new File(productLabel.getLabelPDFPath()));
		PdfWriter writer = PdfWriter.getInstance(document, fos);
		document.open();

		PdfContentByte canvas = writer.getDirectContent();

		Map<String, String> labelAttributes = productLabel.getAttributes();
		String name = ProductHelper.formatName(labelAttributes.get("PRODUCT_NAME"));
		if(UtilValidate.isNotEmpty(labelAttributes.get("PRODUCT_SIZE")) && !name.contains(labelAttributes.get("PRODUCT_SIZE").trim())) {
			name += " (" + labelAttributes.get("PRODUCT_SIZE").trim() + ")";
		}
		String color =  labelAttributes.get("PRODUCT_COLOR");
		String sku = productLabel.isSamplesFlag() ? "SAMPLE" : productLabel.getProductIdWithQty();
		String barcodeSKU = productLabel.isSamplesFlag() ? productLabel.getProductId() : productLabel.getProductIdWithQty();
		String qty = (productLabel.isSamplesFlag() ? "Samples" : "Qty " + productLabel.getLabelQty());

		Barcode128 skuCode = new Barcode128();
		skuCode.setCode(barcodeSKU);
		skuCode.setFont(null);
		Image skuCodeImage = skuCode.createImageWithBarcode(writer.getDirectContent(), null, null);
		skuCodeImage.setAbsolutePosition(10, 114);
		skuCodeImage.scaleAbsoluteHeight(25);
		skuCodeImage.scaleAbsoluteWidth(120);
		document.add(skuCodeImage);

		ColumnText ct = new ColumnText(canvas);


		Phrase productName = new Phrase(name, helvetica10Bold);
		ct.setSimpleColumn(productName, 10, 70, 200, 115, 0, Element.ALIGN_LEFT);
		ct.setLeading(3f,0.9f);
		ct.setUseAscender(false);
		ct.setText(productName);
		ct.go();

		Phrase productColor = new Phrase(color, helvetica9Bold);
		ct.setSimpleColumn(productName, 10, 50, 200, 65, 0, Element.ALIGN_LEFT);
		ct.setLeading(3f,0.9f);
		ct.setUseAscender(false);
		ct.setText(productColor);
		ct.go();

		Phrase productSKU = new Phrase(sku, helvetica9Bold);
		ct.setSimpleColumn(productSKU, 10, 40, 200, 25, 0, Element.ALIGN_LEFT);
		ct.setLeading(3f,0.9f);
		ct.setUseAscender(false);
		ct.setText(productSKU);
		ct.go();

		Phrase productQty = new Phrase(qty, helvetica9Bold);
		ct.setSimpleColumn(productSKU, 10, 25, 200, 10, 0, Element.ALIGN_LEFT);
		ct.setLeading(3f,0.9f);
		ct.setUseAscender(false);
		ct.setText(productQty);
		ct.go();

		document.close();

	}

	protected static void createLabel(ProductLabel productLabel) throws Exception {
		Rectangle labelSize = new Rectangle(360f, 220f);
		Document document = new Document(labelSize, 0f, 0f, 0f, 0f);
		FileOutputStream fos = new FileOutputStream((new File(productLabel.getLabelPDFPath())));
		PdfWriter writer = PdfWriter.getInstance(document, fos);
		document.open();

		PdfContentByte canvas = writer.getDirectContent();
		Map<String, String> labelAttributes = productLabel.getAttributes();
		Image headerImage = Image.getInstance(ASSETS_LOCATION + labelAttributes.get("HEADER_IMAGE"));
		headerImage.scaleToFit(368, 41);
		headerImage.setAbsolutePosition(0, 180);
		document.add(headerImage);

		ColumnText ct = new ColumnText(canvas);
		String productNameStr = ProductHelper.formatName(labelAttributes.get("PRODUCT_NAME"));
		float nameFontSize;
		boolean multiLineName;

		float fontSize;

		if(UtilValidate.isNotEmpty(labelAttributes.get("NAME_FONT_SIZE")) && (fontSize = new Float(labelAttributes.get("NAME_FONT_SIZE"))) > 0) {
			nameFontSize = fontSize;
			multiLineName = UtilValidate.isNotEmpty(labelAttributes.get("MULTI_LINE_NAME")) && labelAttributes.get("MULTI_LINE_NAME").equalsIgnoreCase("Y");
		} else {
			float[] sizeAndLines = getOptimalFontSize(productNameStr);
			nameFontSize = sizeAndLines[0];
			float lines = sizeAndLines[1];
			multiLineName = lines > 1;
		}
		Font nameFont = FontFactory.getFont(FontFactory.HELVETICA, nameFontSize, Font.NORMAL, BaseColor.BLACK);
		Phrase productName = new Phrase(productNameStr, nameFont);
		ct.setSimpleColumn(productName, 10, 100, 200, 155, 0, Element.ALIGN_LEFT);
		ct.setLeading(0,0.9f);
		ct.setUseAscender(multiLineName);
		ct.setText(productName);
		ct.go();

		Font sizeFont = helvetica14Black;
		if(UtilValidate.isNotEmpty(labelAttributes.get("SIZE_FONT_SIZE")) && (fontSize = new Float(labelAttributes.get("SIZE_FONT_SIZE"))) > 0) {
			sizeFont = FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL, BaseColor.BLACK);

		}

		if(UtilValidate.isNotEmpty(labelAttributes.get("PRODUCT_SIZE")) && !productNameStr.contains(labelAttributes.get("PRODUCT_SIZE").trim())) {
			Phrase productSize = new Phrase("(" + labelAttributes.get("PRODUCT_SIZE") + ")", sizeFont);
			ct.setSimpleColumn(productSize, 10, 50, 255, multiLineName ? nameFontSize <= 22 ? 105 : 95 : 115, 10, Element.ALIGN_LEFT);
			ct.go();
		}

		if(UtilValidate.isNotEmpty(labelAttributes.get("PRODUCT_WEIGHT")) && labelAttributes.get("PRODUCT_COLOR").contains(labelAttributes.get("PRODUCT_WEIGHT"))) {
			labelAttributes.put("PRODUCT_COLOR", labelAttributes.get("PRODUCT_COLOR").replace(labelAttributes.get("PRODUCT_WEIGHT"), "").trim());
		}

		Font colorFont = FontFactory.getFont(FontFactory.HELVETICA, findFontSize(labelAttributes.get("PRODUCT_COLOR"), new Rectangle(10, 55, 190, 70), 18, 14), Font.NORMAL, BaseColor.BLACK);
		if(UtilValidate.isNotEmpty(labelAttributes.get("COLOR_FONT_SIZE")) && (fontSize = new Float(labelAttributes.get("COLOR_FONT_SIZE"))) > 0) {
			colorFont = FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL, BaseColor.BLACK);
		}

		Phrase productColor = new Phrase(labelAttributes.get("PRODUCT_COLOR"), colorFont);
		ct.setSimpleColumn(productColor, 10, 50, 255, 70, 10, Element.ALIGN_LEFT);
		ct.go();

		Font weightFont = FontFactory.getFont(FontFactory.HELVETICA, findFontSize(labelAttributes.get("PRODUCT_WEIGHT"), new Rectangle(10, 25, 60, 45), 14, 10), Font.NORMAL, BaseColor.BLACK);
		if(UtilValidate.isNotEmpty(labelAttributes.get("WEIGHT_FONT_SIZE")) && (fontSize = new Float(labelAttributes.get("SIZE_FONT_SIZE"))) > 0) {
			weightFont = FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL, BaseColor.BLACK);
		}

		if(UtilValidate.isNotEmpty(labelAttributes.get("PRODUCT_WEIGHT")) && !labelAttributes.get("PRODUCT_COLOR").contains(labelAttributes.get("PRODUCT_WEIGHT"))) {
			Phrase productWeight = new Phrase(labelAttributes.get("PRODUCT_WEIGHT"), weightFont);
			ct.setSimpleColumn(productWeight, 10, 20, 255, 40, 10, Element.ALIGN_LEFT);
			ct.go();
		}

		if(UtilValidate.isNotEmpty(labelAttributes.get("WORK_ORDER_ID"))) {
			Phrase productBin = new Phrase(labelAttributes.get("WORK_ORDER_ID"), helvetica8Black);
			ct.setSimpleColumn(productBin, 12, 15, 55, 1, 10, Element.ALIGN_LEFT);
			ct.go();
		}


		PdfPTable table = new PdfPTable(1);
		PdfPCell cell;
		table.setTotalWidth(new float[] {80f});
		boolean hasBoxQty = false;

		if(!productLabel.isDefaultLabel()) {
			if(productLabel.isSamplesFlag()) {
				cell = new PdfPCell(new Phrase("SAMPLES", helvetica8Bold));
			} else {
				cell = new PdfPCell(new Phrase(productLabel.getLabelQty() + " PER PACK", helvetica8Bold));
			}
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			table.writeSelectedRows(0, 1, 0, 2, 70, 35, canvas);
		} else {
			if (UtilValidate.isNotEmpty(labelAttributes.get("BOX_QTY"))) {
				cell = new PdfPCell(new Phrase(labelAttributes.get("BOX_QTY") + " PER BOX", helvetica8Bold));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				hasBoxQty = true;
			}

			if (UtilValidate.isNotEmpty(labelAttributes.get("CARTON_QTY"))) {
				cell = new PdfPCell(new Phrase(labelAttributes.get("CARTON_QTY") + "/CT", helvetica8Bold));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				hasBoxQty = true;
			}

			if (hasBoxQty) {
				table.writeSelectedRows(0, 1, 0, 2, 70, 35, canvas);
			}
		}

		try {
			Image productImage = Image.getInstance(new URL("https://actionenvelope2.scene7.com/is/image/ActionEnvelope/" + labelAttributes.get("PRODUCT_IMAGE") + "?wid=175&hei=100&fmt=jpeg"));
			productImage.scaleToFit(94f, 100f);
			productImage.setAbsolutePosition(228, 100);
			document.add(productImage);
		} catch (Exception e) {
			System.out.println("Missing Image - " + labelAttributes.get("PRODUCT_IMAGE"));
		}
		if(UtilValidate.isNotEmpty(labelAttributes.get("PEEL_AND_PRESS")) && labelAttributes.get("PEEL_AND_PRESS").equalsIgnoreCase("Y")) {
			Image peelAndPressImage = Image.getInstance(ASSETS_LOCATION + "PEEL_PRESS.jpg");
			peelAndPressImage.scaleToFit(110, 15);
			peelAndPressImage.setAbsolutePosition(250, 80);
			document.add(peelAndPressImage);
		}


		Barcode128 skuCode = new Barcode128();
		skuCode.setCode(productLabel.getProductIdWithQty());
		Image skuCodeImage = skuCode.createImageWithBarcode(writer.getDirectContent(), null, null);
		skuCodeImage.setAbsolutePosition(200, 10);
		skuCodeImage.scaleAbsoluteHeight(30);
		skuCodeImage.scaleAbsoluteWidth(150);
		document.add(skuCodeImage);

		if(UtilValidate.isNotEmpty(labelAttributes.get("BIN_LOCATION")) && !labelAttributes.get("BIN_LOCATION").equalsIgnoreCase("MADETOORDER")) {
			Barcode128 binCode = new Barcode128();
			binCode.setCode(labelAttributes.get("BIN_LOCATION"));
			Image binCodeImage = binCode.createImageWithBarcode(writer.getDirectContent(), null, null);
			binCodeImage.setAbsolutePosition(200, 45);
			binCodeImage.scaleAbsoluteHeight(30);
			binCodeImage.scaleAbsoluteWidth(150);
			document.add(binCodeImage);
		}

		document.close();

	}

	protected static void createFolderSampleLabel(FolderSampleLabel samplelabel) throws Exception {
		Rectangle labelSize = new Rectangle(288f, 216f);
		Document document = new Document(labelSize, 0f, 0f, 0f, 0f);
        FileOutputStream fos = new FileOutputStream(new File(samplelabel.getLabelPDFPath()));
		PdfWriter writer = PdfWriter.getInstance(document, fos);
		document.open();
		float padding = 10;
		PdfContentByte canvas = writer.getDirectContent();

        Map<String, String> labelAttributes = samplelabel.getAttributes();
		String name = labelAttributes.get("NAME");

		ColumnText ct = new ColumnText(canvas);

		Phrase jobNumber = new Phrase("Job# " + (samplelabel.getJobNumber().indexOf("_") > -1 ? samplelabel.getJobNumber().substring(0, samplelabel.getJobNumber().indexOf("_")) : samplelabel.getJobNumber()), helvetica10Bold);
		ct.setSimpleColumn(jobNumber, 10, 100 - padding, 200, 145 - padding, 0, Element.ALIGN_LEFT);
		ct.setLeading(3f,0.9f);
		ct.setUseAscender(false);
		ct.setText(jobNumber);
		ct.go();

		Phrase style = new Phrase("Style: " + labelAttributes.get("STYLE"), helvetica10Bold);
		ct.setSimpleColumn(style, 100, 100 - padding, 275, 145 - padding, 0, Element.ALIGN_RIGHT);
		ct.setLeading(3f,0.9f);
		ct.setUseAscender(false);
		ct.setText(style);
		ct.go();


		Phrase productName = new Phrase(name, helvetica12Bold);
		ct.setSimpleColumn(productName, 10, 100 - padding, 275, 130 - padding, 0, Element.ALIGN_LEFT);
		ct.setLeading(3f,0.9f);
		ct.setUseAscender(false);
		ct.setText(productName);
		ct.go();

		Phrase paper = new Phrase("Paper: " + labelAttributes.get("STOCK"), helvetica9Bold);
		ct.setSimpleColumn(paper, 10, 80 - padding, 275, 100 - padding, 0, Element.ALIGN_LEFT);
		ct.setLeading(3f,0.9f);
		ct.setUseAscender(false);
		ct.setText(paper);
		ct.go();

		Phrase printMethodLabel = new Phrase("Print Method:", helvetica9Bold);
		ct.setSimpleColumn(paper, 10, 70 - padding, 275, 85 - padding, 0, Element.ALIGN_LEFT);
		ct.setLeading(3f,0.9f);
		ct.setUseAscender(false);
		ct.setText(printMethodLabel);
		ct.go();

		String printMethod1 = labelAttributes.get("PRINT_METHOD_1"), printMethod1aText = "", printMethod1bText = "";
		if(printMethod1.contains(",")) {
			String[] tokens = printMethod1.split(",");
			printMethod1aText = tokens[0].trim();
			if(!tokens[1].trim().isEmpty()) {
				printMethod1bText = tokens[1].trim();
			}
		} else {
			printMethod1aText = printMethod1.trim();
		}

		Phrase printMethod1a = new Phrase(printMethod1aText, helvetica9Bold);
		ct.setSimpleColumn(paper, 70, 70 - padding, 275, 85 - padding, 0, Element.ALIGN_LEFT);
		ct.setLeading(3f,0.9f);
		ct.setUseAscender(false);
		ct.setText(printMethod1a);
		ct.go();
		float offset = 1;

		if(!printMethod1bText.isEmpty()) {
			Phrase printMethod1b = new Phrase(printMethod1bText, helvetica9Bold);
			ct.setSimpleColumn(paper, 70, 70 - (offset * 15) - padding, 275, 85 - (offset * 10) - padding, 0, Element.ALIGN_LEFT);
			ct.setLeading(3f, 0.9f);
			ct.setUseAscender(false);
			ct.setText(printMethod1b);
			ct.go();
			offset = offset + 1;
		}

		if(!labelAttributes.get("PRINT_METHOD_2").isEmpty()) {
			Phrase printMethod2 = new Phrase(labelAttributes.get("PRINT_METHOD_2"), helvetica9Bold);
			ct.setSimpleColumn(paper, 70, 70 - (offset * 15) - padding, 275, 85 - (offset * 10) - padding, 0, Element.ALIGN_LEFT);
			ct.setLeading(3f, 0.9f);
			ct.setUseAscender(false);
			ct.setText(printMethod2);
			ct.go();
			offset = offset + 1;
		}

		if(!labelAttributes.get("PRINT_METHOD_3").isEmpty()) {
			Phrase printMethod3 = new Phrase(labelAttributes.get("PRINT_METHOD_3"), helvetica9Bold);
			ct.setSimpleColumn(paper, 70, 70 - (offset * 15) - padding, 275, 85 - (offset * 10) - padding, 0, Element.ALIGN_LEFT);
			ct.setLeading(3f, 0.9f);
			ct.setUseAscender(false);
			ct.setText(printMethod3);
			ct.go();
			offset = offset + 1;
		}

		if(!labelAttributes.get("COATINGS").isEmpty()) {
			Phrase coatingLabel = new Phrase("Coating:", helvetica9Bold);
			ct.setSimpleColumn(paper, 10, 70 - (offset * 15) + 5 - padding, 275, 85 - (offset * 10) - 5 - padding, 0, Element.ALIGN_LEFT);
			ct.setLeading(3f,0.9f);
			ct.setUseAscender(false);
			ct.setText(coatingLabel);
			ct.go();

			String[] coatings = new String[] {labelAttributes.get("COATINGS")};
			if(labelAttributes.get("COATINGS").contains(",")) {
				coatings = labelAttributes.get("COATINGS").split(",");
			}
			for(String coatingText : coatings) {
				Phrase coating = new Phrase(coatingText.trim(), helvetica9Bold);
				ct.setSimpleColumn(paper, 50, 70 - (offset * 15) + 5 - padding, 275, 85 - (offset * 10) - 5 - padding, 0, Element.ALIGN_LEFT);
				ct.setLeading(3f, 0.9f);
				ct.setUseAscender(false);
				ct.setText(coating);
				ct.go();
				offset = offset + 1;
			}
		}

		document.close();
	}

	public static java.util.List<Object> getLabels(String pageIndex, String pageSize, String productId) throws Exception {
		java.util.List<Object> result = new ArrayList<>();
		java.util.List<String> labels = new ArrayList<>();
		if(UtilValidate.isNotEmpty(productId)) {
			FileFilter fileFilter = new WildcardFileFilter(productId + "*.png");
			File[] matchingLabels = new File(LABEL_FILE_LOCATION).listFiles(fileFilter);
			for(int i = 0; i < matchingLabels.length; i ++) {
				labels.add(matchingLabels[i].getName());
			}
		} else {
			Iterator<File> labelIterator = FileUtils.iterateFiles(new File(LABEL_FILE_LOCATION), new String[]{"png"}, false);
			while (labelIterator.hasNext()) {
				File label = labelIterator.next();
				labels.add(label.getName());
			}
		}

		int _pageIndex = NumberUtils.toInt(pageIndex, 0);
		int _pageSize = NumberUtils.toInt(pageSize, 50);
		result.add(labels.size() / _pageSize + (labels.size() % _pageSize > 0 ? 1 : 0));
		result.add(_pageIndex);
		java.util.List<String> pagedLabels = new ArrayList<>();
		for(int i = _pageIndex * _pageSize, j = 0; j < _pageSize && i < labels.size(); i ++, j++) {
			pagedLabels.add(labels.get(i));
		}
		result.add(pagedLabels);
		return result;
	}

	public static boolean generateLabels(Delegator delegator, boolean rebuildFlag) throws Exception {
		boolean success;
		java.util.List<String> productIds = getProductIdsWithoutLabels(delegator, 10000);
		for(String productId : productIds) {
			String label = "uploads/productLabels/" + LabelPrintHelper.getLabelData(delegator, productId, false, rebuildFlag).get(0).get("labelPath");
			System.out.println(label);
		}
		success = true;
		return success;
	}

	protected static java.util.List<String> getProductIdsWithoutLabels(Delegator delegator, int limit) throws Exception {

		java.util.List<String> produstIds = new ArrayList<>();
		SQLProcessor sqlProcessor = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));
		String sqlCommand = "SELECT  " +
				"    p.product_id, MIN(pp.quantity) as pack_qty " +
				"FROM " +
				"    product p " +
				"        LEFT OUTER JOIN " +
				"    product_price pp ON pp.product_id = p.product_id " +
				"WHERE " +
				"    (p.sales_discontinuation_date IS NULL " +
				"        OR (p.sales_discontinuation_date IS NOT NULL " +
				"        AND p.sales_discontinuation_date > DATE_SUB(NOW(), INTERVAL 4 YEAR))) " +
				"        AND p.is_virtual = 'N' " +
				"        AND p.product_id NOT IN (SELECT  " +
				"            pfa.product_id " +
				"        FROM " +
				"            product_feature_appl pfa " +
				"                INNER JOIN " +
				"            product_feature pf ON pfa.product_feature_id = pf.product_feature_id " +
				"        WHERE " +
				"            pf.product_feature_type_id = 'MILL') " +
				"GROUP BY (p.product_id)";

		ResultSet rs = null;
		try {
			rs = sqlProcessor.executeQuery(sqlCommand);
			if (rs != null) {
				while (rs.next()) {
					if(!hasLabel(rs.getString("product_id"), "")) {
						produstIds.add(rs.getString("product_id"));
						if(produstIds.size() >= limit) {
							break;
						}
					}
				}
			}
		} finally {
			sqlProcessor.close();
		}
		return produstIds;
	}

	protected static boolean hasLabel(String productId, String qty) {
		return new File(LABEL_FILE_LOCATION + productId + (UtilValidate.isNotEmpty(qty) ? "-" + qty : "") + ".pdf").exists();

	}

	public static float[] getOptimalFontSize(String text) throws Exception {
		File tempFile = new File(LABEL_FILE_LOCATION + "temp.pdf");
		float[] fontSize = findFontSize(text, null, 32, tempFile);
		tempFile.delete();
		return fontSize;
	}

	protected static float findFontSize(String text, Rectangle rect, float defaultFontSize, float minFontSize) throws Exception {
		if(UtilValidate.isEmpty(text)) {
			return defaultFontSize;
		}
		BaseFont font = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
		float glyphWidth = font.getWidth(text);
		float fontSize = defaultFontSize;
		while(glyphWidth * 0.001f * fontSize > rect.getWidth()) {
			if(fontSize > minFontSize) {
				fontSize = fontSize - 2;
			} else {
				fontSize = minFontSize;
				break;
			}
		}
		return fontSize;
	}

	protected static float[] findFontSize(String text, Rectangle rectangle, float currentFontSize, File tempFile) throws Exception {
		Rectangle rect = new Rectangle(10, 100, 200, 245);
		if(currentFontSize == 32) {
			float fontSize = findFontSize(text, rect, 32, 16);
			if (fontSize > 16) {
				return new float[]{fontSize, 1};
			}
		}

		tempFile.getParentFile().mkdirs();
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(tempFile));
		document.open();


		rect.setBorder(Rectangle.BOX);
		rect.setBorderWidth(0.5f);
		rect.setBorderColor(BaseColor.RED);
		PdfContentByte cb = writer.getDirectContent();
		cb.rectangle(rect);
		Phrase p = new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA, currentFontSize, Font.NORMAL, BaseColor.BLACK));
		ColumnText ct = new ColumnText(cb);
		ct.setSimpleColumn(rect);
		ct.setLeading(3f,0.8f);
		ct.addText(p);
		ct.go();
		float yLine = ct.getYLine();
		document.close();

		if(currentFontSize == 32 && yLine > 215) {
			return new float[] { currentFontSize, 1 };
		} else if(currentFontSize == 30 && yLine > 216) {
			return new float[] { currentFontSize, 1 };
		} else if(currentFontSize == 28 && yLine > 217) {
			return new float[] { currentFontSize, 1 };
		} else if(currentFontSize == 26 && yLine > 218) {
			return new float[] { currentFontSize, 1 };
		} else if(currentFontSize == 32 && yLine > 186) {
			return new float[] { currentFontSize - 2, 2 };
		} else if(currentFontSize == 30 && yLine > 190) {
			return new float[] { currentFontSize, 2 };
		} else if(currentFontSize == 28 && yLine > 193) {
			return new float[] { currentFontSize, 2 };
		} else if(currentFontSize == 26 && yLine > 196) {
			return new float[] { currentFontSize, 2 };
		} else if(currentFontSize == 24 && yLine > 199) {
			return new float[] { currentFontSize, 2 };
		} else if(currentFontSize == 22 && yLine > 223) {
			return new float[] { currentFontSize, 2 };
		} else if(currentFontSize == 20 && yLine > 206) {
			return new float[] { currentFontSize, 2 };
		} else if(currentFontSize == 18 && yLine > 180) {
			return new float[] { currentFontSize, 2 };
		} else if(currentFontSize == 16 && yLine > 160) {
			return new float[] { currentFontSize, 2 };
		} else if(currentFontSize == 16 && yLine <= 160) {
			return new float[] { currentFontSize, 2 };
		} else {
			return findFontSize(text, new Rectangle(20, 100, 200, 245), currentFontSize - 2, tempFile);
		}
	}

	public static void convertToImage(String productId, boolean packLabelFlag, boolean miniLabel) throws Exception {
		PDDocument document = PDDocument.load(new File((miniLabel ? MINI_LABEL_FILE_LOCATION : packLabelFlag ? PACK_LABEL_FILE_LOCATION : LABEL_FILE_LOCATION) + productId + ".pdf"));
		PDFRenderer pdfRenderer = new PDFRenderer(document);
		BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 125, ImageType.RGB);
		ImageIOUtil.writeImage(bim, (miniLabel ? MINI_LABEL_FILE_LOCATION : packLabelFlag ? PACK_LABEL_FILE_LOCATION : LABEL_FILE_LOCATION) + productId + ".png", 125);
		document.close();
	}

	public static void convertToImage(String jobNumber) throws Exception {
		PDDocument document = PDDocument.load(new File(FOLDER_SAMPLE_LABEL_FILE_LOCATION + jobNumber + ".pdf"));
		PDFRenderer pdfRenderer = new PDFRenderer(document);
		BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 125, ImageType.RGB);
		ImageIOUtil.writeImage(bim, (FOLDER_SAMPLE_LABEL_FILE_LOCATION) + jobNumber + ".png", 125);
		document.close();
	}

}

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.*;
import org.apache.ofbiz.entity.util.*;
import org.apache.ofbiz.entity.condition.*;
import org.apache.ofbiz.base.util.UtilValidate;

import com.envelopes.product.*;
import com.envelopes.util.*;

String productId = request.getParameter("id");
GenericValue product = null;
if(UtilValidate.isNotEmpty(productId)) {
	productId = productId.trim();
	product = delegator.findOne("Product", UtilMisc.toMap("productId", productId), false);
	if(product != null) {
		//get all the features applied to this product
		ArrayList<String> FEATURES_TO_SHOW = new ArrayList<>(ProductEvents.FEATURES_TO_SHOW);
		FEATURES_TO_SHOW.add("SIZE_CODE");
		FEATURES_TO_SHOW.add("COLOR_GROUP");
		FEATURES_TO_SHOW.add("ACCEPTABLE_INSERT_SIZES");
		FEATURES_TO_SHOW.add("CORNERS");
		FEATURES_TO_SHOW.add("MATERIAL");
		FEATURES_TO_SHOW.add("REINFORCED_EDGE");
		FEATURES_TO_SHOW.add("CENTER_POCKET");
		FEATURES_TO_SHOW.add("ORIENTATION");
		FEATURES_TO_SHOW.add("RIGHT_POCKET");
		FEATURES_TO_SHOW.add("LEFT_POCKET");
		FEATURES_TO_SHOW.add("CARD_SLOTS");
		FEATURES_TO_SHOW.add("CARD_SLOT_PLACEMENT");
		FEATURES_TO_SHOW.add("POCKET_PLACEMENT");
		FEATURES_TO_SHOW.add("POCKET_HEIGHT");
		FEATURES_TO_SHOW.add("POCKET_GLUE_LOCATION");
		FEATURES_TO_SHOW.add("BACKBONE");
		FEATURES_TO_SHOW.add("NUMBER_OF_PANELS");
		FEATURES_TO_SHOW.add("PAPER_TEXTURE");
		FEATURES_TO_SHOW.add("IMAGE_SIZE");

		Map<String, String> appliedFeatures = ProductHelper.getProductFeatures(delegator, product, FEATURES_TO_SHOW);
		List<String> appliedFeatureIds = ProductHelper.getProductFeatureIds(delegator, product, FEATURES_TO_SHOW);

		//list of all productFeatures
		List<GenericValue> productFeatureTypes = delegator.findAll("ProductFeatureType", false);

		//get the options for each ProductFeature
		Map<String, Map> productFeatureAndOptions = new HashMap<String, Map>();
		Map<String, String> options = null;

		for(GenericValue productFeatureType : productFeatureTypes) {
			if(FEATURES_TO_SHOW.contains(productFeatureType.getString("productFeatureTypeId"))) {
				options = new LinkedHashMap<String, String>();
				List<GenericValue> productFeatures = delegator.findByAnd("ProductFeature", UtilMisc.toMap("productFeatureTypeId", productFeatureType.getString("productFeatureTypeId")), UtilMisc.toList("description ASC"), true);
				for(GenericValue productFeature : productFeatures) {
					options.put(productFeature.getString("productFeatureId"), productFeature.getString("description"));
				}
				productFeatureAndOptions.put(productFeatureType.getString("productFeatureTypeId"), options);
			}
		}

		//get all the prices
		List<GenericValue> productPrices = delegator.findByAnd("ProductPrice", UtilMisc.toMap("productId", productId), UtilMisc.toList("colors ASC", "quantity ASC"), false);

		//get product assets
		List prodAssetConditions = [EntityCondition.makeCondition("assetType", EntityOperator.NOT_EQUAL, "printed"), EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId)];
		
		List<GenericValue> productBasicAssets = EntityQuery.use(delegator).from("ProductAssets").where(EntityCondition.makeCondition(prodAssetConditions, EntityOperator.AND)).orderBy("sortOrder").queryList();
		List<GenericValue> productPrintedAssets = EntityQuery.use(delegator).from("ProductAssets").where(UtilMisc.toMap("productId", productId, "assetType", "printed")).orderBy("sortOrder").queryList();

		//Get Websites
		GenericValue productWebsite = delegator.findOne("ProductWebSite", UtilMisc.toMap("productId", productId), false);

		context.product = product;
		context.appliedFeatures = appliedFeatures;
		context.appliedFeatureIds = appliedFeatureIds;
		context.productFeatureAndOptions = productFeatureAndOptions;
		context.prices = productPrices;
		context.productBasicAssets = productBasicAssets;
		context.productPrintedAssets = productPrintedAssets;
		context.productWebsite = productWebsite;
	} else if(UtilValidate.isNotEmpty(productId) && product == null) {
		List prodSearchCond = [EntityCondition.makeCondition("variantProductId", EntityOperator.LIKE, "%" + productId + "%")];
		List<GenericValue> activeProducts = delegator.findList("ColorWarehouse", EntityCondition.makeCondition(prodSearchCond, EntityOperator.AND), null, null, null, false);
		context.activeProducts = activeProducts;
	}
} else {
	context.activeProducts = ProductHelper.getNewArrivals(delegator, -24);
}


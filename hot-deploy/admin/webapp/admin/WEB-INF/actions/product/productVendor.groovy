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
import org.apache.ofbiz.base.util.UtilValidate;

import com.envelopes.cxml.CXMLHelper;

String productId = request.getParameter("id");
String vendorPartyId = request.getParameter("partyId");

GenericValue product = null;
GenericValue productVendor = null;
if(UtilValidate.isNotEmpty(productId) && UtilValidate.isNotEmpty(vendorPartyId)) {
	productId = productId.trim();
	vendorPartyId = vendorPartyId.trim();

	product = delegator.findOne("Product", UtilMisc.toMap("productId", productId), false);
	productVendor = EntityUtil.getFirst(delegator.findByAnd("VendorProduct", UtilMisc.toMap("productId", productId, "vendorPartyId", vendorPartyId), null, false));
	if(productVendor != null) {
		//get all the prices
		List<GenericValue> productPrices = delegator.findByAnd("VendorProductPrice", UtilMisc.toMap("partyId", vendorPartyId, "vendorProductId", productVendor.getString("vendorProductId")), UtilMisc.toList("colorsFront ASC", "colorsBack ASC"), false);

		context.product = product;
		context.productVendor = productVendor;
		context.prices = productPrices;
	}
} else if(UtilValidate.isNotEmpty(vendorPartyId)) {
	context.vendorPartyId = vendorPartyId.trim();
	context.vendorProducts = delegator.findByAnd("VendorProduct", UtilMisc.toMap("vendorPartyId", vendorPartyId), null, false);
} else {
	context.vendorProducts = CXMLHelper.getAllVendorProducts(delegator, -60);
}
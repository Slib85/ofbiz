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

import java.util.List;
import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.entity.GenericValue;

//get all promos assigned to the env product store
List<GenericValue> promos = new ArrayList<GenericValue>();
List<GenericValue> promoStoreList = EntityQuery.use(delegator).from("ProductStorePromoAndAppl").where("productStoreId", "10000").orderBy("fromDate DESC").queryList();
for(GenericValue promoStore : promoStoreList) {
	promos.add(EntityQuery.use(delegator).from("ProductPromo").where("productPromoId", promoStore.getString("productPromoId")).queryOne());
}
context.promoStoreList = promoStoreList;
context.promos = promos;
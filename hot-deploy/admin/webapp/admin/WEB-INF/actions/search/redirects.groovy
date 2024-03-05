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

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.GenericValue;
import com.envelopes.util.EnvUtil;
import com.bigname.search.elasticsearch.SearchField;
import org.apache.ofbiz.entity.util.EntityQuery;

import com.google.gson.Gson;

String module = "redirects.groovy";

Map<String, Object> params = EnvUtil.getParameterMap(request);
context.params = params;

try {
    context.fields = SearchField.getFilterValues(delegator, null);
    if(UtilValidate.isNotEmpty((String) params.get("id"))) {
        GenericValue redirect = EntityQuery.use(delegator).from("SearchRedirect").where("id", (String) params.get("id")).queryOne();
        if(UtilValidate.isNotEmpty(redirect)) {
            context.data = new Gson().fromJson(redirect.getString("conditionData"), TreeMap.class);
        }
        context.redirect = redirect;
    }
    context.redirects = EntityQuery.use(delegator).from("SearchRedirect").queryList();
} catch (Exception e) {
    EnvUtil.reportError(e);
    Debug.logError("Error loading redirect", module);
}
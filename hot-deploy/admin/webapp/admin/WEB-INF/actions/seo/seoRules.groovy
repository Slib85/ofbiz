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

import java.util.ArrayList;
import java.util.List;
import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.*;
import org.apache.ofbiz.entity.util.*;
import org.apache.ofbiz.entity.condition.*;
import org.apache.ofbiz.entity.GenericValue;
import com.envelopes.seo.SeoEvents;
import com.envelopes.util.EnvUtil;

String module = "seoRules.groovy";

Map<String, Object> params = EnvUtil.getParameterMap(request);

try {
	if(UtilValidate.isNotEmpty((String) params.get("ruleId"))) {
		GenericValue rule = delegator.findOne("SeoRule", UtilMisc.toMap("ruleId", (String) params.get("ruleId")), false);
		context.rule = rule;
	}
} catch (Exception e) {
	EnvUtil.reportError(e);
	Debug.logError("Error loading product: " + params.get("product_id"), module);
}

request.setAttribute("saveResponse", true);
SeoEvents.getSEORules(request, response);
Map<String, Object> savedResponse = (Map<String, Object>) request.getAttribute("savedResponse");

List<GenericValue> rules = null;
if(UtilValidate.isNotEmpty(savedResponse)) {
	rules = (List<GenericValue>) savedResponse.get("rules");
}

context.rules = rules;
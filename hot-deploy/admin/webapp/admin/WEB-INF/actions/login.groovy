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
import org.apache.ofbiz.common.CommonWorkers;
import org.apache.ofbiz.webapp.control.*;
import javax.servlet.http.HttpSession;

import com.envelopes.util.*;

String module = "login.groovy";

HttpSession session = request.getSession();

if(UtilValidate.isEmpty(session.getAttribute("ENV_PREVIOUS_REQUEST_"))) {
	session.setAttribute("ENV_PREVIOUS_REQUEST_", request.getPathInfo());
}

if(UtilValidate.isEmpty(session.getAttribute("ENV_PREVIOUS_PARAM_MAP_URL_"))) {
	Map<String, Object> urlParams = UtilHttp.getUrlOnlyParameterMap(request);
	if(UtilValidate.isNotEmpty(urlParams)) {
		session.setAttribute("ENV_PREVIOUS_PARAM_MAP_URL_", urlParams);
	}

	if(UtilValidate.isEmpty(session.getAttribute("ENV_PREVIOUS_PARAM_MAP_URL_"))) {
		Map<String, Object> formParams = UtilHttp.getParameterMap(request, urlParams.keySet(), false);
		if(UtilValidate.isNotEmpty(formParams)) {
			session.setAttribute("ENV_PREVIOUS_PARAM_MAP_FORM_", formParams);
		}
	}
}
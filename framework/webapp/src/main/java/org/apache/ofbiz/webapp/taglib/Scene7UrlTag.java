/*******************************************************************************
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
 *******************************************************************************/
package org.apache.ofbiz.webapp.taglib;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import java.util.Random;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.webapp.website.WebSiteWorker;
import org.apache.ofbiz.entity.GenericValue;

/**
 * Scene7UrlTag - Creates a URL string prepending the content prefix from url.properties for S7 image use
 */
public class Scene7UrlTag {

    public static final String module = Scene7UrlTag.class.getName();

    public static void appendContentPrefix(HttpServletRequest request, StringBuilder urlBuffer) {
        try {
            appendContentPrefix(request, (Appendable) urlBuffer);
        } catch (IOException e) {
            throw UtilMisc.initCause(new InternalError(e.getMessage()), e);
        }
    }

    public static void appendContentPrefix(HttpServletRequest request, Appendable urlBuffer) throws IOException {
        if (request == null) {
            Debug.logWarning("Request was null in appendContentPrefix; this probably means this was used where it shouldn't be, like using ofbizScene7Url in a screen rendered through a service; using best-bet behavior: standard prefix from url.properties (no WebSite or security setting known)", module);
            String prefix = getPrefix(false);
            if (prefix != null) {
                urlBuffer.append(prefix.trim());
            }
            return;
        }
        GenericValue webSite = WebSiteWorker.getWebSite(request);
        String forwardedProto = request.getHeader("X-Forwarded-Proto");
        boolean isForwardedSecure = UtilValidate.isNotEmpty(forwardedProto) && "HTTPS".equals(forwardedProto.toUpperCase());
        boolean isSecure = request.isSecure() || isForwardedSecure;
        appendContentPrefix(webSite, isSecure, urlBuffer);
    }

    public static void appendContentPrefix(GenericValue webSite, boolean secure, Appendable urlBuffer) throws IOException {
        String prefix = getPrefix(secure);
        if (prefix != null) {
            urlBuffer.append(prefix.trim());
        }
    }

    public static String getContentPrefix(HttpServletRequest request) {
        StringBuilder buf = new StringBuilder();
        Scene7UrlTag.appendContentPrefix(request, buf);
        return buf.toString();
    }

    public static String getPrefix(boolean secure) {
        StringBuilder prefix = new StringBuilder();
        if(secure) {
            prefix.append(UtilProperties.getPropertyValue("url", "s7.url.prefix.secure"));
        } else {
            prefix.append(UtilProperties.getPropertyValue("url", "s7.url.prefix.standard"));
        }

        String numMax = UtilProperties.getPropertyValue("url", "s7.url.prefix.num");
        if(UtilValidate.isNotEmpty(numMax)) {
            int max = Integer.valueOf(numMax).intValue();
            Random rand = new Random();
            int randomNum = rand.nextInt((max - 1) + 1) + 1;

            if(randomNum > 1) {
                prefix.append(Integer.toString(randomNum));
            }
        }

        prefix.append(UtilProperties.getPropertyValue("url", "s7.url.suffix"));
        return prefix.toString();
    }
}
package com.bigname.pricingengine.util;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Manu on 2/5/2018.
 */
public class BreadcrumbUtil {

    public static final Map<String, String> bosBreadcrumbBase = buildBreadcrumbs("Home", "/admin/control/main");
    public static final Map<String, String> pricingEngineBreadcrumbBase = buildBreadcrumbs(bosBreadcrumbBase, "Pricing Engine", "/admin/control/pricingEngineDashboard");

    public static Map<String, String> buildBreadcrumbs(String... values) {
        return buildBreadcrumbs(null, values);
    }

    public static Map<String, String> buildBreadcrumbs(Map<String, String> breadcrumbs, String... values) {
        if(breadcrumbs == null) {
            breadcrumbs = new LinkedHashMap<>();
        } else {
            breadcrumbs = new LinkedHashMap<>(breadcrumbs);
        }
        if(!EngineUtil.isEmpty(values)) {
            for(int i = 0; i < values.length - 1; i ++) {
                breadcrumbs.put(values[i], values[++i]);
            }
        }
        return breadcrumbs;
    }


    public static String getReferrerParameter(HttpServletRequest request, String parameterName, String... referrerName) {
        String referrer = request.getHeader("referer");
        if(EngineUtil.isEmpty(referrerName) || referrer.endsWith("/" + referrerName) || referrer.contains("/" + referrerName + "?")) {
            if (!EngineUtil.isEmpty(referrer)) {
                Pattern p = Pattern.compile("^.*?(\\?|&)" + parameterName + "=(.*?)(&|$).*");
                Matcher m = p.matcher(referrer);
                if (m.find()) {
                    return (m.group(2));
                }
            }
        }
        return null;
    }



}

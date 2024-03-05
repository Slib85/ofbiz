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

import java.util.*;
import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.order.shoppingcart.ShoppingCartEvents;
import org.apache.ofbiz.product.catalog.CatalogWorker;
import org.apache.ofbiz.product.store.ProductStoreWorker;
import org.apache.ofbiz.webapp.control.LoginWorker;
import org.apache.ofbiz.webapp.website.WebSiteWorker;

import com.bigname.search.elasticsearch.SearchTranslator;
import com.bigname.search.elasticsearch.util.SearchUtil;
import com.envelopes.product.Product;
import com.envelopes.seo.SeoEvents;
import com.envelopes.seo.CanonicalHelper;
import com.folders.category.*;
import com.envelopes.util.*;

String module = "ecommerce.setup.groovy";
String currentView = (UtilValidate.isEmpty(request.getAttribute("_CURRENT_VIEW_"))) ? "" : request.getAttribute("_CURRENT_VIEW_");

SeoEvents.getSEORule(request, response);

productStore = ProductStoreWorker.getProductStore(request);
catalogName = CatalogWorker.getCatalogName(request);
catalogId = CatalogWorker.getCurrentCatalogId(request);
webSiteId = WebSiteWorker.getWebSiteId(request);
webSite = WebSiteWorker.getWebSite(request);

globalContext.enableSwatchbook = true;

oldFreeShippingContent = 299;

nowTimestamp = UtilDateTime.nowTimestamp();
globalContext.currentTimestamp = UtilDateTime.nowTimestamp();

if ((String) nowTimestamp >= "2021-08-16 00:00:00.000" && (String) nowTimestamp <= "2021-08-19 23:59:59.000") {
    freeShippingAmount = 50;
} else {
    freeShippingAmount = 99;
}

globalContext.productStore = productStore;
globalContext.catalogName = catalogName;
globalContext.catalogId = catalogId;
globalContext.checkLoginUrl = LoginWorker.makeLoginUrl(request, "checkLogin");
globalContext.catalogQuickaddUse = CatalogWorker.getCatalogQuickaddUse(request);
globalContext.webSiteId = webSiteId;
globalContext.siteName = webSite.getString("siteName");
globalContext.siteDomain = webSite.getString("cookieDomain");
globalContext.freeShippingAmount = freeShippingAmount;
globalContext.navContent = EnvConstantsUtil.getNavContent(delegator, webSiteId);

cart = ShoppingCartEvents.getCartObject(request)
cart.setOrderType("SALES_ORDER");

/**
 * Determine if the request is from mobile or desktop
 * Nginx sets this variable based on a custom map routine in ofbiz.conf
 * We detect a few useragents to determine if its mobile or not
 * Mobile: envmobilecache
 * Desktop: envcache
 * This will be used to alter page content for speed for mobile users
 */
globalContext.isMobile = (UtilValidate.isNotEmpty(request.getHeader("x-cache")) && request.getHeader("x-cache").equalsIgnoreCase("envmobilecache")) ? "true" : "false";

String suffix = null;
String searchTitleName = null;
String author = null;
String metaKeywords = null;
if(webSiteId.equals("ae")) {
    cart.setChannelType(EnvUtil.getSalesChannelEnumId(webSiteId));
    globalContext.salesChannelEnumID = EnvUtil.getSalesChannelEnumId(webSiteId);
    suffix = "ActionEnvelope.com";
    searchTitleName = "ActionEnvelopes";
    author = "ActionEnvelope.com www.actionenvelope.com";
    metaKeywords = "envelopes, envelope, invitation envelopes, greeting card envelopes, square envelopes, envelope printing, business envelopes, window envelopes, double window envelopes, jumbo envelopes, airmail envelopes, self seal envelopes, foil lined envelopes, return address printed, shipping envelopes, cd envelopes, dvd envelopes, vellum paper, translucent envelopes, colored envelopes, mailers, custom printed, envelope manufacturer, office supplies, printed envelopes";
} else if(webSiteId.equals("envelopes")) {
    cart.setChannelType(EnvUtil.getSalesChannelEnumId(webSiteId));
    globalContext.salesChannelEnumID = EnvUtil.getSalesChannelEnumId(webSiteId);
    suffix = "Envelopes.com";
    searchTitleName = "Envelopes";
    author = "Envelopes.com www.envelopes.com";
    metaKeywords = "envelopes, envelope, invitation envelopes, greeting card envelopes, square envelopes, envelope printing, business envelopes, window envelopes, double window envelopes, jumbo envelopes, airmail envelopes, self seal envelopes, foil lined envelopes, return address printed, shipping envelopes, cd envelopes, dvd envelopes, vellum paper, translucent envelopes, colored envelopes, mailers, custom printed, envelope manufacturer, office supplies, printed envelopes";
} else if(webSiteId.equals("folders")) {
    cart.setChannelType(EnvUtil.getSalesChannelEnumId(webSiteId));
    globalContext.salesChannelEnumID = EnvUtil.getSalesChannelEnumId(webSiteId);
    suffix = "Folders.com";
    searchTitleName = "Folders";
    author = "Folders.com www.folders.com";
    metaKeywords = "custom folders, custom binders, custom printed folders, presentation folders, blank folders, school folders, printed folders, certificate holders, report covers, binders, folios, totes, pocket folders, two pocket folders";
} else if(webSiteId.equals("bags")) {
    cart.setChannelType(EnvUtil.getSalesChannelEnumId(webSiteId));
    globalContext.salesChannelEnumID = EnvUtil.getSalesChannelEnumId(webSiteId);
    suffix = "Bags.com";
    searchTitleName = "Bags";
    author = "Bags.com www.bags.com";
    metaKeywords = "envelopes, envelope, invitation envelopes, greeting card envelopes, square envelopes, envelope printing, business envelopes, window envelopes, double window envelopes, jumbo envelopes, airmail envelopes, self seal envelopes, foil lined envelopes, return address printed, shipping envelopes, cd envelopes, dvd envelopes, vellum paper, translucent envelopes, colored envelopes, mailers, custom printed, envelope manufacturer, office supplies, printed envelopes";
} else {
    cart.setChannelType(EnvUtil.getSalesChannelEnumId(webSiteId));
    globalContext.salesChannelEnumID = EnvUtil.getSalesChannelEnumId(webSiteId);
    suffix = "BigName.com";
    author = "BigName.com www.bigname.com";
    metaKeywords = "envelopes, envelope, invitation envelopes, greeting card envelopes, square envelopes, envelope printing, business envelopes, window envelopes, double window envelopes, jumbo envelopes, airmail envelopes, self seal envelopes, foil lined envelopes, return address printed, shipping envelopes, cd envelopes, dvd envelopes, vellum paper, translucent envelopes, colored envelopes, mailers, custom printed, envelope manufacturer, office supplies, printed envelopes";
}

globalContext.metaTitleSuffix = suffix;
cart.setWebSiteId(webSiteId);
cart.setProductStoreId("10000");

//create SEO related title and tags
String metaTitle = "";
String metaDesc = "";
String canonicalUrl = null;
String pageDesc = "";
String altPageDesc = "";
String footerDesc = "";
String seoImageName = "";
String seoH1 = "";
String seoH2 = "";
String seoH3 = "";
String seoH1Color = "";
String bannerColor = '';
String alternateUrl = "";

Map<String, Object> paramMap = EnvUtil.getParameterMap(request);
Product _product = null;
try {
    if(product != null) {
        _product = product;
    }
} catch (Exception e) {

}

try {
    canonicalUrl = CanonicalHelper.getCanonicalUrls(delegator).get(webSiteId + "-" + request.getRequestURI() + (UtilValidate.isNotEmpty(request.getQueryString()) ? "?" + request.getQueryString() : ""));
    alternateUrl = request.getRequestURL().replaceAll("//www.envelopes.com/", "//m.envelopes.com/");
    globalContext.alternateUrl = alternateUrl;
} catch (Exception e) {
    Debug.logError(e, "Error trying to set Canonical URL.", module);
}

if(currentView == "main") {
    if(webSiteId.equals("folders")) {
        metaTitle = "Presentation Folders" + " | " + "Custom Folders" + " | " + suffix;
        metaDesc = "Looking for something blank or completely custom? Stand out from the crowd by browsing our vast selection of folders, portfolios, report covers, and so much more.";
        //canonicalUrl = "/main";
    } else {
        metaTitle = "Envelopes" + " | " + suffix;
        metaDesc = "Here, we offer a wide variety of envelopes and mailing materials. Sort by color, size, style, and paper texture to find the best solution and biggest savings!";
        //canonicalUrl = "/main";
    }
} else if(currentView == "product" && _product != null) {
    if(UtilValidate.isNotEmpty(paramMap.get("product_id"))) {
        if(webSiteId.equals("folders")) {
            if(UtilValidate.isNotEmpty(paramMap.get("print_method"))) {
                String printMethod = "";
                if(paramMap.get("print_method").equals("FOIL")) {
                    printMethod = "Foil Stamped";
                } else if(paramMap.get("print_method").equals("FOUR_COLOR")) {
                    printMethod = "Printed ";
                } else if(paramMap.get("print_method").equals("SPOT")) {
                    printMethod = "Spot Printed ";
                } else if(paramMap.get("print_method").equals("EMBOSS")) {
                    printMethod = "Embossed ";
                }
                metaTitle = printMethod + " " + _product.getName() + "-" + _product.getColor() + " | " + suffix;
            } else if(UtilValidate.isNotEmpty(_product.getImprintMethods())) {
                metaTitle = "Custom " + _product.getName() + "-" + _product.getColor() + " | " + suffix;
            } else {
                metaTitle = "Blank " + _product.getName() + (UtilValidate.isNotEmpty(_product.getColor()) ? "-" + _product.getColor() + " | " : " | ") + suffix;
            }
        } else {
            metaTitle = _product.getMetaTitle() + " | " + suffix;
        }
        metaDesc = (UtilValidate.isNotEmpty(_product.getMetaDesc())) ? _product.getMetaDesc() : _product.getDescription();
        canonicalUrl = _product.getUrl(false);
    } else if(UtilValidate.isNotEmpty(paramMap.get("designId"))) {
        metaTitle = _product.getFrontDesign().templateDescription + (UtilValidate.isNotEmpty(_product.getFrontDesign().quickDesc) ? " - " + _product.getFrontDesign().quickDesc : "") + " | " + suffix;
        canonicalUrl = _product.getUrl(true);
    }
} else if(currentView == "category") {
    try {
        GenericValue category = null;
        SearchTranslator translator = (SearchTranslator) globalContext.get("translator");
        if(UtilValidate.isNotEmpty(paramMap.get("category_id"))) {
            category = delegator.findOne("ProductCategory", UtilMisc.toMap("productCategoryId", (String) paramMap.get("category_id")), true);
            globalContext.category = category;
        } else if(translator != null && translator.hasStyle()) {
            String styleFacetId = null;
            if(translator.getSearchTuningFilters().get("st") instanceof List) {
                styleFacetId = ((List) translator.getSearchTuningFilters().get("st")).get(0);
            } else {
                styleFacetId = translator.getSearchTuningFilters().get("st");
            }
            category = delegator.findOne("ProductCategory", UtilMisc.toMap("productCategoryId", translator.getFacet(delegator, "st", styleFacetId).getString("referenceId")), true);
            globalContext.category = category;
        } else if(translator != null && translator.hasUse()) {
            String styleFacetId = null;
            if(translator.getSearchTuningFilters().get("use") instanceof List) {
                styleFacetId = ((List) translator.getSearchTuningFilters().get("use")).get(0);
            } else {
                styleFacetId = translator.getSearchTuningFilters().get("use");
            }
            category = delegator.findOne("ProductCategory", UtilMisc.toMap("productCategoryId", translator.getFacet(delegator, "use", styleFacetId).getString("referenceId")), true);
            globalContext.category = category;
        }

        if(category != null && UtilValidate.isNotEmpty(category.getString("metaTitle"))) {
            metaTitle = category.getString("metaTitle") + " | " + suffix;
        } else if(category != null && UtilValidate.isNotEmpty(category.getString("description"))) {
            metaTitle = category.getString("description") + " | " + suffix;
        } else if(translator != null && !translator.getPageTitle().equals("")) {
            metaTitle = "Products in " + translator.getPageTitle() + " | " + suffix;
        }

        if(category != null && UtilValidate.isNotEmpty(category.getString("longDescription"))) {
            metaDesc = category.getString("longDescription");
            pageDesc = category.getString("longDescription");
        }

        if (category != null) {
            canonicalUrl = "/category/~category_id=" + category.getString("productCategoryId");
        }

        if(UtilValidate.isNotEmpty(EnvUtil.getFriendlyURL(request))) {
            canonicalUrl += (UtilValidate.isNotEmpty(canonicalUrl) ? canonicalUrl : "/category") + "?af=" + globalContext.get("requestParameters").get("af").replaceAll("\\s", "%20");
            
            if(UtilValidate.isNotEmpty(globalContext.get("requestParameters").get("sort"))) {
                canonicalUrl = canonicalUrl + "&sort=" + globalContext.get("requestParameters").get("sort");
            }
        }
    } catch (GenericEntityException e) {
        //TODO
    }
} else if(currentView == "search") {
    try {
        SearchTranslator translator = (SearchTranslator) globalContext.get("translator");
        if(translator != null && !translator.getPageTitle().equals("")) {
            metaTitle = "Products in " + translator.getPageTitle() + " | " + suffix;
        } else {
            if (UtilValidate.isNotEmpty(paramMap.get("w")) && ((String) paramMap.get("w")).equals("*")) {
                metaTitle = "Products at " + suffix + ((UtilValidate.isNotEmpty(paramMap.get("page"))) ? " - Page " + (Integer.valueOf((String) paramMap.get("page")) + 1) : "");
            } else {
                metaTitle = searchTitleName + " - Search Results for " + paramMap.get("w") + ((UtilValidate.isNotEmpty(paramMap.get("page"))) ? " - Page " + (Integer.valueOf((String) paramMap.get("page")) + 1) : ""); ;
            }
        }

        String friendlyURL = EnvUtil.getFriendlyURL(request);
        if(UtilValidate.isNotEmpty(friendlyURL)) {
            canonicalUrl = "/search?af=" + globalContext.get("requestParameters").get("af").replaceAll("\\s", "%20");
            if(UtilValidate.isNotEmpty(globalContext.get("requestParameters").get("sort"))) {
                canonicalUrl = canonicalUrl + "&sort=" + globalContext.get("requestParameters").get("sort");
            }
        }
    } catch (Exception e) {
        //TODO
    }
} else if(currentView == "checkout") {
    metaTitle = suffix + " Checkout | 100% Secure";
} else if(currentView == "cart" || currentView == "cross-sell") {
    metaTitle = "Your Shopping Cart" + " | " + suffix;
} else if(currentView == "shopByColor") {
    if(paramMap.get("category_id") == null) {
        if(webSiteId.equals("folders")) {
            metaTitle = "Shop By Color" + " | " + suffix;
        } else {
            metaTitle = "Shop By Color | Airmail Envelopes, Bright Blue Envelopes, Metallic Envelopes, Pastel Envelopes" + " | " + suffix;
        }
    } else {
        try {
            GenericValue category = EntityQuery.use(delegator).from("ProductCategory").where("productCategoryId", (String) paramMap.get("category_id")).cache().queryOne();
            globalContext.category = category;
            if (category != null && UtilValidate.isNotEmpty(category.getString("metaTitle"))) {
                metaTitle = category.getString("metaTitle") + " | " + suffix;
            } else if (category != null && UtilValidate.isNotEmpty(category.getString("description"))) {
                if(webSiteId.equals("folders")) {
                    metaTitle = category.getString("description") + " | " + suffix;
                } else {
                    metaTitle = category.getString("description") + " | Envelopes, Invitations & Stationery | " + suffix;
                }
            }
            if (category != null && UtilValidate.isNotEmpty(category.getString("longDescription"))) {
                metaDesc = category.getString("longDescription");
                pageDesc = metaDesc;
            }

            if(category == null) {
                category = EntityQuery.use(delegator).from("SearchFacet").where("facetTypeId", "cog2", "facetId", SearchUtil.cleanFacet((String) paramMap.get("category_id"), true)).cache().queryOne();
                if(category != null) {
                    if(webSiteId.equals("folders")) {
                        metaTitle = category.getString("facetName") + " | " + suffix;
                    } else {
                        metaTitle = category.getString("facetName") + " | Envelopes, Invitations & Stationery | " + suffix;
                    }

                }
            }
        } catch (GenericEntityException e) {
            Debug.logError(e, "Error trying to get color data.", module);
        }
    }
} else if(currentView == "collections") {
    if(paramMap.get("category_id") == null) {
        metaTitle = "Shop By Collection - Color Envelopes, Translucent Envelopes, Kraft Envelopes, Metallic Envelopes" + " - " + suffix;
    } else {
        try {
            GenericValue category = delegator.findOne("ProductCategory", UtilMisc.toMap("productCategoryId", (String) paramMap.get("category_id")), true);
            globalContext.category = category;
            if (category != null && UtilValidate.isNotEmpty(category.getString("metaTitle"))) {
                metaTitle = category.getString("metaTitle") + " | " + suffix;
            } else if (category != null && UtilValidate.isNotEmpty(category.getString("description"))) {
                metaTitle = category.getString("description") + " Envelopes | " + suffix;
            }
            if (category != null && UtilValidate.isNotEmpty(category.getString("longDescription"))) {
                metaDesc = category.getString("longDescription");
            }
        } catch (GenericEntityException e) {
            Debug.logError(e, "Error trying to get collection data.", module);
        }
    }
} else if(currentView == "sizes") {
    GenericValue size = EntityQuery.use(delegator).from("SearchFacet").where("facetTypeId", "si", "facetId", SearchUtil.cleanFacet((String) paramMap.get("si"), true)).cache().queryOne();
    if(UtilValidate.isEmpty(size)) {
        metaTitle = "Envelope Sizes - 100's of Envelopes in all sizes, styles and colors at Envelopes.com" + " - " + suffix;
    } else {
        metaTitle = size.getString("facetName") + " | " + suffix;
    }
} else {
    String _titleProperty = null;
    String _descProperty = null;
    try {
        if(titleProperty != null) {
            _titleProperty = titleProperty;
        }
        if(descProperty != null) {
            _descProperty = descProperty;
        }
    } catch (Exception e) {

    }
    if(UtilValidate.isNotEmpty(_titleProperty)) {
        metaTitle = _titleProperty + " | " + suffix;
    }
    if(UtilValidate.isNotEmpty(_descProperty)) {
        metaDesc = _descProperty;
    }

    if(currentView == "weddingShop") {
        canonicalUrl = "/weddingShop";
    } else if(currentView == "size-guide") {
        canonicalUrl = "/envelope-sizes";
    } else if(currentView == "designoptions") {
        canonicalUrl = "/designoptions";
    } else if(currentView == "design-templates") {
        canonicalUrl = "/envelope-templates"
    }
}

//meta overrides
if(UtilValidate.isNotEmpty(request.getAttribute("metaTitle"))) {
    metaTitle = request.getAttribute("metaTitle") + " | " + suffix;
}
if(UtilValidate.isNotEmpty(request.getAttribute("metaDescription"))) {
    metaDesc = request.getAttribute("metaDescription");
    if (webSiteId.equals("envelopes")) {
        pageDesc = metaDesc;
    }
}
if(UtilValidate.isNotEmpty(request.getAttribute("metaKeywords"))) {
    metaKeywords = request.getAttribute("metaKeywords");
}

if(UtilValidate.isNotEmpty(request.getAttribute("pageDescription"))) {
    pageDesc = request.getAttribute("pageDescription");
}

if(UtilValidate.isNotEmpty(request.getAttribute("altPageDescription"))) {
    altPageDesc = request.getAttribute("altPageDescription");
}

if(UtilValidate.isNotEmpty(request.getAttribute("footerDescription"))) {
    footerDesc = request.getAttribute("footerDescription");
}

if(UtilValidate.isNotEmpty(request.getAttribute("seoImageName"))) {
    seoImageName = request.getAttribute("seoImageName");
}

if(UtilValidate.isNotEmpty(request.getAttribute("seoH1"))) {
    seoH1 = request.getAttribute("seoH1");
}

if(UtilValidate.isNotEmpty(request.getAttribute("seoH2"))) {
    seoH2 = request.getAttribute("seoH2");
}

if(UtilValidate.isNotEmpty(request.getAttribute("seoH3"))) {
    seoH3 = request.getAttribute("seoH3");
}

if(UtilValidate.isNotEmpty(request.getAttribute("seoH1Color"))) {
    seoH1Color = request.getAttribute("seoH1Color");
}

if(UtilValidate.isNotEmpty(request.getAttribute("bannerColor"))) {
    bannerColor = request.getAttribute("bannerColor");
}

globalContext.canonicalUrl = canonicalUrl;
globalContext.metaTitle = metaTitle;
globalContext.metaDesc = metaDesc;
globalContext.pageDesc = pageDesc;
globalContext.altPageDesc = altPageDesc;
globalContext.footerDesc = footerDesc;
globalContext.metaKeywords = metaKeywords;
globalContext.author = author;
globalContext.currentView = currentView;
globalContext.seoImageName = seoImageName;
globalContext.seoH1 = seoH1;
globalContext.seoH2 = seoH2;
globalContext.seoH3 = seoH3;
globalContext.seoH1Color = seoH1Color;
globalContext.bannerColor = bannerColor;

globalContext.pageTimestamp = "202011031056";

// Custom override for envelopes clearance page
if(webSiteId.equals("envelopes") && currentView == "search" && "onsale:sale onsale:clearance".equals(request.getParameter("af"))) {
    globalContext.seoH1 = "Clearance & Sale Envelopes";
    globalContext.seoH1Color = "ffffff";
    globalContext.metaTitle = "Discounted Envelopes | Envelopes.com";
    globalContext.metaDesc = "Some of our stunning envelopes and stock must go to make room for the latest in upcoming trends. Check out our sale envelopes items now, before they're all gone!";
}

if(StaticCategories.blankCategories.containsKey(request.getParameter("category_id"))) {
    Map<String, Object> s_category = StaticCategories.blankCategories.get(request.getParameter("category_id"));
    globalContext.metaTitle = s_category.get("seoH1") + " | " + suffix;
    globalContext.canonicalUrl = s_category.get("friendlyUrl")
    globalContext.seoH1 = s_category.get("seoH1");
    globalContext.seoH2 = s_category.get("seoH2");
}

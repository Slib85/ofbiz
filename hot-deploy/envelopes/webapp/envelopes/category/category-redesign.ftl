<link href="<@ofbizContentUrl>/html/css/product/category-redesign.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet">
<link href="<@ofbizContentUrl>/html/css/product/category-internal-ad.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet">
<script>
    var searchPageType = 'CATEGORY_PAGE';
</script>

<div class="content container category">
    <#include "../includes/breadcrumbs.ftl" />
    <div id="jqs-refinements-widget">
        <link href="<@ofbizContentUrl>/html/css/util/paginator.css</@ofbizContentUrl>" rel="stylesheet" />
        <#if bannerData?has_content || seoH1?has_content>
            <div class="genericPageHeader jqs-category-header">
                <div class="section no-margin margin-top-xxs no-padding desktop-only">
                    <div class="pageHeaderText">
                        <h1 class="jqs-category-title category-title navyblue"><#if seoH1?has_content>${seoH1?replace("&lt;", "<")?replace("&gt;", ">")?replace("&#x2f;", "/")}<#elseif bannerData?has_content>${bannerData.bannerName?if_exists}</#if></h1>
                    </div>
                    <div class="headerImage jqs-category-banner">
                        <#if bannerData?has_content || seoImageName?has_content><img src="<@ofbizContentUrl>/html/img/category/banners/desktop</@ofbizContentUrl>/<#if seoImageName?has_content>${seoImageName}<#else>${bannerData.banner?if_exists}</#if>.jpg" alt="<#if seoH1?has_content>${seoH1}<#else>${bannerData.bannerName?if_exists}</#if>"/></#if>
                    </div>
                </div>
                <div class="headerImage text-center mobile-tablet-only margin-bottom-xxs jqs-category-mobile-banner">
                    <#if bannerData?has_content || seoImageName?has_content><img src="<@ofbizContentUrl>/html/img/category/banners/mobile</@ofbizContentUrl>/<#if seoImageName?has_content>${seoImageName}<#else>${bannerData.banner?if_exists}</#if>.jpg" alt="<#if seoH1?has_content>${seoH1}<#else>${bannerData.bannerName?if_exists}</#if>"/></#if>
                </div>
                <#if altPageDesc?has_content>
                    <p style="padding-bottom: 0px;">${altPageDesc}</p>
                </#if>
                <div class="env-category-info">
                    <div class="env-category-content">
                        <#if seoH2?has_content>
                            <h2>${seoH2}</h2>
                        </#if>
                        <#if pageDesc?has_content>
                            <p class="category-description no-margin">${pageDesc}</p>
                        <#elseif pageDescription?has_content>
                            <p class="category-description no-margin">${pageDescription}</p>
                        </#if>
                    </div>
                    <div class="env-category-content" id="env-internal-ad">
                        <a href="<@ofbizUrl>/product/~category_id=NOTECARDS/~product_id=SWATCHBOOK</@ofbizUrl>" title="Swatchbook" class="dis-block">
                            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/envelopes-com-swatchbook-banner?fmt=png-alpha&wid=510</@ofbizScene7Url>" atl="Swatchbook | Envelopes.com" />
                        </a>
                    </div>
                </div>
            </div>
        </#if>

        <div class="jqs-searchResultsContainer jqs-search">
            ${screens.render("component://envelopes/widget/CategoryScreens.xml#categoryResults")}
        </div>
    </div>
    <div class="text-center padding-bottom-xxs padding-top-xs">
        <a href="#" class="navyblue" style="font-size:1.4em;"><b>Back To Top</b></a>
    </div>
</div>
<#if seoH3?has_content>
<div class="seoTextContainer">
    <h3 class="categorySEOTitle navyblue">${seoH3?if_exists}</h3>
    <#if footerDesc?has_content><p class="categorySEOText">${footerDesc?replace("&lt;", "<")?replace("&gt;", ">")?replace("&#x2f;", "/")}</p></#if>
</div>
</#if>

<script src="<@ofbizContentUrl>/html/js/util/elasticSearch.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
<script src="<@ofbizContentUrl>/html/js/util/category-internal-ad.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
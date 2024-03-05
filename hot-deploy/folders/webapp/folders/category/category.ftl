<link href="<@ofbizContentUrl>/html/css/folders/search/search.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/folders/search/searchResults.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/folders/category/category.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />

<div class="jqs-search search category margin20">
    <#if bannerData?has_content || seoH1?has_content>
        <div class="categoryHeader">
            <h1 class="<#if !bannerData?has_content || !seoImageName?has_content && seoH1?has_content>seoH1NoBanner</#if>"><#if seoH1?has_content>${seoH1}<#elseif banerData?has_content>${bannerData.bannerName?if_exists}</#if></h1>
            <#if bannerData?has_content || seoImageName?has_content>
            <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/<#if seoImageName?has_content>${seoImageName}<#else>${bannerData.banner}</#if>?fmt=png-alpha&amp;hei=200&amp;ts=1</@ofbizScene7Url>" alt="<#if seoH1?has_content>${seoH1}<#else>${bannerData.bannerName}</#if>" />
            </#if>
        </div>
    </#if>
    <div>
        <div class="categoryResults foldersTabularRow">
            <div bns-resultsset>
                ${screens.render("component://folders/widget/CategoryScreens.xml#categoryResults")}
            </div>
        </div>
    </div>
</div>
<#if seoH3?has_content>
    <div class="seoTextContainer">
        <h3 class="searchSEOTitle">${seoH3?if_exists}</h3>
        <#if footerDesc?has_content><p class="searchSEOText">${footerDesc?replace("&lt;", "<")?replace("&gt;", ">")?replace("&#x2f;", "/")}</p></#if>
    </div>
</#if>
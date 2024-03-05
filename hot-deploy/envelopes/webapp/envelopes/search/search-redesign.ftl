<link href="<@ofbizContentUrl>/html/css/product/category-redesign.css</@ofbizContentUrl>" rel="stylesheet">
<script>
    var searchPageType = "SEARCH_PAGE";
    var stayInTheLoopSearchString = window.location.search.replace('?w=','');
    var stayInTheLoopFriendlySearchString = window.location.pathname;
    $(document).ready(function() { 
        if(stayInTheLoopSearchString == '*&af=onsale:sale%20onsale:clearance' || stayInTheLoopFriendlySearchString == '/clearance') {
            $('#stayInTheLoop').attr('bns-additionalsource', 'clearance');
        }
    });
</script>

<#if redirectData?has_content && redirectData.redirectUrl?has_content>
    <span id="redirectUrl" class="hidden">${redirectData.redirectUrl}</span>
    <script>
        window.location.href = $('#redirectUrl').text();
    </script>
</#if>
<div class="content container category">
    <#include "../includes/breadcrumbs.ftl" />
    <div id="jqs-refinements-widget">
        <link rel="stylesheet" type="text/css" href="<@ofbizContentUrl>/html/css/product/search-redesign.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>">
        <link href="<@ofbizContentUrl>/html/css/util/paginator.css</@ofbizContentUrl>" rel="stylesheet" />
        <#if bannerData?has_content || seoH1?has_content>
            <div class="genericPageHeader jqs-search-header">
                <div class="section no-margin margin-top-xxs no-padding desktop-only searchContent" style="background: <#if bannerColor?has_content>${bannerColor};<#else>url('<@ofbizContentUrl>/html/img/search/banners/desktop</@ofbizContentUrl>/<#if seoImageName?has_content>${seoImageName}<#elseif bannerData?has_content>${bannerData.banner?if_exists.replace(" ", "")}</#if>_bg.jpg') repeat;</#if>">
                    <div class="pageHeaderText">
                        <div class="searchResultsHeaderTextContainer" style="color: #${seoH1Color?default("1a345f")} !important;">
                            <h1 class="jqs-search-title search-title"><#if seoH1?has_content>${seoH1?replace("&lt;", "<")?replace("&gt;", ">")?replace("&#x2f;", "/")}<#elseif bannerData?has_content><img src="<@ofbizContentUrl>/html/img/search/banners/desktop/${bannerData.banner.replace(" ", "")}_text.png</@ofbizContentUrl>" alt="${bannerData.bannerName?if_exists}"></#if></h1>
                            <#if seoH1?has_content>
                                <#if seoH2?has_content>
                                    <h2 class="no-margin">${seoH2}</h2>
                                </#if>
                                <#if pageDesc?has_content>
                                    <p class="search-description no-margin">${pageDesc}</p>
                                <#elseif pageDescription?has_content>
                                    <p class="search-description no-margin">${pageDescription}</p>
                                </#if>
                            </#if>
                        </div>
                    </div>
                    <div class="headerImage jqs-search-banner">
                        <#if bannerData?has_content || seoImageName?has_content>
                            <#if bannerData?has_content && bannerData.banner == 'FoldersSearch'>
                                <a href="https://www.folders.com" target="_blank"><img src="<@ofbizContentUrl>/html/img/search/banners/desktop</@ofbizContentUrl>/<#if seoImageName?has_content>${seoImageName}<#elseif bannerData?has_content>${bannerData.banner?if_exists.replace(" ", "")}</#if>.png" alt="<#if seoH1?has_content>${seoH1}<#elseif bannerData?has_content>${bannerData.bannerName?if_exists}</#if>"/></a>
                            <#else>
                                <img src="<@ofbizContentUrl>/html/img/search/banners/desktop</@ofbizContentUrl>/<#if seoImageName?has_content>${seoImageName}<#elseif bannerData?has_content>${bannerData.banner?if_exists.replace(" ", "")}</#if>.png" alt="<#if seoH1?has_content>${seoH1}<#elseif bannerData?has_content>${bannerData.bannerName?if_exists}</#if>"/>
                            </#if>
                        </#if>
                    </div>
                </div>
                <div class="headerImage text-center mobile-tablet-only margin-bottom-xxs jqs-search-mobile-banner">
                    <#if bannerData?has_content || seoImageName?has_content><img src="<@ofbizContentUrl>/html/img/search/banners/mobile</@ofbizContentUrl>/<#if seoImageName?has_content>${seoImageName}<#else>${bannerData.banner?if_exists.replace(" ", "")}</#if>.jpg" alt="<#if seoH1?has_content>${seoH1}<#elseif bannerData?has_content>${bannerData.bannerName?if_exists}</#if>"/></#if>
                </div>
                <#if !seoH1?has_content>
                    <#if seoH2?has_content>
                        <h2>${seoH2}</h2>
                    </#if>
                    <#if pageDesc?has_content>
                        <p class="search-description no-margin">${pageDesc}</p>
                    <#elseif pageDescription?has_content>
                        <p class="search-description no-margin">${pageDescription}</p>
                    </#if>
                </#if>
            </div>
        </#if>

        <div class="jqs-searchResultsContainer jqs-search">
            ${screens.render("component://envelopes/widget/SearchScreens.xml#searchResults")}
        </div>
    </div>
    <div bns-loadcertonalist class="text-center padding-bottom-xxs padding-top-xs">
        <a href="#" class="navyblue" style="font-size:1.4em;"><b>Back To Top</b></a>
    </div>
</div>
<#if seoH3?has_content>
<div class="seoTextContainer">
    <h3 class="categorySEOTitle">${seoH3?if_exists}</h3>
    <#if footerDesc?has_content><p class="categorySEOText">${footerDesc?replace("&lt;", "<")?replace("&gt;", ">")?replace("&#x2f;", "/")}</p></#if>
</div>
</#if>

<script src="<@ofbizContentUrl>/html/js/util/elasticSearch.js</@ofbizContentUrl>"></script>
<link href="<@ofbizContentUrl>/html/css/folders/search/search.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/folders/search/searchResults.css</@ofbizContentUrl>?ts=${pageTimestamp?default("655356")}" rel="stylesheet" />

<div class="jqs-search search foldersNewLimiter paddingTop20 paddingBottom20">
    <#include "../includes/breadcrumbs.ftl" />
    <link href="<@ofbizContentUrl>/html/css/product/collections.css</@ofbizContentUrl>" rel="stylesheet">

    <#if mode == 'colorGroup'>
        <div class="container search colors">
            <div id="jqs-refinements-result" class="results">
                <ul>
                    <#list colorGroups.keySet() as color>
                        <li class="collection" data-facet-id='${color.facetName?upper_case}' data-facet='<@ofbizUrl>/shopByColor/~category_id=${color.facetId?upper_case}</@ofbizUrl>'>
                            <div>
                                <a class="product-image" href="<@ofbizUrl>/shopByColor/~category_id=${color.facetId?upper_case}</@ofbizUrl>"><img src="<@ofbizContentUrl>/html/img/swatches/colorgroup/${color.facetId}.png</@ofbizContentUrl>" alt="${color.facetName}" /></a>
                                <a href="<@ofbizUrl>/shopByColor/~category_id=${color.facetId?upper_case}</@ofbizUrl>">
                                    <h2>${color.facetName}</h2>
                                    <div class="price">${colorGroups.get(color)} Product<#if colorGroups.get(color) != 1>s</#if></div>
                                </a>
                            </div>
                        </li>
                    </#list>
                </ul>
            </div>
        </div>
    <#elseif mode == 'color'>
        <div class="container search colors">
        <#if seoH1?has_content>
            <div class="shopByColorBanner">
                <h1 class="shopByColorFilter">${seoH1}</h1>
            </div>
            <#if pageDesc?has_content>
            <p class="shopByColorDescription">
                ${pageDesc}
            </p>
            </#if>
        </#if>
            <div id="jqs-refinements-result" class="results">
                <ul>
                    <#list colors.keySet() as color>
                        <li class="collection" data-facet-id='${color.facetId?upper_case}' data-name="${color.facetName}" data-color-group="${colorGroupName?if_exists}" data-facet='<@ofbizUrl>/shopByColor/~category_id=${color.facetId?upper_case}</@ofbizUrl>'>
                            <div>
                                <a class="product-image" href="<@ofbizUrl><#if color.facetId?upper_case == "BROWN_GROCERYBAG">/search?w=grocery+bag&c=0<#else>/shopByColor/~category_id=${color.facetId?upper_case}</#if></@ofbizUrl>"><img src="<@ofbizContentUrl>/html/img/swatches/color/${color.facetId}.png</@ofbizContentUrl>" alt="${color.facetName}" /></a>
                                <a href="<@ofbizUrl>/shopByColor/~category_id=${color.facetId?upper_case}</@ofbizUrl>">
                                    <h2>${color.facetName}</h2>
                                    <div class="price">${colors.get(color)} Product<#if colors.get(color) != 1>s</#if></div>
                                </a>
                            </div>
                        </li>
                    </#list>
                </ul>
            </div>
        </div>
    <#elseif mode == 'products'>
        <link rel="stylesheet" type="text/css" href="<@ofbizContentUrl>/html/css/product/search.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>">
        <link href="<@ofbizContentUrl>/html/css/util/paginator.css</@ofbizContentUrl>" rel="stylesheet" />

        <div class="jqs-searchResultsContainer jqs-search">
            ${screens.render("component://folders/widget/SearchScreens.xml#searchResults")}
        </div>
        <script src="<@ofbizContentUrl>/html/js/folders/search/search.js</@ofbizContentUrl>"></script>
    </#if>
</div>
<#if seoH3?has_content>
    <div class="seoTextContainer foldersNewLimiter">
        <h3 class="searchSEOTitle">${seoH3?if_exists}</h3>
        <#if footerDesc?has_content><p class="searchSEOText">${footerDesc?replace("&lt;", "<")?replace("&gt;", ">")?replace("&#x2f;", "/")}</p></#if>
    </div>
</#if>
<script>
    var searchPageType = "SEARCH_PAGE";
    $(function(){
        $('.collection').on('click', function() {
            <#if mode == 'colorGroup'>
                window.location.href = $(this).data('facet');
            <#else>
                window.location.href = '/' + websiteId + '/control/shopByColor/?category_id=' + $(this).data('facet-id');
            </#if>
        });
    });
</script>

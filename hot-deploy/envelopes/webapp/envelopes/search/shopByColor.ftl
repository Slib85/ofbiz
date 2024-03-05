<link href="<@ofbizContentUrl>/html/css/product/category-redesign.css</@ofbizContentUrl>" rel="stylesheet">
 
<div class="content category container">
    <#include "../includes/breadcrumbs.ftl" />
    <div id="jqs-refinements-widget">
        <!-- Begin Filter -->
        <link href="<@ofbizContentUrl>/html/css/product/filter.css</@ofbizContentUrl>" rel="stylesheet">
        <#if mode != 'products'>
        <div class="banner type-category jqs-category-header">
            <div class="row no-padding">
                <div class="tablet-desktop-only" style="background-color: #f7f8fa;height:86px;">
                    <div class="image-text">
                    <#--<span class="jqs-category-title">Shop By Color <#if colorGroupName?default('') != ''> - ${colorGroupName}</#if></span>-->
                        <h1 class="jqs-category-title">
                            <#if colorGroupName?default('') == ''>
                                Shop By Color
                            <#else>
                                <#if seoH1?has_content>${seoH1}</#if>
                            </#if>
                        </h1>
                    </div>
                    <div class="jqs-category-banner"><img src="<@ofbizContentUrl>/html/img/category/banners/desktop/<#if colorGroupName?default('') == ''>shopbycolor<#else>${colorGroupName?lower_case}</#if>.jpg</@ofbizContentUrl>" alt="${colorGroupName?default('')?capitalize} Envelopes &amp; Paper" /></div>
                </div>
                <div class="mobile-only">
                    <img src="<@ofbizContentUrl>/html/img/category/banners/mobile/<#if colorGroupName?default('') == ''>shopbycolor<#else>${colorGroupName?default('')?lower_case}</#if>.jpg</@ofbizContentUrl>" alt="${colorGroupName?default('')?capitalize} Envelopes &amp; Paper" />
                </div>
            </div>
        </div>
        <#if pageDesc?has_content>
            <p class="category-description no-margin">
                ${pageDesc}
            </p>
        </#if>
        </#if>
        <!-- Begin Results -->
        <link href="<@ofbizContentUrl>/html/css/product/collections-redesign.css</@ofbizContentUrl>" rel="stylesheet">

        <#if mode == 'colorGroup'>
            <div class="container search colors">
                <div id="jqs-refinements-result" class="results">
                    <ul>
                        <#list colorGroups.keySet() as color>
                            <li class="collection glow" data-facet-id='${color.facetName?upper_case}' data-facet='<@ofbizUrl>/shopByColor/~category_id=${color.facetId?upper_case}</@ofbizUrl>'>
                                <div>
                                    <a class="product-image" href="<@ofbizUrl>/shopByColor/~category_id=${color.facetId?upper_case}</@ofbizUrl>"><img src="<@ofbizContentUrl>/html/img/swatches/colorgroup/${color.facetId}.png</@ofbizContentUrl>" alt="${color.facetName}" /></a>
                                    <a href="<@ofbizUrl>/shopByColor/~category_id=${color.facetId?upper_case}</@ofbizUrl>">
                                        <h2>${color.facetName}</h2>
                                        <div class="price">${colorGroups.get(color)} Product<#if colorGroups.get(color) != 1>s</#if></div>
                                    </a>
                                </div>
                            </li>
                        </#list>
                        <!-- Non SLI Data -->
                        <li class="collection glow">
                            <div>
                                <a class="product-image" href="<@ofbizUrl>/luxpaper</@ofbizUrl>"><img src="<@ofbizContentUrl>/html/img/nav/products/shop_by_color/luxpaper.png</@ofbizContentUrl>" alt="LUXPaper" /></a>
                                <a href="<@ofbizUrl>/luxpaper</@ofbizUrl>">
                                    <h2>LUXPaper</h2>
                                    <div class="price">&nbsp;</div>
                                </a>
                            </div>
                        </li>
                        <li class="collection glow">
                            <div>
                                <a class="product-image" href="<@ofbizUrl>/search?af=col:metallics&w=*</@ofbizUrl>"><img src="<@ofbizContentUrl>/html/img/nav/products/shop_by_color/metallic.png</@ofbizContentUrl>" alt="Metallics" /></a>
                                <a href="<@ofbizUrl>/search?af=col:metallics&w=*</@ofbizUrl>">
                                    <h2>Metallics</h2>
                                    <div class="price">&nbsp;</div>
                                </a>
                            </div>
                        </li>
                        <li class="collection glow">
                            <div>
                                <a class="product-image" href="<@ofbizUrl>/search?w=recycled</@ofbizUrl>"><img src="<@ofbizContentUrl>/html/img/nav/products/shop_by_color/recycled.png</@ofbizContentUrl>" alt="Recycled" /></a>
                                <a href="<@ofbizUrl>/search?w=recycled</@ofbizUrl>">
                                    <h2>Recycled</h2>
                                    <div class="price">&nbsp;</div>
                                </a>
                            </div>
                        </li>
                        <li class="collection glow">
                            <div>
                                <a class="product-image" href="<@ofbizUrl>/category/~category_id=LINED?af=st:lined col:linedenvelopes</@ofbizUrl>"><img src="<@ofbizContentUrl>/html/img/nav/products/shop_by_color/lined.png</@ofbizContentUrl>" alt="LUXFoil Lined" /></a>
                                <a href="<@ofbizUrl>/category/~category_id=LINED?af=st:lined col:linedenvelopes</@ofbizUrl>">
                                    <h2>LUXFoil Lined</h2>
                                    <div class="price">&nbsp;</div>
                                </a>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        <#elseif mode == 'color'>
            <div class="container search colors">
                <div id="jqs-refinements-result" class="results">
                    <ul>
                       <#list colors.keySet() as color>
                           <li class="collection glow round-btn" data-facet-id='${color.facetId?upper_case}' data-name="${color.facetName}" data-color-group="${colorGroupName?if_exists}" data-facet='<@ofbizUrl>/shopByColor/~category_id=${color.facetId?upper_case}</@ofbizUrl>'>
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
            <#if banner?has_content || seoH1?has_content>
                <div class="genericPageHeader jqs-search-header">
                    <div class="section no-margin margin-top-xxs no-padding desktop-only searchContent" style="background: <#if bannerColor?has_content>${bannerColor};<#else>url('<@ofbizContentUrl>/html/img/search/banners/desktop</@ofbizContentUrl>/<#if seoImageName?has_content>${seoImageName}<#elseif banner?has_content>${banner.banner?if_exists.replace(" ", "")}</#if>_bg.jpg') repeat;</#if>">
                        <div class="pageHeaderText">
                            <div class="searchResultsHeaderTextContainer" style="color: #${seoH1Color?default("000000")} !important;">
                                <h1 class="jqs-search-title search-title"><#if seoH1?has_content>${seoH1?replace("&lt;", "<")?replace("&gt;", ">")?replace("&#x2f;", "/")}<#elseif banner?has_content><img src="<@ofbizContentUrl>/html/img/search/banners/desktop/${banner.banner.replace(" ", "")}_text.png</@ofbizContentUrl>" alt="${banner.bannerName?if_exists.replace(" ", "")}"></#if></h1>
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
                            <#if banner?has_content || seoImageName?has_content><img src="<@ofbizContentUrl>/html/img/search/banners/desktop</@ofbizContentUrl>/<#if seoImageName?has_content>${seoImageName}<#elseif banner?has_content>${banner.banner?if_exists.replace(" ", "")}</#if>.png" alt="<#if seoH1?has_content>${seoH1}<#elseif banner?has_content>${banner.bannerName?if_exists.replace(" ", "")}</#if>"/></#if>
                        </div>
                    </div>
                    <div class="headerImage text-center mobile-tablet-only margin-bottom-xxs jqs-search-mobile-banner">
                        <#if banner?has_content || seoImageName?has_content><img src="<@ofbizContentUrl>/html/img/search/banners/mobile</@ofbizContentUrl>/<#if seoImageName?has_content>${seoImageName}<#else>${banner.banner?if_exists.replace(" ", "")}</#if>.jpg" alt="<#if seoH1?has_content>${seoH1}<#elseif banner?has_content>${banner.bannerName?if_exists.replace(" ", "")}</#if>"/></#if>
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
            <script src="<@ofbizContentUrl>/html/js/util/elasticSearch.js</@ofbizContentUrl>"></script>
        </#if>
        <!-- End Results -->
    </div>
</div>
<#if seoH3?has_content>
    <div class="seoTextContainer">
        <h3 class="categorySEOTitle">${seoH3?if_exists}</h3>
        <#if footerDesc?has_content><p class="categorySEOText">${footerDesc?replace("&lt;", "<")?replace("&gt;", ">")?replace("&#x2f;", "/")}</p></#if>
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



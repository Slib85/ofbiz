<link href="<@ofbizContentUrl>/html/css/folders/search/search.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet" />
<script>
    var searchPageType = "SEARCH_PAGE";
    var stayInTheLoopSearchString = window.location.search.replace('?w=','');
	var stayInTheLoopFriendlySearchString = window.location.pathname;
    if(stayInTheLoopSearchString == '9x12%20folders' || stayInTheLoopFriendlySearchString == '/9-x-12-custom-folders') {
    	$('#stayInTheLoop').attr('bns-additionalsource', '9x12-folders');
    } else if (stayInTheLoopSearchString == '*&af=sale:clearance%20sale:sale' || stayInTheLoopFriendlySearchString == '/clearance') {
		$('#stayInTheLoop').attr('bns-additionalsource', 'clearance');
	}
</script>
<#if redirectData?has_content && redirectData.redirectUrl?has_content>
	<span id="redirectUrl" class="hidden">${redirectData.redirectUrl}</span>
	<script>
        window.location.href = $('#redirectUrl').text();
	</script>
</#if>

<div class="jqs-search search foldersNewLimiter" bns-certonasearch>
	<div class="marginTop20 marginBottom20">
		<#include "../includes/breadcrumbs.ftl" />
	</div>

	<div class="searchHeader<#if bannerData?has_content> noBackgroundColor</#if> marginBottom10">
		<#-- Desktop Only -->
		<#if bannerData?has_content>
			<div class="desktop-block searchHeaderContainer" style="background: url('/html/img/folders/search/banners/desktop/<#if seoImageName?has_content>${seoImageName}<#else>${bannerData.banner?html}</#if>_bg.jpg') repeat;">
				<div class="pageHeaderText<#if seoH1?has_content> seoH1Text</#if>">
					<h1><#if seoH1?has_content>${seoH1}<#else><img src="<@ofbizContentUrl>/html/img/folders/search/banners/desktop/${bannerData.banner}_text.png</@ofbizContentUrl>" alt="${bannerData.bannerName}" /></#if></h1>
				</div>
				<div class="pageHeaderImage textRight">
					<img src="<@ofbizContentUrl>/html/img/folders/search/banners/desktop</@ofbizContentUrl>/<#if seoImageName?has_content>${seoImageName}<#else>${bannerData.banner?html}</#if>.png" alt="<#if seoH1?has_content>${seoH1}<#else>${bannerData.bannerName?if_exists}</#if>" />
				</div>
			</div>
			<#-- Mobile / Tablet -->
			<div class="mobileTablet-block textCenter<#if seoH1?has_content> seoH1Mobile</#if>">
				<h1><#if seoH1?has_content>${seoH1}<#else><img src="<@ofbizContentUrl>/html/img/folders/search/banners/mobile</@ofbizContentUrl>/<#if seoImageName?has_content>${seoImageName}<#else>${bannerData.banner?html}</#if>.jpg" alt="<#if seoH1?has_content>${seoH1}<#else>${bannerData.bannerName?if_exists}</#if>" /></#if></h1>
			</div>
		<#elseif searchString?exists || seoH1?has_content>
			<div class="searchResult">
				<h1><#if seoH1?has_content>${seoH1}<#else>Search Results For: ${searchString}</#if></h1>
				<#if seoH2?has_content>
					<h2>${seoH2}</h2>
				</#if>
			</div>
		</#if>
		
		<#if pageDesc?has_content>
			<p class="searchDescription noMargin">${pageDesc}</p>
		<#elseif pageDescription?has_content>
			<p class="searchDescription noMargin">${pageDescription}</p>
		</#if>
	</div>
    <div class="foldersRow textCenter">
        <div data-slideout_name="filters" class="slideout_trigger filtersButtonMobile">Filters</div>
    </div>

	<div class="jqs-searchResultsContainer">
		${screens.render("component://folders/widget/SearchScreens.xml#searchResults")}
	</div>
	<div class="textCenter" bns-loadcertonalist>
		<#--  <div class="foldersButton buttonGreen" onclick="resultSetBackToTop()">Back To Top</div>  -->
		<div class="foldersButton buttonGreen" bns-backToTop >Back To Top</div>
	</div>
	
</div>
<#if seoH3?has_content>
	<div class="seoTextContainer foldersNewLimiter">
		<h3 class="searchSEOTitle">${seoH3?if_exists}</h3>
    	<#if footerDesc?has_content><p class="searchSEOText">${footerDesc?replace("&lt;", "<")?replace("&gt;", ">")?replace("&#x2f;", "/")}</p></#if>
	</div>
</#if>

<#-- we do this to avoid html encoding -->

<script src="<@ofbizContentUrl>/html/js/folders/search/search.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
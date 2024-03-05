<link href="<@ofbizContentUrl>/html/css/product/category-redesign.css</@ofbizContentUrl>" rel="stylesheet">
<link href="<@ofbizContentUrl>/html/css/product/sizes.css</@ofbizContentUrl>" rel="stylesheet">
<link href="<@ofbizContentUrl>/html/css/category/shopBySize.css</@ofbizContentUrl>" rel="stylesheet">
<script>
    var searchPageType = 'CATEGORY_PAGE';
</script>

<div class="content container shop-by-size category">
    <#include "../includes/breadcrumbs.ftl" />
    
    <div id="jqs-refinements-widget" style="margin-bottom:40px">
        <!-- Begin Filter -->
        <link href="<@ofbizContentUrl>/html/css/product/filter.css</@ofbizContentUrl>" rel="stylesheet">
		<div class="genericPageHeader">
			<div class="section no-margin margin-top-xxs no-padding jqs-category-header desktop-only">
				<div class="pageHeaderText">
					<h1 class="jqs-category-title category-title navyblue">Shop By Envelope Size</h1>
				</div>
				<div class="headerImage">
					<img src="<@ofbizContentUrl>/html/img/category/banners/desktop/shopbysize.jpg</@ofbizContentUrl>" alt="Shop By Size" />
				</div>
			</div>
			<div class="headerImage text-center mobile-tablet-only margin-bottom-xxs">
				<img src="<@ofbizContentUrl>/html/img/category/banners/mobile/shopbysize.jpg</@ofbizContentUrl>" alt="Shop By Size" />
			</div>
			<p class="category-description no-margin">
				Choose from a huge selection of envelope sizes in both announcement and corporate styles. We carry plain and printed envelopes in every category from
				regular business envelopes to unique square envelopes, mini envelopes, plastic mailers, padded bubble mailers, paperboard mailers, shipping envelopes
				and more.
			</p>
		</div>
    </div>
    <div class="jqs-search">
        <div class="padding-xxs margin-top-xs size-selection-content">
            <div class="row">
                <div class="sizesFilterRow">
                    <div class="selection-header">Choose a standard envelope size:</div>
                    <div>
                        <div class="margin-top-xxs envelope-select margin-left-xs left-select">
                            <select name="si" data-paramname="af">
                                <option value="">All Sizes</option>
                                <optgroup label="Popular Sizes">
                                    <#list staticSizeMap.keySet() as size>
                                        <option value="${staticSizeMap.get(size)}">${size}</option>
                                    </#list>
                                </optgroup>
                                <optgroup label="Other Sizes">
                                    <#assign filterableMap = Static["com.bigname.search.elasticsearch.SearchField"].getFilterableMap(globalContext.webSiteId) />
                                    <#list filterableMap.keySet() as filter>  
                                        <#assign filterId = filterableMap.get(filter) />
                                        <#if filterId == "si">
                                            <#if aggregations.get(filterId)?exists && aggregations.get(filterId).isEmpty()?c == "false">
                                                <#list aggregations[filterId].entrySet() as aggFilter>
                                                    <option value="${aggFilter.getKey().facetId}">${aggFilter.getKey().facetName}</option>
                                                </#list>
                                            </#if>
                                            <#break>
                                        </#if>
                                    </#list>
                                </optgroup>
                            </select>
                        </div>
                     </div>
                </div>
            </div>
        </div>
        <div class="jqs-searchResultsContainer">
            ${screens.render("component://envelopes/widget/CategoryScreens.xml#categoryResults")}
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        $('select[name="si"]').off('change.getSize').on('change.getSize', function() {
            elasticSearchResults.getResults('af', ($(this).val() == '' ? '' : $(this).attr('name') + ':' + $(this).val()), ['page', 'af']);
        });
    });
</script>

<script src="<@ofbizContentUrl>/html/js/util/elasticSearch.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
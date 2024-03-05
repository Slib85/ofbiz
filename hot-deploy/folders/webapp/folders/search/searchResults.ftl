<link href="<@ofbizContentUrl>/html/css/folders/search/searchResults.css</@ofbizContentUrl>?ts=${pageTimestamp?default("655356")}" rel="stylesheet" />

<div class="searchResults foldersTabularRow">
	<#if totalHits gt 0>
		<div id="filters" class="slideout_result" style="width:270px;padding:20px;background-color: #f1f1f1;">
			<div class="filtersSet" style="display:block;position:-webkit-sticky;position:sticky;top:20px;">
				<div class="slideout_header mobileTablet-block">
					<div data-go_back="">&lt; Back</div>
					<h2>Filters</h2>
				</div>
				<div class="slideout_body">
				<#if requestParameters.af?exists && requestParameters.af != ""><div class="clearFilters jqs-clearFilters">Clear All Filters</div></#if>
				<#assign filterableMap = Static["com.bigname.search.elasticsearch.SearchField"].getFilterableMap(globalContext.webSiteId) />
				<#list filterableMap.keySet() as key>
					<#assign filterId = filterableMap.get(key) />
					<#if aggregations.get(filterId)?exists && aggregations.get(filterId).isEmpty()?c == "false">
						<div bns-filterheader class="foldersRow filterHeader">
							<div class="foldersColumn large9 noPadding noMargin">
								${Static["com.bigname.search.elasticsearch.SearchField"].getFilterDescription(filterId, globalContext.webSiteId)}
								<i bns-togglefilters class="fa fa-caret-up"></i>
							</div>
						</div>
						<div class="filterOptions paddingBottom5">
							<#list aggregations[filterId].entrySet() as aggFilter>
								<#if aggFilter_index == 6><div bns-morefilteroptions style="display: none;"></#if>
								<div>
									<input bns-searchresults="change" data-paramname="af" id="filter-${filterId}-${aggFilter.getKey().facetName}" type="checkbox" name="${filterId}" class="foldersCheckbox" value="${aggFilter.getKey().facetId}"<#if requestParameters.af?exists && requestParameters.af?matches("^.*?" + filterId + "&#x3a;" + aggFilter.getKey().facetId + "(?: .*?$|$)")> checked</#if> />
									<label for="filter-${filterId}-${aggFilter.getKey().facetName}">${aggFilter.getKey().facetName?capitalize} <span>(${aggFilter.getValue()?if_exists})</span></label>
								</div>
								<#assign totalFilters = aggFilter_index />
							</#list>
							<#if totalFilters gte 6>
								</div>
								<div bns-additionalfilters="more"><span>+</span> Show More</div>
							</#if>
						</div>
					</#if>
				</#list>
				</div>
			</div>
		</div>
		<div class="resultsSet">
			<div class="foldersRow resultSetHeader textLeft">
				<div class="pullLeft">${totalHits} Results</div>
				<div class="pullRight">
					<div>
						Sort By:
						<label class="bigNameSelect sortByFilter">
							<select bns-searchresults="change" data-paramname="sort" class="jqs-filterSort" name="filterSort">
								<option value="">Relevance</option>
								<option value="price+asc" <#if requestParameters.sort?has_content && (requestParameters.sort?replace(" ", "+") == "price&#x2b;asc" || requestParameters.sort?replace(" ", "+") == "price+asc")>selected</#if>>Price: Low to High</option>
								<option value="price+desc" <#if requestParameters.sort?has_content && (requestParameters.sort?replace(" ", "+") == "price&#x2b;desc" || requestParameters.sort?replace(" ", "+") == "price+desc")>selected</#if>>Price: High to Low</option>
								<option value="avgrating+desc" <#if requestParameters.sort?has_content && (requestParameters.sort?replace(" ", "+") == "avgrating&#x2b;desc" || requestParameters.sort?replace(" ", "+") == "avgrating+desc")>selected</#if>>Most Reviewed</option>
                                <option value="createdstamp+desc" <#if requestParameters.sort?has_content && (requestParameters.sort?replace(" ", "+") == "createdstamp&#x2b;desc" || requestParameters.sort?replace(" ", "+") == "createdstamp+desc")>selected</#if>>New Arrivals</option>
                                <option value="measurement+asc" <#if requestParameters.sort?has_content && (requestParameters.sort?replace(" ", "+") == "measurement&#x2b;asc" || requestParameters.sort?replace(" ", "+") == "measurement+asc")>selected</#if>>Size: Small to Large</option>
                                <option value="measurement+desc" <#if requestParameters.sort?has_content && (requestParameters.sort?replace(" ", "+") == "measurement&#x2b;desc" || requestParameters.sort?replace(" ", "+") == "measurement+desc")>selected</#if>>Size: Large to Small</option>
							</select>
						</label>
					</div>
				</div>
            </div>
			<ul class="foldersFlexRow flexCenter">
			<#list hits as hit>
				<#if hit.getSourceAsMap()?exists>
				<li>
					<a class="positionRelative" href="<@ofbizUrl>${hit.getSourceAsMap().url?if_exists}</@ofbizUrl>" data-sort="<#list hit.getSortValues() as sortValues>${sortValues}<#if sortValues_has_next> </#if></#list>">
						<#if hit.getSourceAsMap().get("customizable")?exists && hit.getSourceAsMap().get("customizable") == 'Y'>
							<!-- <img bns-customizablebutton class="searchCustomBlankButton" src="<@ofbizScene7Url>/is/image/ActionEnvelope/blankButton?fmt=png-alpha&amp;wid=100</@ofbizScene7Url>" alt="Customizable" /> -->
							<div class="customizableRibbon">Customizable
								<div class="ribbonTop customizableRibbonTop"></div>
								<div class="ribbonBottom customizableRibbonBottom"></div>
							</div>
						<#else>
							<div class="blankRibbon">Blank
								<div class="ribbonTop blankRibbonTop"></div>
								<div class="ribbonBottom blankRibbonBottom"></div>
							</div>
						</#if>
						<div class="paddingTop20">
							<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${hit.getSourceAsMap().productid?if_exists}?hei=170&amp;wid=190</@ofbizScene7Url>" alt="${hit.getSourceAsMap().name?if_exists}" />
						</div>
						<h2 bns-hitname class="ftc-blue marginTop5">${hit.getSourceAsMap().name?if_exists}</h2>
						<h4 bns-searchresultscolor>
							<span bns-hitsku class="searchResultsSku">SKU: ${hit.getSourceAsMap().get("productid")?if_exists}<br />
						</h4>
						<#if hit.getSourceAsMap().get("size")?has_content>
                        <ul class="searchResultsProductInfo">
                        	<li>${hit.getSourceAsMap().get("size")}</li>
                        	<#if hit.getSourceAsMap().get("color")?exists><li>${hit.getSourceAsMap().get("color")} ${hit.getSourceAsMap().get("paperweight")?if_exists} ${hit.getSourceAsMap().get("papertexture")?if_exists}</li></#if>
                            <#if hit.getSourceAsMap().get("pocketspec")?exists><li>${hit.getSourceAsMap().get("pocketspec")}</li></#if>
                            <#if hit.getSourceAsMap().get("tabsize")?exists><li>${hit.getSourceAsMap().get("tabsize")}</li></#if>
                            <#if hit.getSourceAsMap().get("spinesize")?exists><li>${hit.getSourceAsMap().get("spinesize")}</li></#if>
                            <#if hit.getSourceAsMap().get("totalcolors")?exists && hit.getSourceAsMap().get("totalcolors")?number gt 1><li class="ftc-lightBlue">${hit.getSourceAsMap().get("totalcolors")} Colors Available</li></#if>
                        </ul>
                        </#if>
						<#if hit.getSourceAsMap().get("basequantity")?has_content>
						<div class="marginBottom10">${hit.getSourceAsMap().get("basequantity")} Minimum Quantity</div>
						</#if>
						<div class="foldersRow">
							<#if hit.getSourceAsMap().get("onsale")?exists && (hit.getSourceAsMap().get("onsale") == 'Clearance' || hit.getSourceAsMap().get("onsale") == 'Sale')>
								<div class="salePercentOff">${hit.getSourceAsMap().get("percentsavings")}% Off</div>
							</#if>
							<#if hit.getSourceAsMap().new?default('N') == 'Y'>
								<div class="newArrivalFlag">New</div>
							</#if>
						</div>
						<div class="foldersRow">
							<div class="noPadding foldersColumn large6 noMargin searchResultRating">
								<span class="rating-${(hit.getSourceAsMap().get("avgrating"))?replace(".", "_")?replace("000$", "", "r")}"></span>
							</div>
							<div class="noPadding foldersColumn large6 textRight ftc-lightBlue noMargin searchResultPrice">
								<#if hit.getSourceAsMap().get("customizable")?exists && hit.getSourceAsMap().get("customizable") == 'Y'>
									<#if (!hit.getSourceAsMap().get("baseprice")?exists || (hit.getSourceAsMap().get("baseprice")?has_content && hit.getSourceAsMap().get("baseprice")?number gt 0)) || hit.getSourceAsMap().parentid?has_content && hit.getSourceAsMap().parentid == "FOLDER_STYLE">
										Get a Quote
									<#else>
	                                    Instant Pricing
									</#if>
								<#elseif hit.getSourceAsMap().get("printable")?exists && hit.getSourceAsMap().get("printable")?upper_case == "N">
									<#if hit.getSourceAsMap().get("onsale")?exists && (hit.getSourceAsMap().get("onsale") == 'Clearance' || hit.getSourceAsMap().get("onsale") == 'Sale')>
										<div class="strikeThrough"><@ofbizCurrency amount=hit.getSourceAsMap().get("originalprice") /></div>
										<div class="salePrice"><@ofbizCurrency amount=hit.getSourceAsMap().get("baseprice") /></div>
									<#else>
										From <@ofbizCurrency amount=hit.getSourceAsMap().get("baseprice") />
									</#if>
								</#if>
							</div>
						</div>
					</a>
				</li>	
				</#if>
			</#list>
			</ul>
			<#if pages?exists && pages?number gt 1>
				<ul class="pagination foldersRow noMargin noPadding marginTop10 marginBottom20 textCenter"><li bns-searchresults="click" data-paramname="page" data-page="<#if currentPage?exists>${currentPage?number - 1}</#if>" class="arrowLeft<#if currentPage?exists && currentPage?number == 0> jqs-unavailable unavailable</#if>">
						<i class="fa fa-chevron-left"></i>
					</li><#list 1..pages as i><li bns-searchresults="click" data-paramname="page" data-page="${i?number - 1}"<#if currentPage?exists && (currentPage?number == i?number - 1)> class="jqs-current current"</#if>>
						${i}
					</li></#list><li bns-searchresults="click" data-paramname="page" data-page="<#if currentPage?exists>${currentPage?number + 1}</#if>" class="arrowRight<#if currentPage?exists && currentPage?number == (pages?number - 1)> jqs-unavailable unavailable</#if>">
						<i class="fa fa-chevron-right"></i>
					</li>
				</ul>
			</#if>
		</div>
	<#else>
		<div bns-foldersnosearchresults class="textCenter">There are no results.</div>
	</#if>
</div>

<#assign filterUrl = "" />
<#list requestParameters.keySet() as key>
	<#assign filterUrl = filterUrl + "&" + key + "=" + requestParameters.get(key) />
</#list>
<div bns-filterurl="${filterUrl?default("")}" class="hidden"></div>

<script src="<@ofbizContentUrl>/html/js/folders/search/searchResults.js</@ofbizContentUrl>"></script>

<script type="text/javascript">
	activeFilterTimeout = false;
	$('[bns-togglefilters]').off('click.toggleFilter').on('click.toggleFilter', function() {
		var element = $(this);
		if (!activeFilterTimeout) {
			console.log(1);
			activeFilterTimeout = true;
			var filterHeader = element.parents('[bns-filterheader]');
			var endRotation = -180;
			var currentRotation = 0;

			if (filterHeader.next().css('display') != 'none') {
				element.css('transform', 'rotate(0deg)');
				element.parents('[bns-filterheader]').next().slideUp();
			} else {
				endRotation = 0;
				currentRotation = -180;
				element.css('transform', 'rotate(-180deg)');
				element.parents('[bns-filterheader]').next().slideDown();
			}
			
			var toggleFilterTimeout = window.setInterval(function() {
				if (endRotation == 0 && currentRotation < endRotation) {
					currentRotation += 6;
				} else if (endRotation == -180 && currentRotation > endRotation) {
					currentRotation -= 6;
				}

				element.css('transform', 'rotate(' + currentRotation + 'deg)');

				if (endRotation == currentRotation) {
					activeFilterTimeout = false;
					clearInterval(toggleFilterTimeout);
				}
			}, 5);

			toggleFilterTimeout;
		}
	});

	$('[bns-additionalfilters]').off('click.showMoreOrLessFilters').on('click.showMoreOrLessFilters', function() {
		if ($(this).attr('bns-additionalfilters') == 'more') {
			$(this).siblings('[bns-morefilteroptions]').slideDown();
			$(this).attr('bns-additionalfilters', 'less');
			$(this).html(' Show Less').prepend(
				$('<span />').html('-')
			);
		} else {
			$(this).siblings('[bns-morefilteroptions]').slideUp();
			$(this).attr('bns-additionalfilters', 'more');
			$(this).html(' Show More').prepend(
				$('<span />').html('+')
			);
		}
	});
</script>
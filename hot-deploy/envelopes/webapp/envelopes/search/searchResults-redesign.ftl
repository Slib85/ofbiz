<#if totalHits?exists && totalHits gt 0>
	<#include "../includes/elasticFilters.ftl" />
	<div bns-elasticsearchcontainer class="container search">
		<div class="results">
			<ul>
				<#list hits as hit>
					<#if hit.getSourceAsMap()?exists>
						<li class="jqs-item glow" data-sort="<#list hit.getSortValues() as sortValues>${sortValues}<#if sortValues_has_next> </#if></#list>">
							<#if hit.getSourceAsMap().new?default('N') == 'Y'>
								<div class="newRibbon jqs-ribbon">
									New 
									<div class="ribbonTop"></div>
									<div class="ribbonBottom"></div>
								</div>
							</#if>
							<#if hit.getSourceAsMap().onsale?exists && hit.getSourceAsMap().onsale == 'Sale'>
								<div class="saleRibbon jqs-ribbon">
									${hit.getSourceAsMap().percentsavings}% OFF
									<div class="ribbonTop"></div>
									<div class="ribbonBottom"></div>
								</div>
							</#if>
							
							<#if hit.getSourceAsMap().onsale?exists && hit.getSourceAsMap().onsale == 'Clearance'>
								<div class="clearanceRibbon jqs-ribbon">
									${hit.getSourceAsMap().percentsavings}% OFF
									<div class="ribbonTop"></div>
									<div class="ribbonBottom"></div>
								</div>
							</#if>
							<a href="<@ofbizUrl>${hit.getSourceAsMap().url?if_exists?replace("&#x2f;", "/")?replace("&#x7e;", "~")?replace("&#x3d;", "=")}</@ofbizUrl>">
								<div class="tablet-desktop-only">
									<#--<br /><br />
									<@ofbizUrl>${hit.getSourceAsMap().url?replace("&#x2f;", "/")?replace("&#x7e;", "~")?replace("&#x3d;", "=")}</@ofbizUrl>
									<br /><br />-->
									<div class="product-image"><img src="<#if hit.getSourceAsMap().producttype == "Products"><@ofbizScene7Url>/is/image/ActionEnvelope/${hit.getSourceAsMap().productid?if_exists}?wid=175&amp;hei=110&amp;fmt=jpeg&amp;qlt=75&amp;bgc=ffffff</@ofbizScene7Url><#else>//texel.envelopes.com/getBasicImage?id=${hit.getSourceAsMap().productid?if_exists}&amp;fmt=png&amp;wid=175</#if>" class="prodImage" alt="${hit.getSourceAsMap().name?if_exists}"></div>
									<h2 style="font-size: 17px;">${hit.getSourceAsMap().name?if_exists}<#if hit.getSourceAsMap().get("size")?exists> <span class="item-size">(${hit.getSourceAsMap().get("size")})</span></#if></h2>
									<h3>${hit.getSourceAsMap().productid?if_exists}</h3>
									<h3>${hit.getSourceAsMap().color?if_exists}</h3>
									<div class="ratings rating-${(hit.getSourceAsMap().get("avgrating"))?replace(".", "_")?replace("000$", "", "r")}"></div>
									<div class="price">From <@ofbizCurrency amount=(hit.getSourceAsMap().baseprice)?default(0) /> / ${hit.getSourceAsMap().basequantity?if_exists}</div>
								</div>
								<div class="mobile-only-table">
									<div class="mobileSearchImageContainer">
										<div><img src="<#if hit.getSourceAsMap().producttype == "Products"><@ofbizScene7Url>/is/image/ActionEnvelope/${hit.getSourceAsMap().productid?if_exists}?wid=175&amp;hei=110&amp;fmt=jpeg&amp;qlt=75&amp;bgc=ffffff</@ofbizScene7Url><#else>//texel.envelopes.com/getBasicImage?id=${hit.getSourceAsMap().productid?if_exists}&amp;fmt=png&amp;wid=175</#if>" class="prodImage" alt="${hit.getSourceAsMap().name?if_exists}"></div>
									</div>
									<div class="padding-left-xxs">
										<h2>${hit.getSourceAsMap().name?if_exists}<#if hit.getSourceAsMap().get("size")?exists> <span class="item-size">${hit.getSourceAsMap().get("size")}</span></#if></h2>
										<h3>${hit.getSourceAsMap().productid?if_exists}</h3>
										<h3>${hit.getSourceAsMap().color?if_exists}</h3>
										<div class="ratings rating-${(hit.getSourceAsMap().get("avgrating"))?replace(".", "_")?replace("000$", "", "r")}"></div>
										<div class="price">From <@ofbizCurrency amount=(hit.getSourceAsMap().baseprice)?default(0) /> / ${hit.getSourceAsMap().basequantity?if_exists}</div>
									</div>
								</div>
							</a>
						</li>
					</#if>
				</#list>
			</ul>
		</div>
	</div>

	<#assign minPage = 1 />
	<#if currentPage?number gt 3>
		<#assign minPage = currentPage?number - 2>
	</#if>
	<#assign maxPage = minPage + 6 />
	<#if maxPage gt pages>
		<#assign maxPage = pages />
	</#if>

	<#if pages?exists && pages?number gt 1>
	<div class="page-selection margin-right-xxs margin-top-xxs margin-bottom-xs">
		<div class="pagination-centered">
			<ul class="pagination no-margin">
				<li bns-searchresults="click" data-paramname="page" data-page="<#if currentPage?exists>${currentPage?number - 1}</#if>" class="arrow arrow-left<#if currentPage?exists && currentPage?number == 0> unavailable jqs-unavailable</#if>">
                    <a href="#" onclick="return false;"><i class="fa fa-chevron-left"></i></a>
				</li><#list minPage..maxPage as i><li bns-searchresults="click" data-paramname="page" data-page="${i?number - 1}"<#if currentPage?exists && (currentPage?number == i?number - 1)> class="jqs-current current"</#if> style="display: inline-block;"><a href="#" onclick="return false;" <#if i?number - 1 gte 4> rel="nofollow"</#if>>${i}</a></li></#list><li bns-searchresults="click" data-paramname="page" data-page="<#if currentPage?exists>${currentPage?number + 1}</#if>" class="arrow arrow-right<#if currentPage?exists && currentPage?number == (pages?number - 1)> jqs-unavailable unavailable</#if>">
                <a href="#" onclick="return false;"><i class="fa fa-chevron-right"></i></a>
				</li>
			</ul>
		</div>
	</div>
	</#if>
</#if>
<div bns-filterurl="${filterUrl?default("")}" class="hidden"></div>

<script src="<@ofbizContentUrl>/html/js/search/searchResults.js</@ofbizContentUrl>"></script>
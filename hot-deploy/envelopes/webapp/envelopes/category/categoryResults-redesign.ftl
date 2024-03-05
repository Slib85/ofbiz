<#assign imgFmtQlty = 80 />

<#if hits?has_content>
	<#include "../includes/elasticFilters.ftl" />
	<div bns-elasticsearchcontainer class="container category">
		<#if staticCategoryProducts?has_content>
			<div class="row padding-bottom-xs margin-bottom-xs featuredProducts">
				<div class="row no-margin">
					<div class="medium-8 large-8 columns">
						<h2 class="jqs-product-name featuredProductRibbon">
							Most Popular Products
							<div class="ribbonTop"></div>
							<div class="ribbonBottom"></div>
						</h2>
					</div>
				</div>
				<div class="slideIt-container margin-top-xs" style="height: 250px;">
					<div class="slideIt-left">
						<#--  <i class="fa fa-chevron-left"></i>  -->
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleleft-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
					</div>
					<div class="slideIt text-left" style="padding-top: 16px;">
						<div class="jqs-variants jqs-featured-products">
							<#if staticCategoryDesigns?has_content><#list staticCategoryDesigns as staticCategoryDesign><div class="jqs-variant tablet-desktop-only-inline-block">
								<a href="<@ofbizUrl>/designProduct?product_id=${staticCategoryDesign.productId?if_exists}&amp;designId=${staticCategoryDesign.designId?if_exists}</@ofbizUrl>">
									<div class="categoryListingImageContainer">
										<img src="<@ofbizContentUrl>${staticCategoryDesign.imageName?if_exists}</@ofbizContentUrl>" alt="${staticCategoryDesign.name?if_exists}" style="max-height: 96px;" />
									</div>
									<div class="text-center margin-top-xxs">${staticCategoryDesign.name?if_exists}<br />Printed w/<br />${staticCategoryDesign.type?if_exists}</div>
								</a>
							</div></#list></#if><#list staticCategoryProducts as staticCategoryProduct><#if staticCategoryProduct.isValid() && staticCategoryProduct.isActive()><div class="glow"><div style="width: 150px; margin:20px;"><div class="jqs-variant">
							<a class="jqs-variant-url" data-sku="${staticCategoryProduct.getId()}" href="<@ofbizUrl>/product/~category_id=${staticCategoryProduct.getCategoryId()}/~product_id=${staticCategoryProduct.getId()}</@ofbizUrl>" title="<#if staticCategoryProduct.getSize()?exists && staticCategoryProduct.getSize() != staticCategoryProduct.getActualSize()>${staticCategoryProduct.getSize()} - (${staticCategoryProduct.getActualSize()})<#else>${staticCategoryProduct.getActualSize()}</#if> ${staticCategoryProduct.getColor()}">
								<img class="jqs-variant-image" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${staticCategoryProduct.getId()}?wid=150&amp;hei=96&amp;bgc=ffffff&amp;qlt=${imgFmtQlty}</@ofbizScene7Url>" alt="<#if staticCategoryProduct.getSize()?exists && staticCategoryProduct.getSize() != staticCategoryProduct.getActualSize()>${staticCategoryProduct.getSize()} - (${staticCategoryProduct.getActualSize()})<#else>${staticCategoryProduct.getActualSize()}</#if> ${staticCategoryProduct.getColor()}" />
								<span class="margin-top-xxs text-center jqs-color navyblue"><#if staticCategoryProduct.getSize()?exists && staticCategoryProduct.getSize() != staticCategoryProduct.getActualSize()>${staticCategoryProduct.getSize()} - <span class="actualSize">(${staticCategoryProduct.getActualSize()})</span><#else>${staticCategoryProduct.getActualSize()}</#if><br />${staticCategoryProduct.getColor()}</span>
								<div class="text-center jqs-productSavings <#if staticCategoryProduct.percentSavings()?default(0)?number gt 0><#if staticCategoryProduct.onSale()?c == "false">clearanceBGColor<#else>saleBGColor</#if><#elseif staticCategoryProduct.isNew()?c == 'true'>newProductBGColor</#if>"><#if staticCategoryProduct.percentSavings()?default(0)?number gt 0>${staticCategoryProduct.percentSavings()}% Off<#elseif staticCategoryProduct.isNew()?c == 'true'>NEW</#if></div></div></div>
							</a>
						</div></#if></#list>
						</div>
					</div>
					<div class="slideIt-right">
						<#--  <i class="fa fa-chevron-right"></i>  -->
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleright-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
					</div>
				</div>
			</div>
		</#if>
		<#list hits.keySet() as parentid>
			<div class="row padding-bottom-xs margin-bottom-xs" style="padding-top: 15px; width: 95%; background-color: transparent; border-bottom: 1.5px dotted #E0E0E0; margin: auto;">
				<div class="row no-margin">
					<div class="small-12 medium-9 large-10 columns categoryProdInfo no-padding text-left">
						<a class="jqs-product-url" href="<#list hits.get(parentid) as hit><#if hit.getSourceAsMap()?exists><@ofbizUrl>${hit.getSourceAsMap().url}</@ofbizUrl></#if><#break></#list>">
							<h2 style="font-size: 26px; font-weight: 400; display: inline-block;">${hits.get(parentid).get(0).getSourceAsMap().name?default("")}</h2>
							<h3 style="font-size: 24px; font-weight: 300; display: inline-block; color:#1a345f;"> <span class="tablet-desktop-only-inline-block">-</span> ${hits.get(parentid).get(0).getSourceAsMap().get("size")?if_exists}</h3>
						</a>
						<!--<#assign tagLine = Static["com.envelopes.product.ProductHelper"].getTagline(delegator, hits.get(parentid).get(0).getSourceAsMap().parentid)?if_exists />
						<#if tagLine?has_content><p class="categoryTagline">${tagLine}</p></#if>-->
					</div>
					<div class="small-12 medium-3 large-2 columns product-url navyblue no-padding" style="text-align: right; font-style: italic;" id="prod_All_CTA">
						<a href="<#list hits.get(parentid) as hit><#if hit.getSourceAsMap()?exists><@ofbizUrl>${hit.getSourceAsMap().url}</@ofbizUrl></#if><#break></#list>">See All Colors (${hits.get(parentid).size()})</a>
					</div>
				</div>

				<div class="slideIt-container margin-top-xs" style="height: 250px;">
					<div class="slideIt-left" style="cursor: default; opacity: 0;">
						<#--  <i class="fa fa-chevron-left"></i>  -->
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleleft-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
					</div>
					<div class="slideIt text-left" style="padding-top: 16px;">
						<ul class="no-margin">
							<#list hits.get(parentid) as hit><#if hit.getSourceAsMap()?exists><div class="glow"><li style="width: 150px; margin:20px; list-style: none;" data-sort="<#list hit.getSortValues() as sortValues>${sortValues}<#if sortValues_has_next> </#if></#list>">
								<a style="display: block;" href="<@ofbizUrl>${hit.getSourceAsMap().url}</@ofbizUrl>" title="${hit.getSourceAsMap().color?if_exists} | Envelopes.com">
									<img class="<#if hit_index gt 10>jqs-defer-img</#if>" <#if hit_index gt 10>src="data:image/png;base64,R0lGODlhAQABAAD/ACwAAAAAAQABAAACADs=" data-</#if>src="<#if hit.getSourceAsMap().producttype == "Products"><@ofbizScene7Url>/is/image/ActionEnvelope/${hit.getSourceAsMap().productid?if_exists}?wid=150&amp;hei=96&amp;fmt=jpeg&amp;qlt=${imgFmtQlty}&amp;bgc=ffffff</@ofbizScene7Url><#else>//texel.envelopes.com/getBasicImage?id=${hit.getSourceAsMap().productid?if_exists}&amp;fmt=png&amp;wid=175</#if>" alt="${hits.get(parentid).get(0).getSourceAsMap().get("name")?if_exists} ${hit.getSourceAsMap().color?if_exists}" onerror="">
									<span class="margin-top-xxs text-center navyblue" style="white-space: normal; display: block; font-weight:700;">${hit.getSourceAsMap().color?if_exists}</span>
									<div bns-productSavings class="text-center<#if hit.getSourceAsMap().percentsavings?default(0)?number gt 0><#if hit.getSourceAsMap().onsale?has_content && hit.getSourceAsMap().onsale == "Clearance"> clearanceBGColor<#else> saleBGColor</#if><#elseif hit.getSourceAsMap().new?default('N') == 'Y'> newProductBGColor</#if>"><#if hit.getSourceAsMap().percentsavings?default(0)?number gt 0>${hit.getSourceAsMap().percentsavings}% Off<#elseif hit.getSourceAsMap().new?default('N') == 'Y'>NEW</#if></div>
								</a>
							</li></div></#if></#list>
						</ul>
					</div>
					<div class="slideIt-right" style="cursor: pointer; opacity: 1;">
						<#--  <i class="fa fa-chevron-right"></i>  -->
						<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleright-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
					</div>
				</div>
			</div>
		</#list>
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
	<div bns-pagination class="page-selection margin-right-xxs margin-top-xxs margin-bottom-xs">
		<div class="pagination-centered">
			<ul class="pagination no-margin">
				<li bns-searchresults="click" data-paramname="page" data-page="<#if currentPage?exists>${currentPage?number - 1}</#if>" class="arrow arrow-left<#if currentPage?exists && currentPage?number == 0> unavailable jqs-unavailable</#if>">
                    <a href="#" onclick="return false;"><i class="fa fa-chevron-left"></i></a>
				</li><#list minPage..maxPage as i><li bns-searchresults="click" data-paramname="page" data-page="${i?number - 1}"<#if currentPage?exists && (currentPage?number == i?number - 1)> class="jqs-current current"</#if> style="display: inline-block;"><a href="#" onclick="return false;">${i}</a></li></#list><li bns-searchresults="click" data-paramname="page" data-page="<#if currentPage?exists>${currentPage?number + 1}</#if>" class="arrow arrow-right<#if currentPage?exists && currentPage?number == (pages?number - 1)> jqs-unavailable unavailable</#if>">
                <a href="#" onclick="return false;"><i class="fa fa-chevron-right"></i></a>
				</li>
			</ul>
		</div>
	</div>
	</#if>
	<#if topReviewList?has_content>
    <div bns-mostrecentreviews class="row padding-bottom-xs margin-bottom-xs jqs-product">
        <div class="row no-margin">
            <div class="small-12 large-12 columns">
                <a href="#" onclick="return false;" class="jqs-product-url no-hover" itemprop="url">
                    <h2 class="jqs-product-name navyblue">Most Recent Reviews</h2>
                </a>
            </div>
        </div>
        <div class="slideIt-container customer-reviews">
            <div class="slideIt-left">
                <#--  <i class="fa fa-chevron-left"></i>  -->
				<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleleft-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
            </div>
            <div class="slideIt text-left" style="padding-top:30px;">
                <div class="jqs-variants">
					<#list topReviewList as review><div class="review">
                        <div class="cr-header">
                            <div class="cr-img">
                                <img src="<@ofbizScene7Url>/is/image/ActionEnvelope/${review.productId}?qlt=${imgFmtQlty}&amp;bgc=247, 248, 250</@ofbizScene7Url>" alt="Review Rating" />
                            </div>
                            <div class="cr-header-right">
                                <a href="<@ofbizUrl>/product/~category_id=${review.primaryProductCategoryId?default(null)}/~product_id=${review.productId}</@ofbizUrl>#reviews"><div class="rating-5_0"></div></a>
                                <div class="cr-product">
                                    <a href="<@ofbizUrl>/product/~category_id=${review.primaryProductCategoryId?default(null)}/~product_id=${review.productId}</@ofbizUrl>">${review.productName?if_exists}</a>
                                </div>
                                <div class="cr-color">
								${review.colorDescription?if_exists}
                                </div>
                            </div>
                        </div>
                        <div class="cr-review">
                            <p>${review.productReview}</p>
                        </div>
                        <div class="cr-date">
                            <p>${review.createdStamp?date}</p>
                        </div>
                        <div class="cr-author">
                            <p>&mdash;${review.nickName}</p>
                        </div>
                    </div></#list>
                </div>
            </div>
            <div class="slideIt-right">
                <#--  <i class="fa fa-chevron-right"></i>  -->
				<img src="<@ofbizScene7Url>/is/image/ActionEnvelope/angleright-large?wid=19&hei=36&fmt=png-alpha</@ofbizScene7Url>" />
            </div>
        </div>
    </div>
	</#if>
</#if>

<div bns-filterurl="${filterUrl?default("")}" class="hidden"></div>

<script src="<@ofbizContentUrl>/html/js/category/categoryResults.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
<link href="<@ofbizContentUrl>/html/css/checkout/cross-sell.css</@ofbizContentUrl>" rel="stylesheet" />

<script>
	var itemsData = $('<div/>').html('${cartStateData.cartItems?default('')}').text();
</script>

<div class="content cross-sell padding-top-xs">
	<div class="container padding-bottom-xs">
		<div class="padding-xxs added">
			<div class="added-left">
				<i class="fa fa-check"></i>
			</div>
			<div class="added-middle">
				<span class="larger-font">
					<#if isDesign?exists && isDesign == true>
					<#if template.templateDescription?has_content>
							${template.templateDescription}
						<#else>
							${product.productName}
						</#if>
					<#else>
						${product.productName}
					</#if>
					(${quantity?number?string["0"]} Quantity)
				</span>
				<span>
					Has been added to your cart
				</span>
			</div>
			<div class="added-right">
				<span>
					<a href="<#if lastVisitedURL?has_content>${lastVisitedURL}<#else><@ofbizUrl>/main</@ofbizUrl></#if>">Continue Shopping</a>
				</span>
				<span>
					or <a href="<@ofbizUrl>/checkout</@ofbizUrl>">Checkout</a>
				</span>
			</div>
		</div>
		<div class="row margin-top-sm savings-and-printing">
			<#assign editUrl = "" />
			<#assign plainItem = priceCalcAttributes.colorsBack?c == "0" && priceCalcAttributes.colorsFront?c == "0" && priceCalcAttributes.isFullBleed?c != 'true' />

			<#if artworkAttributes.scene7ParentId?has_content>
				<#assign projectId = artworkAttributes.scene7ParentId />
				<#assign mainDesign = Static["com.envelopes.scene7.Scene7Helper"].getMainProjectDesign(delegator, artworkAttributes.scene7ParentId) />
				<#if artworkAttributes.isProduct == "true">
					<#assign editUrl = "/product/~category_id=" + product.primaryProductCategoryId?default("null") + "/~product_id=" + product.productId />
				<#else>
					<#assign editUrl = "/product/~designId=" + mainDesign />
				</#if>
			</#if>

			<#if editUrl?has_content>
				<#assign prodUrl><@ofbizUrl>${editUrl}</@ofbizUrl>#${projectId}</#assign>
			<#else>
				<#assign prodUrl><@ofbizUrl>/product/~category_id=${product.primaryProductCategoryId?default("null")}/~product_id=${product.productId}</@ofbizUrl></#assign>
			</#if>


			<#if quantity?number lte 5>
			<div class="columns large-6 savings margin-bottom-sm">
				<h4>Sample Coupon</h4>
				<div class="padding-xs margin-top-xs">
					<div>
						<div>
							<a href="${prodUrl}"><img <#if isDesign?exists && isDesign == true>class="border-gray-1"</#if> src="<#if isDesign?exists && isDesign == true><@ofbizUrl>/scene7Image.png</@ofbizUrl>?id=${scene7DesignId}&amp;setWidth=207&amp;hei=155&amp;setHeight=155<#else>//actionenvelope.scene7.com/is/image/ActionEnvelope/${product.productId}?wid=207&amp;fmt=png-alpha</#if>" alt="${product.productName}" /></a>
						</div>
						<div class="padding-left-xxs padding-right-xxs info">
							<span class="item-name">
								<a href="${prodUrl}"><#if isDesign?exists && isDesign == true>
									<#if template.templateDescription?has_content>
										Samples of ${template.templateDescription}
									<#else>
										Samples of ${product.productName}
									</#if>
								<#else>
									Samples of ${product.productName}
								</#if></a>
							</span>
							<span class="item-benefit">
								You will get a $${quantity?number?string["0"]} coupon to use towards your next purchase.
							</span>
							<a href="<@ofbizUrl>/cart</@ofbizUrl>"><div class="button-regular button-cta cart">View Cart</div></a>
						</div>
					</div>
				</div>
			</div>
			<hr class="hidden-for-large-up" />
			<#elseif nextPriceDifference?exists>
			<div class="columns large-6 savings margin-bottom-sm">
				<h4>Savings Alert!</h4>
				<div class="padding-xs margin-top-xs">
					<div>
						<div>
							<a href="${prodUrl}"><img <#if isDesign?exists && isDesign == true>class="border-gray-1"</#if> src="<#if isDesign?exists && isDesign == true><@ofbizUrl>/scene7Image.png</@ofbizUrl>?id=${scene7DesignId}&amp;setWidth=207&amp;hei=155&amp;setHeight=155<#else>//actionenvelope.scene7.com/is/image/ActionEnvelope/${product.productId}?wid=207&amp;fmt=png-alpha</#if>" alt="${product.productName}" /></a>
						</div>
						<div class="padding-left-xxs padding-right-xxs info">
							<span class="item-name">
								<a href="${prodUrl}"><#if isDesign?exists && isDesign == true>
									<#if template.templateDescription?has_content>
										${template.templateDescription}
									<#else>
										${product.productName}
									</#if>
								<#else>
									${product.productName}
								</#if></a>
							</span>
							<span class="item-benefit">
								Upgrade to ${nextQuantity?if_exists}<br />
								<#if nextPriceDifference?has_content && nextPriceDifference < 0>and save $${nextPriceDifference?abs?string["0.##"]}.<#else> for $${nextPriceDifference?string["0.##"]} more.</#if>
							</span>
							<div class="button-regular button-cta qtyUpgrade" data-qty="${nextQuantity?if_exists}">Yes, Upgrade &amp; Save</div>
						</div>
					</div>
				</div>
			</div>
			<hr class="hidden-for-large-up" />
			</#if>

			<#--
			<#if isItemPrinted?exists && !isItemPrinted>
			<div class="columns large-6 printing margin-bottom-xxs">
				<h4>Add Printing!</h4>
				<div class="padding-xs margin-top-xs">
					<div>
						<div>
							<img src="<#if isDesign?exists && isDesign == true>//actionenvelopew2p.scene7.com/is/agm/ActionEnvelope/${template.scene7TemplateId}?wid=207&amp;hei=155&amp;fmt=png-alpha<#else>//actionenvelope.scene7.com/is/image/ActionEnvelope/${product.productId}?wid=207&amp;hei=155&amp;fmt=png-alpha</#if>" alt="" />
						</div>
						<div class="padding-left-xxs padding-right-xxs info">
							<span class="item-name">
								<#if isDesign?exists && isDesign == true>
									<#if template.templateDescription?has_content>
										${template.templateDescription}
									<#else>
										${product.productName}
									</#if>
								<#else>
									${product.productName}
								</#if>
							</span>
							<span class="item-benefit">
								Upgrade to ${nextQuantity?if_exists}<br />
								<#if nextPriceDifference?has_content && nextPriceDifference < 0>and save $${nextPriceDifference?abs?string["0.##"]}.<#else> for $${nextPriceDifference?string["0.##"]} more.</#if>
							</span>
							<div class="button-regular button-cta qtyUpgrade" data-qty="${nextQuantity?if_exists}">Yes, Upgrade &amp; Save</div>
						</div>
					</div>
				</div>
			</div>
			</#if>
			-->
		</div>

		<#if getMatchingDesigns?has_content && !template.scene7TemplateId?starts_with("8")>
		<hr class="hidden-for-large-up" />
		<div class="item-list">
			<div class="row margin-top-sm">
				<div class="columns small-12 medium-12 large-6 head-left">
					<h4>Other designs to complete your set:</h4>
				</div>
				<div class="columns large-6 show-for-large-up head-right">
					<#--<div class="button-regular button-cta">See More</div>-->
				</div>
			</div>
			<div class="row padding-top-xxs">
				<#list getMatchingDesigns as altDesigns><div class="item-info">
						<a href="<@ofbizUrl>/product/~designId=${altDesigns.scene7TemplateId}</@ofbizUrl>"><img class="border-gray-1" src="//actionenvelopew2p.scene7.com/is/agm/ActionEnvelope/${altDesigns.scene7TemplateId}?hei=155&amp;fmt=png-alpha" alt="<#if altDesigns.templateDescription?has_content>${altDesigns.templateDescription}<#else>${altDesigns.scene7TemplateId}</#if>" /></a>
						<div>
							<span class="item-name">
								<a href="<@ofbizUrl>/product/~designId=${altDesigns.scene7TemplateId}</@ofbizUrl>"><#if altDesigns.templateDescription?has_content>
									${altDesigns.templateDescription}
								<#else>
									${altDesigns.scene7TemplateId}
								</#if></a>
							</span>
							<span class="item-color">
								${altDesigns.quickDesc?if_exists}
							</span>
							<div class="row no-margin padding-top-xxs">
								<div class="rating-5_0"></div>
								<span class="item-cost">${altDesigns.printPriceDescription?if_exists}</span>
							</div>
						</div>
					</div></#list>
			</div>
			<div class="row margin-top-sm">
				<div class="hidden-for-large-up columns small-12 medium-12">
					<div class="button-regular button-cta">See More</div>
				</div>
			</div>
			<hr class="hidden-for-large-up" />
		</div>
		</#if>

		<#if matchingProducts?has_content>
		<hr class="hidden-for-large-up" />
		<div class="item-list">
			<div class="row margin-top-sm">
				<div class="columns small-12 medium-12 large-6 head-left">
					<h4>Recommended Products</h4>
				</div>
				<div class="columns large-6 show-for-large-up head-right">
					<#--<div class="button-regular button-cta">See More</div>-->
				</div>
			</div>
			<div class="row padding-top-xxs">
				<#list matchingProducts as altProduct><div class="item-info">
						<a href="<@ofbizUrl>/product/~category_id=${altProduct.primaryProductCategoryId}/~product_id=${altProduct.variantProductId}</@ofbizUrl>"><img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${altProduct.variantProductId}?wid=207&amp;hei=155&amp;fmt=png-alpha" alt="${altProduct.colorDescription}" /></a>
						<div>
							<span class="item-name">
								<a href="<@ofbizUrl>/product/~category_id=${altProduct.primaryProductCategoryId}/~product_id=${altProduct.variantProductId}</@ofbizUrl>">${altProduct.productName}</a>
							</span>
							<span class="item-color">
								${altProduct.colorDescription}
							</span>
							<div class="row no-margin padding-top-xxs">
								<div class="rating-5_0"></div>
								<span class="item-cost">From ${altProduct.plainPriceDescription?if_exists}</span>
							</div>
						</div>
					</div></#list>
			</div>
			<div class="row margin-top-sm">
				<div class="hidden-for-large-up columns small-12 medium-12">
					<div class="button-regular button-cta">See More</div>
				</div>
			</div>
			<hr class="hidden-for-large-up" />
		</div>
		</#if>
	</div>
</div>

<script type="text/javascript" src="<@ofbizContentUrl>/html/js/checkout/jquery.envelopes.checkout.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/checkout/cross-sell.js</@ofbizContentUrl>"></script>

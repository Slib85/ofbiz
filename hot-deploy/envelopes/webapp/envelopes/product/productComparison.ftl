<link href="<@ofbizContentUrl>/html/css/product/productComparison.css</@ofbizContentUrl>" rel="stylesheet" />

<div class="content">
	<div class="jqs-productComparisonContainer productComparisonContainer">
		<div class="jqs-productComparisonSpecs productComparisonSpecs hidden">
			<table class="productComparisonInfoTable">
				<tr>
					<th colspan="${FEATURES_TO_SHOW?size}">Specs</th>
				</tr>
				<#list FEATURES_TO_SHOW as feature>
				<tr>
					<td>
						<div>${Static["com.envelopes.product.ProductHelper"].getFeatureDescByType(delegator, feature)}</div>
					</td>
				</tr>
				</#list>
			</table>
		</div>
		<div class="jqs-productComparisonPricing productComparisonPricing hidden">
			<table class="productComparisonInfoTable">
				<tr>
					<th colspan="${products?size}">Pricing</th>
				</tr>
				<tr>
					<td>
						<div>Plain Price</div>
					</td>
				</tr>
				<tr>
					<td>
						<div>Print Price</div>
					</td>
				</tr>
			</table>
		</div>
		<div class="productComparisonProductInfo">
			<table class="productComparisonInfoTable">
				<tr>
					<#list products as product>
					<td class="productComparisonItemHeader">
						<div class="text-center">
							<a href="<@ofbizUrl>${product.getUrl(false)?default("")?replace("&#x2f;", "/")?replace("&#x7e;", "~")?replace("&#x3d;", "=")}</@ofbizUrl>"><img src="https://actionenvelope.scene7.com/is/image/ActionEnvelope/${product.getId()}?wid=150&amp;hei=96&amp;fmt=png-alpha" alt="${product.getName()}" /></a>
							<div class="margin-top-xxs"><a href="<@ofbizUrl>${product.getUrl(false)?default("")?replace("&#x2f;", "/")?replace("&#x7e;", "~")?replace("&#x3d;", "=")}</@ofbizUrl>">${product.getName()}</a></div>
							<div class="rating-${product.getRating()} margin-top-xxs"></div>
						</div>
					</td>
					</#list>
				</tr>
			</table>
			<table class="productComparisonInfoTable jqs-productComparisonSpecsInfo">
				<tr>
					<th colspan="${products?size}">&nbsp;</th>
				</tr>
				<#list FEATURES_TO_SHOW as feature>
				<tr>
					<#list products as product>
					<td>
						<div>${product.getFeatures().get(feature)?default("&nbsp;")}</div>
					</td>
					</#list>
				</tr>
				</#list>
			</table>
			<table class="productComparisonInfoTable jqs-productComparisonPricingInfo">
				<tr>
					<th colspan="${products?size}">&nbsp;</th>
				</tr>
				<tr>
					<#list products as product>
					<td>
						<div>from ${product.getPlainPriceDescription()}</div>
					</td>
					</#list>
				</tr>
				<tr>
				<#list products as product>
					<td>
						<div>from ${product.getPrintPriceDescription()}</div>
					</td>
				</#list>
				</tr>
			</table>
		</div>
	</div>
</div>

<script type="text/javascript">
	$('.jqs-productComparisonSpecs td').each(function(i) {
		$(this).css('height', $($($('.jqs-productComparisonSpecsInfo tr')[i + 1]).find('td')[0]).outerHeight() + 'px');
	});
	$('.jqs-productComparisonPricing td').each(function(i) {
		$(this).css('height', $($($('.jqs-productComparisonPricingInfo tr')[i + 1]).find('td')[0]).outerHeight() + 'px');
	});

	var totalImages = $('.productComparisonInfoTable img').length;

	function adjustLeftContent() {
		$('.jqs-productComparisonSpecs').css('top', ($('.jqs-productComparisonSpecsInfo').offset().top - $('.jqs-productComparisonContainer').offset().top) + 'px').removeClass('hidden');
		$('.jqs-productComparisonPricing').css('top', ($('.jqs-productComparisonPricingInfo').offset().top - $('.jqs-productComparisonContainer').offset().top) + 'px').removeClass('hidden');
	}

	adjustLeftContent();

	$('.productComparisonInfoTable img').on('load', function() {
		if (--totalImages == 0) {
			adjustLeftContent();
		}
	});
</script>

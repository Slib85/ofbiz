<link href="<@ofbizContentUrl>/html/css/checkout/shipping.css</@ofbizContentUrl>" rel="stylesheet">
<#assign success = shippingOptions.success?default('false')?c/>
<#if success == 'true'>
	<div class="shippingRateList">
		<div class="row no-margin margin-top-xxs text-left">
			<span>
				Transit times are in business days.
			</span>
		</div>
		<#list shippingOptions.estimates as shippingOption>
			<div class="row no-margin margin-top-xxs">
				<div class="horizontal-input">
					<div>
						<input id="${shippingOption.method}" data-page-type="${pageType}" data-cost="${shippingOption.cost}" data-party-id="${shippingOption.carrierPartyId}" class="jqs-shipping-method no-margin" name="shipping_method" type="radio" value="${shippingOption.method}" <#if shippingOption.method == shippingOptions.shipmentMethodTypeId>checked="checked"</#if>/>
					</div>
					<div>
						<span class="margin-left-xxs text-left">
							<label for="${shippingOption.method}">
								<#if shippingOption.estDeliveryDate?has_content && webSiteId?default('envelopes') != 'folders'>
									${shippingOption.genericDesc} <span class="price-color"><#if shippingOption.cost gte 0 && getShippingDiscount == shippingOption.cost>FREE<#elseif shippingOption.cost lt 0>N/A<#else><@ofbizCurrency amount=shippingOption.cost/></#if></span>
										<div class="estDeliveryDate">${shippingOption.estDeliveryDate}</div>
								<#else>
									${shippingOption.genericDesc} <span class="price-color"><#if shippingOption.cost gte 0 && getShippingDiscount == shippingOption.cost>FREE<#elseif shippingOption.cost lt 0>N/A<#else><@ofbizCurrency amount=shippingOption.cost/></#if></span>
								</#if>
							</label>
						</span>
					</div>
				</div>
			</div>
		</#list>
	</div>
<#else>
<#if pageType == 'checkout'>
<div style="display: table-cell;width: 500px;padding: 10px 0px 10px 15px;">
	Please fill out shipping information above to calculate shipping rates.
</div>
</#if>
<#--TODO - If UPS goes down, we won't get the shipping methods/rates-->
</#if>


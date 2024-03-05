<link href="<@ofbizContentUrl>/html/css/checkout/shipping.css</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}" rel="stylesheet">
<#assign success = shippingOptions.success?default('false')?c/>

<#if success == 'true'>
	<div class="shippingRateList">
		<div class="foldersRow noMargin marginTop10 textLeft">
			<span>
				Transit times are in business days.
			</span>
		</div>
		<#list shippingOptions.estimates as shippingOption>
			<div class="foldersTabularRow noMargin marginTop10 shippingOptionEstimates">
				<div class="noPadding textCenter">
					<input id="${shippingOption.method}" data-page-type="${pageType}" data-cost="${shippingOption.cost}" data-party-id="${shippingOption.carrierPartyId}" class="jqs-shipping-method noMargin" name="shipping_method" type="radio" value="${shippingOption.method}" <#if shippingOption.method == shippingOptions.shipmentMethodTypeId>checked="checked"</#if>/>
				</div>
				<div class="textLeft noPadding">
					<label for="${shippingOption.method}">
						${shippingOption.genericDesc} <span class="ftc-orange"><#if shippingOption.cost?string == '0' || getShippingDiscount == shippingOption.cost>FREE<#else><@ofbizCurrency amount=shippingOption.cost/></#if></span>
					</label>
				</div>
			</div>
		</#list>
	</div>
<#else>
<#if pageType == 'checkout'>
<div style="display: table-cell;width: 500px; padding-top:10px" >
    <div class="row margin-left-xxs">
        <input name="shippingPostalCode" style="display: inline-block;max-width: 200px" type="text" value="" placeholder="Shipping Postal Code" />
        <div id="jqs-show-shipping" class="button-regular button-non-cta" style="display: inline-block;padding: 9px 0 8px;margin-left:10px;width:100px">Show</div>
    </div>
</div>
</#if>
<#--TODO - If UPS goes down, we won't get the shipping methods/rates-->
</#if>


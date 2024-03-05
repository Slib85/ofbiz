<link href="<@ofbizContentUrl>/html/css/checkout/shipping-redesign.css</@ofbizContentUrl>" rel="stylesheet">
<#assign success = shippingOptions.success?default('false')?c/>
<#if success == 'true'>
	<div class="shippingRateList">
		<div class="row no-margin margin-top-xs text-center">
			<span class="bnc_shipping_options_header">
				<span>Transit times</span> <span>are in business days.</span>
			</span>
		</div>
		<#list shippingOptions.estimates as shippingOption>
			<div class="row no-margin margin-top-xxs">
				<div class="bnc_shipping_options">
                    <label class="bnc_button" for="${shippingOption.method}">
                        <input id="${shippingOption.method}" data-page-type="${pageType}" data-cost="${shippingOption.cost}" data-party-id="${shippingOption.carrierPartyId}" class="jqs-shipping-method no-margin" name="shipping_method" type="radio" value="${shippingOption.method}" <#if shippingOption.method == shippingOptions.shipmentMethodTypeId>checked="checked"</#if>/>

                        <span class="section_selector">
                            <#if shippingOption.estDeliveryDate?has_content && webSiteId?default('envelopes') != 'folders'>
                                <span class="bnc_shipping_cost"><#if shippingOption.cost gte 0 && getShippingDiscount == shippingOption.cost>FREE<#elseif shippingOption.cost lt 0>N/A<#else><@ofbizCurrency amount=shippingOption.cost/></#if></span>
                                <span>&nbsp; &#124; &nbsp;</span>
                                <span class="bnc_shipping_selection">${shippingOption.genericDesc}</span>
                        </span>

                        <span class="bnc_estimate_delivery_date">
                            <span class="estDeliveryDate">${shippingOption.estDeliveryDate}</span>
                        </span>

                            <#else>
                                ${shippingOption.genericDesc} <span class="price-color"><#if shippingOption.cost gte 0 && getShippingDiscount == shippingOption.cost>FREE<#elseif shippingOption.cost lt 0>N/A<#else><@ofbizCurrency amount=shippingOption.cost/></#if></span>
                            </#if>
                        </span>
                        <span class="bnc_btn_redesign"></span>
                    </label>
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
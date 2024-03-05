<div class="verticalOrderDetails">
	<div class="row vodHeader" style="display: table; width: 100%;">
		<div style="display: table-cell; vertical-align: middle;">
			<i class="fa fa-shopping-cart"></i> Order Summary
		</div>
		<div style="display: table-cell; vertical-align: middle;">
			<a href="<@ofbizUrl>/cart</@ofbizUrl>">Edit</a>
		</div>
	</div>
	<div class="verticalOrderDetailsCartContent">
		<#list orderInfo.lineItems as lineItem>
		<div class="orderItem">
			<div class="no-padding text-center item-image orderItemImage">
				<#if lineItem.envArtworkAttributes.scene7DesignId?has_content>
				<img class="border-gray-1" src="<@ofbizUrl>/scene7Image.png</@ofbizUrl>?id=${lineItem.envArtworkAttributes.scene7DesignId}&setWidth=100&setSide=0" alt="${lineItem.productName}" />
				<img class="border-gray-1" src="<@ofbizUrl>/scene7Image.png</@ofbizUrl>?id=${lineItem.envArtworkAttributes.scene7DesignId}&setWidth=100&setSide=1" alt="${lineItem.productName}" />
				<#else>
				<img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${lineItem.productId}?wid=100&amp;fmt=png-alpha" alt="${lineItem.productName}" />
				</#if>
			</div>
			<div class="no-padding padding-left-xxs item-info orderItemInfo">
				<div style="font-weight: bold; color: #00a4e4;"><#if lineItem.quantity lte 5 && lineItem.hasSample?exists && lineItem.hasSample != "N">Samples of </#if>${lineItem.productName}</div>
				<div>${lineItem.productSize?if_exists}</div>
				<div>${lineItem.productColor}</div>
				<div style="font-weight: bold;">${lineItem.quantity} Qty: <@ofbizCurrency amount=lineItem.totalPrice/></div>
				<div>
					<span>
					<#if (lineItem.printing?size > 0)>Printing:
						<#list lineItem.printing as printing><#if printing_index != 0>,</#if> ${printing}</#list>
					<#else>
						No Printing
					</#if>
					</span>
				</div>
			</div>
		</div>
		</#list>
	</div>
	<div class="row price-list padding-bottom-xs padding-top-xxs margin-top-xxs padding-left-xs padding-right-xs" style="border-top: 2px solid #dadada;">
		<div class="left padding-left-xxs">
			<div>Subtotal:</div>
			<div>Sales Tax:</div>
			<div>Shipping:</div>
			<#list orderInfo.discounts.entrySet() as entry>
			<div>${entry.key?default('Discount')}</div>
			</#list>
			<div class="cartTotal margin-top-xxs">Total:</div>
		</div>
		<div class="right padding-right-xxs text-right" style="font-weight: bold;">
			<div><@ofbizCurrency amount=orderInfo.subTotal/></div>
			<div><@ofbizCurrency amount=orderInfo.taxTotal/></div>
			<div><#if orderInfo.shippingDetails.shippingPostalCode?exists><@ofbizCurrency amount=orderInfo.shipTotal/><#else>--</#if></div>
			<#list orderInfo.discounts.entrySet() as entry>
			<div><@ofbizCurrency amount=entry.value/></div>
			</#list>
			<div class="cartTotal margin-top-xxs"><@ofbizCurrency amount=orderInfo.grandTotal/></div>
		</div>
	</div>
	<div class="row padding-left-xxs padding-right-xxs verticalOrderDetailsCoupon">
		<div class="coupon-response alert-box hidden"></div>
		<input name="couponCode" data-page-type="checkout" type="text" value="" placeholder="<#if orderInfo.enteredPromos?has_content>Applied ${orderInfo.enteredPromos}<#else>Enter Code</#if>" />
		<div id="jqs-apply-coupon" class="button-regular button-non-cta">Apply Code</div>
	</div>
</div>
